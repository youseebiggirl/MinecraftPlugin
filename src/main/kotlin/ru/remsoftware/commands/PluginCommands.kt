package ru.remsoftware.commands

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import ru.remsoftware.Skills
import ru.remsoftware.Skills.Companion.configManager
import ru.remsoftware.config.HomeModel
import ru.remsoftware.managers.ConfigManager
import ru.remsoftware.utils.ChatUtils.color
import ru.remsoftware.utils.PluginLogger
import kotlin.random.Random

object PluginCommands : TabExecutor {

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String>? = null

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (sender !is Player) {
            PluginLogger.log("Command can't be executed from CONSOLE!")
            return true
        }
        var target = command.name.lowercase()
        when (target) {
            "home" -> {
                val player: Player = sender
                val playerStats: HomeModel? = configManager.getPlayerHome(player.uniqueId)
                if (playerStats != null) {
                    return if (playerStats.isSetTeleportPoint != false) {
                        val world: World = player.world
                        val tpLocation = Location(
                            world,
                            playerStats.x,
                            playerStats.y,
                            playerStats.z,
                            playerStats.yaw.toFloat(),
                            playerStats.pitch.toFloat()
                        )
                        player.sendMessage(color("&6Телепортация..."))
                        player.teleport(tpLocation)
                        true

                    } else {
                        player.sendMessage(color("&cТочка дома не установлена! Для установки напишите /sethome"))
                        true
                    }
                }
            }

            "sethome" -> {
                val player: Player = sender
                val playerStats: HomeModel? = configManager.getPlayerHome(player.uniqueId)
                val x: Double = player.location.x
                val y: Double = player.location.y
                val z: Double = player.location.z
                val yaw: Float = player.location.yaw
                val pitch: Float = player.location.pitch

                if (playerStats != null) {
                    playerStats.x = x
                    playerStats.y = y
                    playerStats.z = z
                    playerStats.yaw = yaw.toDouble()
                    playerStats.pitch = pitch.toDouble()
                    playerStats.isSetTeleportPoint = true
                    player.sendMessage(color("&6Точка телепортации уставновлена"))
                    return true
                }
            }

            "arena" -> {
                val player: Player = sender
                var tpCount: Long = ConfigManager.teleportPointsMemory.count().toLong()
                if (player.isOp) {
                    if (args.size != 0 && !args[0].equals("help", ignoreCase = true)) {
                        when (args[0].lowercase()) {
                            "setspawn" -> {
                                configManager.createNewTeleportPoint(tpCount)
                                val world: String = player.location.world.name
                                val x: Double = player.location.x
                                val y: Double = player.location.y
                                val z: Double = player.location.z
                                val yaw: Float = player.location.yaw
                                val pitch: Float = player.location.pitch
                                if (tpCount == 0L) {
                                    val teleportStats = configManager.getTeleportPoints(0)
                                    if (teleportStats != null) {
                                        teleportStats.world = world
                                        teleportStats.x = x
                                        teleportStats.y = y
                                        teleportStats.z = z
                                        teleportStats.yaw = yaw.toDouble()
                                        teleportStats.pitch = pitch.toDouble()
                                        player.sendMessage(color("&aВы установили одну из точек спавна"))
                                        return true
                                    }
                                } else {
                                    val teleportStats = configManager.getTeleportPoints(tpCount)
                                    if (teleportStats != null) {
                                        teleportStats.world = world
                                        teleportStats.x = x
                                        teleportStats.y = y
                                        teleportStats.z = z
                                        teleportStats.yaw = yaw.toDouble()
                                        teleportStats.pitch = pitch.toDouble()
                                        player.sendMessage(color("&aВы установили одну из точек спавна"))
                                        return true
                                    }
                                }

                            }

                            "tp" -> {
                                var id: Long = Random.nextLong(0, tpCount)
                                val teleportPoints = configManager.getTeleportPoints(id)
                                if (teleportPoints != null) {
                                    val world = Bukkit.getWorld(teleportPoints.world)
                                    val randomTeleport = Location(
                                        world,
                                        teleportPoints.x,
                                        teleportPoints.y,
                                        teleportPoints.z,
                                        teleportPoints.yaw.toFloat(),
                                        teleportPoints.pitch.toFloat()
                                    )
                                    player.teleport(randomTeleport)
                                }
                            }

                        }

                    } else {
                        player.sendMessage(color("&6Справка помощи по команде &c/arena&6:"))
                        player.sendMessage(color("&b/arena setspawn &8- &7установить точку спавна для арены."))
                        player.sendMessage(color("&b/arena tp &8- &7Телепортация на арену."))
                    }
                    return true
                } else {
                    player.sendMessage(color("&cКоманда доступна только администрации!"))
                }
            }
        }
        return true
    }
}