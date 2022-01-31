# Whitelist Manager
A Simple plugin that allows for easy testing of any plugin on a test server

# How to setup
1) Drag and drop the plugin into your plugins folder
2) create a Discord Bot at https://discord.com/developers/applications
3) put the bot token in the config.yml (WhitelistManager/config.yml)
4) Restart, and it's ready!

# Usage
You can modify the discord command prefix in the config.yml but the default is !whitelist

![image](https://user-images.githubusercontent.com/40679762/151727033-96f31688-fbe2-4bd8-9ded-df1c77f0913a.png)


Some examples:

!whitelist temp (player-name) will temporally add a player to the whitelist
![image](https://user-images.githubusercontent.com/40679762/151726749-66382eff-af58-4ffa-bd78-8d3a4e6e6570.png)

!whitelist perm (player-name) will permanently add a player to the bypass list in the config.yml allow them to bypass this system
![image](https://user-images.githubusercontent.com/40679762/151726790-aff9e7c4-a0ff-43bc-b870-2ca5a3c3ce94.png)

!whitelist remove (player-name) will remove them from the test list or perm list, if they are current connected and have a test session it will be cut down to 5 seconds after that it will kick them from the server.
![image](https://user-images.githubusercontent.com/40679762/151726887-ceb08e14-a9e6-4f40-a528-6aab962a0b59.png)

You can change messages and more stuff in the config.yml
This plugin only works with 1.8R_3, due to PacketPlayOutChatPacket being hardcoded for the ActionBar message, change it ur self im lazy
