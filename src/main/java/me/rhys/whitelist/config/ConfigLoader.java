package me.rhys.whitelist.config;

import lombok.Getter;
import me.rhys.whitelist.Plugin;

@Getter
public class ConfigLoader {

    private final ConfigFile configFile = new ConfigFile();

    public void load() {
        this.configFile.setup(Plugin.getInstance());
        this.configFile.writeDefaults();

        Plugin.getInstance().getConfigValues().getBypassList().addAll(this.configFile.getFileConfiguration()
                .getStringList("Plugin.Bypass"));

        Plugin.getInstance().getConfigValues().setDiscordBotToken(this.configFile.getFileConfiguration()
                .getString("Discord.Token"));

        Plugin.getInstance().getConfigValues().setShouldUseRank(this.configFile.getFileConfiguration()
                .getBoolean("Discord.Usage.RequireRank"));

        Plugin.getInstance().getConfigValues().setRankName(this.configFile.getFileConfiguration()
                .getString("Discord.Usage.RankName"));

        Plugin.getInstance().getConfigValues().setCommandPrefix(this.configFile.getFileConfiguration()
                .getString("Discord.Usage.CommandPrefix"));

        Plugin.getInstance().getConfigValues().setMaxMinutes(this.configFile.getFileConfiguration()
                .getInt("Plugin.User.MaxMinutes"));

        Plugin.getInstance().getConfigValues().setMaxSeconds(this.configFile.getFileConfiguration()
                .getInt("Plugin.User.MaxSeconds"));

        Plugin.getInstance().getConfigValues().setWelcomeMessage(this.convertColor(
                this.configFile.getFileConfiguration()
                .getString("Plugin.Messages.Welcome")));

        Plugin.getInstance().getConfigValues().setSessionExpire(this.convertColor(
                this.configFile.getFileConfiguration()
                        .getString("Plugin.Messages.Kick")));

        Plugin.getInstance().getConfigValues().setWhitelistMessage(this.convertColor(
                this.configFile.getFileConfiguration()
                        .getString("Plugin.Messages.Whitelist")));

        Plugin.getInstance().getConfigValues().setKickMessage(this.convertColor(
                this.configFile.getFileConfiguration()
                        .getString("Plugin.Messages.Kick")));

        Plugin.getInstance().getConfigValues().setShowActionBar(this.configFile.getFileConfiguration()
                .getBoolean("Plugin.Functions.ActionBar"));
    }

    private String convertColor(String in) {
        return in.replace("&", "§");
    }
}
