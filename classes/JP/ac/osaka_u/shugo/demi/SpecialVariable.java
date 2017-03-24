package JP.ac.osaka_u.shugo.demi;

public class SpecialVariable {
    SpecialVariableGetter getter;
    SpecialVariableSetter setter;

    public SpecialVariable(SpecialVariableGetter getter,
			   SpecialVariableSetter setter) {
	this.getter = getter;
	this.setter = setter;
    }

    public Object get(Symbol name) throws Throwable {
	if (getter == null)
	    throw new IllegalAccessException(name.getName() +
					     " is write-only variable");
	return getter.get(name);
    }

    public void set(Symbol name, Object value) throws Throwable {
	if (setter == null)
	    throw new AssignmentException(name.getName() +
					  " is read-only variable");
	setter.set(name, value);
    }
}
