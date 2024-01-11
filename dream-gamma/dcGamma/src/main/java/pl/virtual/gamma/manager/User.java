package pl.virtual.gamma.manager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class User {
    private final String name;
    private boolean gamma = false;
}


