class Comparator<T extends Comparable<T>> {
    int compare(T a, T b) {
	return a.compareTo(b);
    }
}
