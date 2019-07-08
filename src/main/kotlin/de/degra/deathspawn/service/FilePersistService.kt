package de.degra.deathspawn.service

import com.google.gson.Gson
import de.degra.deathspawn.dto.DeathSpawnEntity
import de.degra.deathspawn.dto.DeathSpawnEntityContainer
import de.degra.deathspawn.dto.PositionDTO
import de.degra.deathspawn.models.DeathspawnEntry
import org.bukkit.Bukkit
import org.bukkit.Location
import java.io.*


object FilePersistService : IFilePersistService {

    private var filePath: String? = null
    private val mapper = Gson()

    override fun readFromFile(): MutableList<DeathspawnEntry> {
        val entries = mutableListOf<DeathspawnEntry>()

        try {
            FileReader(getFilePath()).use { reader ->
                BufferedReader(reader).use { br ->
                    val containerJson = br.readText()
                    val container =
                        mapper.fromJson<DeathSpawnEntityContainer>(containerJson, DeathSpawnEntityContainer::class.java)

                    container.entries.forEach {
                        val deathspawnEntry = mapEntityToEntry(it)
                        entries.add(deathspawnEntry)
                    }
                }
            }
        } catch (e: IOException) {
            System.err.format("IOException: %s%n", e)
        }

        return entries
    }

    override fun saveToFile(list: MutableList<DeathspawnEntry>) {
        val container = DeathSpawnEntityContainer()

        list.stream()
            .forEach {
                val deathSpawnEntity = mapEntryToEntity(it)
                container.entries.add(deathSpawnEntity)
            }
        val containerJson = mapper.toJson(container)

        try {
            FileWriter(getFilePath(), true).use { writer ->
                BufferedWriter(writer).use { bw ->
                    bw.write(containerJson)
                }
            }
        } catch (e: IOException) {
            System.err.format("IOException: %s%n", e)
        }
    }

    private fun getFilePath(): String {
        if(this.filePath == null){
           this.filePath = Bukkit.getPluginManager().getPlugin("Deathspawn")!!.dataFolder.absolutePath + "/deathspawns.json"
        }
         return this.filePath!!
    }

    private fun mapEntryToEntity(entry: DeathspawnEntry): DeathSpawnEntity {
        val positionDTO = PositionDTO(entry.revLoc)
        return DeathSpawnEntity(entry.id, entry.deathZoneId, positionDTO)
    }

    private fun mapEntityToEntry(entity: DeathSpawnEntity): DeathspawnEntry {
        val world = Bukkit.getWorld(entity.position.worldId)
        val location = Location(
            world,
            entity.position.xPos,
            entity.position.yPos,
            entity.position.zPos,
            entity.position.pitch,
            entity.position.yaw
        )
        return DeathspawnEntry(entity.id, entity.zoneId, location)
    }

}