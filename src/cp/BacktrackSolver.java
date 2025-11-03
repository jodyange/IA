package cp;

//import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
//import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;

/**
 * Cette classe implémente un solveur basé sur la méthode du backtracking pour résoudre les contraintes
 * Elle hérite de la classe abstraite AbstractSolver
 */
public class BacktrackSolver extends AbstractSolver{ 

    /**
     * Constructeur de BacktrackSolver.
     * 
     * @param variables L'ensemble des variables
     * @param constraints L'ensemble des contraintes
     */
    public BacktrackSolver(Set<Variable> variables, Set<Constraint> constraints) {
        super(variables, constraints);
    }

    /**
     * trouve une solution utilisant du backtracking.
     * La méthode initialise l'instanciation partielle des variables et la file des variables non instanciées.
     * Ensuite, elle appelle backtracking() pour trouver une solution.
     * 
     * @return Une Map de Variable à Object représentant la solution si elle existe, 
     *         sinon si elle n'existte pas, elle renvoie null.
     */
    @Override
    public Map<Variable, Object> solve(){
        Map<Variable, Object> instanciationPartielle = new HashMap<>();
        Queue<Variable> varNoInstancie = new LinkedList<Variable>();
        varNoInstancie.addAll(this.variables);
        return backtrack(instanciationPartielle, varNoInstancie);
        
    }

    //méthode, récursive
    /**
     * Algorithme backtracking
     * Elle assigne des valeurs aux variables une par une, et vérifie à chaque étape si l'assignation est cohérente.
     * Si l'assignation est cohérente, elle continue à explorer les autres variables, sinon elle revient en arrière.
     * 
     * @param instanciationPartielle Linstanciation partiel des variables avec leurs valeurs assignées.
     * @param varNoInstancie La file des variables qui n'ont pas été instanciées.
     * 
     * @return Une Map de Variable à Object représentant une solution 
     *         ou null si aucune solution n'est trouvée.
     */
    public Map<Variable, Object> backtrack(Map<Variable, Object> instanciationPartielle, Queue<Variable> varNoInstancie ){

        if(varNoInstancie.isEmpty()){
            return instanciationPartielle;
        }
        
        Variable var = varNoInstancie.poll();
        for (Object valeur : var.getDomain()) {
            Map<Variable,Object> newInstanciation = new HashMap<>(instanciationPartielle);
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
