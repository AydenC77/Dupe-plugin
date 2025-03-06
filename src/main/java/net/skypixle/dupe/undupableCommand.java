package net.skypixle.dupe;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class undupableCommand implements CommandExecutor {
    private final JavaPlugin plugin;

    public undupableCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("makeundupable")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                ItemStack item = player.getEquipment().getItemInMainHand();

                ItemMeta meta = item.getItemMeta();
                if (meta != null) {
                    meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "undupable"), PersistentDataType.BOOLEAN, (boolean) true);
                    List<String> lore = meta.getLore();
                    if (lore == null) {
                        lore = new ArrayList<>();
                    }
                    lore.add(ChatColor.BLUE + "Undupable");
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                }
            }
        }
        return true;
    }
}
