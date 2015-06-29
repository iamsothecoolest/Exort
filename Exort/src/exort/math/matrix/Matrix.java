package exort.math.matrix;

/**
 *
 * @author Doobs
 *
 *         Represents an square (nxn) matrix with doubles as entries.
 */
public class Matrix {
	private double[] data;
	private int dimension;

	/**
	 * Creates an "n" by "n" identity Matrix.
	 */
	public Matrix(int n) {
		this.data = new double[n * n];
		this.dimension = n;
		this.loadIdentity();
	}

	/**
	 * Pre: "data" represents a square Matrix.
	 *
	 * Creates a Matrix with "data" as its entries.
	 */
	public Matrix(double[] data) {
		this.data = data;
		this.dimension = (int) Math.sqrt(data.length);
	}

	/**
	 * Replaces this Matrix with the identity Matrix.
	 */
	public void loadIdentity() {
		for (int i = 0; i < this.dimension; i++) {
			for (int j = 0; j < this.dimension; j++) {
				if (i == j) {
					this.data[(i * this.dimension) + j] = 1;
				} else {
					this.data[(i * this.dimension) + j] = 0;
				}
			}
		}
	}

	/**
	 * Returns the minor of this Matrix by removing the row "row" and the column "col".
	 */
	public Matrix minor(int row, int col) {
		Matrix minor = new Matrix(this.dimension - 1);
		for (int i = 0; i < this.dimension; i++) { // Row.
			if (i != row) {
				for (int j = 0; j < this.dimension; j++) { // Column.
					if (j != col) {
						int index = j + (i * this.dimension);
						if (i > row) {
							index -= this.dimension;
						}
						if (j > col) {
							index -= 1;
						}
						minor.data[index] = this.data[j + (i * this.dimension)];
					}
				}
			}
		}
		return minor;
	}

	/**
	 * Returns the resulting Matrix from this Matrix multiplied by "other".
	 */
	public Matrix multiply(Matrix other) {
		if (this.dimension != other.dimension) {
			throw new IllegalArgumentException("Both matrices must be of the same dimension.");
		}
		Matrix result = new Matrix(this.dimension);
		for (int row = 0; row < this.dimension; row++) {
			for (int col = 0; col < this.dimension; col++) {
				// Sum all entries from the "row" of this and the "col" of
				// "other".
				double sum = 0.0;
				// "i" represents the column of this and the row of "other".
				for (int i = 0; i < this.dimension; i++) {
					sum += this.data[i + (row * this.dimension)] * other.data[col + (i * this.dimension)];
				}
				result.data[col + (row * this.dimension)] = sum;
			}
		}
		return result;
	}

	/**
	 * Returns a String representation of this Matrix.
	 */
	public String toString() {
		String result = "";
		for (int row = 0; row < this.dimension; row++) {
			result += "[" + this.data[row * this.dimension];
			for (int col = 1; col < this.dimension; col++) {
				result += "\t" + this.data[col + (row * this.dimension)];
			}
			result += "]\n";
		}
		return result;
	}

	public static void main(String[] args) {
		Matrix m = new Matrix(3);
		System.out.println(m.minor(0, 0));
	}
}