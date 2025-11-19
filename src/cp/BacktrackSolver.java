package cp;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;

/**
 * Solveur de CSP basé sur un algorithme de retour arrière (backtracking).
 * <p>
 * Les variables sont instanciées récursivement une par une, en testant la
 * cohérence locale à chaque étape. Aucune propagation de contraintes n'est
 * effectuée : seules les affectations déjà construites sont vérifiées.
 */
public class BacktrackSolver extends AbstractSolver{ 

    /**
     * Crée un solveur par backtracking pour le problème donné.
     *
     * @param variables l'ensemble des variables du problème
     * @param constraints l'ensemble des contraintes du problème
     */
    public BacktrackSolver(Set<Variable> variables, Set<Constraint> constraints) {
        super(variables, constraints);
    }

    /**
     * Cherche une solution par exploration récursive avec retour arrière.
     *
     * @return une affectation totale satisfaisant toutes les contraintes,
     *         ou {@code null} si le problème n'a pas de solution
     */
    @Override
    public Map<Variable, Object> solve(){
        Map<Variable, Object> instanciation = new HashMap<>();
        Queue<Variable> varNoInstancie = new LinkedList<Variable>();
        varNoInstancie.addAll(this.variables);
        return backtrack(instanciation, varNoInstancie);
        
    }


    /**
     * Méthode récursive de retour arrière.
     * <p>
     * On cherche à compléter une instanciation partielle en affectant
     * successivement les variables encore non instanciées.
     *
     * @param instanciation l'instanciation partielle actuelle
     * @param varNoInstancie la file des variables encore non instanciées
     * @return une instanciation complète satisfaisant toutes les contraintes,
     *         ou null si aucune extension cohérente n'existe
     */
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
