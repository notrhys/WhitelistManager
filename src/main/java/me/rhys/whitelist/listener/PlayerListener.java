package me.rhys.whitelist.listener;

import me.rhys.whitelist.Plugin;
import me.rhys.whitelist.user.User;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        if (Plugin.getInstance().getConfigValues().getBypassList().contains(event.getUniqueId().toString())) return;

        User user = Plugin.getInstance().getUserManager().getUserMap().get(event.getUniqueId());

        if (user == null) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                    Plugin.getInstance().getConfigValues().getWhitelistMessage());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        if (Plugin.getInstance().getConfigValues().getBypassList().contains(event.getPlayer().getUniqueId()
                .toString())) return;

        User user = Plugin.getInstance().getUserManager().getUserMap().get(event.getPlayer().getUniqueId());

        if (user != null) {
            Plugin.getInstance().getUserManager().restoreUser(event.getPlayer());
            user.startTimer();
        }
    }
}
