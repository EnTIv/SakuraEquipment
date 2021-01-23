package com.entiv.sakuraequipment.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.List;

public class Message {

    public static void send(CommandSender sender, List<String> message) {

        if (message == null || message.isEmpty()) return;

        for (String string : message) {
            sender.sendMessage(toColor(string));
        }
    }

    public static void send(CommandSender sender, String[] message) {

        if (message == null || message.length == 0) return;

        sender.sendMessage(toColor(message));
    }

    public static void send(CommandSender sender, String message) {
        if (message == null || message.isEmpty()) return;
        sender.sendMessage(toColor(message));
    }

    public static void sendConsole(String message) {
        if (message == null || message.isEmpty()) return;

        ConsoleCommandSender consoleSender = Bukkit.getConsoleSender();
        consoleSender.sendMessage(toColor(message));
    }

    public static void sendActionBar(Player player, String message) {
        if (message == null || message.isEmpty()) return;
        player.sendActionBar(toColor(message));
    }

    public static void sendConsole(String[] message) {

        if (message == null || message.length == 0) return;

        ConsoleCommandSender consoleSender = Bukkit.getConsoleSender();
        consoleSender.sendMessage(toColor(message));
    }

    public static void sendConsole(List<String> message) {

        if (message == null || message.size() == 0) return;

        for (String string : message) {
            sendConsole(string);
        }

    }

    public static String toColor(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String[] toColor(String[] strings) {

        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            strings[i] = (toColor(string));
        }

        return strings;
    }

    public static List<String> toColor(List<String> strings) {
        strings.replaceAll(Message::toColor);
        return strings;
    }

    public static String formatNumber(Number number) {
        DecimalFormat format = new DecimalFormat(",###.##");
        return format.format(number);
    }

    public static String withoutColor(String string) {
        return ChatColor.stripColor(string);
    }

    public static String replace(String message, String... replace) {

        if (replace.length % 2 != 0) {
            throw new IllegalArgumentException("replace 参数应为2的倍数！");
        }

        for (int count = 0; count < replace.length; count += 2) {

            String oldChar = replace[count];

            if (!message.contains(oldChar)) {
                continue;
            }

            String newChar = replace[count + 1];
            message = message.replace(oldChar, newChar);
        }

        return message;
    }

    public static void replace(List<String> strings, String... replace) {
        strings.replaceAll(string -> replace(string, replace));
    }

    public static String[] replace(String[] strings, String... replace) {

        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            strings[i] = replace(string, replace);
        }

        return strings;
    }

    /*
    * 用来将字符串左右对齐, 使用 | 符号来设置需要分开的位置
    *
    * @parma width
    * */
    public static String distributeText(String string, int width) {

        int length = string.length();
        if (length >= width) return string.replace("|", "");

        String[] split = string.split("\\|");
        if (split.length == 1) return string;

        int needAdd = width - (getLength(split[0]) + getLength(split[1]));

        System.out.println("当前长度:" + (getLength(split[0]) + getLength(split[1]) + "   需要加的空格数:" + needAdd));

        return String.format(split[0] + "%" + needAdd + "s", split[1]);
    }

    public static void distributeText(List<String> strings, int width) {
        strings.replaceAll(s -> distributeText(s, width));
    }

    public static int getLength(String s) {
        s = s.replaceAll("[^\\x00-\\xff]", "**");
        return s.length();
    }
}
