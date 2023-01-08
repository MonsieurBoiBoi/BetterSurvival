package com.bettersurvival.bettersurvival;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;



public class Commands implements org.bukkit.command.CommandExecutor{
    double voteCount = 0;
    boolean voteStart = false;
    double votePercentage = voteCount / Bukkit.getOnlinePlayers().size();

    private final Plugin plugin;
    
    public Commands(Plugin plugin) {
        this.plugin = plugin;
        
    }

    List<org.bukkit.entity.Player> votesPlayers = new ArrayList<org.bukkit.entity.Player>();
    @Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (sender instanceof org.bukkit.entity.Player) {
            org.bukkit.entity.Player player = (org.bukkit.entity.Player) sender;
            //START PLUIE------------------------------------------------------------------------------------------------------
            if(command.getName().equalsIgnoreCase("rain")){
                if (player.getWorld().hasStorm() == false) {
                    player.sendMessage("[Bettersurvival]§c§lIl ne pleut pas.");
                    votesPlayers.clear();
                } else
                {
                if (votesPlayers.contains(player)) {
                    player.sendMessage("[Bettersurvival]Vous avez déjà voté pour passer à la prochaine pluie.");
                } else {
                
                    votesPlayers.add(player);
                if (voteStart == false) {
                    voteStart = true;
                    Bukkit.broadcastMessage("§a§l" + player.getName() + " a demandé à ce que la pluie cesse. Pour voter, faites /rain");
                    voteCount++;
                    
                }  
                    if (voteStart == true) {
                        if (voteCount >= plugin.getConfig().getInt("Vote.Rain.value")) {
                            Bukkit.broadcastMessage("§a§lLa pluie a cessé.");
                            player.getWorld().setStorm(false);
                            player.getWorld().setThundering(false);
                            voteCount = 0;
                            voteStart = false;
                            votesPlayers.clear();
                        } else {
                            Bukkit.broadcastMessage("§a§l" + player.getName() + "§c§la voté pour passez la pluie.");
                            voteCount++;
                        }
                    }
                }
            }
        }
        //END PLUIE------------------------------------------------------------------------------------------------------ 

        //START NUIT------------------------------------------------------------------------------------------------------
        if(command.getName().equalsIgnoreCase("night")){
            if (player.getWorld().getTime() < 13000) {
                player.sendMessage("[Bettersurvival]§c§lIl fait pas nuit.");
                votesPlayers.clear();
            } else
            {
            if (votesPlayers.contains(player)) {
                player.sendMessage("[Bettersurvival]Vous avez déjà voté pour passer à la prochaine nuit.");
            } else {
            
                votesPlayers.add(player);
            if (voteStart == false) {
                voteStart = true;
                Bukkit.broadcastMessage("§a§l" + player.getName() + " a demandé à ce que la nuit cesse. Pour voter, faites /night");
                voteCount++;
                
            }  
                if (voteStart == true) {
                    if (voteCount >= plugin.getConfig().getInt("Vote.Night.value")) {
                        Bukkit.broadcastMessage("§a§lLa nuit a cessé.");
                        player.getWorld().setTime(0);
                        voteCount = 0;
                        voteStart = false;
                        votesPlayers.clear();
                    } else {
                        Bukkit.broadcastMessage("§a§l" + player.getName() + "§c§la voté pour passez la nuit.");
                        voteCount++;
                    }
                }
            }
        }
    }
    //END NUIT------------------------------------------------------------------------------------------------------
    //START DEATH------------------------------------------------------------------------------------------------------
    if(command.getName().equalsIgnoreCase("death")){
        if (EventDeath.lastPositions.containsKey(player.getUniqueId())) {
            org.bukkit.Location location = EventDeath.lastPositions.get(player.getUniqueId());
            player.sendMessage("§c§lVotre dernière mort se trouve en " + location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ());
        } else {
            player.sendMessage("§c§lVous n'avez jamais été mort.");
        }
        
    }
    //END DEATH------------------------------------------------------------------------------------------------------

    //START SUICIDE------------------------------------------------------------------------------------------------------
    if(command.getName().equalsIgnoreCase("suicide")){
        player.setHealth(0);
        Bukkit.broadcastMessage(player.getName() + " s'est suicidé...");
    }
    //END SUICIDE------------------------------------------------------------------------------------------------------------

}
        return true;

    }

}
