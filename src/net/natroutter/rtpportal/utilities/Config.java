package net.natroutter.rtpportal.utilities;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Config {

    public List<Material> UnsafeBlocks = new ArrayList<Material>() {{
        add(Material.WATER);
        add(Material.LAVA);
        add(Material.CACTUS);
    }};

}
