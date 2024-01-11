package cc.dreamcode.ores.command;

import cc.dreamcode.ores.notice.NoticeSender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class ArgumentHandler implements NoticeSender, CommandPlatform {

    private final String name;
    private final int arg;

}
