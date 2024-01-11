package cc.dreamcode.dropsmp.exception;

import cc.dreamcode.dropsmp.features.notice.Notice;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public final class PluginValidationException extends RuntimeException {

    @Getter private final Notice notice;
    @Getter private Map<String, Object> replaceMap = new HashMap<>();

    public PluginValidationException(Notice notice) {
        this.notice = notice;
    }

    public PluginValidationException(Notice notice, Map<String, Object> replaceMap) {
        this.notice = notice;
        this.replaceMap = replaceMap;
    }

}
