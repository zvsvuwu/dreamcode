package cc.dreamcode.report.model.report;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;

import java.time.Instant;

public class ReportSerdes implements ObjectSerializer<Report> {
    /**
     * @param type the type checked for compatibility
     * @return {@code true} if serializer is able to process the {@code type}
     */
    @Override
    public boolean supports(@NonNull Class<? super Report> type) {
        return Report.class.isAssignableFrom(type);
    }

    /**
     * @param object   the object to be serialized
     * @param data     the serialization data
     * @param generics the generic information about the {@code object}
     */
    @Override
    public void serialize(@NonNull Report object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("id-number", object.getId());
        data.add("name-reporter", object.getNameReporter());
        data.add("name-target", object.getNameTarget());
        data.add("reason", object.getReason());
        data.add("instant", object.getInstant());
    }

    /**
     * @param data     the source deserialization data
     * @param generics the target generic type for the {@code data}
     * @return the deserialized object
     */
    @Override
    public Report deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new Report(
                data.get("id-number", Long.class),
                data.get("name-reporter", String.class),
                data.get("name-target", String.class),
                data.get("reason", String.class),
                data.get("instant", Instant.class)
        );
    }
}
