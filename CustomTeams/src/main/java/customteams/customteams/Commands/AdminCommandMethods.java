package customteams.customteams.Commands;
import customteams.customteams.Groups.GroupMethods;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static customteams.customteams.CustomTeams.*;

public class AdminCommandMethods
{

    public static void ToggleFriendlyFire()
    {
        FriendlyFire = !FriendlyFire;
    }

    public static void ClearGroups()
    {
        for (int groupNum : GroupMethods.groups.keySet())
        {
            GroupMethods.deleteGroup(groupNum);
        }
    }

    public static void listToggles(Player p)
    {
        p.sendMessage(ChatColor.BOLD + "Admin toggles:\n");
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&oGroup Size &ris set to: &l&n&e " + maxPlayersPerTeam));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&oToggled Commands &ris set to: &l&n&e " + toggledCommands));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&oLightning On Death &ris set to: &l&n&e " + LightningOnDeath));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&oCustom Death messages &rare set to: &l&n&e " + CustomDeathMessage));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&oLeave Messages Hidden &ris set to: &l&n&e " + LeaveMessage));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&oFriendly Fire &ris set to: &l&n&e " + FriendlyFire));
    }

    public static void SetGroupMax(String max) { maxPlayersPerTeam = Integer.parseInt(max);}

    public static void ToggleCommands()
    {
         toggledCommands = !toggledCommands;
    }

    public static void ToggleLightningOnDeath(){ LightningOnDeath = !LightningOnDeath; }

    public static void ToggleCustomDeathMessages() { CustomDeathMessage = !CustomDeathMessage; }

    public static void ToggleLeaveMessage() {LeaveMessage = !LeaveMessage; }
    
    public static void OutputStatement(Boolean statement, String toggled, Player p)
    {
        if (statement) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', toggled + " is now " + "&a" + "enabled"));
        } else {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', toggled + " is now " + "&c" + "disabled"));
        }
    }
}
