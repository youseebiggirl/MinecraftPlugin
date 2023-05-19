package ru.remsoftware.utils

import org.bukkit.ChatColor

object ChatUtils {

    fun color(text: String) =
        ChatColor.translateAlternateColorCodes('&', text)
    fun decolor(text: String) =
        ChatColor.stripColor(text)
    fun recolor(text: String) =
        text.replace(ChatColor.COLOR_CHAR, '&')


}