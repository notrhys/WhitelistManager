package me.rhys.whitelist.bot;

import lombok.Getter;
import me.rhys.whitelist.Plugin;
import me.rhys.whitelist.bot.listener.MessageListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class DiscordBot {

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);
    private JDA jda;

    public void createBot() {
        this.executorService.execute(() -> {
            try {
                this.jda = JDABuilder.create(
                        Plugin.getInstance().getConfigValues().getDiscordBotToken(),
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_MESSAGES
                ).build();

                this.jda.addEventListener(new MessageListener());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
