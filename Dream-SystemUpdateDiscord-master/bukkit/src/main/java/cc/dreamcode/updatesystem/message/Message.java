package cc.dreamcode.updatesystem.message;

import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import eu.okaeri.persistence.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class Message extends Document {

    private String message;
    private String channelId;
    private String authorName;
    private String authorId;
    private long createdAt;

    public String getId() {
        return this.getPath().getValue();
    }
}
