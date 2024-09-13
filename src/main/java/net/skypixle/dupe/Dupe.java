package net.skypixle.dupe;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Dupe extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("dupe")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                ItemStack helditem = player.getEquipment().getItemInMainHand();
                if (getConfig().getBoolean("check-shulkers") == true) {
                    if (helditem.getType().toString().contains(Material.SHULKER_BOX.toString())) {
                        BlockStateMeta blockStateMeta = (BlockStateMeta) helditem.getItemMeta();
                        List<String> blockedItems = getConfig().getStringList("Static-blocked-items");

                        if (blockStateMeta != null && blockStateMeta.getBlockState() instanceof ShulkerBox) {
                            ShulkerBox shulkerBox = (ShulkerBox) blockStateMeta.getBlockState();
                            boolean hasBlockedItem = false;

                            for (ItemStack item : shulkerBox.getInventory().getContents()) {
                                if (item != null && blockedItems.contains(item.getType().toString())) {
                                    player.sendMessage(ChatColor.RED + "Sorry you cannot dupe that item.");
                                    hasBlockedItem = true;
                                    break;
                                }
                            }
                            if (!hasBlockedItem) {
                                player.sendMessage(ChatColor.GREEN + "Duped");
                                player.getInventory().addItem(helditem);
                            }
                        }
                    } else {
                        List<String> blockedItems = getConfig().getStringList("Static-blocked-items");
                        if (blockedItems.contains(helditem.getType().toString())) {
                            player.sendMessage(ChatColor.RED + "Sorry you cannot dupe that item.");
                        } else {
                            player.sendMessage(ChatColor.GREEN + "Duped");
                            player.getInventory().addItem(helditem);
                        }
                    }
                } else {
                    List<String> blockedItems = getConfig().getStringList("Static-blocked-items");
                    if (blockedItems.contains(helditem.getType().toString())) {
                        player.sendMessage(ChatColor.RED + "Sorry you cannot dupe that item.");
                    } else {
                        player.sendMessage(ChatColor.GREEN + "Duped");
                        player.getInventory().addItem(helditem);
                    }
                }
            } else {
                sender.sendMessage("Sorry only a player can use this command!");
            }
        }
        return super.onCommand(sender, command, label, args);
    }
}
