package com.bettersurvival.bettersurvival;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;

public class Commands implements org.bukkit.command.CommandExecutor {

    private final Plugin plugin;
    
    public Commands(Plugin plugin) {
        this.plugin = plugin;
    }
    double voteCount = 0;
    boolean voteStart = false;
    
    int onlinePlayers = Bukkit.getOnlinePlayers().size();

  List<org.bukkit.entity.Player> votesPlayers = new ArrayList<org.bukkit.entity.Player>();

  org.bukkit.event.player.PlayerCommandPreprocessEvent event;
  @Override
  public boolean onCommand(
    org.bukkit.command.CommandSender sender,
    org.bukkit.command.Command command,
    String label,
    String[] args
  ) {
    if (sender instanceof org.bukkit.entity.Player) {
      org.bukkit.entity.Player player = (org.bukkit.entity.Player) sender;
      //START PLUIE------------------------------------------------------------------------------------------------------
      if (command.getName().equalsIgnoreCase("rain")) {
        if (player.getWorld().hasStorm() == false) {
            if (plugin.getConfig().getString("Language").equals("fr") == true){
                player.sendMessage("[Bettersurvival]§cIl ne pleut pas.");
            } else {
                player.sendMessage("[Bettersurvival]§cIt's not raining.");
            }
          votesPlayers.clear();
        } else {
          if (votesPlayers.contains(player)) {
            if (plugin.getConfig().getString("Language").equals("fr") == true){
                player.sendMessage("[Bettersurvival]§cVous avez déjà voté pour passer à la prochaine pluie.");
            } else {
                player.sendMessage("[Bettersurvival]§cYou have already voted to go to the next rain.");
            }
          } else {
            votesPlayers.add(player);
            if (voteStart == false) {
              voteStart = true;
              if (plugin.getConfig().getString("Language").equals("fr") == true)
                Bukkit.broadcastMessage(
                  "§a" +
                  player.getName() +
                  " a demandé à ce que la pluie cesse. Pour voter, faites /rain"
                );
              else {
                Bukkit.broadcastMessage(
                  "§a" +
                  player.getName() +
                  " asked to stop the rain. To vote, do /rain"
                );}
              voteCount++;
            }
            if (voteStart == true) {
              if ((voteCount >= plugin.getConfig().getInt("Vote.Rain.value") && plugin.getConfig().getBoolean("Vote.Rain.Percentage.Use") == false)) {
                if (plugin.getConfig().getString("Language").equals("fr") == true)
                  Bukkit.broadcastMessage("§aLa pluie a cessé.");
                else {
                  Bukkit.broadcastMessage("§aThe rain has stopped.");
                }
                player.getWorld().setStorm(false);
                player.getWorld().setThundering(false);
                voteCount = 0;
                voteStart = false;
                votesPlayers.clear();
              } else
              if ((voteCount > (plugin.getConfig().getInt("Vote.Rain.Percentage.value") * onlinePlayers / 100 ) && plugin.getConfig().getBoolean("Vote.Rain.Percentage.Use") == true)) {
                if (plugin.getConfig().getString("Language").equals("fr") == true)
                {Bukkit.broadcastMessage("§aLa pluie a cessé.");}
                else {
                  Bukkit.broadcastMessage("§aThe rain has stopped.");
                }
                player.getWorld().setStorm(false);
                player.getWorld().setThundering(false);
                voteCount = 0;
                voteStart = false;
                votesPlayers.clear();
              }
              else {
                if (plugin.getConfig().getString("Language").equals("fr") == true)
                Bukkit.broadcastMessage(
                  "§a" + player.getName() + "§c a voté pour passez la pluie."
                );
                else {
                  Bukkit.broadcastMessage(
                    "§a" + player.getName() + "§c voted to skip the rain."
                  );
                }
                
                voteCount++;
              }
            }
          }
        }
      }
      //END PLUIE------------------------------------------------------------------------------------------------------

      //START NIGHT------------------------------------------------------------------------------------------------------
      if (command.getName().equalsIgnoreCase("night")) {
        if (player.getWorld().getTime() < 13000) {
            if (plugin.getConfig().getString("Language").equals("fr") == true)
            player.sendMessage("[Bettersurvival]§cIl fait pas nuit.");
            else {
                player.sendMessage("[Bettersurvival]§cIt's not night.");
            }
          votesPlayers.clear();
        } else {
          if (votesPlayers.contains(player)) {
            if (plugin.getConfig().getString("Language").equals("fr") == true)
            player.sendMessage("[Bettersurvival]§cVous avez déjà voté pour passer à la prochaine nuit.");
            else {
                player.sendMessage("[Bettersurvival]§cYou have already voted to go to the next night.");
            }
          } else {
            votesPlayers.add(player);
            if (voteStart == false) {
              voteStart = true;
              if (plugin.getConfig().getString("Language").equals("fr") == true)
                Bukkit.broadcastMessage(
                    "§a" +
                    player.getName() +
                    " a demandé à ce que la nuit cesse. Pour voter, faites /night"
                );
                else {
                    Bukkit.broadcastMessage(
                    "§a" +
                    player.getName() +
                    " asked to stop the night. To vote, do /night"
                    );
                }
              voteCount++;
            }
            if (voteStart == true) {
              if ((voteCount >= plugin.getConfig().getInt("Vote.Night.value") && plugin.getConfig().getBoolean("Vote.Night.Percentage.Use") == false)) {
                if (plugin.getConfig().getString("Language").equals("fr") == true) {
                    Bukkit.broadcastMessage("§aLa nuit a cessé.");
                } else {
                    Bukkit.broadcastMessage("§aThe night has stopped.");
                }
                player.getWorld().setTime(0);
                voteCount = 0;
                voteStart = false;
                votesPlayers.clear();
              } if ((voteCount > (plugin.getConfig().getInt("Vote.Night.Percentage.value") * onlinePlayers / 100 ) && plugin.getConfig().getBoolean("Vote.Night.Percentage.Use") == true)) {
                if (plugin.getConfig().getString("Language").equals("fr") == true) {
                    Bukkit.broadcastMessage("§aLa nuit a cessé.");
                } else {
                    Bukkit.broadcastMessage("§aThe night has stopped.");
                }
                player.getWorld().setTime(0);
                voteCount = 0;
                voteStart = false;
                votesPlayers.clear();
              }
              else {
                if (plugin.getConfig().getString("Language").equals("fr") == true)
                {Bukkit.broadcastMessage(
                  "§a" + player.getName() + "§c a voté pour passez la nuit."
                );}
                else {
                    Bukkit.broadcastMessage(
                    "§a" + player.getName() + "§c voted to skip the night."
                    );
                }
                voteCount++;
              }
            }
          }
        }
      }
      //END NIGHT------------------------------------------------------------------------------------------------------
      //START DEATH------------------------------------------------------------------------------------------------------
      if (command.getName().equalsIgnoreCase("death")) {
        if (EventDeath.lastPositions.containsKey(player.getUniqueId())) {
          org.bukkit.Location location = EventDeath.lastPositions.get(
            player.getUniqueId()
          );
          if (plugin.getConfig().getString("Language").equals("fr") == true) {
            player.sendMessage(
              "§cVotre dernière mort se trouve en X:" +
              location.getBlockX() +
              " Y:" +
              location.getBlockY() +
              " Z:" +
              location.getBlockZ()
            );
          } else {
            player.sendMessage(
              "§cYour last death is at X:" +
              location.getBlockX() +
              " Y:" +
              location.getBlockY() +
              " Z:" +
              location.getBlockZ()
            );
          }
        } else {
            if (plugin.getConfig().getString("Language").equals("fr") == true) {
                player.sendMessage("§cVous n'avez jamais été mort.");
            } else {
                player.sendMessage("§cYou have never been dead.");
            }
        }
      }
      //END DEATH------------------------------------------------------------------------------------------------------

      //START SUICIDE------------------------------------------------------------------------------------------------------
      if (command.getName().equalsIgnoreCase("suicide")) {
        player.setHealth(0);
        if (plugin.getConfig().getString("Language").equals("fr") == true)
        {
            Bukkit.broadcastMessage(player.getName() + " s'est suicidé...");
        }
        else {
            Bukkit.broadcastMessage(player.getName() + " committed suicide");
        }
      }
      //END SUICIDE------------------------------------------------------------------------------------------------------------
    
    }
    return true;

}
}

