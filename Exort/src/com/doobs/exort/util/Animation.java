package com.doobs.exort.util;

public class Animation {
	private int current, max;

	public Animation(int max) {
		this.current = 0;
		this.max = max;
	}

	public void tickUp(int delta) {
		if (current < max)
			current++;
	}

	public void tickDown(int delta) {
		if ((--current) < 0)
			current = 0;
	}

	public void fill() {
		current = max;
	}

	public void empty() {
		current = 0;
	}

	// Getters and setters
	public int getProgress() {
		return current;
	}

	public float getPercentage() {
		return (float) current / (float) max;
	}

	public float getSmoothedPercentage() {
		return (float) Math.sin(((float) current / (float) max) * Math.PI / 2);
	}

	public boolean isFull() {
		return current >= max;
	}

	public boolean isEmpty() {
		return current <= 0;
	}
}
