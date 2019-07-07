package de.degra.deathspawn.listener

import de.degra.deathspawn.service.SpawnPointService
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class RespawnListener : Listener {

    @EventHandler
    fun resetRespawn(event: PlayerDeathEvent) {
        val player = event.entity
        val deathLocation = player.location

        val respawnLocation = SpawnPointService.getRespawnPos(deathLocation)
        if (respawnLocation == null) {
            return
        }

        player.setBedSpawnLocation(respawnLocation, true)

    }
}