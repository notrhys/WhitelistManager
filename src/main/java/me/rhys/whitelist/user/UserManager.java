package me.rhys.whitelist.user;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class UserManager {
    private final Map<UUID, User> userMap = new HashMap<>();

    public void remove(Player player) {
        this.userMap.remove(player.getUniqueId());
    }

    public void restoreUser(Player player) {
        User user = this.userMap.get(player.getUniqueId());
        user.setPlayer(player);
        user.handleObjectFix(player);
    }

    public void addUser(UUID uuid) {
        this.userMap.put(uuid, new User(null));
    }
}
