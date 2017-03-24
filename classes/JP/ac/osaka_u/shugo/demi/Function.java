/* Function.java */

package JP.ac.osaka_u.shugo.demi;

public interface Function {
    public Object call(Object[] args) throws Throwable;
    public Object callSafely(Object[] args);
    public Symbol getName();
}
