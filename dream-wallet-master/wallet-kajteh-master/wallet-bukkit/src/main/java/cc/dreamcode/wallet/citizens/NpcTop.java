package cc.dreamcode.wallet.citizens;

import cc.dreamcode.wallet.top.TopType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NpcTop {
    private int id;
    private int top;
    private TopType type;
}
