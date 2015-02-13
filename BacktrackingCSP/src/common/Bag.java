package common;

public class Bag {
	private String name;
	private int weightCapacity;
	private int lowerFit;
	private int upperFit;

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
	public void setLowerFit(int lowerFit) {
		this.lowerFit = lowerFit;
	}

	/**
	 * @param upperFit
	 *            the upperFit to set
	 */
	public void setUpperFit(int upperFit) {
		this.upperFit = upperFit;
	}

}
