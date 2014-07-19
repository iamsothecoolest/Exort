package com.doobs.exort.level;

import static org.lwjgl.opengl.GL11.*;

import java.util.*;

import res.models.*;

import com.doobs.exort.entity.*;
import com.doobs.exort.entity.creature.*;

public class Level {
	private NetPlayer player;

	private int width, height;
	private byte[] tiles;
	private List<Entity> entities = new ArrayList<Entity>();

	public Level(NetPlayer player) {
		this.player = player;
		
		width = 16;
		height = 14;
		tiles = new byte[width * height];
		for (int i = 0; i < tiles.length; i++) {
			int random = (int) (Math.random() * 12);
			if (random <= 8)
				tiles[i] = 0;
			else if (random == 9)
				tiles[i] = 1;
			else if (random > 9)
				tiles[i] = 2;
		}
	}

	public void tick(int delta) {
		for (Entity entity : entities) {
			if (entity instanceof NetPlayer)
				((NetPlayer) entity).tick(delta);
			else if(entity instanceof Player) // application's player
				((Player) entity).tick(delta);
		}
	}

	public void render() {
		glColor4f(1f, 1f, 1f, 1f);
		Models.stillModels.get("arena").draw();
		
		for(Entity entity : entities) {
			if(entity instanceof Player)
				((Player) entity).render();
		}
	}

	// Getters and setters
	public synchronized void movePlayer(String username, int x, int z, int time) {
		int index = getPlayerIndex(username);
		NetPlayer player = (NetPlayer) entities.get(index);
		player.setX(x);
		player.setZ(z);
	}

	public void addEntity(Entity entity) {
		entities.add(entity);
	}

	public void removePlayer(String name) {
		int index = 0;
		for (Entity entity : entities) {
			if (entity instanceof NetPlayer && ((NetPlayer) entity).getUsername().equals(name)) {
				break;
			}
			index++;
		}
		entities.remove(index);
	}

	private int getPlayerIndex(String name) {
		int index = 0;
		for (Entity entity : entities) {
			if (entity instanceof NetPlayer && ((NetPlayer) entity).getUsername().equals(name)) {
				break;
			}
			index++;
		}
		return index;
	}

	// Getters and Setters
	public Player getPlayer() {
		return player;
	}
}
