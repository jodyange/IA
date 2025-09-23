package modelling;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UnaryConstraint implements Constraint{

    private Variable v;
    private Set<Object> s;


    public UnaryConstraint(Variable v, Set<Object> s) {
        if (v == null || s == null) {
            throw new IllegalArgumentException("Les variables et ensembles ne peuvent pas être null.");
        }else{
             this.v = v;
             this.s = s;
            }
    }
    

    @Override
    public Set<Variable> getScope() {
        Set<Variable> variables = new HashSet<>();
        variables.add(this.v);
        return variables;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instantiation) throws IllegalArgumentException {
        Object valueV = instantiation.get(this.v);
        if(valueV == null){
            throw new IllegalArgumentException("Variable value unknown in instantiation");
        }
        if(this.s.contains(valueV)){
            return true;
        }
        return false;

    }
    
}
