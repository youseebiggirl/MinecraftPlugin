package ru.remsoftware.utils

import ru.remsoftware.Skills
import java.util.logging.Logger

object PluginLogger {
    private val logger: Logger = Skills.pluginLogger

    fun log(vararg messages: String) {
        for (message in messages)
            logger.info(message)
    }
    fun warn(vararg messages: String) {
        for (message in messages)
            logger.warning(message)
    }
    fun error(vararg messages: String) {
        for (message in messages)
            logger.severe(message)
    }

}
