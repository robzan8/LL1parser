public class EqConstraint extends Constraint {
	public EqConstraint() {
		super();
	}
	public EqConstraint(String left, String right) {
		super(left, right);
	}

	@Override public String toString() {
		return "("+left+", "+right+")";
	}
}
