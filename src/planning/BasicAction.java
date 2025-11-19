package planning;

import java.util.HashMap;
import java.util.Map;

import modelling.Variable;

/**
 * Implémentation basique de l'interface Action.
 * <p>
 * Une action basique est définie par :
 * <ul>
 *   <li>une précondition, qui est une instanciation partielle des variables,</li>
 *   <li>un effet, qui est une instanciation partielle appliquée sur l'état.</li>
 * </ul>
 */
public class BasicAction implements Action {
    private Map<Variable, Object> precondition;
    private Map<Variable, Object> effect;
    private int cost;

    /**
     * Crée une action basique avec une précondition et un effet donnés.
     *
     * @param precondition instanciation partielle représentant la précondition
     * @param effect instanciation partielle représentant l'effet
     * @param cost le cout d'une opération
     */
    public BasicAction(Map<Variable, Object> precondiction, Map<Variable, Object> effet, int cost){
        this.precondition = precondiction;
	    this.effect = effet;
	    this.cost = cost;
    }


    /**
     * Vérifie si la précondition de l'action est satisfaite dans l'état donné.
     *
     * @param state état courant
     * @return vrai si toutes les variables de la précondition ont la bonne valeur
     *         dans l'état, faux sinon
     */
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

    /**
     * Renvoie l'état successeur après application de l'effet de l'action.
     * <p>
     * L'état retourné est une nouvelle instanciation qui reprend les valeurs
     * de l'état de départ, modifiées pour les variables spécifiées dans l'effet.
     *
     * @param state état courant
     * @return nouvel état résultant de l'application de l'action
     */
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

    /**
     * Renvoie le coût de cette action.
     * Dans cette implémentation basique, le coût est constant et vaut 1.
     *
     * @return coût de l'action
     */
    @Override
    public int getCost() {
        return this.cost;
    }

    @Override
    public String toString() {
        return "Précondition : " + precondition + ", \n Effet = " + effect + "\n" ;
    }

    
    
    
}
