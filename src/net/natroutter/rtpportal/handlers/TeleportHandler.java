package net.natroutter.rtpportal.handlers;

import net.natroutter.natlibs.handlers.Database.YamlDatabase;
import net.natroutter.natlibs.objects.BaseItem;
import net.natroutter.natlibs.utilities.StringHandler;
import net.natroutter.natlibs.utilities.Utilities;
import net.natroutter.rtpportal.RTPportal;
import net.natroutter.rtpportal.utilities.Config;
import net.natroutter.rtpportal.utilities.Lang;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.sqlite.util.StringUtils;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class TeleportHandler {

    private static final Config config = RTPportal.getConf();
    private static final Lang lang = RTPportal.getLang();

    private static final YamlDatabase database = RTPportal.getDatabase();
    private static HashMap<UUID, Boolean> Teleporting = new HashMap<>();

    public static boolean isTeleporting(Player p) {
        return Teleporting.containsKey(p.getUniqueId());
    }

    public static void RandomTeleport(Player p) {
        Integer radius = database.getInt("Portal","Radius");
        String worldName = database.getString("Portal","WorldName");
        if (radius == null || worldName == null) {return;}

        World world = Bukkit.getWorld(worldName);
        if (world == null) {return;}

        p.sendMessage(lang.prefix + lang.SearchingLocation);
        Teleporting.put(p.getUniqueId(), true);

        Block b;
        Supplier<Integer> randomInt = () -> ThreadLocalRandom.current().nextInt(-radius, radius);
        do {
            b = world.getHighestBlockAt(randomInt.get(), randomInt.get(), HeightMap.MOTION_BLOCKING);
        } while (config.UnsafeBlocks.contains(b.getType()));
        Location randomLoc = b.getLocation().add(0.5, 1, 0.5);

        p.teleport(randomLoc);
        StringHandler msg = new StringHandler(lang.Teleported).setPrefix(lang.prefix);
        msg.replaceAll("{x}", randomLoc.getBlockX());
        msg.replaceAll("{y}", randomLoc.getBlockY());
        msg.replaceAll("{z}", randomLoc.getBlockZ());
        msg.send(p);
        Teleporting.remove(p.getUniqueId());

    }




}
