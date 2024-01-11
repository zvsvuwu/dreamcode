package cc.dreamcode.report.config.report;

import cc.dreamcode.menu.serdes.bukkit.BukkitMenuBuilder;
import cc.dreamcode.report.builder.ItemBuilder;
import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PanelMenuConfig extends OkaeriConfig {

    @Comment("Ustaw konfiguracje menu panelu:")
    public BukkitMenuBuilder panelMenu = new BukkitMenuBuilder(
            "&c&lZgloszenia {nick} &7Strona: &a&l{page}&7&l",
            6,
            true,
            new ImmutableMap.Builder<Integer, ItemStack>()
                    .put(45, new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
                            .setName(" ")
                            .toItemStack())
                    .put(46, new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
                            .setName(" ")
                            .toItemStack())
                    .put(47, new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
                            .setName(" ")
                            .toItemStack())
                    .put(48, new ItemBuilder(XMaterial.RED_WOOL.parseItem())
                            .setName("&cPowrot do porzedniej strony.")
                            .setLore("&7Kliknij, aby zmienic strone.")
                            .toItemStack())
                    .put(49, new ItemBuilder(XMaterial.BARRIER.parseItem())
                            .setName("&cWyjdz z panelu zgloszen gracza {nick}.")
                            .toItemStack())
                    .put(50, new ItemBuilder(XMaterial.GREEN_WOOL.parseItem())
                            .setName("&aPrzejdz do nastepnej strony.")
                            .setLore("&7Kliknij, aby zmienic strone.")
                            .toItemStack())
                    .put(51, new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
                            .setName(" ")
                            .toItemStack())
                    .put(52, new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
                            .setName(" ")
                            .toItemStack())
                    .put(53, new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
                            .setName(" ")
                            .toItemStack())
                    .build()
    );

    @Comment("Jak ma wygladac item od kazdego zgloszenia w menu?")
    public ItemStack item = new ItemBuilder(Material.BOOK)
            .setName("&7Zgloszenie &8(&c&l{id}&8)")
            .setLore("&7Zglosil: &e{reporter}",
                    "&7Zgloszono: &e{target}",
                    "&7Powod: &a{reason}",
                    "",
                    "&7Kliknij &cLPM&7, aby &ateleportowac &7sie do gracza.",
                    "&7Kliknij &cPPM&7, aby &cusunac &7zgloszenie.")
            .toItemStack();

    @Comment("Pod ktorym slotem ma sie znajdowac przycisk od poprzedniej strony?")
    public int previousPageSlot = 48;

    @Comment("Pod ktorym slotem ma sie znajdowac przycisk od nastepnej strony?")
    public int nextPageSlot = 50;

    @Comment("Pod ktorym slotem ma sie znajdowac przycisk do anulowania zgloszenia?")
    public int cancelSlot = 49;
}
