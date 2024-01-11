package cc.dreamcode.dropsmp.nms.v1_13_R2;

import cc.dreamcode.dropsmp.nms.api.NmsAccessor;
import cc.dreamcode.dropsmp.nms.api.inventory.InventoryAccessor;
import cc.dreamcode.dropsmp.nms.v1_13_R2.inventory.V1_13_R2_InventoryAccessor;

public class V1_13_R2_NmsAccessor implements NmsAccessor {
    @Override
    public InventoryAccessor getInventoryAccessor() {
        return new V1_13_R2_InventoryAccessor();
    }
}
