package de.degra.deathspawn.listener

import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class RespawnListener : Listener {

    @EventHandler
    fun resetRespawn(event: PlayerDeathEvent) {
        val player = event.entity
        //val deathLocation = player.location.block
        val location = Location(player.world, 220.0, 69.0, 167.0)
        player.setBedSpawnLocation(location, true)
    }
}