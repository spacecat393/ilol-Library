package com.nali.list.gui;

import com.nali.ilol.data.BoxData;
import com.nali.ilol.entities.EntitiesRegistryHelper;
import com.nali.ilol.entities.skinning.SkinningEntities;
import com.nali.ilol.entities.skinning.SkinningEntitiesRender;
import com.nali.ilol.gui.MixGui;
import com.nali.ilol.gui.features.TargetGUIFeatures;
import com.nali.ilol.networks.NetworksRegistry;
import com.nali.ilol.render.BoxRender;
import com.nali.ilol.system.Reference;
import com.nali.list.container.InventoryContainer;
import com.nali.list.messages.SkinningEntitiesServerMessage;
import com.nali.render.SkinningRender;
import com.nali.system.bytes.BytesWriter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

import static com.nali.ilol.gui.features.TargetGUIFeatures.TARGET_INT_ARRAY;
import static com.nali.ilol.gui.features.TargetGUIFeatures.TROUBLEMAKER_INT_ARRAY;
import static com.nali.ilol.render.RenderHelper.DATALOADER;
import static com.nali.system.Timing.TD;

@SideOnly(Side.CLIENT)
public class InventoryGui extends MixGui
{
    public static ResourceLocation GUI_RESOURCELOCATION = new ResourceLocation(Reference.MOD_ID, "textures/gui/inventory.png");
    public SkinningEntitiesRender skinningentitiesrender;
    public byte page;
    public float px, py/*, rx, ry*/;
    public BoxRender boxrender = new BoxRender(new BoxData(), DATALOADER);

    public InventoryGui(IInventory iinventory, SkinningEntities skinningentities)
    {
        super(new InventoryContainer(iinventory, skinningentities, Minecraft.getMinecraft().player));
        this.mc = Minecraft.getMinecraft();
        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        this.setWorldAndResolution(Minecraft.getMinecraft(), scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight());

//        if (skinningentities != null)
//        {
        this.skinningentitiesrender = (SkinningEntitiesRender)this.mc.getRenderManager().getEntityRenderObject(skinningentities);
//        SkinningRender skinningrender = (SkinningRender)skinningentities.client_object;
//        float s = -25.0F;
//        skinningrender.sx = s;
//        skinningrender.sy = s;
//        skinningrender.sz = s;
//        skinningrender.y = 0.0F;
//        skinningrender.z = 10.0F;
//        this.rx = -1.57079632679F;
//        skinningrender.rx = -90.0F;
//        skinningdata.screen_float_array[6] = -90.0F;
//        skinningrender.rz = 0.0F;
//        }

        this.xSize = 256;//170;
        this.ySize = 256;//206;
//        this.boxrender.rx = 90.0F;
//        this.boxrender.ry = 45.0F;
        this.boxrender.rz = 45.0F * 3.0F;
        float s = -3.5F;
        this.boxrender.sx = s;
        this.boxrender.sy = s;
        this.boxrender.sz = s;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.message_state = -1;

//        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
//        this.setWorldAndResolution(Minecraft.getMinecraft(), scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight());

        SkinningEntities skinningentities = ((InventoryContainer)this.inventorySlots).skinningentities;
        SkinningRender skinningrender = (SkinningRender)skinningentities.client_object;
        if (!skinningrender.should_render)
        {
            this.skinningentitiesrender.updateData(skinningentities, this.mc.getRenderPartialTicks());
        }

//        skinningrender.width = this.width;
//        skinningrender.height = this.height;
        skinningrender.x = this.guiLeft + 127.5F;
        skinningrender.y = this.guiTop + 72;
//        skinningrender.sx = s;
//        skinningrender.sy = s;
//        skinningrender.sz = s;
        skinningrender.lig_b = 208.0F;
        skinningrender.lig_s = 240.0F;
        skinningrender.objectscreendraw.renderScreen(1.0F, 1.0F, 1.0F, 1.0F);

        this.boxrender.x = this.guiLeft + 84 + 6.5F + 0.5F;
        this.boxrender.y = this.guiTop + 33 + 18 + 18 + 6.5F + 2.5F;
        this.boxrender.objectscreendraw.renderScreen(1.0F, 1.0F, 1.0F, 0.9F);

        if (this.page == 0)
        {
            super.drawScreen(mouseX, mouseY, partialTicks);
        }
        else
        {
            this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        }

//        InventoryContainer inventorycontainer = (InventoryContainer)this.inventorySlots;
//        SkinningData skinningdata = null;
//        if (skinningentities != null)
//        {
//        skinningentitiesrender.renderOnScreen(skinningentities, this.width, this.height, mouseX, mouseY);
//        }

//        float ty = 0.0F;
        int x = this.guiLeft + 100, y = this.guiTop + 26, width = 56, height = 49;
        if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height)
        {
            if (this.mouse_clicked == 0)
            {
//                if (skinningentities != null)
//                {
                skinningrender.rz += ((mouseX - this.px) * 10.0F) * TD;
    //                skinningdata.screen_float_array[5] += ((mouseY - this.py) / 20.0F) * DataLoader.TD;
    //                ty = ;
    //                skinningdata.screen_float_array[12] = 0.476175F;
    //                this.rx += mouseX - this.px;
    //                this.ry += mouseY - this.py;
//                }
            }
        }

//        new Quaternion(this.rx, this.ry, 0.0F).getM4x4().multiply(skinningdata.skinning_float_array, 0);

