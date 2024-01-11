package xyz.ravis96.dreamfreeze.tasks.list;

import xyz.ravis96.dreamfreeze.tasks.TaskUse;

public class FreezeTask extends TaskUse {
    @Override
    public void runTask() {
        if(this.pluginStorage.isFreeze()) {
            this.pluginConfig.getFreezeConfig().freezeTitle.sendAll(this.pluginMain.getServer());
        }
    }
}
