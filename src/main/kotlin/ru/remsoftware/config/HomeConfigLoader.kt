package ru.remsoftware.config

import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import ru.remsoftware.Skills
import ru.remsoftware.utils.PluginLogger.log
import java.io.File
import java.io.IOException

class HomeConfigLoader(private val main: Skills, private val fileName: String) {
    private val file: File = File(main.dataFolder, "$fileName.yml")
    private val config: FileConfiguration = YamlConfiguration()

    fun loadConfig() {
        if (!file.exists()) {
            file.parentFile.mkdirs()
            main.saveResource("$fileName.yml", false)
            log("Create home config.")
        }
        try {
            config.load(file)
            log("Loaded home config")
        } catch (ex: InvalidConfigurationException) {
            log("Error while loading home config!")
        } catch (ex: IOException) {
            log("Error while loading home config!")
        }

    }
    fun saveConfig() {
        try {
            config.save(file)
            log("Saved home config")
        } catch (ex : IOException) {
            log("Error while saving home config")
        }
    }
    fun getConfig(): FileConfiguration = config

}