package pl.virutal.mobs.manager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class User {

    private final UUID uuid;
    private final String name;
    private String type;
}


