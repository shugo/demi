package JP.ac.osaka_u.shugo.demi;

import java.util.Stack;

public class Jump extends Throwable {

    private SimpleNode node;
    private Stack stack;

    public Jump(SimpleNode node, Stack stack, String message) {
	super(message);
	this.node = node;
	this.stack = stack;
    }

    public Jump(SimpleNode node, Stack stack) {
	this(node, stack, null);
    }

    public SimpleNode getNode() {
	return node;
    }

    public Stack getStack() {
	return stack;
    }

    public String getExceptionMessgae() {
	return toString();
    }

    public String getBackTrace() {
	StringBuffer buff = new StringBuffer();
	buff.append(getExceptionMessgae());
	buff.append("\n\tat ");
	if (stack != null && !stack.isEmpty()) {
	    Function func = ((CallInfo) stack.peek()).function;
	    buff.append(func.getName().getName());
	} else {
	    buff.append("?");
	}
	buff.append("(");
	buff.append(node.getFileName());
	buff.append(":");
	buff.append(node.line);
	buff.append(")\n");
	if (stack == null)
	    return new String(buff);
	for (int i = stack.size() - 1; i >= 0; i--) {
	    SimpleNode node = ((CallInfo) stack.elementAt(i)).node;
	    buff.append("\tat ");
	    if (i > 0) {
		Function func = ((CallInfo) stack.elementAt(i - 1)).function;
		buff.append(func.getName().getName());
	    } else {
		buff.append("?");
	    }
	    buff.append("(");
	    buff.append(node.getFileName());
	    buff.append(":");
	    buff.append(node.line);
	    buff.append(")\n");
	}
	return new String(buff);
    }

    public void printBackTrace() {
	System.err.print(getBackTrace());
    }
}
