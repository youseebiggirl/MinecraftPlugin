package ru.remsoftware

import org.bukkit.plugin.java.JavaPlugin
import ru.remsoftware.commands.PluginCommands
import ru.remsoftware.config.HomeConfigLoader
import ru.remsoftware.config.TeleportConfigLoader
import ru.remsoftware.handlers.PlayerJoin
import ru.remsoftware.handlers.PlayerQuit
import ru.remsoftware.managers.ConfigManager
import ru.remsoftware.utils.PluginLogger.log
import java.util.logging.Logger

class Skills : JavaPlugin() {

    override fun onEnable() {
        pluginLogger = logger
        homeConfigLoader = HomeConfigLoader(this, "homes")
        teleportConfigLoader = TeleportConfigLoader(this, "teleports")
        homeConfigLoader.loadConfig()
        teleportConfigLoader.loadConfig()
        configManager = ConfigManager()
        configManager.loadHomeFromConfig()
        configManager.loadTeleportPointsFromConfig()
        server.pluginManager.registerEvents(PlayerJoin(), this)
        server.pluginManager.registerEvents(PlayerQuit(), this)
        getCommand("home")?.setExecutor(PluginCommands)
        getCommand("home")?.tabCompleter = PluginCommands
        getCommand("sethome")?.setExecutor(PluginCommands)
        getCommand("sethome")?.tabCompleter = PluginCommands
        getCommand("arena")?.setExecutor(PluginCommands)
        getCommand("arena")?.tabCompleter = PluginCommands
        log("Skills plugin enable")
    }

    override fun onDisable() {
        configManager.saveHomeToConfig()
        homeConfigLoader.saveConfig()
        configManager.saveTeleportPointsToConfig()
        teleportConfigLoader.saveConfig()
        log("Skills plugin disable")
    }
    companion object {
        lateinit var pluginLogger: Logger
        lateinit var homeConfigLoader: HomeConfigLoader
        lateinit var configManager: ConfigManager
        lateinit var teleportConfigLoader: TeleportConfigLoader
    }

}