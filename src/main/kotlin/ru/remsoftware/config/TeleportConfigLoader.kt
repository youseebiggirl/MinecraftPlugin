package ru.remsoftware.config

import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import ru.remsoftware.Skills
import ru.remsoftware.utils.PluginLogger
import java.io.File
import java.io.IOException

class TeleportConfigLoader(private val main: Skills, private val fileName: String) {
    private val file: File = File(main.dataFolder, "$fileName.yml")
    private val config: FileConfiguration = YamlConfiguration()

    fun loadConfig() {
        if (!file.exists()) {
            file.parentFile.mkdirs()
            main.saveResource("$fileName.yml", false)
            PluginLogger.log("Create teleport config.")
        }
        try {
            config.load(file)
            PluginLogger.log("Loaded teleport config")
        } catch (ex: InvalidConfigurationException) {
            PluginLogger.log("Error while loading teleport config!")
        } catch (ex: IOException) {
            PluginLogger.log("Error while loading teleport config!")
        }

    }
    fun saveConfig() {
        try {
            config.save(file)
            PluginLogger.log("Saved teleport config")
        } catch (ex : IOException) {
            PluginLogger.log("Error while saving teleport config")
        }
    }
    fun getConfig(): FileConfiguration = config

}