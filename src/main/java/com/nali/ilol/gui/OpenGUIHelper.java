package com.nali.ilol.gui;

import com.nali.ilol.ILOL;
import com.nali.ilol.entities.skinning.SkinningEntities;
import com.nali.list.container.PlayerContainer;
import com.nali.list.gui.PlayerGui;
import com.nali.render.ObjectRender;
import com.nali.render.SkinningRender;
import com.nali.system.bytes.BytesReader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

public class OpenGUIHelper
{
    public static List<Class> GUI_CLASS_LIST;
    public static void call(byte[] data)
    {
        try
        {
            Class gui_class = GUI_CLASS_LIST.get(BytesReader.getInt(data, 0));
            Constructor gui_constructor = gui_class.getConstructor(IInventory.class, SkinningEntities.class);

            UUID uuid = BytesReader.getUUID(data, 4);
            Minecraft minecraft = Minecraft.getMinecraft();
//            World world = minecraft.player.getEntityWorld();
//            SkinningEntities skinningentities = SkinningEntities.CLIENT_ENTITIES_MAP.get(BytesReader.getUUID(data, 4));
//            Constructor entity_constructor = EntitiesRegistryHelper.ENTITIES_CLASS_LIST.get(BytesReader.getInt(data, 24)).getConstructor(World.class);
//            SkinningEntities skinningentities = (SkinningEntities)entity_constructor.newInstance(world);
            int list_id = BytesReader.getInt(data, 24);
            World world = minecraft.player.getEntityWorld();
            Entity entity = world.getEntityByID(list_id);

            if (!(entity instanceof SkinningEntities))
            {
                entity = EntityList.getClassFromID(BytesReader.getInt(data, 28)).getConstructor(World.class).newInstance(minecraft.player.getEntityWorld());
//                int size = data.length - 24 - 4 - 4;
//                byte[] nbt_byte_array = new byte[size];
//                System.arraycopy(data, data.length - size, nbt_byte_array, 0, nbt_byte_array.length);
//                entity.readFromNBT(NBTHelper.deserializeNBT(nbt_byte_array));
                entity.setEntityId(list_id);
                entity.setUniqueId(uuid);
                SkinningEntities skinningentities = (SkinningEntities)entity;
                skinningentities.fake = true;
                skinningentities.client_uuid = uuid;
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                skinningentities.initWriteEntityToNBT(nbttagcompound);
                for (int ii = 0; ii < skinningentities.bothdata.MaxPart(); ++ii)
                {
                    ((ObjectRender)skinningentities.client_object).texture_index_int_array[ii] = nbttagcompound.getInteger("int_" + ii);
                }

                String key = "int_" + skinningentities.bothdata.MaxPart();
                if (nbttagcompound.hasKey(key))
                {
                    ((SkinningRender)skinningentities.client_object).frame_int_array[0] = nbttagcompound.getInteger(key);
                }
            }

//            SkinningEntities.CLIENT_ENTITIES_MAP.clear();
            SkinningEntities.CLIENT_ENTITIES_MAP.put(uuid, (SkinningEntities)entity);

//            minecraft.displayGuiScreen(new InventoryGui(skinningentities));

            minecraft.displayGuiScreen((GuiContainer)gui_constructor.newInstance(minecraft.player.inventory, entity));
//            minecraft.addScheduledTask(() ->
//            {
//                try
//                {
//                    minecraft.displayGuiScreen((GuiContainer)constructor.newInstance(minecraft.player.inventory, skinningentities));
//                }
//                catch (InstantiationException | IllegalAccessException | InvocationTargetException e)
//                {
//                    throw new RuntimeException(e);
//                }
//            });

            minecraft.player.openContainer.windowId = BytesReader.getInt(data, 20);
        }
        catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e)
        {
            ILOL.error(e);
        }
    }

    public static void callPlayerGUI()
    {
        Minecraft minecraft = Minecraft.getMinecraft();
        minecraft.addScheduledTask(() ->
        {
            minecraft.displayGuiScreen(new PlayerGui(new PlayerContainer()));
        });
    }
}
