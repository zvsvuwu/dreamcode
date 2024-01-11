package cc.dreamcode.wallet.config;

import cc.dreamcode.notice.minecraft.MinecraftNoticeType;
import cc.dreamcode.notice.minecraft.bukkit.BukkitNotice;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.Headers;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Configuration(
        child = "message.yml"
)
@Headers({
        @Header("## Dream-Wallet (Message-Config) ##"),
        @Header("Dostepne type: (DO_NOT_SEND, CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public BukkitNotice noPermission = new BukkitNotice(MinecraftNoticeType.CHAT, "&cNie posiadasz uprawnien. ({permission})");
    public BukkitNotice noPlayer = new BukkitNotice(MinecraftNoticeType.CHAT, "&cPodanego gracza nie znaleziono.");

    public BukkitNotice invalidUsage = new BukkitNotice(MinecraftNoticeType.CHAT, "&7Poprawne uzycie: &c{usage}");
    public BukkitNotice invalidCommandSender = new BukkitNotice(MinecraftNoticeType.CHAT, "&cDo wykonania tej komendy potrzebny jest: {sender}");
    public BukkitNotice invalidNumberValue = new BukkitNotice(MinecraftNoticeType.CHAT, "&cPodana liczba musi byc cyfra. Sprawdz skladnie komendy i sprobuj ponownie. ({argument}, pos: {index})");

    public BukkitNotice moneyAdded = new BukkitNotice(MinecraftNoticeType.CHAT, "&aPoprawnie &7dodano do portfela {nick} &a{added}&7. Stan portfela po operacji: &a{money}");
    public BukkitNotice moneyAddedToAll = new BukkitNotice(MinecraftNoticeType.CHAT, "&aPoprawnie &7dodano do wszystkich portfeli &a{added}");
    public BukkitNotice moneyAddedToAllBroadcast = new BukkitNotice(MinecraftNoticeType.CHAT, "&cAdministrator &7{nick} dodal do wszystkich portfeli &a{added}");

    public BukkitNotice moneyTaked = new BukkitNotice(MinecraftNoticeType.CHAT, "&aPoprawnie &7zabrano z portfela {nick} &a{taked}&7. Stan portfela po operacji: &a{money}");
    public BukkitNotice moneyTakedFromAll = new BukkitNotice(MinecraftNoticeType.CHAT, "&aPoprawnie &7zabrano ze wszystkich portfeli &a{taked}");
    public BukkitNotice moneyTakedFromAllBroadcast = new BukkitNotice(MinecraftNoticeType.CHAT, "&cAdministrator &7{nick} zabral ze wszystkich portfeli &a{taked}");

    public BukkitNotice playerBalance = new BukkitNotice(MinecraftNoticeType.CHAT, "&aGracz &2{nick} &aposiada &2{money}&a!");

    public BukkitNotice coolDownAreNotCompleted = new BukkitNotice(MinecraftNoticeType.CHAT, "&cAby odebrac nagrode, nalezy poczekac: &7{time}");

    public BukkitNotice rewardReceived = new BukkitNotice(MinecraftNoticeType.CHAT, "&7Poprawnie odebrano z nagrody: &a{money}");
    public BukkitNotice rewardReceivedBroadcast = new BukkitNotice(MinecraftNoticeType.CHAT, "&7Gracz &6{nick} &7odebral z nagrody: &a{money}");

    public BukkitNotice cannotAffordThat = new BukkitNotice(MinecraftNoticeType.CHAT, "&cNie stac cie na ten produkt! Kosztuje {cost}");
    public BukkitNotice productPurchased = new BukkitNotice(MinecraftNoticeType.CHAT, "&7Zakupiono produkt &6{name} &7za: &a{cost}&7. Stan portfela po operacji: &a{money}");
    public BukkitNotice productPurchasedBroadcast = new BukkitNotice(MinecraftNoticeType.CHAT, "&7Gracz &6{nick} &7zakupil produkt &6{name} &7za: &a{cost}&7.");
}
