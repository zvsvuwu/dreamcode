package com.eripe14.prestige.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import xyz.ravis96.dreambasis.bukkit.notice.Notice;

@Header({"## DreamCode-Prestiże (Message-Config) ##",
        "Dostępne typy: (CHAT, ACTION_BAR, SUBTITLE, TITLE, SIDEBAR, BOSSBAR)",
        " "})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    @Comment("[#] Wiadomość, gdy gracz nie ma permisji do wykonania komendy.")
    public Notice noPermission = new Notice(Notice.Type.ACTION_BAR, "&4Nie masz permisji do wykonania tej komendy!");

    @Comment("[#] Wiadomość, gdy gracz ma za mało rankingu by zdobyć nowy prestiż.")
    public Notice tooLittleAmountOfRanking = new Notice(Notice.Type.CHAT, "&cMasz za mało rankingu, by zdobyć nowy prestiż. Potrzebujesz go minimum &4%AMOUNT%&c.");

    @Comment("[#] Wiadomość, gdy gracz pierwszy raz wpisze komendę do prestiży, oraz spełnia wszystkie warunki.")
    public Notice firstMessage = new Notice(Notice.Type.ACTION_BAR, "&7By zdobyć nowy prestiż wpisz komendę jeszcze raz!");

    @Comment("[#] Wiadomość, gdy graczu wygaśnie możliwość odebrania prestiżu.")
    public Notice prestigePossibilityExpired = new Notice(Notice.Type.CHAT, "&7Możliwość zdobycia prestiżu wygasła, wpisz komendę jeszcze raz by ponowić możliwość odebrania prestiżu.");

    @Comment("[#] Wiadomość, gdy gracz odblokuje nowy prestiż.")
    public Notice gotNewPrestige = new Notice(Notice.Type.CHAT, "&2Zdobyłeś nowy prestiż! Twój aktualny prestiż to &e%PRESTIGE%&2.");

}
