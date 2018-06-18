public class Problem {
	public ArrayList<Variable> variables;
	public ArrayList<EqConstraint> eqConstraints;
	public ArrayList<DiseqConstraint> diseqConstraints;

	public Problem(ArrayList<Variable> variables, ArrayList<EqConstraint> eqConstraints, ArrayList<DiseqConstraint> diseqConstraints) {
		// eventuale analisi statica qui
		this.variables = variables;
		this.eqConstraints = eqConstraints;
		this.diseqConstraints = diseqConstraints;
	}

	public Variable findVariableFromValue(String value) {
		for (Variable var: variables) {
			if (var.domain.contains(value))
				return var;
			}
		return null;
	}
}
