package com.github.ubiquitousspice.dreamdimension.client.render;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.github.ubiquitousspice.dreamdimension.DreamDimension;
import com.github.ubiquitousspice.dreamdimension.entities.EntityConfusedVillager;
import com.github.ubiquitousspice.dreamdimension.entities.EntityUnicorn;
import com.google.common.collect.Maps;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderUnicorn extends RenderLiving
{
    private static final Map HashMap = Maps.newHashMap();
    private ResourceLocation location = new ResourceLocation(DreamDimension.MODID, "textures/entity/unicorn.png");

    public RenderUnicorn(ModelBase par1ModelBase, float par2)
    {
        super(par1ModelBase, par2);
    }

    protected void func_110847_a(EntityUnicorn par1EntityUnicorn, float par2)
    {
        float f1 = 1.0F;

        GL11.glScalef(f1, f1, f1);
        super.preRenderCallback(par1EntityUnicorn, par2);
    }

    protected void func_110846_a(EntityUnicorn par1EntityUnicorn, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        if (par1EntityUnicorn.isInvisible())
        {
            mainModel.setRotationAngles(par2, par3, par4, par5, par6, par7, par1EntityUnicorn);
        }
        else
        {
            func_110813_b(par1EntityUnicorn);
            mainModel.render(par1EntityUnicorn, par2, par3, par4, par5, par6, par7);
        }	
    }
    protected ResourceLocation func_110863_a(EntityUnicorn par1EntityUnicorn)
    {
        return location;
    }

    protected ResourceLocation func_110849_a(EntityUnicorn par1EntityUnicorn)
    {
        return location;
    }

   

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
        func_110847_a((EntityUnicorn)par1EntityLivingBase, par2);
    }

    /**
     * Renders the model in RenderLiving
     */
    @Override
    protected void renderModel(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        func_110846_a((EntityUnicorn)par1EntityLivingBase, par2, par3, par4, par5, par6, par7);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return location;
    }
}
