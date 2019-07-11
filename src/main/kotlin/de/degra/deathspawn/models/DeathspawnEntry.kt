package de.degra.deathspawn.models

import org.bukkit.Location

data class DeathspawnEntry (val tableId: Int, val playerId: String, var revLoc: Location, var state: DeathspawnState)