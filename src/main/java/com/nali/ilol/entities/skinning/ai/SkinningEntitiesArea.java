package com.nali.ilol.entities.skinning.ai;

import com.nali.ilol.entities.EntitiesRegistryHelper;
import com.nali.ilol.entities.skinning.SkinningEntities;
import com.nali.ilol.mixin.IMixinEntityRegistry;
import com.nali.ilol.mixin.IMixinWorldServer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.*;

public class SkinningEntitiesArea
{
    public SkinningEntities skinningentities;
    public Entity[] target_entity_array = new Entity[2]; // xp item
    public ArrayList<Entity> all_entity_arraylist = new ArrayList<Entity>(); // target
    public double[] distance_to_target_array = new double[2];

    public ArrayList<Integer> troublemaker_arraylist;
    public ArrayList<Integer> target_arraylist;

    public SkinningEntitiesArea(SkinningEntities skinningentities)
    {
        this.skinningentities = skinningentities;
    }

    public void init()
    {
        this.troublemaker_arraylist = new ArrayList<>();
        this.target_arraylist = new ArrayList<>();
    }

    public void onUpdate()
    {
        this.all_entity_arraylist.clear();
//        byte target_level = -1;
//
//        if (this.skinningentities.getByteDataParameterArray().length > 7)
//        {
//            target_level = this.getEntityData().get(this.getByteEntityDataAccessorArray()[7]);
//        }
        Arrays.fill(this.distance_to_target_array, Double.MAX_VALUE);


        Map<UUID, Entity> entity_map = ((IMixinWorldServer)this.skinningentities.getEntityWorld()).entitiesByUuid();
//        List<Entity> entity_list = this.skinningentities.getEntityWorld().getEntitiesWithinAABB(Entity.class, this.skinningentities.getEntityBoundingBox().grow(30.0D), entity -> entity.isEntityAlive());

        for (int i = 0; i < this.target_entity_array.length; ++i)
        {
            if ((this.target_entity_array[i] != null && this.target_entity_array[i].isEntityAlive()))
            {
                this.distance_to_target_array[i] = this.skinningentities.getDistanceSq(this.target_entity_array[i]);
            }
        }

//        for (Entity entity : entity_list)
        Set<UUID> keys_set = new HashSet<>(entity_map.keySet());
        for (UUID id : keys_set)
        {
            Entity entity = entity_map.get(id);

            if (entity.dimension != this.skinningentities.dimension || !entity.isEntityAlive())
            {
                continue;
            }

            // if (entity instanceof LivingEntity)
            // {
            //     LivingEntity livingentity = (LivingEntity)entity_list.get(i);
            // }

            if (entity instanceof EntityItem)
            {
                this.target_entity_array[1] = entity;
//                this.setTarget(entity, 1);
            }

            if (entity instanceof EntityXPOrb)
            {
                this.target_entity_array[0] = entity;
            }

            if (entity.isEntityAlive() && this.isTarget(entity))
            {
                this.all_entity_arraylist.add(entity);
//                this.setTarget(entity, 0);
            }
//            else if (target_level != -1 && entity.isAlive())
//            {
//                switch (target_level)
//                {
//                    case 1:
//                    {
//                        if (entity instanceof Enemy)
//                        {
//                            distance_to_target = this.setTarget(entity, distance_to_target);
//                            break;
//                        }
//
//                        if (entity instanceof Mob && ((Mob)entity).getTarget() == this)
//                        {
//                            distance_to_target = this.setTarget(entity, distance_to_target);
//                        }
//
//                        break;
//                    }
//                    case 2:
//                    {
//                        if (!FriendsMethods.isNotTarget(entity, this))
//                        {
//                            distance_to_target = this.setTarget(entity, distance_to_target);
//                        }
//
//                        break;
//                    }
//                    default:
//                    {
//                        break;
//                    }
//                }
//            }
        }
    }

    public void setTarget(Entity entity, int index)
    {
        double new_distance_to_target = this.skinningentities.getDistanceSq(entity);

        if (this.distance_to_target_array[index] > new_distance_to_target)
        {
            this.target_entity_array[index] = entity;
            this.distance_to_target_array[index] = new_distance_to_target;
        }
    }

    public boolean isTarget(Entity entity)
    {
        for (Class clasz : EntitiesRegistryHelper.ENTITIES_CLASS_LIST)
        {
            if (entity.getClass().equals(clasz))
            {
                return false;
            }
        }

        Object[] key_array = new HashSet<>(((IMixinEntityRegistry) EntityRegistry.instance()).entityClassEntries().keySet()).toArray();
        for (int id : this.troublemaker_arraylist)
        {
            if (entity.getClass().equals(key_array[id]))
            {
                return false;
            }
        }

        return !(entity instanceof EntityPlayer);
    }
}
