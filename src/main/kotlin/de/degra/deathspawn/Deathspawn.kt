package de.degra.deathspawn

import de.degra.deathspawn.listener.RespawnListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class  Deathspawn : JavaPlugin() {

    companion object {
        private var inst: Deathspawn = null!!

        fun getInst(): Deathspawn {
            return inst
        }
    }

    override fun onEnable() {
        inst = this;

        Bukkit.getPluginManager().registerEvents(RespawnListener(), this)
    }
}