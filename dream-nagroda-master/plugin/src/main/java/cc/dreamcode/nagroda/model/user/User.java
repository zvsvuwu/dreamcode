package cc.dreamcode.nagroda.model.user;

import cc.dreamcode.nagroda.storage.document.AbstractDocument;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class User extends AbstractDocument {

    private String name;
    private boolean rewardReceived;
}
