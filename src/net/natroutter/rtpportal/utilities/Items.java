package net.natroutter.rtpportal.utilities;

import net.natroutter.natlibs.objects.BaseItem;
import net.natroutter.rtpportal.RTPportal;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

public class Items {

    private static final Lang lang = RTPportal.getLang();

    public static BaseItem wand() {
        BaseItem item = new BaseItem(Material.BLAZE_ROD);
        item.setDisplayName(lang.items.wand_name);
        item.setLore(lang.items.wand_lore);
        item.addItemFlags(ItemFlag.values());
        return item;
    }


}
