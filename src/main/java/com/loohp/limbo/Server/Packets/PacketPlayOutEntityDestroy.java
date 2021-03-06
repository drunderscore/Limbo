package com.loohp.limbo.Server.Packets;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.loohp.limbo.Utils.DataTypeIO;

public class PacketPlayOutEntityDestroy extends PacketOut {
	
	private int[] entityIds;
	
	public PacketPlayOutEntityDestroy(int... entityIds) {
		this.entityIds = entityIds;
	}

	public int[] getEntityIds() {
		return entityIds;
	}
	
	@Override
	public byte[] serializePacket() throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		
		DataOutputStream output = new DataOutputStream(buffer);
		output.writeByte(Packet.getPlayOut().get(getClass()));
		DataTypeIO.writeVarInt(output, entityIds.length);
		for (int id : entityIds) {
			DataTypeIO.writeVarInt(output, id);
		}
		
		return buffer.toByteArray();
	}

}
