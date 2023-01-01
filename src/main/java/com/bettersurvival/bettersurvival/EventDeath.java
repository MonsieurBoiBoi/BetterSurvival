package com.bettersurvival.bettersurvival;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.type.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


//DON'T WORK FOR NOW, IT DUPLICATE ITEMS LIKE ITEMS ON THE GROUND AND ON THE CHEST  



public class EventDeath implements org.bukkit.event.Listener {
  public static Map<UUID, Location> lastPositions = new HashMap<UUID, Location>();

  @EventHandler
  public void onPlayerDeath(org.bukkit.event.entity.PlayerDeathEvent event) {
    if (event.getEntity() instanceof org.bukkit.entity.Player) {
      org.bukkit.entity.Player player = (org.bukkit.entity.Player) event.getEntity();
      lastPositions.put(player.getUniqueId(), player.getLocation());
      player.sendMessage(
          "§c§lVous êtes mort en §r§c" + player.getLocation().getBlockX() + " " + player.getLocation().getBlockY() + " "
              + player.getLocation().getBlockZ() + " §r§c§l. Pour voir votre dernière mort, faites /death");
      if (player.getInventory().isEmpty() == false) {
        List<ItemStack> drops = event.getDrops();

        World world = event.getEntity().getWorld();
        Location loc = event.getEntity().getLocation();

        Location chest1Location = loc.clone().add(1, 0, 0);
        Location chest2Location = loc.clone().add(0, 0, 0);

        for (Location chestLocation : Arrays.asList(chest1Location, chest2Location)) {
          chestLocation.setY(chestLocation.getY());
          world.getBlockAt(chestLocation).setType(Material.CHEST);
          Chest chest = (Chest) world.getBlockAt(chestLocation).getBlockData();
          chest.setFacing(event.getEntity().getFacing());
          world.getBlockAt(chestLocation).setBlockData(chest);
          Inventory inv = ((org.bukkit.block.Chest) world.getBlockAt(chestLocation).getState()).getInventory();
          for (ItemStack item : drops) {
            inv.addItem(item);
          }
        }
      }
    }

  }
}
