package customteams.customteams;

import customteams.customteams.Commands.AdminCommandTabCompleter;
import customteams.customteams.Commands.AdminCommands;
import customteams.customteams.Commands.CommandTabCompleter;
import customteams.customteams.Commands.TeamCommands;
import customteams.customteams.CustomConfig.CustomConfig;
import customteams.customteams.CustomConfig.ReinstateConfig;
import customteams.customteams.Listener.Listener;
import customteams.customteams.Scoreboards.ScoreboardUtil;
import org.bukkit.plugin.java.JavaPlugin;

import static customteams.customteams.CustomConfig.ReinstateConfig.saveTeamsConfig;

public final class CustomTeams extends JavaPlugin
{
    public static Boolean FriendlyFire = false;
    public static Boolean toggledCommands = true;
    public static Boolean LightningOnDeath = true;
    public static Boolean CustomDeathMessage = true;
    public static Boolean LeaveMessage = true;
    public static int maxPlayersPerTeam = 5;

    @Override
    public void onEnable() {
        // Plugin startup logic

        ScoreboardUtil.createBoard();

        CustomConfig.setup();
        CustomConfig.get().options().copyDefaults(true);
        CustomConfig.save();

        getCommand("group").setExecutor(new TeamCommands());
        getCommand("group").setTabCompleter(new CommandTabCompleter());

        getCommand("gadmin").setExecutor(new AdminCommands());
        getCommand("gadmin").setTabCompleter(new AdminCommandTabCompleter());

        getServer().getPluginManager().registerEvents(new Listener(), this);

        new ReinstateConfig().ReinstateSavedConfig();
        autoSave();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic#
        saveTeamsConfig();
    }

    public static void autoSave() {
        CustomTeams.getPlugin(CustomTeams.class).getServer().getScheduler().scheduleSyncRepeatingTask(CustomTeams.getPlugin(CustomTeams.class), ReinstateConfig::saveTeamsConfig, 0L, 20 * 60L);
    }
}
