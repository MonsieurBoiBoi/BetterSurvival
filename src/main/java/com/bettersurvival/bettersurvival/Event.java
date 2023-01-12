package com.bettersurvival.bettersurvival;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


import org.bukkit.Location;
import org.bukkit.event.EventHandler;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;


public class Event implements org.bukkit.event.Listener{
    private final Plugin plugin;
    
    public Event(Plugin plugin) {
        this.plugin = plugin;
        
    }


    public static Map<UUID, Location> lastPositions = new HashMap<UUID, Location>();
    
  //END MORT------------------------------------------------------------------------------------------------------

  //START PLUIE------------------------------------------------------------------------------------------------------
  //when it rain, say it in the chat
    @EventHandler
    public void onWeatherChange(org.bukkit.event.weather.WeatherChangeEvent event) {
        if (event.toWeatherState()) {
            org.bukkit.Bukkit.broadcastMessage("§b§lIl pleut, faite /rain pour arrêter la pluie.");
        }
    }
    //END PLUIE------------------------------------------------------------------------------------------------------

    


  //START JOIN AND QUIT MESSAGE------------------------------------------------------------------------------------------------------
  //when a player join the server change the text of the join message
    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        org.bukkit.entity.Player player = event.getPlayer();
        event.setJoinMessage("§7[§2+§7] §f" + player.getName() + plugin.getConfig().getString("JoinMessage"));
        //say it with a actionbar
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(plugin.getConfig().getString("WelcomeMessage")));
        player.setPlayerListHeader(plugin.getConfig().getString("Tab.Header") + "\n");
        player.setPlayerListFooter("\n" + "§7" + plugin.getConfig().getString("Tab.Footer"));
    }
    //when a player quit the server change the text of the quit message
    @EventHandler
    public void onPlayerQuit(org.bukkit.event.player.PlayerQuitEvent event) {
        org.bukkit.entity.Player player = event.getPlayer();
        event.setQuitMessage("§7[§4-§7] §f" + player.getName() + plugin.getConfig().getString("LeftMessage"));
        player.setPlayerListHeader(null);
    }
    //END JOIN AND QUIT MESSAGE------------------------------------------------------------------------------------------------------
    
    //START TCHAT EDIT------------------------------------------------------------------------------------------------------
    @EventHandler
    public void onPlayerChat(org.bukkit.event.player.AsyncPlayerChatEvent event) {
        org.bukkit.entity.Player player = event.getPlayer();
        event.setFormat("§7[§f" + player.getName() + "§7] §f" + event.getMessage());
    }
    //END TCHAT EDIT------------------------------------------------------------------------------------------------------
    
    //START DISABLE GAMEMODE CREATIVE AND SPECTATOR------------------------------------------------------------------------------------------------------
    @EventHandler
    public void onPlayerGamemodeChange(org.bukkit.event.player.PlayerGameModeChangeEvent event) {
        org.bukkit.entity.Player player = event.getPlayer();
        if (plugin.getConfig().getBoolean("NoGamemode") == true && event.getNewGameMode() == org.bukkit.GameMode.CREATIVE || event.getNewGameMode() == org.bukkit.GameMode.SPECTATOR) {
            event.setCancelled(true);
            player.sendMessage("[Bettersurvival] §cVous ne pouvez pas changer de gamemode.");
        }
    }
    //END DISABLE GAMEMODE CREATIVE AND SPECTATOR------------------------------------------------------------------------------------------------------

    //START DISABLE COMMANDS------------------------------------------------------------------------------------------------------
    @EventHandler
    public void onPlayerCommandPreprocess(org.bukkit.event.player.PlayerCommandPreprocessEvent event) {
        org.bukkit.entity.Player player = event.getPlayer();
        String command = event.getMessage().toLowerCase();
        if (command.startsWith("/version") || command.startsWith("/plugins") || command.startsWith("/bukkit:pl") || command.startsWith("/bukkit:plugins") || command.startsWith("/bukkit:version") || command.startsWith("/bukkit:about")|| command.startsWith("/me") || command.startsWith("/trigger") || command.startsWith("/minecraft:me") || command.startsWith("/minecraft:trigger") || command.startsWith("/give") || command.startsWith("/minecraft:give") || command.startsWith("/effect") || command.startsWith("/minecraft:effect")) {
            event.setCancelled(true);
            player.sendMessage("[Bettersurvival] §cVous ne pouvez pas utiliser cette commande.");
        }
    }
    //END DISABLE COMMANDS------------------------------------------------------------------------------------------------------

    //START CHANGE TELL MESSAGE------------------------------------------------------------------------------------------------------
    @EventHandler
    public void onPlayerCommandPreprocessMessage(org.bukkit.event.player.PlayerCommandPreprocessEvent event) {
        org.bukkit.entity.Player player = event.getPlayer();
        String command = event.getMessage().toLowerCase();
        if (command.startsWith("/tell") || command.startsWith("/msg") || command.startsWith("/whisper") || command.startsWith("/minecraft:tell") || command.startsWith("/minecraft:msg") || command.startsWith("/minecraft:w") || command.startsWith("/minecraft:whisper")) {
            event.setCancelled(true);
            String[] args = command.split(" ");
            if (args.length >= 3) {
                org.bukkit.entity.Player target = org.bukkit.Bukkit.getPlayer(args[1]);
                if (target != null) {
                    String message = "";
                    for (int i = 2; i < args.length; i++) {
                        message += args[i] + " ";
                    }
                    player.sendMessage("§7[§f" + player.getName() + "§7 -> §f" + target.getName() + "§7] §f" + message);
                    target.sendMessage("§7[§f" + player.getName() + "§7 -> §f" + target.getName() + "§7] §f" + message);
                    target.playSound(target.getLocation(), org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("Vous avez reçu un message de " + target.getName()));


                } else {
                    player.sendMessage("[Bettersurvival] §cLe joueur n'est pas connecté.");
                }
            } else {
                player.sendMessage("[Bettersurvival] §cUtilisation: /tell <joueur> <message>");
            }
        }
    }
    //END CHANGE TELL MESSAGE------------------------------------------------------------------------------------------------------

    //START ENDERDRAGON DEATH MESSAGE WHO KILL------------------------------------------------------------------------------------------------------
    @EventHandler
    public void onEnderDragonDeath(org.bukkit.event.entity.EntityDeathEvent event) {
        org.bukkit.entity.Entity entity = event.getEntity();
        if (entity instanceof org.bukkit.entity.EnderDragon) {
            org.bukkit.entity.EnderDragon enderdragon = (org.bukkit.entity.EnderDragon) entity;
            org.bukkit.entity.Player killer = enderdragon.getKiller();
            if (killer != null) {
                org.bukkit.Bukkit.broadcastMessage("§1" + killer.getName() + "§f a tué l'§5EnderDragon§f !");
                // play sound for all players
                for (org.bukkit.entity.Player player : org.bukkit.Bukkit.getOnlinePlayers()) {
                    player.playSound(player.getLocation(), org.bukkit.Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
                }
            } else {
                org.bukkit.Bukkit.broadcastMessage("§fL'§5EnderDragon§f a été tué !");
            }
        }
    }
    //END ENDERDRAGON DEATH MESSAGE WHO KILL------------------------------------------------------------------------------------------------------

    //START WITHER DEATH MESSAGE WHO KILL------------------------------------------------------------------------------------------------------
    @EventHandler
    public void onWitherDeath(org.bukkit.event.entity.EntityDeathEvent event) {
        org.bukkit.entity.Entity entity = event.getEntity();
        if (entity instanceof org.bukkit.entity.Wither) {
            org.bukkit.entity.Wither wither = (org.bukkit.entity.Wither) entity;
            org.bukkit.entity.Player killer = wither.getKiller();
            if (killer != null) {
                org.bukkit.Bukkit.broadcastMessage("§1" + killer.getName() + "§f a tué le §0Wither§f !");
                for (org.bukkit.entity.Player player : org.bukkit.Bukkit.getOnlinePlayers()) {
                    player.playSound(player.getLocation(), org.bukkit.Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
                }
            } else {
                org.bukkit.Bukkit.broadcastMessage("§fLe §0Wither§f a été tué !");
            }
        }
    }
    //END WITHER DEATH MESSAGE WHO KILL------------------------------------------------------------------------------------------------------
    
    


    
}
        
    
   

