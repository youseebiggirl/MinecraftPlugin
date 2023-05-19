package ru.remsoftware.handlers

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import ru.remsoftware.Skills
import ru.remsoftware.config.HomeModel
import ru.remsoftware.config.TeleportPointsModel
import ru.remsoftware.managers.ConfigManager
import ru.remsoftware.utils.PluginLogger.log

class PlayerJoin : Listener {
    private val configManager: ConfigManager = Skills.configManager

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player: Player = event.player
        val homeModel: HomeModel? = configManager.getPlayerHome(player.uniqueId)
        if (homeModel == null) {
            configManager.createNewHomeConfig(player)
            log("New player ${player.name} joined the server!")
        } else {
            log("Player ${player.name} joined the server!")
        }

    }

}