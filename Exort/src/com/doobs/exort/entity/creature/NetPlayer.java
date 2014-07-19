package com.doobs.exort.entity.creature;

import com.doobs.exort.level.*;
import com.doobs.exort.net.client.*;

public class NetPlayer extends Player {
	// Other players are defined entirely by Netplayer whereas as the application's player
	// is defined by the Client + Player combination
	
	private Client client;
	private String username;
	private String address;
	private int port;

	public NetPlayer(Client client, String username, String address, int port, Level level) {
		super(Player.spawn[0], Player.spawn[1], Player.spawn[2], level);
		this.client = client;
		this.username = username;
		this.address = address;
		this.port = port;
	}
	
	@Override
	public void tick(int delta) {
		if(client != null)
			super.tick(delta);
	}
	
	// Getters and setters
	public Client getClient() {
		return client;
	}

	public String getUsername() {
		return username;
	}

	public String getAddress() {
		return address;
	}
	
	public int getPort() {
		return port;
	}

	public void setAddress(String username) {
		this.username = username;
	}
}
