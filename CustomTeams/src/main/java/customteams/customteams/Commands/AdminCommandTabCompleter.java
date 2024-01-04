package customteams.customteams.Commands;

import customteams.customteams.Groups.Group;
import customteams.customteams.Groups.GroupMethods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AdminCommandTabCompleter implements TabCompleter
{
    List<String> SubCommands = new ArrayList<>();
    List<String> Players = new ArrayList<>();
    List<String> groupNames = new ArrayList<>();
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (sender.isOp()) {
            if (SubCommands.isEmpty()) {
                SubCommands.add("add");
                SubCommands.add("remove");
                SubCommands.add("list");
                SubCommands.add("disband");
                SubCommands.add("clearGroups");
                SubCommands.add("setGroupMax");
                SubCommands.add("toggleCommands");
                SubCommands.add("toggleFriendlyFire");
                SubCommands.add("toggleLightningOnDeath");
                SubCommands.add("toggleCustomDeathMessage");
                SubCommands.add("toggleLeaveMessage");
            }

            for (Player player : Bukkit.getOnlinePlayers()) {
                Players.add(player.getName());
            }

            for (Group group : GroupMethods.groups.values())
            {
                groupNames.add(String.valueOf(group.getGroupNum()));
            }

            List<String> result = new ArrayList<>();

            switch (args.length) {
                case 1:
                    for (String s : SubCommands)
                        if (s.toLowerCase().startsWith(args[0].toLowerCase()))
                            result.add(s);

                    groupNames.clear();
                    Players.clear();

                    return result;
                case 2:
                    if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) {
                        for (String s : Players)
                            if (s.toLowerCase().startsWith(args[1].toLowerCase())) {
                                result.add(s);
                            }
                    } else if (args[0].equalsIgnoreCase("disband")) {
                        for (String s : groupNames)
                            if (s.toLowerCase().startsWith(args[1].toLowerCase())) {
                                result.add(s);
                            }
                    }

                    groupNames.clear();
                    Players.clear();

                    return result;
                case 3:
                    if (args[0].equalsIgnoreCase("add")) {
                        for (String s : groupNames)
                            if (s.toLowerCase().startsWith(args[2].toLowerCase())) {
                                result.add(s);
                            }
                    }

                    groupNames.clear();
                    Players.clear();

                    return result;
            }
            return null;
        }
        return null;
    }
}
