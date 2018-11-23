import java.util.Iterator;
import java.util.NoSuchElementException;

class VariableIterator implements Iterator<String> {
	private Variable variable;
	private int currentValue;

	public VariableIterator(Variable variable) {
		this.variable = variable;
		currentValue = -1;
	}

	public String current() {
		return (currentValue == -1) ? null : variable.domain.get(currentValue);
	}
	public boolean hasNext() {
		return (currentValue+1) < variable.domain.size();
	}
	public String next() {
		if (!hasNext())
			throw new NoSuchElementException();
		currentValue++;
		return variable.domain.get(currentValue);
	}
	public void remove() {
		throw new UnsupportedOperationException();
	}
	public void reset() {
		currentValue = -1;
	}
}
