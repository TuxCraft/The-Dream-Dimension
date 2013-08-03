package com.github.ubiquitousspice.dreamdimension;

import com.github.ubiquitousspice.dreamdimension.blocks.BlockBooster;
import com.github.ubiquitousspice.dreamdimension.blocks.BlockCheatyPortal;
import com.github.ubiquitousspice.dreamdimension.client.CreativeTabDream;
import com.github.ubiquitousspice.dreamdimension.dimension.WorldProviderMod;
import com.github.ubiquitousspice.dreamdimension.entities.EntityGiantItem;
import com.github.ubiquitousspice.dreamdimension.entities.EntityLargeSheep;
import com.github.ubiquitousspice.dreamdimension.world.BiomeGenDream;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.DimensionManager;

import java.util.logging.Logger;

//import com.github.ubiquitousspice.dreamdimension.blocks.BlockDreamDirt;

@Mod(modid = DreamDimension.MODID, version = DreamDimension.VERSION, name = "The Dream Dimension")
public class DreamDimension
{
    public static final String MODID = "dreamdimension";
    public static final String VERSION = "0.1";

    @Mod.Instance
    public static DreamDimension instance;

    @SidedProxy(modId = MODID, clientSide = "com.github.ubiquitousspice.dreamdimension.client.ProxyClient",
            serverSide = "com.github.ubiquitousspice.dreamdimension.ProxyCommon")
    public static ProxyCommon proxy;

    // Random Stuff
    public static Logger logger;
    public static Material material;
    public static boolean dreamMaterialBreakable = false;
    public static BiomeGenBase dreamy;
    public static CreativeTabDream tabDream;

    // IDS
    public static int dimensionID;
    static int startEntityId = 300;
    private int idDreamDirt;
    private int idDreamBooster;
    private int idPortalBlock;

    // blocks
    public static Block dreamDirt;
    public static Block boosterBlock;
    public static Block portalBlock;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        // get logger
        logger = event.getModLog();

        // mess with material
        material = (new MaterialDream());

        // CONFIGURATION STUFF
        {
            Configuration config = new Configuration(event.getSuggestedConfigurationFile());
            idDreamDirt = config.getBlock("DreamDirt", 300).getInt();
            idPortalBlock = config.getBlock("PortalBlock", 301).getInt();

            // config blockIDs
            int baseid = 300;
            idDreamDirt = config.getBlock("DreamDirt", baseid++).getInt();
            idDreamBooster = config.getBlock("DreamLauncher", baseid++).getInt();

            // config itemIDs

            // config dimension
            dimensionID = config.get(Configuration.CATEGORY_GENERAL, "Dream Dimension Idea", 2).getInt();

            // config other
            dreamMaterialBreakable = config.get("Adventure", "dreamMaterialBreakable", false).getBoolean(false);

            // save it.
            if (config.hasChanged())
            {
                config.save();
            }
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // creative tab
        tabDream = new CreativeTabDream();

        // do blocks and stuff here.
        dreamDirt = new Block(idDreamDirt, material).setUnlocalizedName(MODID + ":dreamDirt").setCreativeTab(tabDream);
        boosterBlock = new BlockBooster(idDreamBooster).setCreativeTab(tabDream);
        portalBlock = new BlockCheatyPortal(idPortalBlock).setUnlocalizedName(MODID + ".portalBlock");

        // registrations
        GameRegistry.registerBlock(dreamDirt, "dreamDirt");
        GameRegistry.registerBlock(boosterBlock, ItemBlockWithMetadata.class, "dreamBooster");
        GameRegistry.registerBlock(portalBlock, "portalBlock");

        // dimension stuff
        dreamy = new BiomeGenDream(25);
        DimensionManager.registerProviderType(dimensionID, WorldProviderMod.class, true);
        DimensionManager.registerDimension(dimensionID, dimensionID);

        // Entity stuff
        //registerEntity(EntityLargeSheep.class, "LargeSheep", 0xffffff, 0x000000);
        registerEntity(EntityLargeSheep.class, "LargeSheep", 0xffb5b5, 0x0fc6b7);
        //EntityRegistry.registerModEntity(EntityLargeSheep.class, "LargeSheep", 1, DreamDimension.instance, 80, 3,
        // true);
        EntityRegistry.addSpawn(EntityLargeSheep.class, 10000000, 1, 2, EnumCreatureType.creature, dreamy);
        //registerEntity(EntityLargeSheep.class, "GiantItem", 0xffffff, 0x000000);
        EntityRegistry.registerModEntity(EntityGiantItem.class, "GiantItem", 1, DreamDimension.instance, 80, 3, true);

        // entities
        proxy.registerRenderers();
    }

    /**
     * registers an entity
     *
     * @param entityClass Entity Class
     * @param entityName  Entity Name
     * @param fgColor     Primary foreground egg color
     * @param bgColor     Secondary background egg color
     */
    public void registerEntity(Class<? extends Entity> entityClass, String entityName, int fgColor, int bgColor)
    {
        int id = EntityRegistry.findGlobalUniqueEntityId();

        EntityRegistry.registerGlobalEntityID(entityClass, entityName, id);

        EntityList.entityEggs.put(Integer.valueOf(id), new EntityEggInfo(id, bgColor, fgColor));
    }
}
