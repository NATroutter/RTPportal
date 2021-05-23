package net.natroutter.rtpportal.handlers;

import net.natroutter.natlibs.objects.BaseItem;
import net.natroutter.natlibs.utilities.StringHandler;
import net.natroutter.rtpportal.RTPportal;
import net.natroutter.rtpportal.objects.DoubleLocation;
import net.natroutter.rtpportal.utilities.Items;
import net.natroutter.rtpportal.utilities.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class WandHandler implements Listener {

    private Lang lang = RTPportal.getLang();

    public static HashMap<UUID, DoubleLocation> WandPos = new HashMap<>();

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        WandPos.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.hasBlock() && e.hasItem()) {
            if (!BaseItem.from(e.getItem()).isSimilar(Items.wand())) {
                return;
            }
            Player p = e.getPlayer();
            Block block = e.getClickedBlock();

            if (!p.hasPermission("rtpportal.usewand")) {
                p.sendMessage(lang.prefix + lang.noPerm);
                return;
            }
            e.setCancelled(true);

            int posNum;
            DoubleLocation pos = WandPos.getOrDefault(p.getUniqueId(), new DoubleLocation());
            if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                posNum = 1;
                pos.setPos1(block.getLocation());
            } else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                posNum = 2;
                pos.setPos2(block.getLocation());

            } else { return; }
            WandPos.put(p.getUniqueId(), pos);

            if (pos.valid()) {
                WandPos.put(p.getUniqueId(), pos);
            }

            StringHandler message = new StringHandler(lang.PosSelected).setPrefix(lang.prefix);
            message.replaceAll("{num}", posNum);
            message.replaceAll("{x}", block.getLocation().getBlockX());
            message.replaceAll("{y}", block.getLocation().getBlockY());
            message.replaceAll("{z}", block.getLocation().getBlockZ());
            message.send(p);

        }
    }
}
