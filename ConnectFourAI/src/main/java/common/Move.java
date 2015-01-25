package common;

public enum Move {
	DROP, POP;

	public static String toString(Move m) {
		switch (m) {
		default:
		case DROP:
			return "1";
		case POP:
			return "0";
		}
	}

}
