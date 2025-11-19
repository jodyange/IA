package modelling;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DifferenceConstraint implements Constraint{

    private Variable v1;
    private Variable v2;

    

    public DifferenceConstraint(Variable v1, Variable v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    @Override
    public Set<Variable> getScope() {
        Set<Variable> variables = new HashSet<>();
        variables.add(this.v1);
        variables.add(this.v2);
        return variables;
        
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instantiation) throws IllegalArgumentException {
        Object valueV1 = instantiation.get(this.v1);
        Object valueV2 = instantiation.get(this.v2);


        if(valueV1 == null || valueV2 == null){
            throw new IllegalArgumentException("Unimplemented method 'isNotStisfiedBy'");
        }

        if(valueV1.equals(valueV2)){
            return false;
        }
        return true;
    }
    public String toString(){
            return "Variable 1 : " + v1 + ", Et  Variable 2 : " + v2 + "\n";
         }
}
