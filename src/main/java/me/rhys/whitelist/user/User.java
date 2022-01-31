package me.rhys.whitelist.user;

import lombok.Getter;
import lombok.Setter;
import me.rhys.whitelist.Plugin;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Getter @Setter
public class User {
    private Player player;
    private UUID uuid;

    private final ScheduledExecutorService executorService;

    private int maxMinutes = Plugin.getInstance().getConfigValues().getMaxMinutes();
    private int maxSeconds = Plugin.getInstance().getConfigValues().getMaxSeconds();

    private int minutes;
    private int seconds;

    private boolean started;

    public User(Player player) {

        if (player != null) {
            this.player = player;
            this.uuid = player.getUniqueId();
        }

        this.executorService = Executors.newSingleThreadScheduledExecutor();
    }

    public void handleObjectFix(Player player) {

        this.player = player;
        this.uuid = player.getUniqueId();

        new BukkitRunnable() {

            @Override
            public void run() {
                if (player.isOnline()) {
                    player.sendMessage(Plugin.getInstance().getConfigValues().getWelcomeMessage()
                            .replace("%MAX_MINUTES%", String.valueOf(maxMinutes))
                            .replace("%MAX_SECONDS%", String.valueOf(maxSeconds)));
                }
            }
        }.runTaskLaterAsynchronously(Plugin.getInstance(), 20L);
    }

    public void startTimer() {
        if (this.started) return;

        this.started = true;

        if (Plugin.getInstance().getConfigValues().isShowActionBar()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (player != null && player.isOnline()) {

                        int deltaMinutes = Math.abs(maxMinutes - minutes);
                        int deltaSeconds = Math.abs(maxSeconds - seconds);

                        String message = ChatColor.YELLOW.toString()
                                + deltaMinutes + ChatColor.RED + ":" + ChatColor.YELLOW + deltaSeconds
                                + ChatColor.GREEN + " Remaining for this session";

                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(
                                IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message + "\"}"), (byte) 2));
                    }
                }
            }.runTaskTimerAsynchronously(Plugin.getInstance(), 1L, 1L);
        }

        this.executorService.scheduleAtFixedRate(() -> {

            if (this.seconds++ >= this.maxSeconds) {
                this.seconds = 0;

                if (this.minutes++ >= this.maxMinutes) {
                    this.minutes = 0;

                    this.kick();
                    this.executorService.shutdownNow();
                }
            }

        }, 1L, 1L, TimeUnit.SECONDS);
    }

    private void kick() {
        new BukkitRunnable() {

            @Override
            public void run() {

                if (player != null && player.isOnline()) {
                    player.kickPlayer(Plugin.getInstance().getConfigValues().getKickMessage());
                }

                Plugin.getInstance().getUserManager().getUserMap().remove(uuid);
            }
        }.runTask(Plugin.getInstance());
    }
}
