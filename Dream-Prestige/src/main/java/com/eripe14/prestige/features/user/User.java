package com.eripe14.prestige.features.user;

import eu.okaeri.persistence.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class User extends Document {

    private String name;

    private int prestige;

    private long executePossibilityExpireIn;

    private boolean canExecuteCommand;

    public void addPrestige() {
        this.prestige++;
    }

    public UUID getUniqueId() {
        return this.getPath().toUUID();
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.getUniqueId());
    }

}
