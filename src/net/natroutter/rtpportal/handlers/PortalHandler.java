package net.natroutter.rtpportal.handlers;

import net.natroutter.natlibs.handlers.Database.YamlDatabase;
import net.natroutter.natlibs.objects.BasePlayer;
import net.natroutter.natlibs.objects.Cuboid;
import net.natroutter.natlibs.utilities.Utilities;
import net.natroutter.rtpportal.RTPportal;
import net.natroutter.rtpportal.utilities.Lang;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.UUID;

public class PortalHandler implements Listener {

    private YamlDatabase database = RTPportal.getDatabase();
    private Lang lang = RTPportal.getLang();

    private HashMap<UUID, Long> cooldowns = new HashMap<>();
    private int cooldown = 3;

    @EventHandler
    public void onPortalEnter(PlayerMoveEvent e) {
        BasePlayer p = BasePlayer.from(e.getPlayer());
        Location pos1 = database.getLocation("Portal", "Pos1");
        Location pos2 = database.getLocation("Portal", "Pos2");
        if (pos1 != null && pos2 != null) {
            Cuboid portalArea = new Cuboid(pos1, pos2);
            if (portalArea.contains(p.getLocation())) {

                if (cooldowns.containsKey(p.getUniqueId())) {
                    long seconds = ((cooldowns.get(p.getUniqueId())/1000)+cooldown) - (System.currentTimeMillis()/1000);
                    if (seconds > 0) {
                        return;
                    }
                }
                cooldowns.put(p.getUniqueId(), System.currentTimeMillis());

                if (TeleportHandler.isTeleporting(p)) {
                    p.sendMessage(lang.prefix + lang.AlreadyTeleporting);
                    return;
                }

                TeleportHandler.RandomTeleport(p);

            }
        }
    }

}
