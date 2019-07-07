package de.degra.deathspawn.models

import com.sk89q.worldguard.protection.regions.ProtectedRegion
import org.bukkit.Location

data class DeathspawnEntry (val id: String, var zone: ProtectedRegion, var revLoc: Location)