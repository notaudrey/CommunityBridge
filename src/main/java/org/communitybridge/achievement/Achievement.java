package org.communitybridge.achievement;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.communitybridge.main.BukkitWrapper;
import org.communitybridge.main.Environment;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class Achievement {
    protected Environment environment;

    protected int limit;
    protected double cashReward;
    protected Map<Material, Integer> itemRewards = new EnumMap<Material, Integer>(Material.class);

    protected BukkitWrapper bukkit = new BukkitWrapper();

    public Achievement(Environment environment) {
        this.environment = environment;
    }

    public abstract boolean playerQualifies(Player player, PlayerAchievementState state);

    public void rewardPlayer(Player player, PlayerAchievementState state) {
        if(environment.getConfiguration().economyEnabled) {
            environment.getEconomy().depositPlayer(player, cashReward);
        }

        for(Entry<Material, Integer> entry : itemRewards.entrySet()) {
            ItemStack stack = new ItemStack(entry.getKey(), entry.getValue());
            player.getInventory().addItem(stack);
        }
        if(!itemRewards.isEmpty()) {
            player.updateInventory();
        }
    }

    protected boolean canRewardAllItemRewards(Player player) {
        final Inventory testInventory = bukkit.getServer().createInventory(null, player.getInventory().getType());
        testInventory.setContents(player.getInventory().getContents());

        for(Entry<Material, Integer> entry : itemRewards.entrySet()) {
            ItemStack stack = new ItemStack(entry.getKey(), entry.getValue());
            if(!testInventory.addItem(stack).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public void load(YamlConfiguration configuration, String path) {
        limit = configuration.getInt(path + ".Limit", 1);
        cashReward = configuration.getDouble(path + ".Money", 0.0);

        ConfigurationSection itemsSection = configuration.getConfigurationSection(path + ".Items");

        if(itemsSection == null) {
            return;
        }

        Set<String> itemSet = itemsSection.getKeys(false);

        for(String key : itemSet) {
            Material material = Material.getMaterial(key);
            if(material == null) {
                environment.getLog().warning("Invalid material in achievements file");
                continue;
            }
            int amount = itemsSection.getInt(key, 1);
            itemRewards.put(material, amount);
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public double getCashReward() {
        return cashReward;
    }

    public void setCashReward(double cashReward) {
        this.cashReward = cashReward;
    }

    public Map<Material, Integer> getItemRewards() {
        return itemRewards;
    }

    public void setItemRewards(Map<Material, Integer> itemRewards) {
        this.itemRewards = itemRewards;
    }
}
