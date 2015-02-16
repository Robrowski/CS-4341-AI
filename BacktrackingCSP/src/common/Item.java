package common;

public class Item {
	public String name;
	public int weight;

	/**
	 * Create an Item
	 * 
	 * @param name
	 * @param weight
	 */
	public Item(String name, int weight) {
		this.name = name;
		this.weight = weight;
	}

	@Override
	public boolean equals(Object obj) {
		Item other = (Item) obj;
		return this.name.equals(other.name) && weight == other.weight;
	}

	@Override
	public String toString() {
		return name + " " + weight;
	}
}
