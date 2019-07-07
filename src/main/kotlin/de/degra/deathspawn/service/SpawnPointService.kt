package de.degra.deathspawn.service

import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.protection.regions.RegionContainer
import de.degra.deathspawn.models.DeathspawnEntry
import org.bukkit.Location


object SpawnPointService {

    private val regionContainer: RegionContainer = WorldGuard.getInstance().platform.regionContainer

    var respawnZones: MutableList<DeathspawnEntry> = FilePersistService.readFromFile()

    fun addRespawnZone(newEntry: DeathspawnEntry) {
        respawnZones.plus(newEntry)

        FilePersistService.saveToFile(respawnZones)
        this.respawnZones = FilePersistService.readFromFile()
    }

    fun updateRespawnZone(updatedEntry: DeathspawnEntry) {
        //TODO: UPDATE

        FilePersistService.saveToFile(respawnZones)
        this.respawnZones = FilePersistService.readFromFile()
    }

    fun deleteRespawnZone(entry: DeathspawnEntry) {
        respawnZones.minus(entry)

        FilePersistService.saveToFile(respawnZones)
        this.respawnZones = FilePersistService.readFromFile()
    }

    /**
     * Returns null if no appropriate revLocation was found
     */
    fun getRespawnPos(deathLocation: Location): Location? {

        for (region in regionContainer[BukkitAdapter.adapt(deathLocation.world)]!!
            .getApplicableRegions(BukkitAdapter.asBlockVector(deathLocation))) {
            val zone = respawnZones
                .stream()
                .filter { it.zone.id == region.id }
                .findAny()

            if(zone.isPresent){
                return zone.get().revLoc
            }
        }
        return null
    }
}