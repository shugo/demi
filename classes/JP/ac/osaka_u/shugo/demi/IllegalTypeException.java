package JP.ac.osaka_u.shugo.demi;

public class IllegalTypeException extends Exception {
    public IllegalTypeException() {
	super();
    }

    public IllegalTypeException(String message) {
	super(message);
    }

    public IllegalTypeException(Object obj, Class type) {
	this((obj == null ? "null" : obj.getClass().getName()) +
	     " (expected " + type.getName() + ")");
    }

    public IllegalTypeException(Object obj, String type) {
	this((obj == null ? "null" : obj.getClass().getName()) +
	     " (expected " + type + ")");
    }
}
