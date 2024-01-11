package cc.dreamcode.home.rawlocation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Objects;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class RawLocation {

    private final String worldName;
    private final double x;
    private final double y;
    private final double z;
    private float yaw = 0.0F;
    private float pitch = 0.0F;

    public RawLocation(@NonNull Location location) {
        this.worldName = Objects.requireNonNull(location.getWorld()).getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
    }

    public Location toLocation() {
        return new Location(
                Bukkit.getWorld(this.worldName),
                this.x,
                this.y,
                this.z,
                this.yaw,
                this.pitch
        );
    }

}
