package me.tss1410.RegionerPlus;

import org.bukkit.Location;

public class CuboidSelection
{

    private Location min, max;

    public Location getLocMax()
    {
        return max;
    }

    public Location getLocMin()
    {
        return min;
    }

    public void setLocMax(final Location loc)
    {
        this.max = loc;
    }

    public void setLocMin(final Location loc)
    {
        this.min = loc;
    }
}
