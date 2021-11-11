package me.conutik.lobby;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Objects;

public class gui implements CommandExecutor {

    public static FileConfiguration config;
    public static Inventory inv;

    public static void setConfig(FileConfiguration m) {
        config = m;
        createInv();
    }

    public static void createInv() {
        inv = Bukkit.createInventory(null, 45, ChatColor.YELLOW + "" + ChatColor.BOLD + ChatColor.UNDERLINE + "Lobby Selector");


        inv.setItem(21, createGuiItem(Material.getMaterial(Objects.requireNonNull(config.getString("firstItem"))), ChatColor.WHITE + "" + ChatColor.BOLD + ChatColor.UNDERLINE + "Lobby 1", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Click to join Lobby 1"));

        inv.setItem(23, createGuiItem(Material.getMaterial(Objects.requireNonNull(config.getString("secondItem"))), ChatColor.WHITE + "" + ChatColor.BOLD + ChatColor.UNDERLINE + "Lobby 2", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Click to join Lobby 2"));

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "This command is only for players!");
            return false;
        }

        final Player player = (Player) sender;


        player.openInventory(inv);
        return true;
    }

    protected static ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        assert meta != null;
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }
}
