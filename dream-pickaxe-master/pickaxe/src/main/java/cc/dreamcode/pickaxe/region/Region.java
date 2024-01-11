package cc.dreamcode.pickaxe.region;

import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class Region extends OkaeriConfig {

    private String region;
    private Set<String> allowedTools;
    private Set<String> allowedMaterials;
    private int minEfficiencyLevel;
    private int minX, maxX, minY, maxY, minZ, maxZ;

    public static Region fromCorners(String region, Location firstCorner, Location secondCorner) {
        int firstX = firstCorner.getBlockX();
        int secondX = secondCorner.getBlockX();

        int firstZ = firstCorner.getBlockZ();
        int secondZ = secondCorner.getBlockZ();

        int firstY = firstCorner.getBlockY();
        int secondY = secondCorner.getBlockY();

        return new Region(
                region,
                new HashSet<>(Collections.singletonList(
                        "STONE_PICKAXE"
                )),
                new HashSet<>(Arrays.asList(
                        "STONE",
                        "COBBLESTONE"
                )),
                3,
                Math.min(firstX, secondX),
                Math.max(firstX, secondX),
                Math.min(firstY, secondY),
                Math.max(firstY, secondY),
                Math.min(firstZ, secondZ),
                Math.max(firstZ, secondZ));
    }

    public boolean isIn(Location location) {
        return
                location.getX() <= getMaxX()
                        && location.getX() >= getMinX()
                        && location.getY() >= getMinY()
                        && location.getY() <= getMaxY()
                        && location.getZ() <= getMaxZ()
                        && location.getZ() >= getMinZ();
    }

    public void setX(int x) {
        if (x > this.maxX) {
            this.maxX = x;
        } else if (x < this.minX) {
            this.minX = x;
        }
    }

    public void setZ(int z) {
        if (z > this.maxZ) {
            this.maxZ = z;
        } else if (z < this.minZ) {
            this.minZ = z;
        }
    }

    public void setY(int y) {
        if (y > this.maxY) {
            this.maxY = y;
        } else if (y < this.minY) {
            this.minY = y;
        }
    }

    public int length() {
        return this.maxX - this.minX + 1;
    }

    public int width() {
        return this.maxZ - this.minZ + 1;
    }

    public int area() {
        return this.length() * this.width();
    }

}
