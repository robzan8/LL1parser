public class Parser {
	private Scanner scanner;
	private Scanner.TokenType currentToken;

	public Parser() {}

	private void nextToken() throws IOException {
		currentToken = scanner.nextToken();
	}
	private void expect(Scanner.TokenType token) throws SyntaxException, IOException {
		if (currentToken != token)
			throw new SyntaxException("expected token: "+token+", found: "+currentToken);
		nextToken();
	}

	// PROBLEM -> VARIABLES { CONSTRAINT_LIST } !{ CONSTRAINT_LIST }
	public Problem parseProblem(Reader reader) throws SyntaxException, IOException, InstantiationException, IllegalAccessException {
		scanner = new Scanner(reader);
		nextToken();
		ArrayList<Variable> vars = parseVariables();
		expect(Scanner.TokenType.L_BRACE);
		ArrayList<EqConstraint> eqConstraints = parseConstraints(EqConstraint.class);
		expect(Scanner.TokenType.R_BRACE);
		expect(Scanner.TokenType.NOT);
		expect(Scanner.TokenType.L_BRACE);
		ArrayList<DiseqConstraint> diseqConstraints = parseConstraints(DiseqConstraint.class);
		expect(Scanner.TokenType.R_BRACE);
		expect(Scanner.TokenType.EOF);
		return new Problem(vars, eqConstraints, diseqConstraints);
	}

	// VARIABLES -> VARIABLE [VARIABLES]
	private ArrayList<Variable> parseVariables() throws SyntaxException, IOException {
		ArrayList<Variable> vars = new ArrayList<Variable>();
		do {
			vars.add(parseVariable());
		} while (currentToken == Scanner.TokenType.IDE);
		return vars;
	}

	// VARIABLE -> ide = { VALUES }
	private Variable parseVariable() throws SyntaxException, IOException {
		String name = scanner.getString();
		expect(Scanner.TokenType.IDE);
		expect(Scanner.TokenType.EQUAL);
		expect(Scanner.TokenType.L_BRACE);
		ArrayList<String> domain = parseValues();
		expect(Scanner.TokenType.R_BRACE);
		return new Variable(name, domain);
	}

	// VALUES -> ide [, VALUES]
	private ArrayList<String> parseValues() throws SyntaxException, IOException {
		ArrayList<String> values = new ArrayList<String>();
		values.add(scanner.getString());
		expect(Scanner.TokenType.IDE);
		while (currentToken == Scanner.TokenType.COMMA) {
			nextToken();
			values.add(scanner.getString());
			expect(Scanner.TokenType.IDE);
		}
		return values;
	}

	// CONSTRAINT_LIST -> [CONSTRAINTS]
	// CONSTRAINTS -> CONSTRAINT [, CONSTRAINTS]
	private <C extends Constraint> ArrayList<C> parseConstraints(Class<C> constraintType) throws SyntaxException, IOException, InstantiationException, IllegalAccessException {
		ArrayList<C> constraints = new ArrayList<C>();
		if (currentToken == Scanner.TokenType.L_PAREN) {
			constraints.add(parseConstraint(constraintType));
			while (currentToken == Scanner.TokenType.COMMA) {
				nextToken();
				constraints.add(parseConstraint(constraintType));
			}
		}
		return constraints;
	}

	// CONSTRAINT -> (ide, ide)
	private <C extends Constraint> C parseConstraint(Class<C> constraintType) throws SyntaxException, IOException, InstantiationException, IllegalAccessException {
		expect(Scanner.TokenType.L_PAREN);
		String left = scanner.getString();
		expect(Scanner.TokenType.IDE);
		expect(Scanner.TokenType.COMMA);
		String right = scanner.getString();
		expect(Scanner.TokenType.IDE);
		expect(Scanner.TokenType.R_PAREN);
		C constraint = constraintType.newInstance();
		constraint.left = left;
		constraint.right = right;
		return constraint;
	}
}
