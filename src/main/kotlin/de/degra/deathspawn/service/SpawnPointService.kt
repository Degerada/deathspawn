package de.degra.deathspawn.service

import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldguard.WorldGuard
import de.degra.deathspawn.Deathspawn.Companion.DEATH_RESPAWN_FLAG
import org.bukkit.Location


object SpawnPointService {

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
}