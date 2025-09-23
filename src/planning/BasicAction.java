package planning;

import java.util.HashMap;
import java.util.Map;

import modelling.Variable;

public class BasicAction implements Action {
    private Map<Variable, Object> precondition;
    private Map<Variable, Object> effect;
    private int cost;




    public BasicAction(Map<Variable, Object> precondition, Map<Variable, Object> effect, int cost) {
        this.precondition = precondition;
        this.effect = effect;
        this.cost = cost;
    }

    @Override
    public boolean isApplicable(Map<Variable, Object> state) {
        //Vérifier si toutes les variables dans précondiction ont la bonne valeur dans state
        for (Map.Entry<Variable, Object> entry : this.precondition.entrySet()) {
            Variable key = entry.getKey();
            Object value = entry.getValue();
            if(state.get(key)==null){
                return false;
            }
            if(!state.get(key).equals(value)){
                return false;
            }

        }
        return true;
    }

    @Override
    public Map<Variable, Object> successor(Map<Variable, Object> state) {
        if(!isApplicable(state)){
            throw new IllegalArgumentException("L'action n'est pas applicable dans l'état. ");
        }
        Map<Variable, Object> successor = new HashMap<>(state);
        for (Map.Entry<Variable, Object> entry : this.effect.entrySet()) {
            Variable key = entry.getKey();
            Object value = entry.getValue();
            successor.put(key, value);
            
            
        }
        return successor;
    }

    @Override
    public int getCost() {
        return this.cost;
    }

    @Override
    public String toString() {
        String s ="\nPrécondition {";
        for (Map.Entry<Variable,Object> entry : this.precondition.entrySet()) {
            s += entry.getKey().getName()+ " = " + entry.getValue()+" ";
        }
        s+="}\n\n";

        s +="Effet {";
        for (Map.Entry<Variable,Object> entry : this.effect.entrySet()) {
            s += entry.getKey().getName()+ " = " + entry.getValue()+" ";
        }
        s+="}\n\n";

        s +="Coût {" + cost + "}\n";
        return s;
        //return "Precondition = " + precondition + ", effect = " + effect + ", cost = " + cost ;
    }

    
    
    
}