        this.px = mouseX;
        this.py = mouseY;

//        if (skinningentities != null)
//        {
//        float s = -25.0F;
//        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
//        if (scaledresolution.getScaleFactor() <= 1)//this.mc.gameSettings.guiScale == 0//if ((this.width / (double)this.height) <= 1.3325D || (this.height / (double)this.width) <= 0.5608D)
//        {
//            s = 0.1F;
//        }

//        new Quaternion(-rx, -ry, 0.0F).getM4x4().multiply(skinningdata.skinning_float_array, 0);
//        }

        if (this.page == 0)
        {
            this.renderHoveredToolTip(mouseX, mouseY);
        }
        else if (this.page == 2)
        {
            this.setMessage();
        }

        this.renderEntitiesName(skinningentities, this.guiLeft + 100, this.guiTop + 75, 56, 11, mouseX, mouseY);

        x = this.guiLeft + 47; y = this.guiTop + 25; width = 5; height = 62;
        if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height)
        {
            this.drawHoveringText(new String[]
            {
                I18n.translateToLocal("gui.info.hp") + " : " + skinningentities.getHealth(),
                I18n.translateToLocal("gui.info.mhp") + " : " + skinningentities.getMaxHealth()
            }, mouseX, mouseY, false);
        }

        x = this.guiLeft + 158;/* y = this.guiTop + 25;*/ width = 9; height = 19;
        if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height)
        {
            int size = skinningentities.getAttributeMap().getAllAttributes().size();
            String[] string_array = new String[size];
            int index = 0;
            for (IAttributeInstance iattributeinstance : skinningentities.getAttributeMap().getAllAttributes())
            {
                double value = iattributeinstance.getAttributeValue();
                for (AttributeModifier attributemodifier : iattributeinstance.getModifiers())
                {
                    value += attributemodifier.getAmount();
                }

                string_array[index++] = I18n.translateToLocal("attribute.name." + iattributeinstance.getAttribute().getName()) + " : " + value;
            }
            this.drawHoveringText(string_array, mouseX, mouseY, false);
        }

