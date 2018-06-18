public class DiseqConstraint extends Constraint {
	public DiseqConstraint() {
		super();
	}
	public DiseqConstraint(String left, String right) {
		super(left, right);
	}

	@Override public String toString() {
		return "!("+left+", "+right+")";
	}
}
