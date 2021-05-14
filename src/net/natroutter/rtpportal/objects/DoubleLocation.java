package net.natroutter.rtpportal.objects;

import org.bukkit.Location;

public class DoubleLocation {

    private Location pos1;
    private Location pos2;

    public DoubleLocation() {}

    public DoubleLocation(Location pos1, Location pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public boolean valid() {
        if (pos1 != null && pos2 != null) {
            return true;
        }
        return false;
    }

    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public void setPos1(Location pos1) {
        this.pos1 = pos1;
    }

    public void setPos2(Location pos2) {
        this.pos2 = pos2;
    }
}
