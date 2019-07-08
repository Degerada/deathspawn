package de.degra.deathspawn.service

import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.protection.regions.RegionContainer
import de.degra.deathspawn.models.DeathspawnEntry
import org.bukkit.Location


object SpawnPointService {

    private val regionContainer: RegionContainer by lazy { WorldGuard.getInstance().platform.regionContainer }

    var respawnZones: MutableList<DeathspawnEntry> = FilePersistService.readFromFile()

    fun addRespawnZone(location: Location, zoneName: String): DeathspawnEntry {
        var nextId = if (respawnZones.size > 0) {
            respawnZones.maxBy { it.id }!!.id + 1
        } else {
            1
        }
        val deathspawnEntry = DeathspawnEntry(nextId, zoneName, location)
        addRespawnZone(deathspawnEntry)
        return deathspawnEntry
    }

    fun addRespawnZone(newEntry: DeathspawnEntry) {
        respawnZones.add(newEntry)

        refreshZones(respawnZones)
    }

    fun updateRespawnZone(updatedEntry: DeathspawnEntry) {
        respawnZones.removeIf { it.deathZoneId == updatedEntry.deathZoneId }
        respawnZones.add(updatedEntry)

        refreshZones(respawnZones)
    }

    fun deleteRespawnZone(id: Int) {
        respawnZones.removeIf { it.id == id }

        refreshZones(respawnZones)
    }

    fun deleteRespawnZone(entry: DeathspawnEntry) {
        respawnZones.remove(entry)

        refreshZones(respawnZones)
    }

    fun refreshZones(respawnZones: MutableList<DeathspawnEntry>) {
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
                .filter { it.deathZoneId == region.id }
                .findAny()

            if (zone.isPresent) {
                return zone.get().revLoc
            }
        }
        return null
    }
}