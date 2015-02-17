package common;

public class Bag implements Comparable {
	public String name;
	public int weightCapacity;
	public int lowerFit = 0; // -1 == uninitialized
	public int upperFit = Integer.MAX_VALUE;

	/**
	 * Create a bag with only knowing the name and weight capacity
	 * 
	 * @param name
	 * @param weightCapacity
	 */
	public Bag(String name, int weightCapacity) {
		this.name = name;
		this.weightCapacity = weightCapacity;
	}

	/**
	 * Create a Bag with all required parameters
	 * 
	 * @param name
	 * @param weightCapacity
	 * @param lowerFit
	 * @param upperFit
	 */
	public Bag(String name, int weightCapacity, int lowerFit, int upperFit) {
		this.name = name;
		this.weightCapacity = weightCapacity;
		this.lowerFit = lowerFit;
		this.upperFit = upperFit;
	}

	/**
	 * @param lowerFit
	 *            the lowerFit to set
	 */
	public Bag setLowerFit(int lowerFit) {
		this.lowerFit = lowerFit;
		return this;
	}

	/**
	 * @param upperFit
	 *            the upperFit to set
	 */
	public Bag setUpperFit(int upperFit) {
		this.upperFit = upperFit;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		Bag other = (Bag) obj;
		// Only performing basic comparison
		return this.name.equals(other.name)
				&& weightCapacity == other.weightCapacity;
	}

	@Override
	public String toString() {
		return "(" + name + " " + weightCapacity + ")";
	}

	@Override
	public int compareTo(Object arg0) {
		return name.compareTo(((Bag) arg0).name);
	}

}
