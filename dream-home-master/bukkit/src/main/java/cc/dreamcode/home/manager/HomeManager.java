package cc.dreamcode.home.manager;

import lombok.Getter;
import org.bukkit.Location;

import java.util.*;

public class HomeManager {
    private final @Getter Map<UUID, Integer> timeMap = new HashMap<>();
    private final @Getter Map<UUID, Location> locationMap = new HashMap<>();
}
