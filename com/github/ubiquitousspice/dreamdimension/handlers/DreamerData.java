package com.github.ubiquitousspice.dreamdimension.handlers;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.github.ubiquitousspice.dreamdimension.Util;
import net.minecraft.util.ChunkCoordinates;

//@Data
public class DreamerData
{
    public long            timeLeft = Long.MAX_VALUE; // seconds

    // bed coordinates.
    public float           bedX;
    public float           bedY;
    public float           bedZ;
    public int             bedDim;

    public float           spewX;
    public float           spewY;
    public float           spewZ;

    public int             hunger;
    public float           health;

    public InventoryPlayer oldInv;

    public DreamerData()
    {
    }

    /**
     * Only loads data from the player, doesn't edit the player.
     */
    public DreamerData(EntityPlayer player)
    {
        // get inventory data.
        oldInv = new InventoryPlayer(player);
        oldInv.copyInventory(player.inventory);

        // come back coords
        float[] coords = Util.getWakeLocation(player);
        bedX = coords[0];
        bedY = coords[1];
        bedZ = coords[2];

        // item spew coords
        ChunkCoordinates loc = player.playerLocation;
        spewX = loc.posX;
        spewY = loc.posY+1;
        spewZ = loc.posZ;

        // health and hunger
        health = player.getHealth();
        hunger = player.getFoodStats().getFoodLevel();
        

        bedDim = player.worldObj.provider.dimensionId;
    }

    /**
     * @return TRUE if there is time left. ELSE if there is not.
     */
    public boolean decrementTime()
    {
        timeLeft--;

        return timeLeft > 0;
    }

    public void writeToNBT(NBTTagCompound nbtBig)
    {
        NBTTagCompound nbt = new NBTTagCompound();

        nbt.setLong("timeLeft", timeLeft);

        nbt.setFloat("bedX", bedX);
        nbt.setFloat("bedY", bedY);
        nbt.setFloat("bedZ", bedZ);
        nbt.setInteger("bedDim", bedDim);

        nbt.setFloat("spewX", spewX);
        nbt.setFloat("spewY", spewY);
        nbt.setFloat("spewZ", spewZ);

        nbt.setInteger("hunger", hunger);
        nbt.setFloat("health", health);

        NBTTagList inv = new NBTTagList();
        oldInv.writeToNBT(inv);
        nbt.setTag("inv", inv);

        nbtBig.setCompoundTag("dreamerData", nbt);
    }

    public static DreamerData read(EntityPlayer player)
    {
        DreamerData data = new DreamerData();

        NBTTagCompound nbt = player.getEntityData().getCompoundTag("dreamerData");

        data.timeLeft = nbt.getLong("timeLeft");

        data.bedX = nbt.getFloat("bedX");
        data.bedY = nbt.getFloat("bedY");
        data.bedZ = nbt.getFloat("bedZ");
        data.bedDim = nbt.getInteger("bedDim");

        data.spewX = nbt.getFloat("spewX");
        data.spewY = nbt.getFloat("spewY");
        data.spewZ = nbt.getFloat("spewZ");

        data.health = nbt.getFloat("health");
        data.hunger = nbt.getInteger("hunger");

        InventoryPlayer inv = new InventoryPlayer(player);
        inv.readFromNBT(nbt.getTagList("inv"));
        data.oldInv = inv;

        return data;
    }

	
}
