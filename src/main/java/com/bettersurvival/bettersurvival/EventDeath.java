package com.bettersurvival.bettersurvival;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.Chest.Type;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EventDeath implements org.bukkit.event.Listener {

  public static Map<UUID, Location> lastPositions = new HashMap<UUID, Location>();

  @EventHandler
  public void onPlayerDeath(org.bukkit.event.entity.PlayerDeathEvent event) {
    if (event.getEntity() instanceof org.bukkit.entity.Player) {
      org.bukkit.entity.Player player = (org.bukkit.entity.Player) event.getEntity();
      lastPositions.put(player.getUniqueId(), player.getLocation());
      player.sendMessage(
        "§c§lVous êtes mort en §r§c" +
        player.getLocation().getBlockX() +
        " " +
        player.getLocation().getBlockY() +
        " " +
        player.getLocation().getBlockZ() +
        " §r§c§l. Pour voir votre dernière mort, faites /death"
      );
      if (player.getInventory().isEmpty() == false) {
        List<ItemStack> drops = event.getDrops();

        World world = event.getEntity().getWorld();
        Location loc = event.getEntity().getLocation();

        Location chest1Location = loc.clone().add(1, 0, 0);
        Location chest2Location = loc.clone().add(0, 0, 0);
        Block block = chest1Location.getBlock();
        Block block2 = chest2Location.getBlock();

        block.setType(Material.CHEST);
        block2.setType(Material.CHEST);

        Inventory inv1 =
          (
            (org.bukkit.block.Chest) world.getBlockAt(chest1Location).getState()
          ).getInventory();
        Inventory inv2 =
          (
            (org.bukkit.block.Chest) world.getBlockAt(chest2Location).getState()
          ).getInventory();

        Chest chestBlockState1 = (Chest) block.getBlockData();
        Chest chestBlockState2 = (Chest) block2.getBlockData();

        chestBlockState1.setType(Type.RIGHT);
        chestBlockState2.setType(Type.LEFT);

        block.setBlockData(chestBlockState1, true);
        block2.setBlockData(chestBlockState2, true);

        int counter = 0;

        for (ItemStack item : drops) {
          if (counter % 2 == 0) {
            inv1.addItem(item);
          } else {
            inv2.addItem(item);
          }

          counter++;
        }

        drops.clear();
      }
    }
  }
}
