package com.doobs.exort;

import org.lwjgl.input.*;

import com.doobs.exort.gfx.*;
import com.doobs.exort.state.*;
import com.doobs.exort.util.*;
import com.doobs.exort.util.gl.Cursor;
import com.doobs.exort.util.loaders.*;
import com.doobs.modern.*;
import com.doobs.modern.util.*;

public class Main implements GameLoop {
	public static final String TITLE = "Exort Test";

	private static GraphicsContext context;

	public static InputHandler input;

	private GameState state;

	public Main() {
		context = new GraphicsContext(this);
		Shaders.init();
		Lighting.init();
		Cursor.init();
		Textures.init();
		Fonts.init();
		Models.init();

		input = new InputHandler();

		state = new MainMenuState(this);

		context.run();
	}

	@Override
	public void tick(int delta) {
		if(GLTools.wasResized())
			resize();
		
		input.tick();

		if (input.isKeyPressed(Keyboard.KEY_F11))
			GLTools.toggleFullscreen();

		state.tick(delta);
	}

	@Override
	public void render() {
		state.render();
	}
	
	private void resize() {
		
		
		if(state instanceof DuelState) {
			((DuelState) state).getGUI().recalculatePositions();
		}
	}

	public void changeState(GameState state) {
		this.state = state;
	}

	public void exit() {
		context.requestExit();
	}

	public static void main(String[] args) {
		new Main();
	}

	// Getters and setters
	public static int getWidth() {
		return context.getWidth();
	}

	public static int getHeight() {
		return context.getHeight();
	}

	public GameState getCurrentState() {
		return state;
	}
}
