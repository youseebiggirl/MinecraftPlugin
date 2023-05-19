package ru.remsoftware.managers

import org.bukkit.World
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import ru.remsoftware.Skills
import ru.remsoftware.config.HomeConfigLoader
import ru.remsoftware.config.HomeModel
import ru.remsoftware.config.TeleportConfigLoader
import ru.remsoftware.config.TeleportPointsModel
import ru.remsoftware.utils.PluginLogger.log
import java.util.UUID

class ConfigManager(
    private val homeConfigLoader: HomeConfigLoader = Skills.homeConfigLoader,
    private val teleportConfigLoader: TeleportConfigLoader = Skills.teleportConfigLoader,
    private val homeConfigFile: FileConfiguration = homeConfigLoader.getConfig(),
    private val teleportConfigFile: FileConfiguration = teleportConfigLoader.getConfig(),
) {
    companion object {
        val homeMemory: MutableMap<UUID, HomeModel> = HashMap()
        val teleportPointsMemory: MutableMap<Long, TeleportPointsModel>  = HashMap()
    }



    fun loadTeleportPointsFromConfig() {
        for (id: String in teleportConfigFile.getConfigurationSection("")?.getKeys(false)!!) {
            val tpId: Long = id.toLong()
            val world: String = teleportConfigFile.getString("$id.world")!!
            val x: Double = teleportConfigFile.getDouble("$id.X")
            val y: Double = teleportConfigFile.getDouble("$id.Y")
            val z: Double = teleportConfigFile.getDouble("$id.Z")
            val yaw: Double = teleportConfigFile.getDouble("$id.yaw")
            val pitch: Double = teleportConfigFile.getDouble("$id.pitch")
            val loadTeleportPoints = TeleportPointsModel(world ,x, y, z, yaw, pitch)
            teleportPointsMemory[tpId] = loadTeleportPoints
        }
    }

    fun saveTeleportPointsToConfig() {
        for (id: Long in teleportPointsMemory.keys) {
            val stats: TeleportPointsModel = teleportPointsMemory[id]!!
            teleportConfigFile.set("$id.world", stats.world)
            teleportConfigFile.set("$id.X", stats.x)
            teleportConfigFile.set("$id.Y", stats.y)
            teleportConfigFile.set("$id.Z", stats.z)
            teleportConfigFile.set("$id.yaw", stats.yaw)
            teleportConfigFile.set("$id.pitch", stats.pitch)
        }
    }

    fun loadHomeFromConfig() {
        for (id: String in homeConfigFile.getConfigurationSection("")?.getKeys(false)!!) {
            val uuid: UUID = UUID.fromString(id)
            val x: Double = homeConfigFile.getDouble("$id.X")
            val y: Double = homeConfigFile.getDouble("$id.Y")
            val z: Double = homeConfigFile.getDouble("$id.Z")
            val yaw: Double = homeConfigFile.getDouble("$id.yaw")
            val pitch: Double = homeConfigFile.getDouble("$id.pitch")
            val isSetTeleportPoint: Boolean = homeConfigFile.getBoolean("$id.is_set_teleport_point")
            val loadHome = HomeModel(x, y, z, yaw, pitch, isSetTeleportPoint)
            homeMemory[uuid] = loadHome
        }

    }

    fun saveHomeToConfig() {
        for (uuid: UUID in homeMemory.keys) {
            val id = uuid.toString()
            val stats: HomeModel = homeMemory[uuid]!!
            homeConfigFile.set("$id.X", stats.x)
            homeConfigFile.set("$id.Y", stats.y)
            homeConfigFile.set("$id.Z", stats.z)
            homeConfigFile.set("$id.yaw", stats.yaw)
            homeConfigFile.set("$id.pitch", stats.pitch)
            homeConfigFile.set("$id.is_set_teleport_point", stats.isSetTeleportPoint)
        }
    }


    fun createNewHomeConfig(player: Player) {
        homeMemory[player.uniqueId] = HomeModel(1.0, 1.0, 1.0, 1.0, 1.0, false)
    }
    fun createNewTeleportPoint(id: Long) {
        teleportPointsMemory[id] = TeleportPointsModel("default", 1.0, 1.0, 1.0, 1.0,1.0)
    }

    fun getPlayerHome(uuid: UUID) = homeMemory[uuid]
    fun getTeleportPoints(id: Long) = teleportPointsMemory[id]
}
