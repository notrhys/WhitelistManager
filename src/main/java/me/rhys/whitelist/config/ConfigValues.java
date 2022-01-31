package me.rhys.whitelist.config;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ConfigValues {
    private List<String> bypassList = new ArrayList<>();
    private boolean shouldUseRank;
    private String rankName;
    private String discordBotToken;
    private String commandPrefix;
    private int maxMinutes;
    private int maxSeconds;
    private String welcomeMessage;
    private String sessionExpire;
    private String kickMessage;
    private String whitelistMessage;
    private boolean showActionBar;
}
