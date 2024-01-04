package customteams.customteams.Listener;

import customteams.customteams.Groups.Group;
import customteams.customteams.Groups.GroupMethods;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static customteams.customteams.CustomTeams.*;
import static org.bukkit.Bukkit.getServer;

public class Listener implements org.bukkit.event.Listener
{
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e)
    {
        Player p = e.getEntity();

        if (LightningOnDeath)
        {
            Location location = p.getLocation();
            World world = p.getWorld();

            // Spawn lightning at the player's location
            world.strikeLightningEffect(location);
        }

        if (CustomDeathMessage)
        {
            List<Player> pList = getServer().getOnlinePlayers().stream()
                                 .filter(Player::isOp)
                                 .collect(Collectors.toList());

            for (Player player : pList) {
                player.sendMessage(Objects.requireNonNull(e.getDeathMessage()));
            }

            String deathMessage = ChatColor.translateAlternateColorCodes('&', "&c&l" + "ELIMINATION!" + " &rA player has fallen.");
            e.setDeathMessage(deathMessage);
        }

        Group group = GroupMethods.getGroup(p);
        if (group != null)
            group.removeMember(p.getUniqueId());
    }

    @EventHandler
    public void onEntityHurtEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;
        if (!(event.getDamager() instanceof Player))
            return;

        Player hurt = (Player)event.getEntity();
        Player damager = (Player)event.getDamager();
        Group hurtGroup = GroupMethods.getGroup(hurt);
        Group damagerGroup = GroupMethods.getGroup(damager);
        if (hurtGroup == null)
            return;
        if (damagerGroup == null)
            return;
        if (hurtGroup.getGroupNum() == damagerGroup.getGroupNum() && !FriendlyFire)
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e)
    {
        if (LeaveMessage)
        {
            e.setQuitMessage(null);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        Group group = GroupMethods.getGroup(p);

        if (group == null) {
            p.setDisplayName(ChatColor.WHITE + p.getName());
            p.setPlayerListName(ChatColor.WHITE + p.getName());
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();
        Group group = GroupMethods.getGroup(player);
        if (group == null) {
            for (Player target : event.getRecipients())
                target.sendMessage(event.getPlayer().getName() + ": " + event.getMessage());
        } else {
            for (Player target : event.getRecipients())
                target.sendMessage(group.getColor() + "[" + group.getGroupNum() + "] " + ChatColor.WHITE + event.getPlayer().getName() + ": " + event.getMessage());
        }
    }
}
