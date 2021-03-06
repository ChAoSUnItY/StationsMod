package com.noto0648.stations.common;

import net.minecraft.nbt.NBTTagCompound;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Noto on 14/08/06.
 */
public class MarkData
{
    public static final String REGEX = "^<([0-9A-F]*)>\\s*(.*)";
    public static final Pattern p = Pattern.compile(REGEX);

    public int hours;
    public int minutes;
    public String dest;
    public String type;

    public int typeColor;
    public int destColor;
    public int timeColor;

    public MarkData() {}

    public MarkData(int h, int m, String d, String t)
    {
        hours = h;
        minutes = m;
        timeColor = 0x00FF00;
        {
            Matcher mt = p.matcher(d);
            if(mt.find())
            {
                if(mt.groupCount() == 2)
                {
                    String colorCode = mt.group(1);
                    dest = mt.group(2);
                    destColor = Integer.parseInt(colorCode, 16);
                }
            }
            if(dest == null)
            {
                dest = d;
                destColor = 0x00FF00;
            }
        }

        {
            Matcher mt = p.matcher(t);
            if(mt.find())
            {
                if(mt.groupCount() == 2)
                {
                    String colorCode = mt.group(1);
                    type = mt.group(2);
                    typeColor = Integer.parseInt(colorCode, 16);
                }
            }
            if(type == null)
            {
                type = t;
                typeColor = timeColor = 0x00FF00;
            }
        }
    }


    @Override
    public String toString()
    {
        return getTimeString() + " " + type + " " + dest;
    }

    public String getTimeString()
    {
        return String.format("%1$02d:%2$02d", hours, minutes);
    }

    public void writeToNBTTag(NBTTagCompound tag)
    {
        tag.setInteger("hours", hours);
        tag.setInteger("min", minutes);
        tag.setString("trainType", type);
        tag.setString("dest", dest);

        tag.setInteger("typeColor", typeColor);
        tag.setInteger("destColor", destColor);
        tag.setInteger("timeColor", timeColor);
    }

    public void readFromNBTTag(NBTTagCompound tag)
    {
        hours = tag.getInteger("hours");
        minutes = tag.getInteger("min");
        type = tag.getString("trainType");
        dest = tag.getString("dest");

        typeColor = tag.getInteger("typeColor");
        destColor = tag.getInteger("destColor");
        timeColor = tag.getInteger("timeColor");

    }
}