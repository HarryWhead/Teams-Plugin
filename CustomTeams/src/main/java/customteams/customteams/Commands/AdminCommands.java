package customteams.customteams.Commands;

import customteams.customteams.Groups.Group;
import customteams.customteams.Groups.GroupMethods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static customteams.customteams.Commands.AdminCommandMethods.*;
import static customteams.customteams.CustomTeams.*;

public class AdminCommands implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
            return true;
        }

        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
            return true;
        }

        Player p = ((Player) sender);

        if (args.length == 0) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l<!> &r&cIncorrect usage, please use /gadmin <toggleCommands:toggleFriendlyFire:toggleLightningOnDeath:toggleCustomDeathMessage:toggleLeaveMessage:add:remove:disband:setGroupMax:clearGroups>"));
        }
        else {
            if (args[0].equalsIgnoreCase("add")) {
                if (args.length != 3) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l<!> &r&cIncorrect usage, please use /gadmin add (name) (teamNum)"));
                    return true;
                }

                int groupNumber = 0;
                try {
                    groupNumber = Integer.parseInt(args[2]);
                } catch (Exception e) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis is not a valid group number."));
                    return true;
                }

                if (GroupMethods.getGroup(groupNumber) == null) {
                    p.sendMessage(ChatColor.RED + "This is not a valid group!");
                    return true;
                }

                Group group = GroupMethods.getGroup(groupNumber);

                if (Bukkit.getPlayer(args[1]) == null) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis is not a valid player."));
                    return true;
                }

                Player addedPlayer = Bukkit.getPlayer(args[1]);
                group.addMember(addedPlayer.getUniqueId());
                addedPlayer.sendMessage(ChatColor.GREEN + "You have successfully joined group " + group.getColor() + group.getGroupNum());
                p.sendMessage(ChatColor.GREEN + "You have successfully added " + addedPlayer.getName() + " to group " + group.getColor() + group.getGroupNum());
            }
            else if (args[0].equalsIgnoreCase("remove")) {
                if (args.length != 2) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l<!> &r&cIncorrect usage, please use /gadmin remove (name) "));
                    return true;
                }

                if (Bukkit.getPlayer(args[1]) == null) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis is not a valid player."));
                    return true;
                }

                Player removedPlayer = Bukkit.getPlayer(args[1]);

                if (GroupMethods.getGroup(removedPlayer) == null) {
                    p.sendMessage(ChatColor.RED + "This player is not in a group!");
                    return true;
                }

                Group group = GroupMethods.getGroup(removedPlayer);
                group.removeMember(removedPlayer.getUniqueId());
                p.sendMessage(ChatColor.GREEN + removedPlayer.getName() + " has been kicked from their group");
                removedPlayer.sendMessage(ChatColor.RED + "You have been removed from group " + group.getColor() + group.getGroupNum());
            }
            else if (args[0].equalsIgnoreCase("disband")) {
                if (args.length != 2) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l<!> &r&cIncorrect usage, please use /gadmin disband (teamNum)"));
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

                GroupMethods.deleteGroup(groupNumber);
                p.sendMessage(ChatColor.GREEN + "You have successfully disbanded group " + groupNumber);
            }
            else if (args[0].equalsIgnoreCase("list")) {
                listToggles(p);
            }
            else if (args[0].equalsIgnoreCase("setGroupMax")) {
                SetGroupMax(args[1]);
                sender.sendMessage("Max group size set to: " + ChatColor.BOLD + args[1]);
            }
            else if (args[0].equalsIgnoreCase("clearGroups")) {
                ClearGroups();
                sender.sendMessage(ChatColor.GREEN + "All groups have been cleared!");
            }
            else if (args[0].equalsIgnoreCase("toggleCommands")) {
                ToggleCommands();
                OutputStatement(toggledCommands, "Command usage", p);
            }
            else if (args[0].equalsIgnoreCase("toggleFriendlyFire")) {
                ToggleFriendlyFire();
                OutputStatement(FriendlyFire, "Friendly fire", p);
            }
            else if (args[0].equalsIgnoreCase("toggleLightningOnDeath")) {
                ToggleLightningOnDeath();
                OutputStatement(LightningOnDeath, "Lightning on death", p);
            }
            else if (args[0].equalsIgnoreCase("toggleCustomDeathMessage")) {
                ToggleCustomDeathMessages();
                OutputStatement(CustomDeathMessage, "Custom death message", p);
            }
            else if (args[0].equalsIgnoreCase("toggleLeaveMessage")) {
                ToggleLeaveMessage();
                OutputStatement(LeaveMessage, "Leave message hidden", p);
            }
        }
        return true;
    }
}
