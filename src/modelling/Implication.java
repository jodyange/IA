package modelling;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Implication implements Constraint {

    private Variable v1, v2;
    private Set<Object> s1, s2;

    

    public Implication(Variable v1, Set<Object> s1, Variable v2, Set<Object> s2) {
        if (v1 == null || v2 == null || s1 == null || s2 == null) {
            throw new IllegalArgumentException("Les variables et ensembles ne peuvent pas être null.");
        }else{
            this.v1 = v1;
            this.s1 = s1;
            this.v2 = v2;
            this.s2 = s2;
        }   
    }


    @Override
    public Set<Variable> getScope() {
        Set<Variable> variables = new HashSet<>();
        variables.add(this.v1);
        variables.add(this.v2);
        return variables;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instantiation) throws IllegalArgumentException  {
        Object valueV1 = instantiation.get(this.v1);
        Object valueV2 = instantiation.get(this.v2);

        if(valueV1 == null || valueV2==null){
            throw new IllegalArgumentException("Wrong instanciation");
        }
        if(this.s1.contains(valueV1)){
            if(this.s2.contains(valueV2)){
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
        public String toString() {
            return "Variable 1 : " + v1 + ", domaine 1 : " + s1 + ", Et Variable 2 : " + v2 + ", domaine 2 : " + s2+ "\n";
        }
    
}
