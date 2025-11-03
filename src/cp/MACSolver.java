package cp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;

/**
 * Classe représentant un solveur de contraintes utilisant l'algorithme MAC
 * 
 * L'algorithme MAC = backtracking + consistance d'arc, => moins de domaines des variables pendant l'exploration
 * implémente cette approche en utilisant l'algorithme AC-1 pour maintenir la consistance d'arc à chaque étape.
 * 
 * L'objectif de MACsolveur est de trouver une affectation complète des variables qui respecte toutes les contraintes.
 */
public class MACSolver extends AbstractSolver {

    /**
     * Constructeur du solveur MACSolver.
     * 
     * @param variables L'ensemble des variables
     * @param constraints L'ensemble des contraintes
     */
    public MACSolver(Set<Variable> variables, Set<Constraint> constraints) {
        super(variables, constraints);
    }

    /**
     * Méthode principale pour résoudre problème utilisant l'algorithme MAC.
     * 
     * Cette méthode initialise l'affectation partielle et les domaines des variables, 
     * puis appelle la méthode récursive pour résoudre le problème.
     * 
     * @return Une Map contenant les variables et leurs valeurs affectées, ou null si aucune solution n'a été trouvée.
     */
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

    /**
     * Méthode récursive qui applique l'algorithme MAC
     * 
     * Cette méthode tente de trouver une solution en affectant des valeurs aux variables, 
     * en maintenant la consistance d'arc à chaque étape.
     * 
     * @param instanciationP L'affectation partielle actuelle des variables.
     * @param varNoInstancie La liste des variables non instanciées restantes.
     * @param domaine Les domaines des variables.
     * @return Une Map contenant les variables et leurs valeurs affectées, ou null si aucune solution n'a été trouvée.
     */
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
