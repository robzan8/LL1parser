public class Solver {
	protected Problem problem;
	protected VariableIterator[] varIterators;
	private HashSet<Pair> forbiddenPairs;

	public Solver() {}

	public Solution findSolution(Problem problem) {
		this.problem = problem;
		preprocessProblem();
		return findSolutionRec(0) ? createSolutionFromVars() : null;
	}

	private boolean findSolutionRec(int firstUnassignedVar) {
		if (firstUnassignedVar >= varIterators.length)
			return true;
		VariableIterator varIterator = varIterators[firstUnassignedVar];
		while (varIterator.hasNext()) {
			String value = varIterator.next();
			if (checkCompatibility(firstUnassignedVar, value) && // short circuit!
			findSolutionRec(firstUnassignedVar+1))
				return true;
		}
		varIterator.reset();
		return false;
	}
	
	protected Solution createSolutionFromVars() {
		HashMap<String, String> assignment = new HashMap<String, String>(varIterators.length);
		ArrayList<Constraint> explanation = new ArrayList<Constraint>();
		for (int i = 0; i < varIterators.length; i++) {
			String value = varIterators[i].current();
			assignment.put(problem.variables.get(i).name, value);
			for (EqConstraint eqConstraint: problem.eqConstraints) {
				if (eqConstraint.left.equals(value))
					explanation.add(eqConstraint);
			}
			for (DiseqConstraint diseqConstraint: problem.diseqConstraints) {
				if (diseqConstraint.left.equals(value))
					explanation.add(diseqConstraint);
			}
		}
		return new Solution(assignment, explanation);
	}

	protected void preprocessProblem() {
		varIterators = new VariableIterator[problem.variables.size()];
		for (int i = 0; i < varIterators.length; i++)
			varIterators[i] = problem.variables.get(i).iterator();

		forbiddenPairs = new HashSet<Pair>(problem.eqConstraints.size()+problem.diseqConstraints.size());
		for (Constraint eqConstraint: problem.eqConstraints) {
			Variable var = problem.findVariableFromValue(eqConstraint.right);
			for (String value: var.domain) {
				if (!value.equals(eqConstraint.right))
					forbiddenPairs.add(new Pair(eqConstraint.left, value));
			}
		}
		for (Constraint diseqConstraint: problem.diseqConstraints) {
			forbiddenPairs.add(new Pair(diseqConstraint.left, diseqConstraint.right));
		}
	}

	protected boolean checkCompatibility(int lastAssignedVar, String lastValue) {
		for (int i = 0; i < lastAssignedVar; i++) {
			if (forbiddenPairs.contains(new Pair(varIterators[i].current(), lastValue)))
				return false;
		}
		return true;
	}
}
