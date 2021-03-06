package com.loohp.limbo.Server.Packets;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.loohp.limbo.Utils.DataTypeIO;

public class PacketPlayOutPositionAndLook extends PacketOut {
	
	public enum PlayerTeleportFlags {
		X((byte) 0x01),
		Y((byte) 0x02),
		Z((byte) 0x04),
		Y_ROT((byte) 0x08),
		X_ROT((byte) 0x10);
		
		byte bit;
		
		PlayerTeleportFlags(byte bit) {
			this.bit = bit;
		}
		
		public byte getBit() {
			return bit;
		}
	}
	
	private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private Set<PlayerTeleportFlags> flags;
    private int teleportId;
	
	public PacketPlayOutPositionAndLook(double x, double y, double z, float yaw, float pitch, int teleportId, PlayerTeleportFlags... flags) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.teleportId = teleportId;
		this.flags = Arrays.asList(flags).stream().collect(Collectors.toSet());
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public Set<PlayerTeleportFlags> getFlags() {
		return flags;
	}

	public int getTeleportId() {
		return teleportId;
	}
	
	@Override
	public byte[] serializePacket() throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		
		DataOutputStream output = new DataOutputStream(buffer);
		output.writeByte(Packet.getPlayOut().get(getClass()));
		output.writeDouble(x);
		output.writeDouble(y);
		output.writeDouble(z);
		output.writeFloat(yaw);
		output.writeFloat(pitch);
		
		byte flag = 0;
		for (PlayerTeleportFlags each : flags) {
			flag = (byte) (flag | each.getBit());
		}
		
		output.writeByte(flag);
		DataTypeIO.writeVarInt(output, teleportId);
		
		return buffer.toByteArray();
	}

}
