package com.nali.ilol.entities.skinning.ai;

import com.nali.ilol.entities.skinning.SkinningEntities;

import java.util.function.Supplier;

public class SkinningEntitiesLiveFrame extends SkinningEntitiesAI
{
    public int integer_index;
    public int[][] int_2d_array; // start end
    public Supplier<Boolean>[] condition_boolean_supplier_array;

    public SkinningEntitiesLiveFrame(SkinningEntities skinningentities, int integer_index, int[][] int_2d_array)
    {
        super(skinningentities);
        this.integer_index = integer_index;
        this.int_2d_array = int_2d_array;
    }

    @Override
    public void onUpdate()
    {
        for (int i = 0; i < this.condition_boolean_supplier_array.length; ++i)
        {
            if (this.condition_boolean_supplier_array[i].get())
            {
                if (this.skinningentities.server_frame_int_array[this.integer_index] < this.int_2d_array[i][1])
                {
                    this.skinningentities.getDataManager().set(this.skinningentities.getIntegerDataParameterArray()[this.skinningentities.bothdata.MaxPart() + this.integer_index], this.skinningentities.server_frame_int_array[this.integer_index] + 1);
                }

                return;
            }
        }
    }

    public boolean setFLoop(int id0)
    {
        if (this.skinningentities.server_frame_int_array[this.integer_index] == this.int_2d_array[id0][1] - 1)
        {
            this.skinningentities.server_frame_int_array[this.integer_index] = this.int_2d_array[id0][1];
        }
        else if (this.skinningentities.server_frame_int_array[this.integer_index] < this.int_2d_array[id0][0] || this.skinningentities.server_frame_int_array[this.integer_index] > this.int_2d_array[id0][1])
        {
            this.skinningentities.server_frame_int_array[this.integer_index] = this.int_2d_array[id0][0];
        }

        return true;
    }

    public boolean setFLoop(int id0, boolean result)
    {
        if (result)
        {
            return setFLoop(id0);
        }

        return result;
    }

    public boolean setFLoopFree(int id0, int byte_id, boolean result)
    {
        if (result)
        {
            if (this.skinningentities.server_frame_int_array[this.integer_index] == this.int_2d_array[id0][1] - 1)
            {
                this.skinningentities.server_frame_int_array[this.integer_index] = this.int_2d_array[id0][1];
            }
            else if (this.skinningentities.server_frame_int_array[this.integer_index] < this.int_2d_array[id0][0] || this.skinningentities.server_frame_int_array[this.integer_index] > this.int_2d_array[id0][1])
            {
                this.skinningentities.server_frame_int_array[this.integer_index] = this.int_2d_array[id0][0];
            }

            if (this.skinningentities.server_frame_int_array[this.integer_index] == this.int_2d_array[id0][1])
            {
                this.skinningentities.getDataManager().set(this.skinningentities.getByteDataParameterArray()[byte_id], (byte)0);
            }
        }

        return result;
    }

    public boolean setFLoopOffSet(int id0, int id1)
    {
        boolean result = this.skinningentities.server_frame_int_array[this.integer_index] > this.int_2d_array[id0][0] && this.skinningentities.server_frame_int_array[this.integer_index] < this.int_2d_array[id1][1];

        if (result)
        {
            if (this.skinningentities.server_frame_int_array[this.integer_index] > this.int_2d_array[id0][1] - 1 && this.skinningentities.server_frame_int_array[this.integer_index] < this.int_2d_array[id1][0])
            {
                this.skinningentities.server_frame_int_array[this.integer_index] = this.int_2d_array[id1][0];
            }
        }

        return result;
    }

    public boolean setTLoop(int id0, boolean result)
    {
        if (result)
        {
            if (this.skinningentities.server_frame_int_array[this.integer_index] < this.int_2d_array[id0][0] || this.skinningentities.server_frame_int_array[this.integer_index] > this.int_2d_array[id0][1] - 1)
            {
                this.skinningentities.server_frame_int_array[this.integer_index] = this.int_2d_array[id0][0];
            }
        }

        return result;
    }
}