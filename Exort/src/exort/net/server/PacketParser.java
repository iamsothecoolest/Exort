package exort.net.server;

import exort.entity.creature.*;
import exort.gfx.*;
import exort.level.*;
import exort.net.packets.*;
import exort.net.packets.Packet.*;

public class PacketParser {
	private GUI gui;

	private Server server;
	private Level level;

	public PacketParser(GUI gui, Server server, Level level) {
		this.gui = gui;
		this.server = server;
		this.level = level;
	}

	public void parsePacket(byte[] data, String address, int port) {
		String message = new String(data).trim();
		PacketType type = Packet.lookupPacket(message.substring(0, 2));
		switch (type) {
			case INVALID:
				break;
			case LOGIN:
				Packet00Login loginPacket = new Packet00Login(data);
				if (loginPacket.getUsername().length() > Server.USERNAME_MAX_LENGTH) {
					loginPacket.setUsername(loginPacket.getUsername().substring(0, Server.USERNAME_MAX_LENGTH));
				}
				this.gui.addMessage(loginPacket.getUsername() + " has joined the game.");
				Player player = new Player(null, this.level, null, loginPacket.getUsername(), address, port);
				this.server.addConnection(player, loginPacket);
				break;
			case DISCONNECT:
				Packet01Disconnect disconnectPacket = new Packet01Disconnect(data);
				if (this.server.getPlayer(disconnectPacket.getUsername()) != null) {
					this.gui.addMessage(disconnectPacket.getUsername() + " has left the game.");
					this.server.removeConnection(disconnectPacket);
				}
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
