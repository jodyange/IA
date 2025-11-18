package cp;

import java.util.Map;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;

public class NbConstraintsVariableHeuristic implements VariableHeuristic {

    private Set<Constraint> constraints;
    private boolean favoriteVariable;


    public NbConstraintsVariableHeuristic(Set<Constraint> constraints, boolean favoriteVariable) {
        this.constraints = constraints;
        this.favoriteVariable = favoriteVariable;
    }


    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domaines) {
        if(variables==null || variables.size()==0){
            return null;
        }
        int max  = Integer.MIN_VALUE;    // pour chercher le plus grand
        int min = Integer.MAX_VALUE;
        Variable maxVariable = null;
        Variable minVariable = null;
        for (Variable var : variables) {
             // Compter combien de contraintes concernent cette variable
            int count = 0;
            for (Constraint c : this.constraints) {
                if(c.getScope().contains(var)){
                    count++;
                }
            }
            if(count>max){
                max=count;
                maxVariable = var;  //Variable apparaissant dans le plus de contraintes
            }
            if(count<min){
                min = count;
                minVariable = var;  //Variable apparaissant dans le moins de contraintes
            }
        }

        if(this.favoriteVariable){
            return maxVariable;
        }
        return minVariable;
        
    }
    
    
}
