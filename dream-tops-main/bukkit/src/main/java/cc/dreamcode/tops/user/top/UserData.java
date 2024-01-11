package cc.dreamcode.tops.user.top;

import cc.dreamcode.tops.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.function.ToDoubleFunction;

@Getter
@RequiredArgsConstructor
public enum UserData {

    BLOCKMINED(User::getMinedblocks, true),
    DIAMONDMINED(User::getMinediamonds, true),
    EMERALDMINED(User::getMinedemeralds, true),
    GOLDMINED(User::getMinedgold, true),
    IRONMINED(User::getMinediron, true),
    LAPISMINED(User::getMinedlapis, true),
    DEATHS(User::getDeaths, true),
    KGOLDEATEN(User::getEatenkoxapple, true),
    GOLDEATEN(User::getEatengapple, true),
    KILLS(User::getKills, true);

    private final ToDoubleFunction<User> function;
    private final boolean shouldCountTop;

}