//        get from server
//        x = this.guiLeft + 204; y = this.guiTop + 144; width = 5; height = 6;
//        if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height)
//        {
//            int size = skinningentities.getActivePotionEffects().size();
//            String[] string_array = new String[size];
//            int[] int_array = new int[size];
//            int index = 0;
//            for (PotionEffect potioneffect : skinningentities.getActivePotionEffects())
//            {
//                int id = Potion.getIdFromPotion(potioneffect.getPotion());
//                int_array[index] = index % 2 == 0 ? 0xFFFFFFFF : 0xFFF85A52;
//                string_array[index++] = I18n.translateToLocal(potioneffect.getEffectName()) + " : D[" + potioneffect.getDuration() + "] A[" + potioneffect.getAmplifier() + "]";
//            }
//            this.drawHoveringText(string_array, int_array, mouseX, mouseY);
//        }

        x = this.guiLeft + 57; y = this.guiTop + 27; width = 5; height = 6;
        if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height)
        {
            if (this.mouse_released == 0)
            {
                byte[] byte_array = new byte[21];
                byte_array[0] = 2;
                BytesWriter.set(byte_array, skinningentities.client_uuid, 1);
                BytesWriter.set(byte_array, 1, 17);
                NetworksRegistry.I.sendToServer(new SkinningEntitiesServerMessage(byte_array));
                byte_array = new byte[22];
                byte_array[0] = 1;
                BytesWriter.set(byte_array, skinningentities.client_uuid, 1);
                BytesWriter.set(byte_array, skinningentities.skinningentitiesbytes.LOCK_INVENTORY(), 17);
                byte_array[21] = skinningentities.client_work_byte_array[skinningentities.skinningentitiesbytes.LOCK_INVENTORY()] == 1 ? (byte)0 : (byte)1;
                NetworksRegistry.I.sendToServer(new SkinningEntitiesServerMessage(byte_array));
            }

            String lock = I18n.translateToLocal("gui.info.l");
            String unlock = I18n.translateToLocal("gui.info.ul");

            this.drawHoveringText(new String[]
            {
                I18n.translateToLocal("gui.info.cv") + " : " + (skinningentities.client_work_byte_array[skinningentities.skinningentitiesbytes.LOCK_INVENTORY()] == 1 ? lock : unlock),
                lock + " : " + I18n.translateToLocal("gui.info.l0"),
                unlock + " : " + I18n.translateToLocal("gui.info.ul0")
            }, mouseX, mouseY, true);
        }

        /*x = this.guiLeft + 57; */y = this.guiTop + 35;/* width = 5; height = 6;*/
        if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height)
        {
            if (this.mouse_released == 0)
            {
                byte[] byte_array = new byte[21];
                byte_array[0] = 2;
                BytesWriter.set(byte_array, skinningentities.client_uuid, 1);
                BytesWriter.set(byte_array, 1, 17);
                NetworksRegistry.I.sendToServer(new SkinningEntitiesServerMessage(byte_array));
                byte_array = new byte[22];
                byte_array[0] = 1;
                BytesWriter.set(byte_array, skinningentities.client_uuid, 1);
                BytesWriter.set(byte_array, skinningentities.skinningentitiesbytes.LOCK_DAMAGE(), 17);
                byte_array[21] = skinningentities.client_work_byte_array[skinningentities.skinningentitiesbytes.LOCK_DAMAGE()] == 1 ? (byte)0 : (byte)1;
                NetworksRegistry.I.sendToServer(new SkinningEntitiesServerMessage(byte_array));
            }

            String lock = I18n.translateToLocal("gui.info.l");
            String unlock = I18n.translateToLocal("gui.info.ul");

            this.drawHoveringText(new String[]
            {
                I18n.translateToLocal("gui.info.cv") + " : " + (skinningentities.client_work_byte_array[skinningentities.skinningentitiesbytes.LOCK_DAMAGE()] == 1 ? lock : unlock),
                lock + " : " + I18n.translateToLocal("gui.info.l1"),
                unlock + " : " + I18n.translateToLocal("gui.info.ul1")
            }, mouseX, mouseY, true);
        }

        x = this.guiLeft + 82; y = this.guiTop + 31; width = 18; height = 18;
        if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height)
        {
            if (this.mouse_released == 0)
            {
                this.page = this.page == 2 ? 0 : this.page == 1 ? 2 : (byte)1;
            }

            String ss = I18n.translateToLocal("gui.info.ss");
            String sp = I18n.translateToLocal("gui.info.sp");
            String si = I18n.translateToLocal("gui.info.si");

            this.drawHoveringText(new String[]
            {
                I18n.translateToLocal("gui.info.cv") + " : " + (this.page == 2 ? ss : this.page == 1 ? sp : si),
                sp + " : " + I18n.translateToLocal("gui.info.sp0"),
                si + " : " + I18n.translateToLocal("gui.info.si0"),
                ss + " : " + I18n.translateToLocal("gui.info.ss0")
            }, mouseX, mouseY, true);
        }

        /*x = this.guiLeft + 82; */y = this.guiTop + 31 + 18;// width = 18; height = 18;
        if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height)
        {
            if (this.mouse_released == 0)
            {
                byte[] byte_array = new byte[1];
                byte_array[0] = 9;
                NetworksRegistry.I.sendToServer(new SkinningEntitiesServerMessage(byte_array));
            }

            this.drawHoveringText(new String[]
            {
                I18n.translateToLocal("gui.info.a2"),
                I18n.translateToLocal("gui.info.a20")
            }, mouseX, mouseY, false);
        }

        /*x = this.guiLeft + 82; */y = this.guiTop + 31 + 18 + 18;// width = 18; height = 18;
        if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height)
        {
            if (this.mouse_released == 0)
            {
                byte[] byte_array = new byte[17];
                byte_array[0] = 8;
                BytesWriter.set(byte_array, skinningentities.client_uuid, 1);
                NetworksRegistry.I.sendToServer(new SkinningEntitiesServerMessage(byte_array));
            }

            this.drawHoveringText(new String[]
            {
                I18n.translateToLocal("gui.info.i0"),
                I18n.translateToLocal("gui.info.i00")
            }, mouseX, mouseY, false);
        }

        if (this.page > 0)
        {
            x = this.guiLeft + 48; y = this.guiTop + 89; width = 16; height = 16;
            if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height)
            {
                if (this.page == 2)
                {
                    this.message_state = 0;
                    this.drawHoveringText(new String[]
                    {
                        this.message_stringbuilder.toString(),
                        I18n.translateToLocal("gui.info.t0"),
                        I18n.translateToLocal("gui.info.st0"),
                        I18n.translateToLocal("gui.info.st1")
                    }, mouseX, mouseY, false);
                }
                else
                {
                    if (this.mouse_released == 0)
                    {
                        byte[] byte_array = new byte[22];
                        byte_array[0] = 1;
                        BytesWriter.set(byte_array, skinningentities.client_uuid, 1);
                        BytesWriter.set(byte_array, skinningentities.skinningentitiesbytes.SIT(), 17);
                        byte_array[21] = skinningentities.client_work_byte_array[skinningentities.skinningentitiesbytes.SIT()] == 1 ? (byte)0 : (byte)1;
                        NetworksRegistry.I.sendToServer(new SkinningEntitiesServerMessage(byte_array));
                    }

                    String bs0 = I18n.translateToLocal("gui.info.bs0");
                    String bs1 = I18n.translateToLocal("gui.info.bs1");

                    this.drawHoveringText(new String[]
                    {
                        I18n.translateToLocal("gui.info.cv") + " : " + (skinningentities.client_work_byte_array[skinningentities.skinningentitiesbytes.SIT()] == 1 ? bs1 : bs0),
                        bs0 + " : " + I18n.translateToLocal("gui.info.bs00"),
                        bs1 + " : " + I18n.translateToLocal("gui.info.bs10")
                    }, mouseX, mouseY, true);
                }
            }

            x = this.guiLeft + 66;// y = this.guiTop + 89; width = 16; height = 16;
            if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height)
            {
                if (this.page == 2)
                {
                    this.message_state = 1;
                    this.drawHoveringText(new String[]
                    {
                        this.message_stringbuilder.toString(),
                        I18n.translateToLocal("gui.info.t5"),
                        I18n.translateToLocal("gui.info.st0"),
                        I18n.translateToLocal("gui.info.st1")
                    }, mouseX, mouseY, false);
                }
                else
                {
                    if (this.mouse_released == 0)
                    {
                        byte[] byte_array = new byte[21];
                        byte_array[0] = 2;
                        BytesWriter.set(byte_array, skinningentities.client_uuid, 1);
                        BytesWriter.set(byte_array, 1, 17);
                        NetworksRegistry.I.sendToServer(new SkinningEntitiesServerMessage(byte_array));
                        byte_array = new byte[22];
                        byte_array[0] = 1;
                        BytesWriter.set(byte_array, skinningentities.client_uuid, 1);
                        BytesWriter.set(byte_array, skinningentities.skinningentitiesbytes.FOLLOW(), 17);
                        byte_array[21] = skinningentities.client_work_byte_array[skinningentities.skinningentitiesbytes.FOLLOW()] == 1 ? (byte)0 : (byte)1;
                        NetworksRegistry.I.sendToServer(new SkinningEntitiesServerMessage(byte_array));
                    }

                    String bf0 = I18n.translateToLocal("gui.info.bf0");
                    String bf1 = I18n.translateToLocal("gui.info.bf1");

                    this.drawHoveringText(new String[]
                    {
                        I18n.translateToLocal("gui.info.cv") + " : " + (skinningentities.client_work_byte_array[skinningentities.skinningentitiesbytes.FOLLOW()] == 1 ? bf1 : bf0),
                        bf0 + " : " + I18n.translateToLocal("gui.info.bf00"),
                        bf1 + " : " + I18n.translateToLocal("gui.info.bf10"),
                        I18n.translateToLocal("gui.info.bf000")
                    }, mouseX, mouseY, true);
                }
            }

            x = this.guiLeft + 84;// y = this.guiTop + 89; width = 16; height = 16;
            if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height)
            {
                if (this.page == 2)
                {
                    String[] string_array = new String[EntitiesRegistryHelper.ENTITY_KEY_ARRAY.length + 1];
                    string_array[0] = I18n.translateToLocal("gui.info.t3");
                    int index = 1;
                    for (Object o : EntitiesRegistryHelper.ENTITY_KEY_ARRAY)
                    {
                        string_array[index] = (index++ - 1) + " " + ((Class)o).getName();
                    }

                    this.drawHoveringText(string_array, mouseX, mouseY, false);
                }
                else
                {
                    if (this.mouse_released == 0)
                    {
                        byte[] byte_array = new byte[22];
                        byte_array[0] = 1;
                        BytesWriter.set(byte_array, skinningentities.client_uuid, 1);
                        BytesWriter.set(byte_array, skinningentities.skinningentitiesbytes.FIND_ITEM(), 17);
                        byte_array[21] = skinningentities.client_work_byte_array[skinningentities.skinningentitiesbytes.FIND_ITEM()] == 1 ? (byte)0 : (byte)1;
                        NetworksRegistry.I.sendToServer(new SkinningEntitiesServerMessage(byte_array));
                    }

                    String bfi0 = I18n.translateToLocal("gui.info.bfi0");
                    String bfi1 = I18n.translateToLocal("gui.info.bfi1");

                    this.drawHoveringText(new String[]
                    {
                        I18n.translateToLocal("gui.info.cv") + " : " + (skinningentities.client_work_byte_array[skinningentities.skinningentitiesbytes.FIND_ITEM()] == 1 ? bfi1 : bfi0),
                        bfi0 + " : " + I18n.translateToLocal("gui.info.bfi00"),
                        bfi1 + " : " + I18n.translateToLocal("gui.info.bfi10")
                    }, mouseX, mouseY, true);
                }
            }

            x = this.guiLeft + 102;// y = this.guiTop + 89; width = 16; height = 16;
            if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height)
            {
                if (this.page == 1)
                {
                    if (this.mouse_released == 0)
                    {
                        byte[] byte_array = new byte[22];
                        byte_array[0] = 1;
                        BytesWriter.set(byte_array, skinningentities.client_uuid, 1);
                        BytesWriter.set(byte_array, skinningentities.skinningentitiesbytes.RANDOM_WALK(), 17);
                        byte_array[21] = skinningentities.client_work_byte_array[skinningentities.skinningentitiesbytes.RANDOM_WALK()] == 1 ? (byte)0 : (byte)1;
                        NetworksRegistry.I.sendToServer(new SkinningEntitiesServerMessage(byte_array));
                    }

                    String brw0 = I18n.translateToLocal("gui.info.brw0");
                    String brw1 = I18n.translateToLocal("gui.info.brw1");

                    this.drawHoveringText(new String[]
                    {
                        I18n.translateToLocal("gui.info.cv") + " : " + (skinningentities.client_work_byte_array[skinningentities.skinningentitiesbytes.RANDOM_WALK()] == 1 ? brw1 : brw0),
                        brw0 + " : " + I18n.translateToLocal("gui.info.brw00"),
                        brw1 + " : " + I18n.translateToLocal("gui.info.brw10")
                    }, mouseX, mouseY, true);
                }
            }

            x = this.guiLeft + 120;// y = this.guiTop + 89; width = 16; height = 16;
            if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height)
            {
                if (this.page == 1)
                {
                    if (this.mouse_released == 0)
                    {
                        byte[] byte_array = new byte[22];
                        byte_array[0] = 1;
                        BytesWriter.set(byte_array, skinningentities.client_uuid, 1);
                        BytesWriter.set(byte_array, skinningentities.skinningentitiesbytes.RANDOM_LOOK(), 17);
                        byte_array[21] = skinningentities.client_work_byte_array[skinningentities.skinningentitiesbytes.RANDOM_LOOK()] == 1 ? (byte)0 : (byte)1;
                        NetworksRegistry.I.sendToServer(new SkinningEntitiesServerMessage(byte_array));
                    }

                    String brl0 = I18n.translateToLocal("gui.info.brl0");
                    String brl1 = I18n.translateToLocal("gui.info.brl1");

                    this.drawHoveringText(new String[]
                    {
                        I18n.translateToLocal("gui.info.cv") + " : " + (skinningentities.client_work_byte_array[skinningentities.skinningentitiesbytes.RANDOM_LOOK()] == 1 ? brl1 : brl0),
                        brl0 + " : " + I18n.translateToLocal("gui.info.brl00"),
                        brl1 + " : " + I18n.translateToLocal("gui.info.brl10")
                    }, mouseX, mouseY, true);
                }
            }

            x = this.guiLeft + 138;// y = this.guiTop + 89; width = 16; height = 16;
            if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height)
            {
                if (this.page == 1)
                {
                    if (this.mouse_released == 0)
                    {
                        byte[] byte_array = new byte[22];
                        byte_array[0] = 1;
                        BytesWriter.set(byte_array, skinningentities.client_uuid, 1);
                        BytesWriter.set(byte_array, skinningentities.skinningentitiesbytes.ATTACK(), 17);
                        byte_array[21] = skinningentities.client_work_byte_array[skinningentities.skinningentitiesbytes.ATTACK()] == 1 ? (byte)0 : (byte)1;
                        NetworksRegistry.I.sendToServer(new SkinningEntitiesServerMessage(byte_array));
                    }

                    String ba0 = I18n.translateToLocal("gui.info.ba0");
                    String ba1 = I18n.translateToLocal("gui.info.ba1");

                    this.drawHoveringText(new String[]
                    {
                        I18n.translateToLocal("gui.info.cv") + " : " + (skinningentities.client_work_byte_array[skinningentities.skinningentitiesbytes.ATTACK()] == 1 ? ba1 : ba0),
                        ba0 + " : " + I18n.translateToLocal("gui.info.ba00"),
                        ba1 + " : " + I18n.translateToLocal("gui.info.ba10")
                    }, mouseX, mouseY, true);
                }
            }

            x = this.guiLeft + 156;// y = this.guiTop + 89; width = 16; height = 16;
            if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height)
            {
                if (this.page == 1)
                {
                    if (this.mouse_released == 0)
                    {
                        byte[] byte_array = new byte[22];
                        byte_array[0] = 1;
                        BytesWriter.set(byte_array, skinningentities.client_uuid, 1);
                        BytesWriter.set(byte_array, skinningentities.skinningentitiesbytes.REVIVE(), 17);
                        byte_array[21] = skinningentities.client_work_byte_array[skinningentities.skinningentitiesbytes.REVIVE()] == 1 ? (byte)0 : (byte)1;
                        NetworksRegistry.I.sendToServer(new SkinningEntitiesServerMessage(byte_array));
                    }

                    String br0 = I18n.translateToLocal("gui.info.br0");
                    String br1 = I18n.translateToLocal("gui.info.br1");

                    this.drawHoveringText(new String[]
                    {
                        I18n.translateToLocal("gui.info.cv") + " : " + (skinningentities.client_work_byte_array[skinningentities.skinningentitiesbytes.REVIVE()] == 1 ? br1 : br0),
                        br0 + " : " + I18n.translateToLocal("gui.info.br00"),
                        br1 + " : " + I18n.translateToLocal("gui.info.br10")
                    }, mouseX, mouseY, true);
                }
            }

            x = this.guiLeft + 48; y = this.guiTop + 107;// width = 16; height = 16;
            if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height)
            {
                if (this.page == 2)
                {
                    this.message_state = 2;
                    this.drawHoveringText(new String[]
                    {
                        this.message_stringbuilder.toString(),
                        I18n.translateToLocal("gui.info.t1"),
                        I18n.translateToLocal("gui.info.st0"),
                        I18n.translateToLocal("gui.info.st1")
                    }, mouseX, mouseY, false);
                }
            }

            /*x = this.guiLeft + 48; */y = this.guiTop + 125;// width = 16; height = 16;
            if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height)
            {
                if (this.page == 2)
                {
                    if (this.mouse_released == 0)
                    {
                        byte[] byte_array = new byte[1 + 16];
                        byte_array[0] = 11;
                        BytesWriter.set(byte_array, skinningentities.client_uuid, 1);
                        NetworksRegistry.I.sendToServer(new SkinningEntitiesServerMessage(byte_array));
                    }

                    TargetGUIFeatures.draw(TARGET_INT_ARRAY, "gui.info.t2", this, mouseX, mouseY);
                }
            }

            x = this.guiLeft + 66; y = this.guiTop + 107;// width = 16; height = 16;
            if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height)
            {
                if (this.page == 2)
                {
                    this.message_state = 3;
                    this.drawHoveringText(new String[]
                    {
                        this.message_stringbuilder.toString(),
                        I18n.translateToLocal("gui.info.t6"),
                        I18n.translateToLocal("gui.info.st0"),
                        I18n.translateToLocal("gui.info.st1")
                    }, mouseX, mouseY, false);
                }
            }

            /*x = this.guiLeft + 66; */y = this.guiTop + 125;// width = 16; height = 16;
            if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height)
            {
                if (this.page == 2)
                {
                    if (this.mouse_released == 0)
                    {
                        byte[] byte_array = new byte[1 + 16];
                        byte_array[0] = 14;
                        BytesWriter.set(byte_array, skinningentities.client_uuid, 1);
                        NetworksRegistry.I.sendToServer(new SkinningEntitiesServerMessage(byte_array));
                    }

                    TargetGUIFeatures.draw(TROUBLEMAKER_INT_ARRAY, "gui.info.t4", this, mouseX, mouseY);
                }
            }
        }

        this.mouse_released = -1;
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        boolean gl_blend = GL11.glIsEnabled(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_BLEND);

