package me.rhys.whitelist.bot.listener;

import me.rhys.whitelist.Plugin;
import me.rhys.whitelist.bot.util.EmbedUtil;
import me.rhys.whitelist.bot.util.UUIDUtil;
import me.rhys.whitelist.user.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String message = event.getMessage().getContentRaw();

        if (message.startsWith(Plugin.getInstance().getConfigValues().getCommandPrefix())) {
            String[] spilt = message.split(" ");

            if (Plugin.getInstance().getConfigValues().isShouldUseRank() &&
                    Objects.requireNonNull(event.getMember()).getRoles()
                    .stream().noneMatch(role -> role.getName().toLowerCase(Locale.ROOT)
                    .equalsIgnoreCase(Plugin.getInstance().getConfigValues().getRankName()))) return;

            if (spilt.length >= 2) {
                String mode = spilt[1];
                String username = spilt[2];

                if (username == null || mode == null) {
                    EmbedUtil.sendEmbed("Error", "Invalid arguments", Color.RED, event.getTextChannel());
                    return;
                }

                switch (mode) {
                    case "remove": {
                        Plugin.getInstance().getDiscordBot().getExecutorService().execute(() -> {
                            String uuid = UUIDUtil.getUUID(username);

                            EmbedUtil.sendEmbed("Whitelist", "Removed and kicked " + username
                                            + "(" + uuid + ")",
                                    Color.GREEN, event.getTextChannel());

                            Plugin.getInstance().getConfigValues().getBypassList().remove(uuid);

                            Plugin.getInstance().getConfigLoader().getConfigFile()
                                    .getFileConfiguration().set("Plugin.Bypass", Plugin.getInstance().getConfigValues()
                                    .getBypassList());

                            Plugin.getInstance().getConfigLoader().getConfigFile().saveData();

                            User user = Plugin.getInstance().getUserManager().getUserMap().get(UUID.fromString(uuid));

                            if (user != null) {
                                user.setMinutes(user.getMaxMinutes());
                                user.setSeconds(user.getMaxSeconds() - 5);
                            }
                        });
                        break;
                    }

                    case "temp": {
                        Plugin.getInstance().getDiscordBot().getExecutorService().execute(() -> {
                            String uuid = UUIDUtil.getUUID(username);

                            EmbedUtil.sendEmbed("Whitelist", "Whitelisted " + username
                                            + " (" + uuid + ") it will expire in "
                                            + Plugin.getInstance().getConfigValues().getMaxMinutes() + " minutes!",
                                    Color.GREEN, event.getTextChannel());

                            Plugin.getInstance().getUserManager().addUser(UUID.fromString(uuid));
                        });
                        break;
                    }

                    case "perm": {
                        Plugin.getInstance().getDiscordBot().getExecutorService().execute(() -> {
                            String UUID = UUIDUtil.getUUID(username);

                            EmbedUtil.sendEmbed("Whitelist", "Whitelisted " + username
                                            + "(" + UUID + ")",
                                    Color.GREEN, event.getTextChannel());

                            Plugin.getInstance().getConfigValues().getBypassList().add(UUID);

                            Plugin.getInstance().getConfigLoader().getConfigFile()
                                    .getFileConfiguration().set("Plugin.Bypass", Plugin.getInstance().getConfigValues()
                                    .getBypassList());

                            Plugin.getInstance().getConfigLoader().getConfigFile().saveData();
                        });
                        break;
                    }
                }
            } else {
                EmbedUtil.sendEmbed("Error", "Invalid arguments", Color.RED, event.getTextChannel());
            }
        }
    }
}
