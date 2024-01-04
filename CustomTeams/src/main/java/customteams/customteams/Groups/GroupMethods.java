package customteams.customteams.Groups;

import customteams.customteams.Scoreboards.ScoreboardUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class GroupMethods
{
    public static Map<Integer, Group> groups = new HashMap<>();
    public static int currentGroupNum;
    public static Group createNewGroup(UUID player, net.md_5.bungee.api.ChatColor color) {
        currentGroupNum = currentGroupNum + 1;
        int groupNum = currentGroupNum;
        Group group = new Group(groupNum);

        if (color != null) {
            group.setColor(color);
        } else {
            group.getRandomColor();
        }

        ScoreboardUtil.createGroup(group);
        group.setLeader(player);
        group.addMember(player);
        groups.put(groupNum, group);
        return group;
    }

    public static void deleteGroup(int groupNum) {
        Group group = groups.remove(groupNum);

        List<UUID> members = new ArrayList<>(group.getMembers());

        for (UUID member : members) {
            group.removeMember(member);
        }
    }

    public static Group getGroup(Player player) {
        if (groups.isEmpty())
            return null;

        for (Group group : groups.values()) {
            if (group.getMembers().contains(player.getUniqueId())) {
                return  group;
            }
        }

        return null;
    }
    public static Group getGroup(int groupNum) {
        if (groups.isEmpty())
            return null;
        if (!groups.containsKey(groupNum))
            return null;
        return groups.get(groupNum);
    }

    public static void listGroups(Player player) {
        player.sendMessage("There are currently: " + ChatColor.BOLD + GroupMethods.groups.size() + " groups\n");
        player.sendMessage(ChatColor.GREEN + "online " + ChatColor.WHITE + "|" + ChatColor.RED + " offline\n");
        player.sendMessage("\n");

        for (Group group : GroupMethods.groups.values()) {

            StringBuilder message = new StringBuilder("Group " + group.getGroupNum() + ": ");
            for (UUID playerUUID : group.getMembers()) {

                OfflinePlayer players = Bukkit.getOfflinePlayer(playerUUID);

                if (!players.isOnline()) {
                    message.append(ChatColor.RED).append(players.getName()).append(ChatColor.WHITE).append(", ");
                } else {
                    message.append(ChatColor.GREEN).append(players.getName()).append(ChatColor.WHITE).append(", ");
                }
            }

            player.sendMessage(String.valueOf(message));
        }
    }
}
