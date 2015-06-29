package exort.util.compression;

import java.util.*;

/**
 * Contains a string of bits that can be appended to and converted to a byte array.
 */
public class BitString {
	private List<Byte> data;
	// Keeps track of the current index in the last byte.
	private byte index;

	public BitString() {
		this.data = new ArrayList<Byte>();
		this.data.add((byte) 0);
		this.index = 0;
	}

	/**
	 * Adds "bit" to the end of this BitString.
	 */
	public void add(int bit) {
		if (this.index == 8) {
			this.data.add((byte) 0);
			this.index = 0;
		}
		int add = bit << 7 - this.index;
		this.setLast((byte) (this.getLast() + add));
		this.index++;
	}

	/**
	 * Returns the contents of this BitString that have been converted to a byte array.
	 */
	public byte[] toByteArray() {
		byte[] result = new byte[this.data.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = this.data.get(i);
		}
		return result;
	}

	/**
	 * Sets the last element in this BitString's List of Bytes.
	 */
	public void setLast(Byte b) {
		this.data.set(this.data.size() - 1, b);
	}

	/**
	 * Returns the last element in this BitString's List of Bytes.
	 */
	private Byte getLast() {
		return this.data.get(this.data.size() - 1);
	}
}