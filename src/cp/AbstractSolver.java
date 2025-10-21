package cp;

import java.util.Map;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;

public abstract class AbstractSolver implements Solver{

    protected Set<Variable> variables;
    protected Set<Constraint> constraints;
    
    public AbstractSolver(Set<Variable> variables, Set<Constraint> constraints ){
        this.variables = variables;
        this.constraints = constraints;
    }

    public Set<Variable> getVariables() {
        return variables;
    }

    public void setVariables(Set<Variable> variables) {
        this.variables = variables;
    }

    public Set<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(Set<Constraint> constraints) {
        this.constraints = constraints;
    }

    public boolean isConsistent(Map<Variable, Object> instanciation){
        for (Constraint c : this.constraints) {
            if(instanciation.keySet().containsAll(c.getScope())){
                if(!c.isSatisfiedBy(instanciation)){
                    return false;
                }
            }  
        }
        return true;
    }


    
}
