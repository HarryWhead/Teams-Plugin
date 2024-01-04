package customteams.customteams.CustomConfig;

import customteams.customteams.Groups.Group;
import customteams.customteams.Groups.GroupMethods;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.Team;

import java.util.*;

import static customteams.customteams.Commands.AdminCommandMethods.ToggleFriendlyFire;
import static customteams.customteams.CustomTeams.*;

public class ReinstateConfig
{
    public void ReinstateSavedConfig()
    {
       toggledCommands = CustomConfig.get().getBoolean("toggledCommands");
       CustomDeathMessage = CustomConfig.get().getBoolean("CustomDeathMessage");
       LeaveMessage = CustomConfig.get().getBoolean("LeaveMessage");
       LightningOnDeath = CustomConfig.get().getBoolean("LightningOnDeath");
       maxPlayersPerTeam = CustomConfig.get().getInt("maxPlayersPerTeam");

       if (FriendlyFire != CustomConfig.get().getBoolean("FriendlyFire"))
           ToggleFriendlyFire();

        if(CustomConfig.get().getConfigurationSection("groups.") == null)
            return;

       for (String name : CustomConfig.get().getConfigurationSection("groups.").getKeys(false))
       {
           UUID leader = UUID.fromString(CustomConfig.get().getString("groups." + name + ".leader"));
           ChatColor color;
           String colorName = CustomConfig.get().getString("groups." + name + ".color");
           if (colorName != null) {
               color = (net.md_5.bungee.api.ChatColor.of(colorName.toUpperCase()));
           } else {
               color = null;
           }

           Group group = GroupMethods.createNewGroup(leader, color);

           List<String> playerList = new ArrayList<>(CustomConfig.get().getStringList("groups." + name + ".players"));
           for (String players : playerList) {
               UUID player = UUID.fromString(players);
               if (player.equals(leader)) {
                   continue;
               }
               group.addMember(player);
           }

           List<String> invited = new ArrayList<>(CustomConfig.get().getStringList("groups." + name + ".invited"));
           for (String players : invited) {
               UUID player = UUID.fromString(players);
               group.inviteMem(player);
           }

           GroupMethods.currentGroupNum = Integer.parseInt(name);
       }
    }

    public static void saveTeamsConfig()
    {
        for (String key : CustomConfig.get().getKeys(false)) {
            CustomConfig.get().set(key, null);
            CustomConfig.save();
        }

        for (Group group : GroupMethods.groups.values())
        {
            int groupNum = group.getGroupNum();
            List<UUID> members = group.getMembers();
            List<UUID> InvitedPlayers = group.getInvites();

            List<String> StringMembers = new ArrayList<>();
            for (UUID mem : members) {
                StringMembers.add(String.valueOf(mem));
            }

            List<String> StringInvitedPlayers = new ArrayList<>();
            for (UUID p : InvitedPlayers) {
                StringInvitedPlayers.add(String.valueOf(p));
            }

            CustomConfig.get().set("groups." + groupNum + "." + "leader", group.getLeader().toString());
            CustomConfig.get().set("groups." + groupNum + "." + "color", group.getColor().getName());
            CustomConfig.get().set("groups." + groupNum + "." + "players", StringMembers);
            CustomConfig.get().set("groups." + groupNum + "." + "invited", StringInvitedPlayers);
            CustomConfig.save();
        }

        CustomConfig.get().set("toggledCommands", toggledCommands);
        CustomConfig.get().set("CustomDeathMessage", CustomDeathMessage);
        CustomConfig.get().set("FriendlyFire", FriendlyFire);
        CustomConfig.get().set("LeaveMessage", LeaveMessage);
        CustomConfig.get().set("LightningOnDeath", LightningOnDeath);
        CustomConfig.get().set("maxPlayersPerTeam", maxPlayersPerTeam);
        CustomConfig.save();
    }
}
