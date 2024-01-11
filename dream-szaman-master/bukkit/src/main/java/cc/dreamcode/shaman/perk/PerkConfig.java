package cc.dreamcode.shaman.perk;

import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PerkConfig extends OkaeriConfig {

    public Perk healthPerk = new Perk("Perk zycia", "&c&lPerk zycia", 11, "Zwieksza zycie do: &c{value}❤",
            new MapBuilder<Integer, PerkUpgrade>()
            .put(1, new PerkUpgrade(new ItemBuilder(XMaterial.EMERALD.parseMaterial(), 5).toItemStack(), 100, 1))
            .put(2, new PerkUpgrade(new ItemBuilder(XMaterial.EMERALD.parseMaterial(), 10).toItemStack(), 200, 2))
            .put(3, new PerkUpgrade(new ItemBuilder(XMaterial.EMERALD.parseMaterial(), 15).toItemStack(),  300, 3))
            .build());

    public Perk speedPerk = new Perk("Perk szybkosci", "&f&lPerk szybkosci", 12, "Zwieksza szybkosc o: &c{value}%",
            new MapBuilder<Integer, PerkUpgrade>()
            .put(1, new PerkUpgrade(new ItemBuilder(XMaterial.EMERALD.parseMaterial(), 5).toItemStack(), 0, 5))
            .put(2, new PerkUpgrade(new ItemBuilder(XMaterial.EMERALD.parseMaterial(), 10).toItemStack(), 0, 10))
            .put(3, new PerkUpgrade(new ItemBuilder(XMaterial.EMERALD.parseMaterial(), 15).toItemStack(), 0, 15))
            .put(4, new PerkUpgrade(new ItemBuilder(XMaterial.EMERALD.parseMaterial(), 20).toItemStack(), 0, 20))
            .put(5, new PerkUpgrade(new ItemBuilder(XMaterial.EMERALD.parseMaterial(), 25).toItemStack(), 0, 25))
            .build());

    public Perk damagePerk = new Perk("Perk sily", "&4&lPerk sily", 13, "Zwieksza damage o: &c{value}%",
            new MapBuilder<Integer, PerkUpgrade>()
            .put(1, new PerkUpgrade(new ItemBuilder(XMaterial.EMERALD.parseMaterial(), 5).toItemStack(), 0, 10))
            .put(2, new PerkUpgrade(new ItemBuilder(XMaterial.EMERALD.parseMaterial(), 10).toItemStack(), 0, 20))
            .put(3, new PerkUpgrade(new ItemBuilder(XMaterial.EMERALD.parseMaterial(), 15).toItemStack(), 0, 30))
            .put(4, new PerkUpgrade(new ItemBuilder(XMaterial.EMERALD.parseMaterial(), 20).toItemStack(), 0, 40))
            .put(5, new PerkUpgrade(new ItemBuilder(XMaterial.EMERALD.parseMaterial(), 25).toItemStack(), 0, 50))
            .build());

    public Perk lifeStealPerk = new Perk("Perk wampiryzmu", "&6&lPerk wampiryzmu", 14, "Zwieksza wampiryzm o: &c{value}%",
            new MapBuilder<Integer, PerkUpgrade>()
            .put(1, new PerkUpgrade(new ItemBuilder(XMaterial.EMERALD.parseMaterial(), 5).toItemStack(), 0, 10))
            .put(2, new PerkUpgrade(new ItemBuilder(XMaterial.EMERALD.parseMaterial(), 10).toItemStack(), 0, 20))
            .build());

    public Perk fallPerk = new Perk("Perk upadku", "&b&lPerk updaku", 15, "Zwieksza sznse na unikniecie fall damage o: &c{value}%",
            new MapBuilder<Integer, PerkUpgrade>()
            .put(1, new PerkUpgrade(new ItemBuilder(XMaterial.EMERALD.parseMaterial(), 5).toItemStack(), 0, 20))
            .put(2, new PerkUpgrade(new ItemBuilder(XMaterial.EMERALD.parseMaterial(), 10).toItemStack(), 0, 40))
            .put(3, new PerkUpgrade(new ItemBuilder(XMaterial.EMERALD.parseMaterial(), 15).toItemStack(), 0, 60))
            .put(4, new PerkUpgrade(new ItemBuilder(XMaterial.EMERALD.parseMaterial(), 20).toItemStack(), 0, 80))
            .put(5, new PerkUpgrade(new ItemBuilder(XMaterial.EMERALD.parseMaterial(), 25).toItemStack(), 0, 100))
            .build());


}
