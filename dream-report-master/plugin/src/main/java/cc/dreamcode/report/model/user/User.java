package cc.dreamcode.report.model.user;

import cc.dreamcode.report.model.report.Report;
import cc.dreamcode.report.storage.document.AbstractDocument;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class User extends AbstractDocument {

    private String name;

    private long reportCoolDown = 0L;
    private boolean reportToggle = false;
    private Map<Long, Report> reportMap = new HashMap<>();

    public UUID getUniqueId() {
        return this.getPath().toUUID();
    }

}
