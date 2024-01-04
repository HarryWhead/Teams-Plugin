package customteams.customteams.Commands;
import customteams.customteams.Groups.Group;
import customteams.customteams.Groups.GroupMethods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.Arrays;

import static customteams.customteams.CustomTeams.*;

public class TeamCommands implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
            return true;
        }

        Player p = (Player) sender;

        if (!p.isOp() && p.getGameMode().equals(GameMode.SPECTATOR)) {
            sender.sendMessage(ChatColor.RED + "You do not have these permissions in spectator");
            return true;
        }

        if (args.length == 0) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l<!> &r&cIncorrect usage, please use /group <create:invite:remove:join:leave:disband:msg>"));
            return true;
        }
        else if (!toggledCommands) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l<!> &r&cThis command is currently locked."));
        } else {
            if (args[0].equalsIgnoreCase("invite")) {
                if (args.length != 2) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l<!> &r&cIncorrect usage, please use /group invite (name)"));
                    return true;
                }

                if (GroupMethods.getGroup(p) == null) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are not currently in a group."));
                    return true;
                }

                if (Bukkit.getPlayer(args[1]) == null) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis is not a valid player."));
                    return true;
                }

                Player invite = Bukkit.getPlayer(args[1]);
                Group group = GroupMethods.getGroup(p);

                if (group.getMembers().contains(invite.getUniqueId())) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis player is already in your group."));
                    return true;
                }

                group.inviteMem(invite.getUniqueId());

                invite.sendMessage(ChatColor.GREEN + "You have been invited to group " + group.getColor() + group.getGroupNum() + "\n/group join " + group.getGroupNum() + " to join");
                p.sendMessage(ChatColor.GREEN + "You have invited " + invite.getName() + " to your group!");
            }
            if (args[0].equalsIgnoreCase("msg")) {

                if (args.length < 2) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlease write a message."));
                    return true;
                }

                if (GroupMethods.getGroup(p) == null) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are not currently in a group."));
                    return true;
                }

                String[] messageArgs = Arrays.copyOfRange(args, 1, args.length);
                String message = String.join(" ", messageArgs);

                Group group = GroupMethods.getGroup(p);
                group.sendGroupMsg(group.getColor() + "[" + group.getGroupNum() + "] " + ChatColor.WHITE + p.getName() + " -> " + message);
            }
            else if (args[0].equalsIgnoreCase("remove")) {
                if (args.length != 2) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l<!> &r&cIncorrect usage, please use /group remove (player)"));
                    return true;
                }

                if (GroupMethods.getGroup(p) == null) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are not currently in a group."));
                    return true;
                }

                Group group = GroupMethods.getGroup(p);

                if (!group.getLeader().equals(p.getUniqueId())) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are not a leader of a group!"));
                    return true;
                }

                if (Bukkit.getPlayer(args[1]) == null) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis is not a valid player."));
                    return true;
                }

                Player kicked = Bukkit.getPlayer(args[1]);

                if (p == kicked) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou cannot remove youself from the team."));
                    return true;
                }

                group.removeMember(kicked.getUniqueId());
                kicked.sendMessage(ChatColor.RED + "You have been removed from group " + group.getGroupNum());
                group.sendGroupMsg(kicked.getName() + " has been kicked from your group.");
            }
            else if (args[0].equalsIgnoreCase("join")) {
                if (args.length != 2) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l<!> &r&cIncorrect usage, please use /group join (groupNum)"));
                    return true;
                }

                if (GroupMethods.getGroup(p) != null) {
                    p.sendMessage(ChatColor.RED + "You are already in a group.");
                    return true;
                }

                int groupNumber = 0;
                try {
                    groupNumber = Integer.parseInt(args[1]);
                } catch (Exception e) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis is not a valid group number."));
                    return true;
                }

                if (GroupMethods.getGroup(groupNumber) == null) {
                    p.sendMessage(ChatColor.RED + "This is not a valid group!");
                    return true;
                }

                Group group = GroupMethods.getGroup(groupNumber);

                if (!group.isInvited(p)) {
                    p.sendMessage(ChatColor.RED + "You have not been invited to this group");
                    return true;
                }

                if (group.getMembers().size() >= maxPlayersPerTeam) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis group has reached it's player limit!"));
                    return true;
                }

                group.addMember(p.getUniqueId());
                p.sendMessage(ChatColor.GREEN + "You have successfully joined group " + group.getColor() + group.getGroupNum());
            }
            else if (args[0].equalsIgnoreCase("leave")) {
                if (GroupMethods.getGroup(p) == null) {
                    p.sendMessage(ChatColor.RED + "You are not currently in a group!");
                    return true;
                }
                Group group = GroupMethods.getGroup(p);

                if (group.getLeader().equals(p.getUniqueId())) {
                    p.sendMessage(ChatColor.RED + "You own a team. Please use /group disband");
                    return true;
                }

                group.removeMember(p.getUniqueId());
                p.sendMessage(ChatColor.GREEN + "You have left your current group!");
                group.sendGroupMsg(ChatColor.RED + p.getName() + "has left your group!");
            }
            else if (args[0].equalsIgnoreCase("create")) {
                if (GroupMethods.getGroup(p) != null) {
                    p.sendMessage(ChatColor.RED + "You are already a part of a group!");
                    return true;
                }

                Group group = GroupMethods.createNewGroup(p.getUniqueId(), null);
                p.sendMessage(ChatColor.GREEN + "You have created group " + group.getColor() + "[" + group.getGroupNum() + "]");
            }
            else if (args[0].equalsIgnoreCase("disband")) {
                if (GroupMethods.getGroup(p) == null) {
                    p.sendMessage(ChatColor.RED + "You are not currently in a group!");
                    return true;
                }
                Group group = GroupMethods.getGroup(p);

                if (!group.getLeader().equals(p.getUniqueId())) {
                    p.sendMessage(ChatColor.RED + "You do not currently own a group!");
                    return true;
                }

                GroupMethods.deleteGroup(group.getGroupNum());
                p.sendMessage(ChatColor.GREEN + "You have successfully disbanded your group!");
            }
        }
        if (args[0].equalsIgnoreCase("list")) {
            GroupMethods.listGroups(p);
        }
        return true;
    }
}
