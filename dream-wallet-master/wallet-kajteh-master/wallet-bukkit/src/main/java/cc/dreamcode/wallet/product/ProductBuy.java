package cc.dreamcode.wallet.product;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
@RequiredArgsConstructor
public class ProductBuy {
    private final String id;
    private final Instant date;

    public String getFormattedDate() {
        final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final ZonedDateTime zonedDateTime = this.getDate().atZone(ZoneId.systemDefault());

        return zonedDateTime.format(dateFormat);
    }
}
