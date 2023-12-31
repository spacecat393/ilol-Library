package com.nali.ilol;

import com.nali.ilol.capabilities.CapabilitiesRegistryHelper;
import com.nali.ilol.entities.EntitiesRegistryHelper;
import com.nali.ilol.entities.skinning.SkinningEntities;
import com.nali.ilol.gui.GuiHandler;
import com.nali.ilol.gui.OpenGUIHelper;
import com.nali.ilol.networks.NetworksRegistry;
import com.nali.ilol.render.RenderHelper;
import com.nali.ilol.system.Reference;
import com.nali.ilol.world.ChunkCallBack;
import com.nali.system.DataLoader;
import com.nali.system.Reflect;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.HashSet;
import java.util.WeakHashMap;

import static com.nali.ilol.entities.EntitiesRegistryHelper.ENTITY_CLASS_ENTRIES;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME)
public class ILOL
{
    @Instance
    public static ILOL I;

    public static Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);

    @EventHandler
    public void onFMLPreInitializationEvent(FMLPreInitializationEvent event)
    {
        if (event.getSide().isClient())
        {
            DataLoader.setModels(RenderHelper.DATALOADER, Reference.MOD_ID);

            CapabilitiesRegistryHelper.update();
            OpenGUIHelper.GUI_CLASS_LIST = Reflect.getClasses("com.nali.list.gui");
            OpenGUIHelper.GUI_CLASS_LIST.sort(Comparator.comparing(Class::getName));
            SkinningEntities.CLIENT_ENTITIES_MAP = new WeakHashMap<>();
            SkinningEntities.FAKE_CLIENT_ENTITIES_MAP = new WeakHashMap<>();
        }
    }

    @EventHandler
    public void onFMLInitializationEvent(FMLInitializationEvent event)
    {
        EntitiesRegistryHelper.set();
        NetworksRegistry.register();
        CapabilitiesRegistryHelper.register();
        NetworkRegistry.INSTANCE.registerGuiHandler(I, new GuiHandler());

        if (event.getSide().isClient())
        {
            RenderHelper.init();
        }
    }

    @EventHandler
    public void onFMLPostInitializationEvent(FMLPostInitializationEvent event)
    {
        EntitiesRegistryHelper.ENTITY_KEY_ARRAY = new HashSet<>(ENTITY_CLASS_ENTRIES.keySet()).toArray();
    }

    @EventHandler
    public void onFMLServerStartedEvent(FMLServerStartedEvent event)
    {
        SkinningEntities.SERVER_ENTITIES_MAP = new WeakHashMap<>();
        ChunkCallBack.set();
    }

    public static void error(Throwable t)
    {
        LOGGER.error(t);
        FMLCommonHandler.instance().exitJava(-1, true);
    }

    public static void error(String s)
    {
        LOGGER.error(s);
        FMLCommonHandler.instance().exitJava(-1, true);
    }
}
