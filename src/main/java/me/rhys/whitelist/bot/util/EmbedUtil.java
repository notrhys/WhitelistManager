package me.rhys.whitelist.bot.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;

public class EmbedUtil {
    public static void sendEmbedPrivate(String header, String footer, Color color, User user) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor("Whitelist Bot", null, null);
        builder.setTitle(header);
        builder.setDescription(footer);
        builder.setColor(color);

        user.openPrivateChannel().queue((channel) ->
                channel.sendMessage(builder.build()).queue());
    }

    public static void sendEmbed(String header, String footer, Color color, TextChannel textChannel) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor("Whitelist bot", null, null);
        builder.setTitle(header);
        builder.setDescription(footer);
        builder.setColor(color);

        textChannel.sendMessage(builder.build()).queue();
    }
}
