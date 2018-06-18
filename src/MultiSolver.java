public class MultiSolver extends Solver {
	private int firstUnassignedVar;

	public MultiSolver() {
		super();
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
		preprocessProblem();
		firstUnassignedVar = 0;
	}

	public Solution nextSolution() {
		vars_loop:
		while (firstUnassignedVar >= 0 && firstUnassignedVar < varIterators.length) {
			VariableIterator varIterator = varIterators[firstUnassignedVar];
			while (varIterator.hasNext()) {
				String value = varIterator.next();
				if (checkCompatibility(firstUnassignedVar, value)) {
					firstUnassignedVar++;
					continue vars_loop;
				}
			}
			varIterator.reset();
			firstUnassignedVar--;
		}
		if (firstUnassignedVar < 0)
			return null;
		firstUnassignedVar--;
		return createSolutionFromVars();
	}
	
	@Override public Solution findSolution(Problem problem) {
		setProblem(problem);
		return nextSolution();
	}
}
