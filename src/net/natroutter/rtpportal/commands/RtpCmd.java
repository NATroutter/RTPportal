package net.natroutter.rtpportal.commands;

import net.natroutter.natlibs.handlers.Database.YamlDatabase;
import net.natroutter.natlibs.utilities.StringHandler;
import net.natroutter.rtpportal.RTPportal;
import net.natroutter.rtpportal.handlers.WandHandler;
import net.natroutter.rtpportal.objects.DoubleLocation;
import net.natroutter.rtpportal.utilities.Items;
import net.natroutter.rtpportal.utilities.Lang;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RtpCmd extends Command {

    private Lang lang = RTPportal.getLang();
    private YamlDatabase database = RTPportal.getDatabase();

    public RtpCmd() {
        super("rtpportal");
        this.setAliases(Collections.singletonList("rtpp"));
    }

    @Override
    public boolean execute(CommandSender sender, String _label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(lang.prefix + lang.ingameOnly);
            return false;
        }
        Player p = (Player)sender;

        if (args.length == 0) {
            p.sendMessage(" ");
            p.sendMessage("§e§m§l------+§6§l RTPPortals §e§m§l+------");
            p.sendMessage(" ");
            p.sendMessage("§8§l» §7Plugin Created by: §eNATroutter");
            p.sendMessage("§8§l» §7Website: §ehttps://NATroutter.net");
            p.sendMessage("§8§l» §7Version: §e1.0.0");
            p.sendMessage(" ");
            p.sendMessage("§8§l» §7If you need help type §e/rtpp help");
            p.sendMessage(" ");
            p.sendMessage("§e§m§l------+§6§l RTPPortals §e§m§l+------");
            p.sendMessage(" ");
        } else if (args.length == 1) {

            if (args[0].equalsIgnoreCase("help")) {
                if (p.hasPermission("rtpportal.help")) {
                    for (String str : lang.help) {
                        p.sendMessage(str);
                    }
                } else {
                    p.sendMessage(lang.prefix + lang.noPerm);
                }
            } else if (args[0].equalsIgnoreCase("wand")) {
                if (p.hasPermission("rtpportal.givewand")) {
                    p.getInventory().addItem(Items.wand());
                    p.sendMessage(lang.prefix + lang.wandGiven);
                } else {
                    p.sendMessage(lang.prefix + lang.noPerm);
                }

            } else {
                p.sendMessage(lang.prefix + lang.InvalidArgs);
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("create")) {

                World world = Bukkit.getWorld(args[1]);
                if (world == null) {
                    StringHandler msg = new StringHandler(lang.InvalidWorld).setPrefix(lang.prefix);
                    msg.replaceAll("{world}", args[1]);
                    msg.send(p);
                    return false;
                }

                Integer radius = null;
                try {radius = Integer.parseInt(args[2]);} catch (Exception ignored){
                    StringHandler msg = new StringHandler(lang.InvalidRadius).setPrefix(lang.prefix);
                    msg.replaceAll("{radius}", args[2]);
                    msg.send(p);
                    return false;
                };

                if (WandHandler.WandPos.containsKey(p.getUniqueId())) {
                    DoubleLocation wand = WandHandler.WandPos.get(p.getUniqueId());
                    if (!wand.valid()) {
                        p.sendMessage(lang.prefix + lang.AreaNotSelected);
                        return false;
                    }

                    database.save("Portal.Creator", "Name", p.getName());
                    database.save("Portal.Creator", "UUID", p.getUniqueId().toString());
                    database.save("Portal", "WorldName", world.getName());
                    database.save("Portal", "Radius", radius);
                    database.saveLoc("Portal", "Pos1", wand.getPos1());
                    database.saveLoc("Portal", "Pos2", wand.getPos2());
                    WandHandler.WandPos.remove(p.getUniqueId());
                    p.sendMessage(lang.prefix + lang.portalCreated);

                } else {
                    p.sendMessage(lang.prefix + lang.AreaNotSelected);
                }

            } else {
                p.sendMessage(lang.prefix + lang.InvalidArgs);
            }
        } else {
            p.sendMessage(lang.prefix + lang.ToomanyArgs);
        }
        return false;
    }

    List<String> firstArgs = Arrays.asList(
            "help", "wand", "create"
    );

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {

        if (args.length == 1) {
            List<String> shorted = new ArrayList<>();
            StringUtil.copyPartialMatches(args[0], firstArgs, shorted);
            Collections.sort(shorted);
            return shorted;

        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("create")) {
                ArrayList<String> worlds = new ArrayList<>();
                Bukkit.getWorlds().forEach((e)->{worlds.add(e.getName());});

                List<String> shorted = new ArrayList<>();
                StringUtil.copyPartialMatches(args[1], worlds, shorted);
                Collections.sort(shorted);
                return shorted;
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("create")) {
                List<String> shorted = new ArrayList<>();
                StringUtil.copyPartialMatches(args[2], Arrays.asList("1000", "2000", "3000", "4000", "5000"), shorted);
                Collections.sort(shorted);
                return shorted;
            }
        }
        return null;
    }
}
