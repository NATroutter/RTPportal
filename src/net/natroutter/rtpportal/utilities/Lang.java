package net.natroutter.rtpportal.utilities;

import java.util.ArrayList;
import java.util.List;

public class Lang {

    public String prefix = "§6§lRTP §8§l» ";
    public String ingameOnly = "§7This command can only be run ingame!";
    public String noPerm = "§7You do not have permission to do this";
    public String InvalidArgs = "§7Invalid command arguments";
    public String ToomanyArgs = "§7Too many command arguments";
    public String wandGiven = "§7Portal wand added to your inventory";
    public String portalCreated = "§7portal created!";
    public String PosSelected = "§7Position §e{num} §7selected at §8( §e{x}§7, §e{y}§7, §e{z} §8)";
    public String InvalidWorld = "§e{world} §7Is not valid world!";
    public String AreaNotSelected = "§7Portal area is not selected!";
    public String InvalidRadius = "§e{radius} §7is not valid radius";
    public String SearchingLocation = "§7Searching safe location, Please wait...";
    public String Teleported = "§7You have been teleported to random location §8( §e{x}§7, §e{y}§7, §e{z} §8)";
    public String AlreadyTeleporting = "§7You are already about to teleport, please wait...";


    public List<String> help = new ArrayList<String>() {{
        add(" ");
        add("§e§m§l------+§6§l RTPPortals §e§m§l+------");
        add(" ");
        add("§8§l» §e/rtpp wand §8:: §7Gives selection wand");
        add("§8§l» §e/rtpp create <world> <radius> §8:: §7Create portal");
        add(" ");
        add("§e§m§l------+§6§l RTPPortals §e§m§l+------");
        add(" ");
    }};

    public items items = new items();
    public static class items {
        public String wand_name = "§6§lRTP Wand";
        public List<String> wand_lore = new ArrayList<String>() {{
            add("§7Select positions where to create portal");
            add("§eLeft-Click §7to select position 1");
            add("§eRight-Click §7to select position 2");
            add("§7After selecting do §e/rtpp create <world name> <rtp radius>");
        }};
    }

}
