package cc.dreamcode.wallet.user;

import cc.dreamcode.wallet.product.ProductBuy;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import eu.okaeri.persistence.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class User extends Document {

    private String name;

    private Instant reward;
    private double money = 0.0D;
    private double moneySpent = 0.0D;
    private List<ProductBuy> productBuyList = new ArrayList<>();

    public UUID getUniqueId() {
        return this.getPath().toUUID();
    }

    public void addMoney(double money) {
        this.money = this.money + money;
    }

    public void takeMoney(double money) {
        if (this.money <= money) {
            this.money = 0.0D;
            return;
        }

        this.money = this.money - money;
    }

    public void addMoneySpent(double money) {
        this.moneySpent = this.moneySpent + money;
    }
}
