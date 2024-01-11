package cc.dreamcode.report.config;

import cc.dreamcode.report.config.annotations.Configuration;
import cc.dreamcode.report.config.report.PanelMenuConfig;
import cc.dreamcode.report.config.report.ReportConfig;
import cc.dreamcode.report.config.report.ReportMenuConfig;
import cc.dreamcode.report.config.storage.StorageConfig;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-Report (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {
    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    public boolean debug = true;

    @Comment("Uzupelnij ponizsze menu danymi.")
    public StorageConfig storageConfig = new StorageConfig();
    public ReportConfig reportConfig = new ReportConfig();
    public ReportMenuConfig reportMenuConfig = new ReportMenuConfig();
    public PanelMenuConfig panelMenuConfig = new PanelMenuConfig();

}
