package de.degra.deathspawn.service

import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldguard.WorldGuard
import de.degra.deathspawn.Deathspawn.Companion.DEATH_RESPAWN_FLAG
import de.degra.deathspawn.entity.PositionDTO
import de.degra.deathspawn.models.DeathspawnEntry
import de.degra.deathspawn.models.DeathspawnState
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.*


object SpawnPointService {

    private var playerRespawns: Map<UUID, DeathspawnEntry> = mapOf()

    fun getRespawnPos(player: Player, deathLocation: Location): Location? {
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
        val entry = playerRespawns[player.uniqueId]
        if(entry != null && entry.state != DeathspawnState.ToDelete){
            playerRespawns.getOrElse(player.uniqueId).state = DeathspawnState.ToDelete
            return entry.revLoc
        }
        return player.bedSpawnLocation
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