package de.degra.deathspawn

import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.protection.flags.LocationFlag
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException
import de.degra.deathspawn.listener.RespawnListener
import de.degra.deathspawn.service.db.QueryService
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
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
        if (this.config["sql.enable"] == true) {
            this.openConnection()
        }
    }

    @Throws(SQLException::class, ClassNotFoundException::class)
    fun openConnection() {
        var connection: Connection? = null
        if (!(connection == null || connection.isClosed)) {
            return
        }

        synchronized(this) {
            if (!(connection == null || connection!!.isClosed)) {
                return
            }
            Class.forName("com.mysql.jdbc.Driver")
            connection = DriverManager.getConnection(
                ("jdbc:mysql://" + this.config["sql.address"] + ":" + this.config["sql.port"] + "/" + this.config["sql.database"]),
                this.config["sql.username"].toString(), this.config["sql.password"].toString()
            )
            QueryService.connection = connection as Connection
            QueryService.isEnabled = true
            QueryService.createTable()
        }
    }
}
