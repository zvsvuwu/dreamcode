package xyz.ravis96.dreamkit.features.plugin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@RequiredArgsConstructor
public class PluginStorage {

    private boolean disabledKits = false;
    private List<String> disabledKit = Collections.synchronizedList(new ArrayList<>());

}
