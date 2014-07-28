package com.doobs.exort.net.packets;

import com.doobs.exort.net.client.*;
import com.doobs.exort.net.server.*;

public class Packet03Chat extends Packet {

	private String username;
	private String message;

	public Packet03Chat(byte[] data) {
		super(03);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.message = dataArray[1];
	}

	public Packet03Chat(String username, String message) {
		super(03);
		this.username = username;
		this.message = message;
	}

	public void sendData(Client client) {
		super.sendData(client, getData());
	}

	public void sendData(Server server) {
		super.sendData(server, getData());
	}

	@Override
	public byte[] getData() {
		return ("03" + this.username + "," + this.message).getBytes();
	}

	// Getters and setters
	public String getUsername() {
		return username;
	}

	public String getMessage() {
		return message;
	}
}
