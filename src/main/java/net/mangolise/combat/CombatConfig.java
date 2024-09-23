package net.mangolise.combat;

public record CombatConfig(boolean fakeDeath, boolean disableBuiltinDeathMsgs, int iframes, boolean voidDeath, boolean automaticRespawn) {

    public CombatConfig() {
        this(false, false, 500, false, false);
    }

    /**
     * Specify whether players should go through the vanilla death process.
     * If you specify false there may be issues with respawning.
     * <p>
     * Default: false
     * @param fakeDeath Whether to use fake death.
     * @return A new modified config.
     */
    public CombatConfig withFakeDeath(boolean fakeDeath) {
        return new CombatConfig(fakeDeath, disableBuiltinDeathMsgs, iframes, voidDeath, automaticRespawn);
    }

    /**
     * Specify whether we should disable vanilla death messages.
     * <p>
     * Default: false
     * @param dis Whether to disable vanilla death messages.
     * @return A new modified config.
     */
    public CombatConfig withDisableBuiltinDeathMsgs(boolean dis) {
        return new CombatConfig(fakeDeath, dis, iframes, voidDeath, automaticRespawn);
    }

    /**
     * Specify how many millis players will be invincible for after taking damage.
     * <p>
     * Default: 500
     * @param iframes The amount of milliseconds.
     * @return A new modified config.
     */
    public CombatConfig withIframes(int iframes) {
        return new CombatConfig(fakeDeath, disableBuiltinDeathMsgs, iframes, voidDeath, automaticRespawn);
    }

    /**
     * Specify whether players should die in the void.
     * <p>
     * Default: false
     * @param voidDeath Whether to enable void death.
     * @return A new modified config.
     */
    public CombatConfig withVoidDeath(boolean voidDeath) {
        return new CombatConfig(fakeDeath, disableBuiltinDeathMsgs, iframes, voidDeath, automaticRespawn);
    }

    /**
     * Specify whether players should respawn immediately after dying.
     * This only applies when fakeDeath is set to false.
     * <p>
     * Default: false
     * @param automaticRespawn Whether to enable automatic respawn.
     * @return A new modified config.
     */
    public CombatConfig withAutomaticRespawn(boolean automaticRespawn) {
        return new CombatConfig(fakeDeath, disableBuiltinDeathMsgs, iframes, voidDeath, automaticRespawn);
    }

    /**
     * Create a new config with default values.
     * @return The created config.
     */
    public static CombatConfig create() {
        return new CombatConfig();
    }
}
