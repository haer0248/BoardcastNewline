package me.haer0248;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Main extends JavaPlugin implements Listener {
    String Header, Footer;
    String NewlineStr = ",";

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        File config = new File("plugins/BoardcastNewline/config.yml");
        if (config.exists()) {
            Header = this.getConfig().getString("Header");
            Footer = this.getConfig().getString("Footer");
        } else {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
            getLogger().info("Config created at plugins/BoardcastNewline.");
        }
    }

    public void onDisable() {
        saveDefaultConfig();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String help = """
                ----- §e§lBoardcastNewLine§r -----
                §6Author: §fNekomataOnigiri
                §6Website: §fhttps://haer0248.me/
                §6Version §f1.0.0
                """;
        if (cmd.getName().equalsIgnoreCase("bcn")) {
            if (sender.isOp() || sender.hasPermission("bcn.use")) {
                if (args.length == 0) {
                    sender.sendMessage(format(help + "§6/bcn <訊息> (使用 §e"+NewlineStr+"§6 換行)"));
                } else {
                    List<String> bc_list;
                    String[] bc_line = String.join(" ", args).split(NewlineStr);
                    bc_list = Arrays.asList(bc_line);

                    if (Header.length() > 0) {
                        Bukkit.getServer().broadcastMessage(format(Header));
                    }
                    for (String message: bc_list) {
                        Bukkit.getServer().broadcastMessage(format(message));
                    }
                    if (Footer.length() > 0) {
                        Bukkit.getServer().broadcastMessage(format(Footer));
                    }
                }
            } else {
                sender.sendMessage(help);
            }
        }
        return true;
    }

    public static String format(String format){
        return ChatColor.translateAlternateColorCodes('&', format);
    }
}
