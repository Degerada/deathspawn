package de.degra.deathspawn

import de.degra.deathspawn.listener.RespawnListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class  Deathspawn : JavaPlugin() {

    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(RespawnListener(), this)
    }
}