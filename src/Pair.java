class Pair {
	private String a, b;

	public Pair(String a, String b) {
		if (a.compareTo(b) <= 0) {
			this.a = a;
			this.b = b;
		}
		else {
			this.a = b;
			this.b = a;
		}
	}

	@Override public boolean equals(Object o) {
		Pair p = (Pair) o;
		return p != null && a.equals(p.a) && b.equals(p.b);
	}
	
	@Override public int hashCode() {
		return (a+","+b).hashCode();
	}
}
