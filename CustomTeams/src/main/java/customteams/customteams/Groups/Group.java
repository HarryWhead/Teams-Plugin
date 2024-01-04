package customteams.customteams.Groups;

import customteams.customteams.Scoreboards.ScoreboardUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Group
{
    private UUID leader = null;
    private List<UUID> members = new ArrayList<>();
    private List<UUID> invites = new ArrayList<>();
    private net.md_5.bungee.api.ChatColor color = null;
    private int teamNum;

    public List<UUID> getMembers() { return members; }
    public List<UUID> getInvites() { return invites; }
    public net.md_5.bungee.api.ChatColor getColor() { return color; }
    public int getGroupNum() {return teamNum;}
    public UUID getLeader() {return leader;}

    public void setLeader(UUID leader) {this.leader = leader;}
    public void inviteMem(UUID member) {this.invites.add(member);}

    public Group(int num) {
        this.teamNum = num;
    }

    public void addMember(UUID newMember) {
        invites.remove(newMember);
        members.add(newMember);

        Player player = Bukkit.getPlayer(newMember);
        if (player != null) {
            player.setPlayerListName(color + "[" + teamNum + "] " + ChatColor.WHITE + player.getName());
            player.setDisplayName(color + "[" + teamNum + "] " + ChatColor.WHITE + player.getName());
            ScoreboardUtil.addPlayer(this, player);
        }
    }
    public void removeMember(UUID removeMember) {
        members.remove(removeMember);

        OfflinePlayer OfflinePlayer = Bukkit.getOfflinePlayer(removeMember);

        if (OfflinePlayer.isOnline()) {
            Player player = Bukkit.getPlayer(removeMember);
            if (player != null) {
                player.setPlayerListName(ChatColor.WHITE + player.getName());
                player.setDisplayName(ChatColor.WHITE + player.getName());
                ScoreboardUtil.removePlayer(this, player);
            }
        } else
        {
            ScoreboardUtil.removePlayer(this, OfflinePlayer);
        }
    }

    public boolean isInvited(Player player) {
        for (UUID p : invites) {
            if (p.equals(player.getUniqueId())) {
                return true;
            }
        }
        return  false;
    }

    public void sendGroupMsg(String message) {
        for (UUID p : members) {
            Player player = Bukkit.getPlayer(p);
            if (player != null) {
                player.sendMessage(message);
            }
        }
    }

    public void setColor(net.md_5.bungee.api.ChatColor newColor) {
        color = newColor;
        for (UUID p : members) {
            Player player = Bukkit.getPlayer(p);
            if (player != null) {
                player.setPlayerListName(color + "[" + teamNum + "] " + ChatColor.WHITE + player.getName());
                player.setDisplayName(color + "[" + teamNum + "] " + ChatColor.WHITE + player.getName());
            }
        }
    }

    public void getRandomColor() {
        net.md_5.bungee.api.ChatColor[] validColors = {
                net.md_5.bungee.api.ChatColor.of("#FC4690"), net.md_5.bungee.api.ChatColor.of("#BD46FC"), net.md_5.bungee.api.ChatColor.of("#FC4690"), net.md_5.bungee.api.ChatColor.of("#46FC64"), net.md_5.bungee.api.ChatColor.of("#467AFC"),
                net.md_5.bungee.api.ChatColor.of("#00DA84"), net.md_5.bungee.api.ChatColor.of("#FF69B0"), net.md_5.bungee.api.ChatColor.of("#69CBFF"), net.md_5.bungee.api.ChatColor.of("#5961DD"), net.md_5.bungee.api.ChatColor.of("#DDD359"),
                net.md_5.bungee.api.ChatColor.of("#FFA034"), net.md_5.bungee.api.ChatColor.AQUA, net.md_5.bungee.api.ChatColor.BLUE, net.md_5.bungee.api.ChatColor.DARK_AQUA, net.md_5.bungee.api.ChatColor.GRAY,
                net.md_5.bungee.api.ChatColor.GREEN, net.md_5.bungee.api.ChatColor.LIGHT_PURPLE, net.md_5.bungee.api.ChatColor.RED, net.md_5.bungee.api.ChatColor.YELLOW
        };

        Random random = new Random();
        net.md_5.bungee.api.ChatColor randomColor;

        randomColor = validColors[random.nextInt(validColors.length)];

        setColor(randomColor);
    }
}
