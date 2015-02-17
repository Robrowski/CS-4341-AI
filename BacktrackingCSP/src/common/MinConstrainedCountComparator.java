package common;

import java.util.Comparator;

public class MinConstrainedCountComparator implements
		Comparator<ConstrainedBagCount> {

	public MinConstrainedCountComparator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(ConstrainedBagCount o1, ConstrainedBagCount o2) {
		if (o1.count < o2.count) {
			return -1;
		}
		if (o1.count > o2.count) {
			return 1;
		}
		return 0;
	}

}
