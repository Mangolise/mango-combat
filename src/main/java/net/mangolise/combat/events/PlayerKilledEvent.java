package net.mangolise.combat.events;

import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.damage.Damage;
import net.minestom.server.event.Event;
import org.jetbrains.annotations.Nullable;

public class PlayerKilledEvent implements Event {
    private final @Nullable Player killer;
    private final Player victim;
    private final @Nullable Damage lastDamage;
    private boolean cancelled;
    private @Nullable Component deathMsg;
    private @Nullable Component deathText;

    public PlayerKilledEvent(Player victim, @Nullable Player killer, @Nullable Damage lastDamage, @Nullable Component deathMsg, @Nullable Component deathText) {
        this.killer = killer;
        this.victim = victim;
        this.lastDamage = lastDamage;
        this.deathMsg = deathMsg;
        this.deathText = deathText;
    }

    public @Nullable Player killer() {
        return killer;
    }

    public Player victim() {
        return victim;
    }

    public @Nullable Damage lastDamage() {
        return lastDamage;
    }

    public boolean cancelled() {
        return cancelled;
    }

    public Component deathMsg() {
        return deathMsg;
    }

    public Component deathText() {
        return deathText;
    }

    public void setDeathMsg(Component deathMsg) {
        this.deathMsg = deathMsg;
    }

    public void setDeathText(Component deathText) {
        this.deathText = deathText;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
