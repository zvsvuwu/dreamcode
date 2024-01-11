package pl.virtual.strefa.manager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@RequiredArgsConstructor
public class User {

    private final UUID uuid;
    private final String name;
    private long time;
}


