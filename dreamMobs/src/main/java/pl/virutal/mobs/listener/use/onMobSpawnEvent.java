package pl.virutal.mobs.listener.use;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import pl.virutal.mobs.listener.ListenerUse;

public class onMobSpawnEvent extends ListenerUse {

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)){
            configPlugin.disabledSpawnMobsFromEgs.forEach(all ->{
                if (e.getEntity().getName().equalsIgnoreCase(all))e.setCancelled(true);
            });
        }
        else if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.valueOf("COMMAND"))){
            configPlugin.disabledSpawnMobsFromCommand.forEach(all ->{
                if (e.getEntity().getName().equalsIgnoreCase(all))e.setCancelled(true);
            });
        }
        else if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER)){
            configPlugin.disabledSpawnMobsFromSpawner.forEach(all ->{
                if (e.getEntity().getName().equalsIgnoreCase(all))e.setCancelled(true);
            });
        }
        else if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)){
            configPlugin.disabledSpawnMobsFromNatural.forEach(all ->{
                if (e.getEntity().getName().equalsIgnoreCase(all))e.setCancelled(true);
            });
        }
        else if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.BREEDING)){
            configPlugin.disabledSpawnMobsFromBreeding.forEach(all ->{
                if (e.getEntity().getName().equalsIgnoreCase(all))e.setCancelled(true);
            });
        }
    }

    @EventHandler
    public void mobSpawnEvent(EntitySpawnEvent e){
        if (configPlugin.worldIntegration) {
            for (String worlds : configPlugin.worldNameBypass) {
                if (e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase(worlds)) {
                    return;
                }
            }
        }
        configPlugin.disabledSpawnMobs.forEach(all ->{
            if (e.getEntityType().getName().equalsIgnoreCase(all))e.setCancelled(true);
        });
    }
}
