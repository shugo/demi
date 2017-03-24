package JP.ac.osaka_u.shugo.demi;

public class NameError extends Error {
    public NameError() {
	super();
    }

    public NameError(String message) {
	super(message);
    }

    public NameError(Symbol symbol) {
	super(symbol.getName());
    }
}
