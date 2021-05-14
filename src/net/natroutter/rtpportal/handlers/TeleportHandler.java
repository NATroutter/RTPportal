package net.natroutter.rtpportal.handlers;

import net.natroutter.natlibs.handlers.Database.YamlDatabase;
import net.natroutter.natlibs.objects.BaseItem;
import net.natroutter.natlibs.objects.BasePlayer;
import net.natroutter.natlibs.utilities.StringHandler;
import net.natroutter.natlibs.utilities.Utilities;
import net.natroutter.rtpportal.RTPportal;
import net.natroutter.rtpportal.utilities.Config;
import net.natroutter.rtpportal.utilities.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.StringUtil;
import org.sqlite.util.StringUtils;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class TeleportHandler {

    private static final Config config = RTPportal.getConf();
    private static final Lang lang = RTPportal.getLang();

    private static final YamlDatabase database = RTPportal.getDatabase();
    private static HashMap<UUID, Boolean> Teleporting = new HashMap<>();

    public static boolean isTeleporting(BasePlayer p) {
        return Teleporting.containsKey(p.getUniqueId());
    }

    public static void RandomTeleport(BasePlayer p) {
        Integer radius = database.getInt("Portal","Radius");
        String worldName = database.getString("Portal","WorldName");
        if (radius == null || worldName == null) {return;}

        World world = Bukkit.getWorld(worldName);
        if (world == null) {return;}

        p.sendMessage(lang.prefix + lang.SearchingLocation);
        Teleporting.put(p.getUniqueId(), true);
        while (true) {
            int x = ThreadLocalRandom.current().nextInt(-radius, radius + 1);
            int z = ThreadLocalRandom.current().nextInt(-radius, radius + 1);
            int y = 256;

            Location randomLoc = new Location(world, x, y, z);
            randomLoc = world.getHighestBlockAt(randomLoc).getLocation();
            if (!config.UnsafeBlocks.contains(randomLoc.getBlock().getType())) {
                randomLoc.setX(randomLoc.getBlockX() + 0.5);
                randomLoc.add(0, 1, 0);
                randomLoc.setZ(randomLoc.getBlockZ() + 0.5);

                p.teleport(randomLoc);
                StringHandler msg = new StringHandler(lang.Teleported).setPrefix(lang.prefix);
                msg.replaceAll("{x}", randomLoc.getBlockX());
                msg.replaceAll("{y}", randomLoc.getBlockY());
                msg.replaceAll("{z}", randomLoc.getBlockZ());
                msg.send(p);
                Teleporting.remove(p.getUniqueId());
                break;
            }
        }


    }




}
