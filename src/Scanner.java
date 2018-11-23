import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;

class Scanner {
	public enum TokenType { EQUAL, COMMA, NOT, L_PAREN, R_PAREN, L_BRACE, R_BRACE, IDE, EOF, INVALID_CHAR };

	private StreamTokenizer input;

	public Scanner(Reader reader) {
		input = new StreamTokenizer(reader);
	}

	public TokenType nextToken() throws IOException {
		switch (input.nextToken()) {
		case '=': return TokenType.EQUAL;
		case ',': return TokenType.COMMA;
		case '!': return TokenType.NOT;
		case '(': return TokenType.L_PAREN;
		case ')': return TokenType.R_PAREN;
		case '{': return TokenType.L_BRACE;
		case '}': return TokenType.R_BRACE;
		case StreamTokenizer.TT_WORD: return TokenType.IDE;
		case StreamTokenizer.TT_EOF: return TokenType.EOF;
		default: return TokenType.INVALID_CHAR;
		}
	}
	
	public String getString() {
		return input.sval;
	}
}
