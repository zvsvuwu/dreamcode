package cc.dreamcode.report.config.report;

import cc.dreamcode.menu.serdes.bukkit.BukkitMenuBuilder;
import cc.dreamcode.report.builder.ItemBuilder;
import cc.dreamcode.report.builder.MapBuilder;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class ReportMenuConfig extends OkaeriConfig {

    @Comment("Ustaw wyglad menu od zglaszania gracza:")
    public BukkitMenuBuilder reportMenu = new BukkitMenuBuilder(
            "&7Zglos {nick}",
            3,
            true,
            new MapBuilder<Integer, ItemStack>()
                    .put(11, new ItemBuilder(XMaterial.BOOK.parseItem())
                            .setName("&7Zglos gracza za: &4&lCHEATY")
                            .setLore("&7Kliknij, aby &azglosic &7gracza {nick}.")
                            .toItemStack())
                    .put(12, new ItemBuilder(XMaterial.BOOK.parseItem())
                            .setName("&7Zglos gracza za: &4&lOSZUSTWO")
                            .setLore("&7Kliknij, aby &azglosic &7gracza {nick}.")
                            .toItemStack())
                    .put(13, new ItemBuilder(XMaterial.BOOK.parseItem())
                            .setName("&7Zglos gracza za: &4&lBRAK KULTURY")
                            .setLore("&7Kliknij, aby &azglosic &7gracza {nick}.")
                            .toItemStack())
                    .put(14, new ItemBuilder(XMaterial.BOOK.parseItem())
                            .setName("&7Zglos gracza za: &4&lSTALKING")
                            .setLore("&7Kliknij, aby &azglosic &7gracza {nick}.")
                            .toItemStack())
                    .put(15, new ItemBuilder(XMaterial.BOOK.parseItem())
                            .setName("&7Zglos gracza za: &4&lKOPIOWANIE")
                            .setLore("&7Kliknij, aby &azglosic &7gracza {nick}.")
                            .toItemStack())
                    .put(26, new ItemBuilder(XMaterial.BARRIER.parseItem())
                            .setName("&cAnuluj zglaszanie gracza.")
                            .toItemStack())
                    .build());

    @Comment("Oznacz sloty, ktore maja odpowiadac powodom do zglaszania graczy.")
    public Map<Integer, String> reportSlotMap = new MapBuilder<Integer, String>()
            .put(11, "cheaty")
            .put(12, "oszustwo")
            .put(13, "brak kultury")
            .put(14, "stalking")
            .put(15, "kopiowanie")
            .build();

    @Comment("Pod ktorym slotem ma sie znajdowac przycisk do anulowania zgloszenia?")
    public int cancelSlot = 26;
}
