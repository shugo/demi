/* AbstractFunction.java */

package JP.ac.osaka_u.shugo.demi;

import java.awt.event.*;

public abstract class AbstractFunction
    implements Function, Runnable,
	       ActionListener, AdjustmentListener,
	       ItemListener, TextListener {

    Demi demi;

    public AbstractFunction(Demi demi) {
	this.demi = demi;
    }

    public Object callSafely(Object[] args) {
	try {
	    return call(args);
	} catch (Jump e) {
	    e.printBackTrace();
	    System.exit(1);
	} catch (Throwable e) {
	    System.err.println(e);
	    System.exit(1);
	}
	return null;
    }

    public void run() {
	callSafely(new Object[0]);
    }

    public void actionPerformed(ActionEvent event) {
	callSafely(new Object[]{event});
    }

    public void adjustmentValueChanged(AdjustmentEvent event) {
	callSafely(new Object[]{event});
    }

    public void itemStateChanged(ItemEvent event) {
	callSafely(new Object[]{event});
    }

    public void textValueChanged(TextEvent event) {
	callSafely(new Object[]{event});
    }
}
