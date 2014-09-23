package com.doobs.exort.util.sat;

public class Projection {
	double min, max;
	
	public Projection(double min, double max) {
		this.min = min;
		this.max = max;
	}
	
	public Projection() {
		this(0, 0);
	}
	
	public boolean overlaps(Projection p) {
		return p.getMin() > max || p.getMax() < min;
	}

	// Getters and setters
	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}
}
