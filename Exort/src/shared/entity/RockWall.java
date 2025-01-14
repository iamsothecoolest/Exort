package shared.entity;

import org.lwjgl.util.vector.*;

import shared.level.*;
import shared.sat.*;
import client.util.loaders.*;

import com.doobs.modern.util.*;
import com.doobs.modern.util.matrix.*;

/**
 * A wall with a limited life time that can be conjured by Players.
 */
public class RockWall extends Entity {
	public static final int LIFE = 500; // 60;

	private int currentLife;

	/**
	 * Creates a RockWall on "level" at "position" facing "direction".
	 */
	public RockWall(Vector3f position, float direction, Level level) {
		this(position.getX(), position.getZ(), direction, level);
	}

	/**
	 * Creates a RockWall on "level" at ("x", "z") facing "direction".
	 */
	public RockWall(float x, float z, float direction, Level level) {
		super(x, z, level);
		// TODO: Make these values non-hardcoded.
		this.bb = new OBB(x, 2f, z, 6f);
		this.bb.rotate(direction);
		this.currentLife = LIFE;
	}

	/**
	 * Handles the behavior of this RockWall.
	 */
	public void tick(int delta) {
		this.currentLife -= 1;
		if (this.currentLife < 0) {
			this.remove();
		}
	}

	/**
	 * Renders this RockWall.
	 */
	public void render() {
		this.bb.render();

		Shaders.use("lighting");
		Matrices.translate(this.x, 0f, this.z);
		// ModernGL currently uses degree-based rotations.
		Matrices.rotate(Math.toDegrees(this.bb.getAngle()), 0, 1, 0);
		Matrices.sendMVPMatrix(Shaders.current);
		Color.set(Shaders.current, 0f, 1f, 1f, 1f);
		Models.get("rockWall").draw();

		// Reset
		Matrices.rotate(Math.toDegrees(-this.bb.getAngle()), 0, -1, 0);
		Matrices.translate(-this.x, 0f, -this.z);
	}
}