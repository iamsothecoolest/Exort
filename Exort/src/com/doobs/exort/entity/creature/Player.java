package com.doobs.exort.entity.creature;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.vector.Vector3f;

import res.models.*;

import com.doobs.exort.entity.*;
import com.doobs.exort.gfx.*;
import com.doobs.exort.level.*;

public class Player extends MovingEntity {
	public static double[] spawn = new double[] { 0, 0, 0 };

	private float targetX, targetZ;

	private static float moveSpeed = 1f / 50f;

	public Player(double x, double y, double z, Level level) {
		super(x, y, z, level);
	}

	public Player() {
		super();
		targetX = 0;
		targetZ = 0;
		xa = 0;
		za = 0;
	}

	public void tick(int delta) {
		// Player movement
		boolean xDone = false, zDone = false;
		if (xa > 0 && this.x + xa * delta > targetX) {
			xa = targetX - this.x;
			xDone = true;
		} else if (xa < 0 && this.x + xa * delta < targetX) {
			xa = targetX - this.x;
			xDone = true;
		}

		if (za > 0 && this.z + za * delta > targetZ) {
			za = targetZ - this.z;
			zDone = true;
		} else if (za < 0 && this.z + za * delta < targetZ) {
			za = targetZ - this.z;
			zDone = true;
		}

		if (xDone) {
			this.x = targetX;
			xa = 0;
		}

		if (zDone) {
			this.z = targetZ;
			za = 0;
		}

		this.x += xa * delta;
		this.z += za * delta;

		// Ability handling

		// Update lighting
		Lighting.moveLight(new Vector3f((float) this.x, 4f, (float) this.z), false);
	}

	public void render() {
		// Draw model command
		glTranslated(x, y, z);
		glColor3f(1.0f, 0.0f, 0.0f);
		glCallList(Models.stillModels.get("player").getHandle());

		// Draw move command
		glTranslated(targetX - x, 0, targetZ - z);
		glColor3f(0.0f, 1.0f, 0.0f);
		glCallList(Models.stillModels.get("move").getHandle());

		// Reset
		glTranslated(-targetX, -y, -targetZ);
		glColor3f(1.0f, 1.0f, 1.0f);
	}

	public void move(Vector3f position) {
		if (position.getX() != this.x || position.getZ() != this.z) {
			targetX = position.getX();
			targetZ = position.getZ();
			calculateSpeeds();
		}
	}

	public void calculateSpeeds() {
		Vector3f target = new Vector3f((float) (targetX - this.x), 0f, (float) (targetZ - this.z));
		if (target.getX() != 0 || target.getZ() != 0) {
			target.normalise();
			xa = target.getX() * moveSpeed;
			za = target.getZ() * moveSpeed;
		}
	}

	// Getters and setters
	public Vector3f getPosition() {
		return new Vector3f((float) this.x, (float) this.y, (float) this.z);
	}

	public void setTargetX(float x) {
		this.targetX = x;
		calculateSpeeds();
	}

	public void setTargetZ(float z) {
		this.targetZ = z;
		calculateSpeeds();
	}

	public void setTargetPosition(float x, float z) {
		this.targetX = x;
		this.targetZ = z;
		calculateSpeeds();
	}
}
