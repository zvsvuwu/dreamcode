package cc.dreamcode.report.model.report;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Data
@RequiredArgsConstructor
public class Report {

    private final long id;

    private final String nameReporter;
    private final String nameTarget;

    private final String reason;
    private final Instant instant;

}
