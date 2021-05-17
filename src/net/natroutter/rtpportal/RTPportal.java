package net.natroutter.rtpportal;

import net.natroutter.natlibs.NATLibs;
import net.natroutter.natlibs.handlers.Database.YamlDatabase;
import net.natroutter.natlibs.handlers.EventManager;
import net.natroutter.natlibs.handlers.FileManager;
import net.natroutter.natlibs.objects.ConfType;
import net.natroutter.natlibs.utilities.Utilities;
import net.natroutter.rtpportal.commands.RtpCmd;
import net.natroutter.rtpportal.handlers.PortalHandler;
import net.natroutter.rtpportal.handlers.Schedulers;
import net.natroutter.rtpportal.handlers.WandHandler;
import net.natroutter.rtpportal.utilities.Config;
import net.natroutter.rtpportal.utilities.Lang;
import org.bukkit.plugin.java.JavaPlugin;

public class RTPportal extends JavaPlugin {

    private static YamlDatabase database;
    private static Lang lang;
    private static Config conf;
    private static Utilities utilities;

    public static YamlDatabase getDatabase() {return database;}
    public static Lang getLang() {return lang;}
    public static Config getConf() {return conf;}
    public static Utilities getUtilities() {return utilities;}

    @Override
    public void onEnable() {
        NATLibs lib = new NATLibs(this);

        database = new YamlDatabase(this);
        lang = new FileManager(this, ConfType.Lang).load(Lang.class);
        conf = new FileManager(this, ConfType.Config).load(Config.class);

        utilities = new Utilities(this);

        EventManager evm = new EventManager(this);
        evm.RegisterCommands(RtpCmd.class);
        evm.RegisterListeners(WandHandler.class, PortalHandler.class);

        Schedulers schedulers = new Schedulers(this);

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
