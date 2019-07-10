package de.degra.deathspawn.service

import com.sk89q.squirrelid.util.UUIDs
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldguard.WorldGuard
import de.degra.deathspawn.Deathspawn.Companion.DEATH_RESPAWN_FLAG
import de.degra.deathspawn.entity.PositionDTO
import org.bukkit.Bukkit
import org.bukkit.Location


object SpawnPointService {

    var respawnMap: Map<UUIDs, PositionDTO> = mapOf()

    fun getRespawnPos(deathLocation: Location): Location? {
        val container = WorldGuard.getInstance().platform.regionContainer
        val query = container.createQuery()
        val set = query.getApplicableRegions(BukkitAdapter.adapt(deathLocation))

        set.forEach { region ->
            run {
                val resFlag = region.getFlag(DEATH_RESPAWN_FLAG)
                if (resFlag != null) {
                    return BukkitAdapter.adapt(resFlag)
                }
            }
        }
        return null
    }

    private fun mapPositionToLocation(position: PositionDTO): Location {
        val world = Bukkit.getWorld(position.worldId)
        val location = Location(
            world,
            position.xPos,
            position.yPos,
            position.zPos,
            position.pitch,
            position.yaw
        )
        return location
    }

    private fun mapLocationToPosition(location: Location): PositionDTO {
        return PositionDTO(location)
    }
}