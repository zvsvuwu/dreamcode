package cc.dreamcode.dropsmp.nms.v1_16_R3;

import cc.dreamcode.dropsmp.nms.api.NmsAccessor;
import cc.dreamcode.dropsmp.nms.api.inventory.InventoryAccessor;
import cc.dreamcode.dropsmp.nms.v1_16_R3.inventory.V1_16_R3_InventoryAccessor;

public class V1_16_R3_NmsAccessor implements NmsAccessor {
    @Override
    public InventoryAccessor getInventoryAccessor() {
        return new V1_16_R3_InventoryAccessor();
    }
}
