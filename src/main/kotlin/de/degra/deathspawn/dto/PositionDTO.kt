package de.degra.deathspawn.dto

import org.bukkit.Location
import java.util.*

data class PositionDTO(
    val worldId: UUID, val xPos: Double, val yPos: Double, val zPos: Double,
    val pitch: Float, val yaw: Float
) {
    constructor(location: Location) : this(location.world.uid, location.x, location.y, location.z, location.pitch, location.yaw)
}