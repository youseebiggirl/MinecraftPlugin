package ru.remsoftware.handlers

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import ru.remsoftware.Skills
import ru.remsoftware.managers.ConfigManager
import ru.remsoftware.utils.PluginLogger.log

class PlayerQuit : Listener {
    private val configManager: ConfigManager = Skills.configManager
    private val homeConfigLoader = Skills.homeConfigLoader
    private val teleportConfigLoader = Skills.teleportConfigLoader

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        configManager.saveHomeToConfig()
        homeConfigLoader.saveConfig()
        log("Saving home for ${event.player.name}")
        if (event.player.name.equals("Remkun")) {
            configManager.saveTeleportPointsToConfig()
            teleportConfigLoader.saveConfig()
            log("Saving tp points for arenas")
        }
    }
}