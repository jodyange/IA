package planning;


import java.util.Map;

import modelling.Variable;

public class BasicGoal implements Goal {
    private Map<Variable, Object> buts;

    public BasicGoal(Map<Variable, Object> buts) {
        this.buts = buts;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> state) {
        //Vérifier que toutes les variables dans  le buts ont la bonne valeur dans state
        for (Map.Entry<Variable, Object> entry : this.buts.entrySet()) {
            Variable v = entry.getKey();
            Object valueOfV = entry.getValue();
            if(state.get(v)==null){
                return false;
            }
            if(!state.get(v).equals(valueOfV)){
                return false;
            }

        }
        return true;
    }

    @Override
    public String toString() {
        return "BasicGoal [buts=" + buts + "]";
    }

    


    
    
}
