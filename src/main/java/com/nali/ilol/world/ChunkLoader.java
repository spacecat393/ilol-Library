package com.nali.ilol.world;

import com.nali.ilol.ILOL;
import com.nali.ilol.entities.skinning.SkinningEntities;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;

import static com.nali.ilol.world.ChunkCallBack.CHUNK_MAP;

public class ChunkLoader
{
    public static void updateChunk(SkinningEntities skinningentities)
    {
        ChunkPos chunkpos = new ChunkPos(skinningentities.getPosition());
        World world = skinningentities.getEntityWorld();

        if (CHUNK_MAP.containsKey(skinningentities))
        {
            ChunkData chunkdata = CHUNK_MAP.get(skinningentities);

            if (!world.equals(chunkdata.world) || !chunkpos.equals(chunkdata.chunkpos))
            {
//                ForgeChunkManager.unforceChunk(chunkdata.ticket, chunkdata.chunkpos);
                ForgeChunkManager.releaseTicket(chunkdata.ticket);
                chunkdata.world = world;
                chunkdata.chunkpos = chunkpos;
                chunkdata.ticket = ForgeChunkManager.requestTicket(ILOL.I, chunkdata.world, ForgeChunkManager.Type.ENTITY);
                chunkdata.ticket.bindEntity(skinningentities);
                ForgeChunkManager.forceChunk(chunkdata.ticket, chunkdata.chunkpos);
            }
        }
        else
        {
            ChunkData chunkdata = new ChunkData();
            chunkdata.world = world;
            chunkdata.chunkpos = chunkpos;
            chunkdata.ticket = ForgeChunkManager.requestTicket(ILOL.I, chunkdata.world, ForgeChunkManager.Type.ENTITY);
            chunkdata.ticket.bindEntity(skinningentities);
            ForgeChunkManager.forceChunk(chunkdata.ticket, chunkdata.chunkpos);
        }
    }

    public static void removeChunk(SkinningEntities skinningentities)
    {
        if (CHUNK_MAP.containsKey(skinningentities))
        {
            ChunkData chunkdata = CHUNK_MAP.get(skinningentities);
//            ForgeChunkManager.unforceChunk(chunkdata.ticket, chunkdata.chunkpos);
            ForgeChunkManager.releaseTicket(chunkdata.ticket);
        }
    }
}
