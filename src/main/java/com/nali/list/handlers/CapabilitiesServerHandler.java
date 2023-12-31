package com.nali.list.handlers;

import com.nali.ilol.networks.NetworksRegistry;
import com.nali.list.capabilitiesserializations.IlolSakuraSerializations;
import com.nali.list.messages.CapabilitiesClientMessage;
import com.nali.list.messages.CapabilitiesServerMessage;
import com.nali.system.bytes.BytesWriter;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CapabilitiesServerHandler implements IMessageHandler<CapabilitiesServerMessage, IMessage>
{
    @Override
    public IMessage onMessage(CapabilitiesServerMessage capabilityservermessage, MessageContext messagecontext)
    {
        byte[] byte_array = new byte[8];
        System.arraycopy(capabilityservermessage.data, 0, byte_array, 0, 4);
        BytesWriter.set(byte_array, messagecontext.getServerHandler().player.getCapability(IlolSakuraSerializations.ILOLSAKURATYPES_CAPABILITY, null).get(), 4);
        NetworksRegistry.I.sendTo(new CapabilitiesClientMessage(byte_array), messagecontext.getServerHandler().player);
        return null;
    }
}
