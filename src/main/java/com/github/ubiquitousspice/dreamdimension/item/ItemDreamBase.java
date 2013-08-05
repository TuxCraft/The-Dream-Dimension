package com.github.ubiquitousspice.dreamdimension.item;

import com.github.ubiquitousspice.dreamdimension.dimension.WorldProviderMod;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDreamBase extends Item
{

    private String normalName;
    private String dreamName;
    
    protected boolean inDreamWorld;

    public ItemDreamBase(int par1, String a, String b)
    {
        super(par1);
        this.normalName = a;
        this.dreamName = b;
        
    }
    
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) 
    {
        if(par2World.provider instanceof WorldProviderMod)
        {
            this.inDreamWorld = true;
        }
        
        else
        {
            this.inDreamWorld = false;
        }
    }
    
    public String getItemDisplayName(ItemStack stack)
    {
        
        return (this.inDreamWorld) ? dreamName : normalName;
    }
    
    

}
