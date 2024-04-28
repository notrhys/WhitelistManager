package me.rhys.whitelist.config;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Getter
public class ConfigFile {

    private FileConfiguration fileConfiguration;
    private File dataFile;

    public void setup(JavaPlugin plugin) {
        plugin.getDataFolder().mkdir();

        this.dataFile = new File("plugins/WhitelistManager/Config.yml");

        if (!this.dataFile.exists()) {
            try {
                this.dataFile.createNewFile();
            } catch (IOException ignored) {
            }
        }

        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.dataFile);
    }

    public void writeDefaults() {

        if (!this.fileConfiguration.contains("Plugin.Bypass")) {
            this.fileConfiguration.set("Plugin.Bypass", Collections.emptyList());
        }

        if (!this.fileConfiguration.contains("Plugin.User.MaxSeconds")) {
            this.fileConfiguration.set("Plugin.User.MaxSeconds", 60);
        }

        if (!this.fileConfiguration.contains("Plugin.User.MaxMinutes")) {
            this.fileConfiguration.set("Plugin.User.MaxMinutes", 45);
        }

        if (!this.fileConfiguration.contains("Plugin.Messages.Welcome")) {
            this.fileConfiguration.set("Plugin.Messages.Welcome", "&aWelcome to the test server, " +
                    "your session will expire in %MAX_MINUTES% minutes and %MAX_SECONDS% seconds!");
        }

        if (!this.fileConfiguration.contains("Plugin.Messages.Kick")) {
            this.fileConfiguration.set("Plugin.Messages.Kick", "&cYour session has expired!");
        }

        if (!this.fileConfiguration.contains("Plugin.Functions.ActionBar")) {
            this.fileConfiguration.set("Plugin.Functions.ActionBar", true);
        }

        if (!this.fileConfiguration.contains("Plugin.Messages.Whitelist")) {
            this.fileConfiguration.set("Plugin.Messages.Whitelist", "&cYou are not whitelisted, contact us on Discord!");
        }

        if (!this.fileConfiguration.contains("Discord.Token")) {
            this.fileConfiguration.set("Discord.Token", "BOT_TOKEN");
        }

        if (!this.fileConfiguration.contains("Discord.Usage.RequireRank")) {
            this.fileConfiguration.set("Discord.Usage.RequireRank", false);
        }

        if (!this.fileConfiguration.contains("Discord.Usage.RankName")) {
            this.fileConfiguration.set("Discord.Usage.RankName", "staff");
        }

        if (!this.fileConfiguration.contains("Discord.Usage.CommandPrefix")) {
            this.fileConfiguration.set("Discord.Usage.CommandPrefix", "!whitelist");
        }

        saveData();
    }

    public void saveData() {
        try {
            this.fileConfiguration.save(this.dataFile);
        } catch (IOException ignored) {
        }
    }
}
