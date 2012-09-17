package tk.ultimatedev.jailplusplus.util.serialization;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * @author Sushi
 */
public class InventorySerializer {
    public static String getString(Inventory toSerialize) {
        String serialization = toSerialize.getSize() + ";";

        for (int i = 0; i < toSerialize.getSize(); i++) {
            ItemStack itemStack = toSerialize.getItem(i);

            if (itemStack != null) {
                String serializedItemStack = "";

                String itemStackType = String.valueOf(itemStack.getType().getId());
                serializedItemStack += "t@" + itemStackType;

                if (itemStack.getDurability() != 0) {
                    String itemStackDurability = String.valueOf(itemStack.getDurability());
                    serializedItemStack += ":d@" + itemStackDurability;
                }

                if (itemStack.getAmount() != 1) {
                    String itemStackAmount = String.valueOf((itemStack.getAmount()));
                    serializedItemStack += ":a@" + itemStackAmount;
                }

                Map<Enchantment, Integer> enchantments = itemStack.getEnchantments();
                if (enchantments.size() > 0) {
                    for (Map.Entry<Enchantment, Integer> enchantment : enchantments.entrySet()) {
                        serializedItemStack += ":e@" + enchantment.getKey().getId() + "@" + enchantment.getValue();
                    }
                }

                serialization += i + "#" + serializedItemStack + ";";
            }
        }
        return serialization;
    }

    public static Inventory getInventory(String toSerialize) {
        String[] serializedSlots = toSerialize.split(";");
        String inventoryInfo = serializedSlots[0];
        Inventory deSerializedInventory = Bukkit.getServer().createInventory(null, Integer.valueOf(inventoryInfo));

        for (int i = 1; i < serializedSlots.length; i++) {
            String[] serializedSlot = serializedSlots[i].split("#");
            int stackPosition = Integer.valueOf(serializedSlot[0]);

            if (stackPosition >= deSerializedInventory.getSize()) {
                continue;
            }

            ItemStack itemStack = null;
            boolean createdItemStack = false;

            String[] serializedItemStack = serializedSlot[1].split(":");
            for (String itemInfo : serializedItemStack) {
                String[] attribute = itemInfo.split("@");
                if (attribute[0].equals("t")) {
                    itemStack = new ItemStack(Material.getMaterial(Integer.valueOf(attribute[1])));
                    createdItemStack = true;
                } else if (attribute[0].equals("d") && createdItemStack) {
                    itemStack.setDurability(Short.valueOf(attribute[1]));
                } else if (attribute[0].equals("a") && createdItemStack) {
                    itemStack.setAmount(Integer.valueOf(attribute[1]));
                } else if (attribute[0].equals("e") && createdItemStack) {
                    itemStack.addEnchantment(Enchantment.getById(Integer.valueOf(attribute[1])), Integer.valueOf(attribute[2]));
                }
            }
            deSerializedInventory.setItem(stackPosition,  itemStack);
        }

        return deSerializedInventory;
    }
}