package exort.net.server;

import exort.net.*;
import exort.net.packets.*;
import exort.net.packets.Packet.PacketType;

public class PacketParser {
	private Server server;

	public PacketParser(Server server) {
		this.server = server;
	}

	public void parsePacket(byte[] data, String address, int port) {
		String message = new String(data).trim();
		PacketType type = Packet.lookupPacket(message.substring(0, 2));
		switch (type) {
			case INVALID:
				break;
			case LOGIN:
				Packet00Login loginPacket = new Packet00Login(data);
				if (loginPacket.getUsername().length() > NetVariables.MAX_USERNAME_LENGTH) {
					// Send it right back without an assigned ID to show the Player that
					// they fucked up.
					loginPacket.sendData(this.server);
				} else {
					this.server.addPlayer(loginPacket.getUsername(), address, port);
				}
				break;
			case DISCONNECT:
				this.server.removePlayer(new Packet01Disconnect(data));
				break;
			case MOVE:
				this.server.handleMove(new Packet02Move(data));
				break;
			case CHAT:
				this.server.sendDataToAllClients(data);
				break;
			case SONIC_WAVE:
				this.server.sendDataToAllClients(data);
				break;
			case ROCK_WALL:
				this.server.sendDataToAllClients(data);
				break;
			default:
				break;
		}
	}
}
