package cc.dreamcode.fortune.config;

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
        @Header("## Dream-Fortune (Message-Config) ##"),
        @Header("Dostepne type: (DO_NOT_SEND, CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public BukkitNotice usage = new BukkitNotice(MinecraftNoticeType.CHAT, "&7Poprawne uzycie: &c{usage}");
    public BukkitNotice noPermission = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie posiadasz uprawnien.");
    public BukkitNotice notPlayer = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie jestes graczem.");
    public BukkitNotice notNumber = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Podana liczba &cnie jest cyfra.");

    public BukkitNotice materialAlreadyExist = new BukkitNotice(MinecraftNoticeType.CHAT, "&4>> &cTen blok jest juz dodany do fortuny!");
    public BukkitNotice materialSuccessfulyAdded = new BukkitNotice(MinecraftNoticeType.CHAT, "&2>> &aPomyslnie dodales &6{material} &az fortuna &6{fortune} &a!");

    public BukkitNotice materialCannotRemove = new BukkitNotice(MinecraftNoticeType.CHAT, "&2>> &7{material} &anie jest dodany wiec nie mozesz go usunac!");
    public BukkitNotice materialSuccessfulyRemoved = new BukkitNotice(MinecraftNoticeType.CHAT, "&2>> &7{material} &azostal pomyslnie usuniety!");


    public BukkitNotice noFortuneMaterials = new BukkitNotice(MinecraftNoticeType.CHAT, "&2>> &7Na liscie nic nie ma!");
    public BukkitNotice fortuneMaterialList = new BukkitNotice(MinecraftNoticeType.CHAT, "&2>> &7Lista: {fortuneList}!");
    public BukkitNotice invalidMaterial = new BukkitNotice(MinecraftNoticeType.CHAT, "&4>> &cPodany blok jest nieprawidlowy!");






}
