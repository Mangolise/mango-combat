import net.kyori.adventure.text.Component;
import net.mangolise.combat.CombatConfig;
import net.mangolise.combat.MangoCombat;
import net.mangolise.combat.events.PlayerKilledEvent;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerChatEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.component.EnchantmentList;
import net.minestom.server.item.enchant.Enchantment;

public class TestServer {

    public static void main(String[] args) {
        System.out.println("Starting test server...");

        MinecraftServer server = MinecraftServer.init();

        Instance instance = MinecraftServer.getInstanceManager().createInstanceContainer();
        instance.enableAutoChunkLoad(true);
        for (int x = -24; x < 24; x++) {
            for (int z = -24; z < 24; z++) {
                instance.setBlock(x, 32, z, Block.GLOWSTONE);
            }
        }

        MinecraftServer.getGlobalEventHandler().addListener(AsyncPlayerConfigurationEvent.class, e -> {
            e.setSpawningInstance(instance);
            e.getPlayer().setRespawnPoint(new Pos(0, 36, 0));
        });

        MinecraftServer.getGlobalEventHandler().addListener(PlayerChatEvent.class, e -> {
            if (e.getMessage().equals("die")) {
//                e.getPlayer().damage(new Damage(DamageType.FALL, null, null, null, 99999f));
//                e.getPlayer().kill();  // This will bypass fake death
                MangoCombat.kill(e.getPlayer());
            }

            if (e.getMessage().startsWith("kb")) {
                try {
                    int level = Integer.parseInt(e.getMessage().split(" ")[1]);
                    e.getPlayer().setItemInHand(Player.Hand.MAIN, ItemStack.of(Material.STICK).with(ItemComponent.ENCHANTMENTS, new EnchantmentList(Enchantment.KNOCKBACK, level)));
                } catch (RuntimeException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        MinecraftServer.getGlobalEventHandler().addListener(PlayerKilledEvent.class, e -> {
            Player killer = e.killer();
            String cause = killer == null ? "nothing" : killer.getUsername();
            e.setDeathMsg(Component.text(
                    e.victim().getUsername() + " was killed by " + cause));
        });

        MangoCombat.enableGlobal(CombatConfig.create()
                .withFakeDeath(true)
                .withDisableBuiltinDeathMsgs(true)
                .withIframes(500)
                .withVoidDeath(true)
                .withAutomaticRespawn(true));

        server.start("0.0.0.0", 25565);
    }
}
