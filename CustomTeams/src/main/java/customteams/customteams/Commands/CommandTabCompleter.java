package customteams.customteams.Commands;

import customteams.customteams.Groups.Group;
import customteams.customteams.Groups.GroupMethods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class CommandTabCompleter implements TabCompleter
{
    List<String> SubCommands = new ArrayList<>();
    List<String> Players = new ArrayList<>();
    List<String> groupNames = new ArrayList<>();
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
    {
        if(SubCommands.isEmpty())
        {
            SubCommands.add("invite");
            SubCommands.add("remove");
            SubCommands.add("create");
            SubCommands.add("disband");
            SubCommands.add("join");
            SubCommands.add("leave");
            SubCommands.add("list");
            SubCommands.add("msg");
        }

        for (Player player : Bukkit.getOnlinePlayers())
        {
            Players.add(player.getName());
        }

        for (Group group : GroupMethods.groups.values())
        {
            groupNames.add(String.valueOf(group.getGroupNum()));
        }

        List<String> result = new ArrayList<>();

        switch(args.length) {
            case 1:
                for (String s : SubCommands)
                    if (s.toLowerCase().startsWith(args[0].toLowerCase()))
                        result.add(s);

                groupNames.clear();
                Players.clear();

                return result;
            case 2:
                if (args[0].equalsIgnoreCase("invite") || args[0].equalsIgnoreCase("remove")) {
                    for (String s : Players)
                        if (s.toLowerCase().startsWith(args[1].toLowerCase())) {
                            result.add(s);
                        }
                }
                else if (args[0].equalsIgnoreCase("join")) {
                    for (String s : groupNames)
                        if (s.toLowerCase().startsWith(args[1].toLowerCase())) {
                            result.add(s);
                        }
                }

                groupNames.clear();
                Players.clear();

                return result;
        }
        return null;
    }
}
