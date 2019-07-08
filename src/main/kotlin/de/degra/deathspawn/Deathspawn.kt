package de.degra.deathspawn

import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.protection.flags.LocationFlag
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException
import de.degra.deathspawn.listener.RespawnListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level

class Deathspawn : JavaPlugin() {

    companion object {
        val DEATH_RESPAWN_FLAG = LocationFlag("dspawn")
    }

    override fun onLoad() {
        val registry = WorldGuard.getInstance().flagRegistry
        try {
            // register our flag with the registry
            registry.register(DEATH_RESPAWN_FLAG)
        } catch (e: FlagConflictException) {
            logger.log(Level.SEVERE, e.localizedMessage)
        }
    }

    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(RespawnListener(), this)
    }
}