//        this.mc.getTextureManager().bindTexture(GUI0_RESOURCELOCATION);
//
//        blit(this.guiLeft, this.guiTop, 0, 0, 16, 16, 16, 16);

        this.mc.getTextureManager().bindTexture(GUI_RESOURCELOCATION);
        this.drawTexturedModalRect(this.guiLeft + 43, this.guiTop + 25, 86, 50, 170, 206);

        float tx = this.guiLeft + 84;
        if (this.page == 0)
        {
            this.drawTexturedModalRect(tx, this.guiTop + 33, 238, 0, 14, 14);
        }
        else if (this.page == 2)
        {
            this.drawTexturedModalRect(tx, this.guiTop + 33, 44, 0, 14, 14);
        }
        else
        {
            this.drawTexturedModalRect(tx, this.guiTop + 33, 16, 0, 14, 14);
        }

        this.drawTexturedModalRect(tx, this.guiTop + 53/*33 + 18 + 2*/, 224, 0, 14, 11);

        for (int i = 0; i < 36; ++i)
        {
            Slot slot = this.inventorySlots.inventorySlots.get(i);
            if (slot.getStack().isEmpty())
            {
                this.drawTexturedModalRect(this.guiLeft + 4 + slot.xPos, this.guiTop + 5 + slot.yPos, 30, 14, 8, 6);
            }
        }

        if (this.page == 0)
        {
            for (int i = 36; i < 63; ++i)
            {
                Slot slot = this.inventorySlots.inventorySlots.get(i);

                if (slot.getStack().isEmpty())
                {
                    this.drawTexturedModalRect(this.guiLeft + 4 + slot.xPos, this.guiTop + 5 + slot.yPos, 30, 14, 8, 6);
                }
            }
        }

        switch (this.page)
        {
            case 0:
            {
                Slot slot = this.inventorySlots.inventorySlots.get(63);
                if (slot.getStack().isEmpty())
                {
                    this.drawTexturedModalRect(this.guiLeft + 1 + slot.xPos, this.guiTop + 1 + slot.yPos, 0, 104, 14, 14);
                }
                slot = this.inventorySlots.inventorySlots.get(64);
                if (slot.getStack().isEmpty())
                {
                    this.drawTexturedModalRect(this.guiLeft + 1 + slot.xPos, this.guiTop + 1 + slot.yPos, 0, 118, 14, 14);
                }
                slot = this.inventorySlots.inventorySlots.get(65);
                if (slot.getStack().isEmpty())
                {
                    this.drawTexturedModalRect(this.guiLeft + 1 + slot.xPos, this.guiTop + 1 + slot.yPos, 0, 48, 14, 14);
                }
                slot = this.inventorySlots.inventorySlots.get(66);
                if (slot.getStack().isEmpty())
                {
                    this.drawTexturedModalRect(this.guiLeft + 1 + slot.xPos, this.guiTop + 1 + slot.yPos, 0, 62, 14, 14);
                }
                slot = this.inventorySlots.inventorySlots.get(67);
                if (slot.getStack().isEmpty())
                {
                    this.drawTexturedModalRect(this.guiLeft + 3 + slot.xPos, this.guiTop + 1 + slot.yPos, 0, 76, 10, 14);
                }
                slot = this.inventorySlots.inventorySlots.get(68);
                if (slot.getStack().isEmpty())
                {
                    this.drawTexturedModalRect(this.guiLeft + 1 + slot.xPos, this.guiTop + 1 + slot.yPos, 0, 90, 14, 14);
                }

                break;
            }
            case 1:
            {
                this.drawTexturedModalRect(this.guiLeft + 49, this.guiTop + 90, 86, 0, 14, 14);
                this.drawTexturedModalRect(this.guiLeft + 67, this.guiTop + 90, 30, 0, 14, 14);
                this.drawTexturedModalRect(this.guiLeft + 85, this.guiTop + 90, 142, 0, 14, 14);
                this.drawTexturedModalRect(this.guiLeft + 105, this.guiTop + 92, 52, 14, 12, 12);
                this.drawTexturedModalRect(this.guiLeft + 121, this.guiTop + 94, 38, 14, 14, 8);
                this.drawTexturedModalRect(this.guiLeft + 139, this.guiTop + 90, 58, 0, 14, 14);
                this.drawTexturedModalRect(this.guiLeft + 157, this.guiTop + 90, 64, 14, 14, 14);

                break;
            }
            case 2:
            {
                this.drawTexturedModalRect(this.guiLeft + 49, this.guiTop + 90, 106, 14, 14, 14);
                this.drawTexturedModalRect(this.guiLeft + 67, this.guiTop + 90, 78, 14, 14, 14);
                this.drawTexturedModalRect(this.guiLeft + 67 + 18, this.guiTop + 90, 16, 0, 14, 14);
                this.drawTexturedModalRect(this.guiLeft + 49, this.guiTop + 108, 92, 14, 14, 14);
                this.drawTexturedModalRect(this.guiLeft + 67, this.guiTop + 108, 92, 14, 14, 14);
                this.drawTexturedModalRect(this.guiLeft + 49, this.guiTop + 126, 120, 14, 14, 14);
                this.drawTexturedModalRect(this.guiLeft + 67, this.guiTop + 126, 120, 14, 14, 14);
                break;
            }
            default:
            {
                break;
            }
        }

        SkinningEntities skinningentities = ((InventoryContainer)this.inventorySlots).skinningentities;
        float health_percent = skinningentities.getHealth() / skinningentities.getMaxHealth();
        int health = (int)(59 * health_percent);
        for (int i = 0; i < health; ++i)
        {
            this.drawTexturedModalRect(this.guiLeft + 48, this.guiTop + 84 - i, 0, 0, 3, 1);
        }

        this.drawTexturedModalRect(this.guiLeft + 57, this.guiTop + 27, 30, skinningentities.client_work_byte_array[skinningentities.skinningentitiesbytes.LOCK_INVENTORY()] == 1 ? 20 : 26, 5, 6);
        this.drawTexturedModalRect(this.guiLeft + 57, this.guiTop + 35, 30, skinningentities.client_work_byte_array[skinningentities.skinningentitiesbytes.LOCK_DAMAGE()] == 1 ? 20 : 26, 5, 6);

        if (gl_blend)
        {
            GL11.glEnable(GL11.GL_BLEND);
        }
        else
        {
            GL11.glDisable(GL11.GL_BLEND);
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException
    {
//        this.type_char = typedChar;
//        this.key_code = keyCode;
//        CutePomi.LOGGER.info((int)typedChar);
        if (this.message_state != -1)
        {
            int index = this.message_stringbuilder.length() - 1;
            char end = this.message_stringbuilder.charAt(index);

            switch (typedChar)
            {
                case '\b':
                {
                    if (this.message_stringbuilder.length() > 1)
                    {
                        this.message_stringbuilder.deleteCharAt(index - 1);
                    }

                    break;
                }
                case '\r':
                {
                    SkinningEntities skinningentities = ((InventoryContainer)this.inventorySlots).skinningentities;

                    byte[] string_byte_array = this.message_stringbuilder.toString().getBytes();
                    int string_byte_array_size = string_byte_array.length - 1;
                    byte[] byte_array = new byte[string_byte_array_size + 1 + 16];

                    switch (this.message_state)
                    {
                        case 0:
                        {
                            byte_array[0] = 10;
                            break;
                        }
                        case 1:
                        {
                            byte_array[0] = 13;
                            break;
                        }
                        case 2:
                        {
                            byte_array[0] = 12;
                            break;
                        }
                        case 3:
                        {
                            byte_array[0] = 15;
                            break;
                        }
                        default:
                        {
                            break;
                        }
                    }

                    BytesWriter.set(byte_array, skinningentities.client_uuid, 1);
                    System.arraycopy(string_byte_array, 0, byte_array, 1 + 16, string_byte_array_size);
                    NetworksRegistry.I.sendToServer(new SkinningEntitiesServerMessage(byte_array));
                    this.message_stringbuilder.setLength(0);
                    this.message_stringbuilder.append("!");

                    break;
                }
//                case 0:// if ((typedChar >= 'a' && typedChar <= 'z') || (typedChar >= 'A' && typedChar <= 'Z'))
//                {
//                    break;
//                }
                default:
                {
                    this.message_stringbuilder.deleteCharAt(index).append(typedChar).append(end);
                    break;
                }
            }
        }
        else
        {
            super.keyTyped(typedChar, keyCode);
        }
    }

//    @Override
//    public void initGui()
//    {
//        super.initGui();
//        SkinningData skinningdata = (SkinningData)((InventoryContainer)this.inventorySlots).skinningentities.client_object;
//        skinningdata.screen_float_array[7] = 0.0F;
//        skinningdata.screen_float_array[5] = -1.57079632679F;
//        skinningdata.screen_float_array[3] = 0.0F;
////        this.px = this.guiLeft + 127.5F;
////        this.py = this.guiTop + 50;
//    }
}
