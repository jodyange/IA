package cp;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;

public class BacktrackSolver extends AbstractSolver{ 

    public BacktrackSolver(Set<Variable> variables, Set<Constraint> constraints) {
        super(variables, constraints);
    }

    @Override
    public Map<Variable, Object> solve(){
        Map<Variable, Object> instanciation = new HashMap<>();
        Queue<Variable> varNoInstancie = new LinkedList<Variable>();
        varNoInstancie.addAll(this.variables);
        return backtrack(instanciation, varNoInstancie);
        
    }


    //méthode, récursive
    public Map<Variable, Object> backtrack(Map<Variable, Object> instanciation, Queue<Variable> varNoInstancie ){

        if(varNoInstancie.isEmpty()){
            return instanciation;
        }
        
        Variable var = varNoInstancie.poll();
        for (Object valeur : var.getDomain()) {
            Map<Variable,Object> newInstanciation = new HashMap<>(instanciation);
            newInstanciation.put(var,valeur);
            //vérifier la cohérence locale
            if(this.isConsistent(newInstanciation)){
                Map<Variable,Object> result = backtrack(newInstanciation, varNoInstancie);
                 if (result!=null){
                    return result;
                }
            }
        }
        varNoInstancie.add(var);
        return null;


    }
    
}
