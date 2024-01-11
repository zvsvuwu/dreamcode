package cc.dreamcode.sprawdzanie.content;


import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class SprawdzanieManager {
   private final @Getter Map<Player, Location> sprawdzaniGracze = new HashMap<>();
}
