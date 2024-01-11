package cc.dreamcode.fortune.fortune;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

@Getter
@RequiredArgsConstructor
public class Fortune {

    private final Material material;
    private final int level;

}
