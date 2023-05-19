package ru.remsoftware.config

import org.bukkit.World

data class TeleportPointsModel(
    var world: String,
    var x: Double,
    var y: Double,
    var z: Double,
    var yaw: Double,
    var pitch: Double,
)


