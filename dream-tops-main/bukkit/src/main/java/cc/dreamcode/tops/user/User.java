package cc.dreamcode.tops.user;

import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import eu.okaeri.persistence.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class User extends Document {

    private String name;

    private int minedblocks = 0;
    private int minediamonds = 0;
    private int minedemeralds = 0;
    private int minedgold = 0;
    private int minediron = 0;
    private int minedlapis = 0;
    private int deaths = 0;
    private int eatenkoxapple = 0;
    private int eatengapple = 0;
    private int kills = 0;
    private int points = 0;

    public UUID getUniqueId() {
        return this.getPath().toUUID();
    }

}