package com.bettersurvival.bettersurvival;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class Settings implements org.bukkit.command.CommandExecutor, TabCompleter {
    
    
    private final Plugin plugin;
    
    public Settings(Plugin plugin) {
        this.plugin = plugin;
        
    }
    @Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (sender instanceof org.bukkit.entity.Player) {
            org.bukkit.entity.Player player = (org.bukkit.entity.Player) sender;
            //add permission to use the command
            if (!player.hasPermission("bettersurvival.settings")) {
                player.sendMessage("[Bettersurvival]§c§lErreur: §c§lVous n'avez pas la permission d'utiliser cette commande.");
                return true;
            }
            if (command.getName().equalsIgnoreCase("settings")) {
                if(args.length == 0)
                {
                    player.sendMessage("[Bettersurvival]§c§lErreur: §c§lVeuillez spécifier un paramètre.");
                }
                // start setting pluie -------------------------------------------------
                else if (args[0].equalsIgnoreCase("rain")) {
                    if (args.length == 1) {
                        player.sendMessage("[Bettersurvival]§a§l il faut " + plugin.getConfig().getInt("Vote.Rain.value") + " joueurs pour passer la pluie.");
                    } else {
                        try {
                            plugin.getConfig().set("Vote.Rain.value",  Integer.parseInt(args[1]));
                            player.sendMessage("[Bettersurvival]§a§lLe nombre de joueurs a été modifié a: " + args[1] + " joueurs.");
                        } catch (NumberFormatException e) {
                            player.sendMessage("[Bettersurvival]§c§lErreur: §c§lLe paramètre doit être un nombre.");
                        }
                    }
                }
                // end setting pluie -------------------------------------------------
                // start setting nuit -------------------------------------------------
                else if (args[0].equalsIgnoreCase("night")) {
                    if (args.length == 1) {
                        player.sendMessage("[Bettersurvival]§a§l il faut " + plugin.getConfig().getInt("Vote.Night.value") + " joueurs pour passer la nuit.");
                    } else {
                        try {
                            plugin.getConfig().set("Vote.Night.value", Integer.parseInt(args[1]));
                            player.sendMessage("[Bettersurvival]§a§lLe nombre de joueurs a été modifié a " + args[1] + " joueurs.");
                        } catch (NumberFormatException e) {
                            player.sendMessage("[Bettersurvival]§c§lErreur: §c§lLe paramètre doit être un nombre.");
                        }
                    }
                }
                // end setting nuit -------------------------------------------------
                else
                {
                    player.sendMessage("[Bettersurvival]§c§lErreur: §c§lLe paramètre n'existe pas.");
                }
                
            }
        }
        return true;
    }

    List<String> argument = new java.util.ArrayList<String>();    
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof org.bukkit.entity.Player) {
            org.bukkit.entity.Player player = (org.bukkit.entity.Player) sender;
            //add permission to use the command
            if (!player.hasPermission("bettersurvival.settings")) {
                return null;
            }
            if (cmd.getName().equalsIgnoreCase("settings")) {
                if (args.length == 1) {
                    argument.clear();
                    argument.add("rain");
                    argument.add("night");
                    return argument;
                }
            }
        }

        return null;
    }
    
}
