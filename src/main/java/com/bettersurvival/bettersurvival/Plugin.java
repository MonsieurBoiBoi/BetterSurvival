package com.bettersurvival.bettersurvival;

import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * bettersurvival java plugin
 */
public class Plugin extends JavaPlugin
{
  private static final Logger LOGGER=Logger.getLogger("bettersurvival");

  public void onEnable()
  {
    saveDefaultConfig();
    LOGGER.info("\n[---------------------------]\n bettersurvival enabled\n[---------------------------]");
    getServer().getPluginManager().registerEvents(new Event(this), this);
    getServer().getPluginManager().registerEvents(new EventDeath(this), this);
    getServer().getPluginManager().registerEvents(new EventPVP(this), this);
    getCommand("rain").setExecutor(new Commands(this));
    getCommand("night").setExecutor(new Commands(this));
    getCommand("death").setExecutor(new Commands(this));
    getCommand("suicide").setExecutor(new Commands(this));
    
  }

  public void onDisable()
  {
    LOGGER.info("\n[---------------------------]\n bettersurvival disabled \n[---------------------------] ");
  }
}
