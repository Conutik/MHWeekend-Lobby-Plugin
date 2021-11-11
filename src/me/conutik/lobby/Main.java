package me.conutik.lobby;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin implements Listener {



    PluginDescriptionFile info = this.getDescription();

    public FileConfiguration config = getConfig();


    @Override
    public void onEnable() {
        gui.setConfig(config);
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Lockers v" + info.getVersion() + " is now Enabled");
        config.addDefault("firstItem", "compass");
        config.addDefault("firstItemLocked", false);
        config.addDefault("firstCommand", "send lobby1");
        config.addDefault("secondItem", "barrier");
        config.addDefault("secondItemLocked", false);
        config.addDefault("secondCommand", "send lobby2");

        config.options().copyDefaults(true);
        saveConfig();

        getCommand("lobbyselector").setExecutor(new gui());

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Lockers v" + info.getVersion() + " is now Disabled");
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getInventory() != gui.inv) return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        final Player p = (Player) e.getWhoClicked();

        // Using slots click is a best option for your inventory click's
//        p.sendMessage("You clicked at slot " + e.getRawSlot());
        String ts = null;
        if(e.getRawSlot() == 21) ts = "firstCommand";
        if(e.getRawSlot() == 23) ts = "secondCommand";
        if(ts == null) return;

        p.performCommand(config.getString(ts));
    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().equals(gui.inv)) {
            e.setCancelled(true);
        }
    }
}
