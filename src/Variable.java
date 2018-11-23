import java.util.ArrayList;

public class Variable implements Iterable<String> {
	public String name;
	public ArrayList<String> domain;

	public Variable(String name, ArrayList<String> domain) {
		this.name = name;
		this.domain = domain;
	}

	public VariableIterator iterator() {
		return new VariableIterator(this);
	}
}
