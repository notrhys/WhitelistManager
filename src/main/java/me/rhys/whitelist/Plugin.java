package me.rhys.whitelist;

import lombok.Getter;
import me.rhys.whitelist.bot.DiscordBot;
import me.rhys.whitelist.config.ConfigLoader;
import me.rhys.whitelist.config.ConfigValues;
import me.rhys.whitelist.listener.PlayerListener;
import me.rhys.whitelist.user.UserManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Plugin extends JavaPlugin {

    @Getter
    private static Plugin instance;

    private final UserManager userManager = new UserManager();
    private final DiscordBot discordBot = new DiscordBot();

    private final ConfigLoader configLoader = new ConfigLoader();
    private final ConfigValues configValues = new ConfigValues();

    @Override
    public void onEnable() {
        instance = this;
        this.configLoader.load();

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        this.discordBot.createBot();
    }
}
