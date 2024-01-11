package cc.dreamcode.shaman.perk;

import cc.dreamcode.platform.DreamLogger;
import cc.dreamcode.shaman.config.MessageConfig;
import cc.dreamcode.shaman.config.PluginConfig;
import eu.okaeri.injector.annotation.Inject;

public class PerkManager {

    @Inject private DreamLogger dreamLogger;
    @Inject private MessageConfig messageConfig;
    @Inject private PluginConfig pluginConfig;

    public boolean validatePerks(Perk... perks) {
        for (Perk perk : perks) {
            if (!validatePerk(perk)) {
                return false;
            }
        }
        return true;
    }

    public boolean validatePerk(Perk perk) {
        int start = 0;
        int last = 0;
        for (int j : perk.getUpgrades().keySet()) {
            start++;
            if (start == j) {
                last = j;
                continue;
            }
            this.dreamLogger.error("Level " + last + " > " + j + " must be changed to: " + last + " > " + start,
                    new Exception("Wrong configuration `upgrades` for perk: " + perk.getName() + " levels must be incremented!"));
            return false;
        }
        if (this.pluginConfig.shamanMenu.getItems().getOrDefault(perk.getSlot(), null) == null) {
            this.dreamLogger.error("The shaman menu must have an item that is set in the slot: " + perk.getSlot() + "",
                    new Exception("Item not found in shaman menu for " + perk.getName() + "!"));
            return false;
        }
        return true;
    }
}
