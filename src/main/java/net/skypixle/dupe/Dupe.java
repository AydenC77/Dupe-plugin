package net.skypixle.dupe;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.ShulkerBox;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;

public final class Dupe extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("makeundupable").setExecutor(new undupableCommand(this));

        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("dupe")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                int dupecooldown = 5;
                boolean dupecooldownbool = false;

                ItemStack helditem = player.getEquipment().getItemInMainHand();
                ItemMeta meta = helditem.getItemMeta();
                if (!helditem.equals(Material.AIR)) {
                    if (args.length == 0) {
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
                                        } else if (item != null && item.getItemMeta() != null && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(this, "undupable"), PersistentDataType.BOOLEAN)) {
                                            boolean undupable = item.getItemMeta() != null && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(this, "undupable"), PersistentDataType.BOOLEAN);
                                            if (undupable == true) {
                                                player.sendMessage(ChatColor.RED + "Sorry you cannot dupe that item.");
                                                hasBlockedItem = true;
                                                break;
                                            }
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
                                } else if (meta != null && meta.getPersistentDataContainer().has(new NamespacedKey(this, "undupable"), PersistentDataType.BOOLEAN)) {
                                    boolean undupable = meta != null && meta.getPersistentDataContainer().has(new NamespacedKey(this, "undupable"), PersistentDataType.BOOLEAN);
                                    if (undupable == true) {
                                        player.sendMessage(ChatColor.RED + "Sorry you cannot dupe that item.");
                                    }
                                } else {
                                    player.sendMessage(ChatColor.GREEN + "Duped");
                                    player.getInventory().addItem(helditem);
                                }
                            }
                        } else {
                            List<String> blockedItems = getConfig().getStringList("Static-blocked-items");
                            if (blockedItems.contains(helditem.getType().toString())) {
                                player.sendMessage(ChatColor.RED + "Sorry you cannot dupe that item.");
                            } else if (meta != null && meta.getPersistentDataContainer().has(new NamespacedKey(this, "undupable"), PersistentDataType.BOOLEAN)) {
                                boolean undupable = meta != null && meta.getPersistentDataContainer().has(new NamespacedKey(this, "undupable"), PersistentDataType.BOOLEAN);
                                if (undupable == true) {
                                    player.sendMessage(ChatColor.RED + "Sorry you cannot dupe that item.");
                                }
                            } else {
                                player.sendMessage(ChatColor.GREEN + "Duped");
                                player.getInventory().addItem(helditem);
                            }
                        }
                    } else {
                        if (isInteger(args[0])) {
                            int times = Integer.parseInt(args[0]);
                            if (times <= 1000) {
                                while (times > 0) {
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
                                                    } else if (item.getItemMeta() != null && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(this, "undupable"), PersistentDataType.BOOLEAN)) {
                                                        boolean undupable = item.getItemMeta() != null && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(this, "undupable"), PersistentDataType.BOOLEAN);
                                                        if (undupable == true) {
                                                            player.sendMessage(ChatColor.RED + "Sorry you cannot dupe that item.");
                                                            hasBlockedItem = true;
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (!hasBlockedItem) {
                                                    player.getInventory().addItem(helditem);
                                                } else {
                                                    break;
                                                }
                                            }
                                        } else {
                                            List<String> blockedItems = getConfig().getStringList("Static-blocked-items");
                                            if (blockedItems.contains(helditem.getType().toString())) {
                                                player.sendMessage(ChatColor.RED + "Sorry you cannot dupe that item.");
                                                break;
                                            } else if (meta != null && meta.getPersistentDataContainer().has(new NamespacedKey(this, "undupable"), PersistentDataType.BOOLEAN)) {
                                                boolean undupable = meta != null && meta.getPersistentDataContainer().has(new NamespacedKey(this, "undupable"), PersistentDataType.BOOLEAN);
                                                if (undupable == true) {
                                                    player.sendMessage(ChatColor.RED + "Sorry you cannot dupe that item.");
                                                }
                                            } else {
                                                player.getInventory().addItem(helditem);
                                            }
                                        }
                                    } else {
                                        List<String> blockedItems = getConfig().getStringList("Static-blocked-items");
                                        if (blockedItems.contains(helditem.getType().toString())) {
                                            player.sendMessage(ChatColor.RED + "Sorry you cannot dupe that item.");
                                            break;
                                        } else if (meta != null && meta.getPersistentDataContainer().has(new NamespacedKey(this, "undupable"), PersistentDataType.BOOLEAN)) {
                                            boolean undupable = meta != null && meta.getPersistentDataContainer().has(new NamespacedKey(this, "undupable"), PersistentDataType.BOOLEAN);
                                            if (undupable == true) {
                                                player.sendMessage(ChatColor.RED + "Sorry you cannot dupe that item.");
                                            }
                                        } else {
                                            player.getInventory().addItem(helditem);
                                        }
                                    }
                                    times--;
                                }
                                player.sendMessage(ChatColor.GREEN + "Duped");
                            } else {
                                player.sendMessage(ChatColor.RED + "Sorry you cannot dupe a item more then 1000 times.");
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "Usage: /dupe [<Number>]");
                        }
                    }
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Sorry only a player can use this command!");
            }
        }
        return super.onCommand(sender, command, label, args);
    }
}
