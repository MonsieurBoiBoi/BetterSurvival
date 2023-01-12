package com.bettersurvival.bettersurvival;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;


public class EventPVP implements Listener {
    private final Plugin plugin;
    public EventPVP(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player defender = (Player) event.getEntity();
            Player attacker = (Player) event.getDamager();

            // Check if either player has no items in their inventory
            if (attacker.getInventory().isEmpty() == false || defender.getInventory().isEmpty() == false && plugin.getConfig().getBoolean("PVP.CheckNoStuff") == true) {
                event.setCancelled(true);
                attacker.sendMessage("You can't attack other players when you have no items in your inventory!");
                defender.sendMessage("You can't be attacked by other players when you have no items in your inventory!");
            }
        }
    }

    
}