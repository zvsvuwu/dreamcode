package cc.dreamcode.tops.user.top;

import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import cc.dreamcode.menu.bukkit.holder.DefaultMenuHolder;
import cc.dreamcode.menu.bukkit.setup.BukkitMenuPlayerSetup;
import cc.dreamcode.menu.bukkit.setup.BukkitMenuSetup;
import cc.dreamcode.menu.serdes.bukkit.BukkitMenuBuilder;
import cc.dreamcode.platform.bukkit.exception.BukkitPluginException;
import cc.dreamcode.tops.config.PluginConfig;
import cc.dreamcode.tops.user.User;
import cc.dreamcode.tops.user.UserManager;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class TopMenu implements BukkitMenuPlayerSetup {

    private @Inject PluginConfig pluginConfig;
    private @Inject TopManager topManager;
    private @Inject UserManager userManager;


    @Override
    public BukkitMenu build(HumanEntity humanEntity) {

        final BukkitMenuBuilder menuBuilder = pluginConfig.topMenuBuilder;
        final BukkitMenu bukkitMenu = menuBuilder.buildWithItem();

        // WYKOPANE BLOKI

        List<User> topBlocksMinedUsers = userManager.getUsersByTop(UserData.BLOCKMINED, pluginConfig.blocksMinedTopSize);
        Map<String, Object> blocksPlaceholders = countTop(topBlocksMinedUsers, pluginConfig.blocksMinedTopSize, pluginConfig.blocksMinedTopPlaceHolder, pluginConfig.blocksMinedTopNonePlaceHolder, pluginConfig.blocksMinedTopAmountPlaceHolder, UserData.BLOCKMINED);

        ItemStack minedBlocks = new ItemBuilder(pluginConfig.blocksBrokenTopItem)
                .fixColors(blocksPlaceholders)
                .toItemStack();
        bukkitMenu.setItem(pluginConfig.blocksMinedTopSlot, minedBlocks);

        // WYKOPANE DIAXY

        List<User> topDiamondsMinedUsers = userManager.getUsersByTop(UserData.DIAMONDMINED, pluginConfig.diamondsMinedTopSize);
        Map<String, Object> diamondPlaceholders = countTop(topDiamondsMinedUsers, pluginConfig.diamondsMinedTopSize, pluginConfig.diamondsMinedTopPlaceHolder, pluginConfig.diamondsMinedTopNonePlaceHolder, pluginConfig.diamondsMinedTopAmountPlaceHolder, UserData.DIAMONDMINED);

        ItemStack minedDiamonds = new ItemBuilder(pluginConfig.diamondsBrokenTopItem)
                .fixColors(diamondPlaceholders)
                .toItemStack();
        bukkitMenu.setItem(pluginConfig.diamondsMinedTopSlot, minedDiamonds);

        // WYKOPANE EMERALDY

        List<User> topEmeraldsMinedUsers = userManager.getUsersByTop(UserData.EMERALDMINED, pluginConfig.emeraldsMinedTopSize);
        Map<String, Object> emeraldPlaceholders = countTop(topEmeraldsMinedUsers, pluginConfig.emeraldsMinedTopSize, pluginConfig.emeraldsMinedTopPlaceHolder, pluginConfig.emeraldsMinedTopNonePlaceHolder, pluginConfig.emeraldsMinedTopAmountPlaceHolder, UserData.EMERALDMINED);

        ItemStack minedEmeralds = new ItemBuilder(pluginConfig.emeraldsBrokenTopItem)
                .fixColors(emeraldPlaceholders)
                .toItemStack();
        bukkitMenu.setItem(pluginConfig.emeraldsMinedTopSlot, minedEmeralds);

        // WYKOPANE ZLOTO

        List<User> topGoldMinedUsers = userManager.getUsersByTop(UserData.GOLDMINED, pluginConfig.goldMinedTopSize);
        Map<String, Object> goldPlaceholders = countTop(topGoldMinedUsers, pluginConfig.goldMinedTopSize, pluginConfig.goldMinedTopPlaceHolder, pluginConfig.goldMinedTopNonePlaceHolder, pluginConfig.goldMinedTopAmountPlaceHolder, UserData.GOLDMINED);

        ItemStack minedGold = new ItemBuilder(pluginConfig.goldBrokenTopItem)
                .fixColors(goldPlaceholders)
                .toItemStack();
        bukkitMenu.setItem(pluginConfig.goldMinedTopSlot, minedGold);

        // WYKOPANE ZELAZO

        List<User> topIronMinedUsers = userManager.getUsersByTop(UserData.IRONMINED, pluginConfig.ironMinedTopSize);
        Map<String, Object> ironMinedPlaceholders = countTop(topIronMinedUsers, pluginConfig.ironMinedTopSize, pluginConfig.ironMinedTopPlaceHolder, pluginConfig.ironMinedTopNonePlaceHolder, pluginConfig.ironMinedTopAmountPlaceHolder, UserData.IRONMINED);

        ItemStack ironMined = new ItemBuilder(pluginConfig.ironBrokenTopItem)
                .fixColors(ironMinedPlaceholders)
                .toItemStack();
        bukkitMenu.setItem(pluginConfig.ironMinedTopSlot, ironMined);


        //WYKOPANY LAPIS

        List<User> topLapisMinedUsers = userManager.getUsersByTop(UserData.LAPISMINED, pluginConfig.lapisMinedTopSize);
        Map<String, Object> lapisPlaceholders = countTop(topLapisMinedUsers, pluginConfig.lapisMinedTopSize, pluginConfig.lapisMinedTopPlaceHolder, pluginConfig.lapisMinedTopNonePlaceHolder, pluginConfig.lapisMinedTopAmountPlaceHolder, UserData.LAPISMINED);

        ItemStack minedLapis = new ItemBuilder(pluginConfig.lapisBrokenTopItem)
                .fixColors(lapisPlaceholders)
                .toItemStack();
        bukkitMenu.setItem(pluginConfig.lapisMinedTopSlot, minedLapis);

        //SMIERCI

        List<User> topDeathsUsers = userManager.getUsersByTop(UserData.DEATHS, pluginConfig.deathsTopSize);
        Map<String, Object> deathsPlaceholders = countTop(topDeathsUsers, pluginConfig.deathsTopSize, pluginConfig.deathsTopPlaceHolder, pluginConfig.deathsTopNonePlaceHolder, pluginConfig.deathsTopAmountPlaceHolder, UserData.DEATHS);

        ItemStack deaths = new ItemBuilder(pluginConfig.deathsTopItem)
                .fixColors(deathsPlaceholders)
                .toItemStack();
        bukkitMenu.setItem(pluginConfig.deathsTopSlot, deaths);

        //ZJEDZONE REFY

        List<User> topGoldEatenUsers = userManager.getUsersByTop(UserData.GOLDEATEN, pluginConfig.goldEatenTopSize);
        Map<String, Object> goldEatenPlaceholders = countTop(topGoldEatenUsers, pluginConfig.goldEatenTopSize, pluginConfig.goldEatenTopPlaceHolder, pluginConfig.goldEatenTopNonePlaceHolder, pluginConfig.goldEatenTopAmountPlaceHolder, UserData.GOLDEATEN);

        ItemStack goldEaten = new ItemBuilder(pluginConfig.goldEatenTopItem)
                .fixColors(goldEatenPlaceholders)
                .toItemStack();
        bukkitMenu.setItem(pluginConfig.goldEatenTopSlot, goldEaten);

        //ZJEDZONE KOXY

        List<User> topKGoldEatenUsers = userManager.getUsersByTop(UserData.KGOLDEATEN, pluginConfig.kgoldEatenTopSize);
        Map<String, Object> kgoldEatenPlaceholders = countTop(topKGoldEatenUsers, pluginConfig.kgoldEatenTopSize, pluginConfig.kgoldEatenTopPlaceHolder, pluginConfig.kgoldEatenTopNonePlaceHolder, pluginConfig.kgoldEatenTopAmountPlaceHolder, UserData.KGOLDEATEN);

        ItemStack kgoldEaten = new ItemBuilder(pluginConfig.kgoldEatenTopItem)
                .fixColors(kgoldEatenPlaceholders)
                .toItemStack();
        bukkitMenu.setItem(pluginConfig.kgoldEatenTopSlot, kgoldEaten);

        //ZABOJSTWA

        List<User> topKillsUsers = userManager.getUsersByTop(UserData.KILLS, pluginConfig.killsTopSize);
        Map<String, Object> killsPlaceholders = countTop(topKillsUsers, pluginConfig.killsTopSize, pluginConfig.killsTopPlaceHolder, pluginConfig.killsTopNonePlaceHolder, pluginConfig.killsTopAmountPlaceHolder, UserData.KILLS);

        ItemStack kills = new ItemBuilder(pluginConfig.killsTopItem)
                .fixColors(killsPlaceholders)
                .toItemStack();
        bukkitMenu.setItem(pluginConfig.killsTopSlot, kills);

        //OGOLNE STATY

        User user = userManager.getUserByPlayer((Player) humanEntity);

        ItemStack stats = new ItemBuilder(pluginConfig.statsItem)
                .fixColors(new MapBuilder<String, Object>()
                        .put(pluginConfig.blocksMinedPlaceHolder, user.getMinedblocks())
                        .put(pluginConfig.diamondsMinedPlaceHolder, user.getMinediamonds())
                        .put(pluginConfig.emeraldsMinedPlaceHolder, user.getMinedemeralds())
                        .put(pluginConfig.goldMinedPlaceHolder, user.getMinedgold())
                        .put(pluginConfig.ironMinedPlaceHolder, user.getMinediron())
                        .put(pluginConfig.lapisMinedPlaceHolder, user.getMinedlapis())
                        .put(pluginConfig.deathsPlaceHolder, user.getDeaths())
                        .put(pluginConfig.kgoldEatenPlaceHolder, user.getEatenkoxapple())
                        .put(pluginConfig.goldEatenPlaceHolder, user.getEatengapple())
                        .put(pluginConfig.killsPlaceHolder, user.getKills())
                        .build())
                .toItemStack();
        bukkitMenu.setItem(pluginConfig.statsSlot, stats);

        return bukkitMenu;
    }

    public static Map<String, Object> countTop(List<User> playerList, int topSize, String topPlaceholderString, String topPlaceholderEmpty, String topPlaceholderAmount, UserData userData) {
        Map<String, Object> placeholders = new HashMap<>();
        for (int i = 0; i < playerList.size(); i++) {
            String topNumber = topPlaceholderString + (i + 1);
            String playerName = playerList.get(i).getName();
            placeholders.put(topNumber, playerName);

            double userDataValue = userData.getFunction().applyAsDouble(playerList.get(i));
            String userDataAmountNumber = topPlaceholderAmount + (i + 1);
            placeholders.put(userDataAmountNumber, (int) userDataValue);
        }
        for (int i = playerList.size(); i < topSize; i++) {
            String topNumber = topPlaceholderString + (i + 1);
            placeholders.put(topNumber, topPlaceholderEmpty);

            String userDataAmountNumber = topPlaceholderAmount + (i + 1);
            placeholders.put(userDataAmountNumber, 0);
        }
        return placeholders;
    }
}