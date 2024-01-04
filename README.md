# Minecraft Teams plugin

This plugin is a player-usable teams plugin, which has commands for players to create, disband, invite, remove and list teams, and also has a bunch of admin commands to moderate the teams created. This is used in a Hunger Games style setting so players can team up and fight together. 
## Player commands

/group create  
/group disband  
/group invite [player]  
/group join [group]  
/group leave  
/group remove [player]  
/group list

## Admin commands

/gadmin add [player] [group]  
/gadmin remove [player]  
/gadmin disband [team]  
/gadmin clearGroups  
/gadmin list  
/gadmin setGroupMax [number]  
/gadmin toggleCommands  
/gadmin toggleCustomDeathMessage  
/gadmin toggleFriendlyFire  
/gadmin toggleLeaveMessage  
/gadmin toggleLightningOnDeath  

All these commands are pretty self-explanatory, and with the admin commands, you can set the max group limit, take off friendly fire, add lightning when players die etc.
All these groups are stored in data files, so when the server resets or goes down for the night, the player data is saved and teams stay the same.
