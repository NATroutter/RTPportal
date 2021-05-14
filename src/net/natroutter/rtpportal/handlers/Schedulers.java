package net.natroutter.rtpportal.handlers;

import net.natroutter.natlibs.objects.Cuboid;
import net.natroutter.natlibs.objects.ParticleSettings;
import net.natroutter.natlibs.utilities.Utilities;
import net.natroutter.rtpportal.RTPportal;
import net.natroutter.rtpportal.objects.DoubleLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;

public class Schedulers {

    private Utilities util = RTPportal.getUtilities();

    private JavaPlugin pl;

    private Integer WandParticleTask;

    public ParticleSettings particleSettings(Location loc) {
        Location newloc = loc.clone();
        newloc.setX(newloc.getBlockX() + 0.5);
        newloc.setY(newloc.getBlockY() + 0.5);
        newloc.setZ(newloc.getBlockZ() + 0.5);
        return new ParticleSettings(Particle.SOUL_FIRE_FLAME, newloc, 5, 0.1, 0.1, 0.1, 0);
    }

    public Schedulers(JavaPlugin pl) {
        this.pl = pl;
        init();
    }

    public void init() {
        WandParticleTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, ()->{
            for (Map.Entry<UUID, DoubleLocation> wand : WandHandler.WandPos.entrySet()) {
                DoubleLocation wandPos = wand.getValue();
                if (wandPos.valid()) {
                    Cuboid cub = new Cuboid(wandPos.getPos1(), wandPos.getPos2());
                    for (Block block : cub.getBlocks()) {
                        util.spawnParticleInRadius(particleSettings(block.getLocation()), 30);
                    }
                }
            }
        }, 0, 20);
    }




}
