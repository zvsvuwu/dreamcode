package cc.dreamcode.dropsmp.nms.v1_15_R1;

import cc.dreamcode.dropsmp.nms.api.NmsAccessor;
import cc.dreamcode.dropsmp.nms.api.inventory.InventoryAccessor;
import cc.dreamcode.dropsmp.nms.v1_15_R1.inventory.V1_15_R1_InventoryAccessor;

public class V1_15_R1_NmsAccessor implements NmsAccessor {
    @Override
    public InventoryAccessor getInventoryAccessor() {
        return new V1_15_R1_InventoryAccessor();
    }
}
