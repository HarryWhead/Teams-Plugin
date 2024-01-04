package customteams.customteams.Scoreboards;

import customteams.customteams.Groups.Group;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardUtil
{
    private static Scoreboard scoreboard;

    public static void createBoard() {
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        for (Team group : scoreboard.getTeams()) {
            group.unregister();
        }
    }

    public static void  createGroup(Group currentGroup) {
        Team group = scoreboard.registerNewTeam(String.valueOf(currentGroup.getGroupNum()));
        updateGroupPrefix(currentGroup);
    }
    public static Team getGroup(int groupNum) {
        return scoreboard.getTeam(String.valueOf(groupNum));
    }
    public static void updateGroupPrefix(Group currentGroup) {
        Team group = getGroup(currentGroup.getGroupNum());
        group.setPrefix(currentGroup.getColor() + "[" + currentGroup.getGroupNum() + "] " + ChatColor.WHITE);
    }
    public static void addPlayer(Group currentGroup, Player player) {
        Team group = getGroup(currentGroup.getGroupNum());
        group.addEntry(player.getName());
    }
    public static void removePlayer(Group currentGroup, OfflinePlayer player) {
        Team group = getGroup(currentGroup.getGroupNum());

        if (player != null) {
            group.removeEntry(player.getName());
        }
    }
}
