package de.degra.deathspawn.entity

import com.sk89q.squirrelid.util.UUIDs

data class DeathPointEntity(val id: Int, val playerId: UUIDs, var respawnPoint: PositionDTO)