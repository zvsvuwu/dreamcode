package cc.dreamcode.shaman.perk;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class Perk {

    private String name;
    private String guiName;
    private int slot;
    private String perkDesc;
    private Map<Integer, PerkUpgrade> upgrades;

}
