package cp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;

public class MACSolver extends AbstractSolver {

    public MACSolver(Set<Variable> variables, Set<Constraint> constraints) {
        super(variables, constraints);
    }


    @Override
    public Map<Variable, Object> solve(){
        Map<Variable, Object> instanciationP = new HashMap<>();
        Map<Variable, Set<Object>> domaine = new HashMap<>();
        LinkedList<Variable> varNoInstancie= new LinkedList<>();
        for(Variable variable : this.variables){
            domaine.put(variable, variable.getDomain());
            varNoInstancie.push(variable);
        }
        return MAC(instanciationP, varNoInstancie, domaine);
        
    }

    private Map<Variable, Object>  MAC(Map<Variable,Object> instanciationP, LinkedList<Variable> varNoInstancie, Map<Variable, Set<Object>> domaine){
        if(varNoInstancie.size() == 0){
            return instanciationP;
        }
        ArcConsistency ac = new ArcConsistency(this.constraints);
        if(!ac.ac1(domaine)){
            return null;
        }
        Variable variable = varNoInstancie.poll();
        for (Object valeur : domaine.get(variable)) {
            Map<Variable,Object> instanciation = new HashMap<>(instanciationP);
            instanciation.put(variable, valeur);
            if(this.isConsistent(instanciation)){
                Map<Variable, Object> r = this.MAC(instanciation, varNoInstancie, domaine);
                if(r!=null){
                    return r;
                }
            }
        }
        varNoInstancie.addLast(variable);;
        return null;
    }




    
}
