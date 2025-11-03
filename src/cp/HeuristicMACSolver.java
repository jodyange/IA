package cp;

import java.util.*;
import modelling.*;

/**
 * Implémentation d'un solveur utilisant l'algo Consistancet Arc
 * 
 */
public class HeuristicMACSolver extends AbstractSolver {

    private VariableHeuristic variableHeuristic;
    private ValueHeuristic valueHeuristic;
    protected ArcConsistency arc;
    

    /**
     * Initialise le solveur HeuristicMACSolver avec les variables, les contraintes, et les heuristiques 
     * 
     * @param variables  set of variables 
     * @param constraints  set of constraints 
     * @param variableHeuristic  heuristic for selecting the next variable to assign.
     * @param valueHeuristic A heuristic for ordering the possible values for a variable.
     */
    public HeuristicMACSolver(Set<Variable> variables, Set<Constraint> constraints, VariableHeuristic variableHeuristic, ValueHeuristic valueHeuristic){
        super(variables, constraints);

        this.variableHeuristic = variableHeuristic;
        this.valueHeuristic = valueHeuristic;

        this.arc= new ArcConsistency(constraints);
    }

    /**
     * Résout en utilisant l'algorithme MAC avec les heuristiques spécifiées
     * Applique la consistance de l'arc à chaque étape
     * 
     * @return Une carte associant chaque variable à une valeur, ou null si aucune solution n'a été trouvée.
     */
    @Override
    public Map<Variable, Object> solve(){
    
        Set<Variable> V = new HashSet<>(this.variables); 
        Map<Variable, Set<Object>> domains= new HashMap<>();
        
        for(Variable v : this.variables){
            domains.put(v, v.getDomain());
        }
        
        return macHeuristic(new HashMap<>(),V,domains );
    }


     /**
     * Méthode récursive qui applique l'algorithme MAC en assignant des valeurs aux variables. 
     * Les variables sont sélectionnées selon les heuristiques choisieis, et les domaines sont réduits à chaque 
     * étape en appliquant la consist jeanne d'arc.
     * 
     * @param intatiation La carte des assignations actuelles des variables.
     * @param V L'ensemble des variables non assignées.
     * @param domains Une carte contenant les domaines de chaque variable.
     * @return Une carte <Variable, Object> avec une assignation valide des variables, ou null si aucune solution 
     *         n'est trouvée.
     */
    public Map<Variable, Object> macHeuristic(Map<Variable, Object> intatiation, Set<Variable> V, Map<Variable, Set<Object>> domains){
        if (V.isEmpty()) return intatiation;

        if (!arc.ac1(domains)) return null;
        

        Variable x = variableHeuristic.best(V, domains); 
        V.remove(x);

        List<Object> domain = valueHeuristic.ordering(x, domains.get(x)); 

        Map<Variable, Object> etat = new HashMap<>(intatiation);
        
        for(Object val : domain){
            etat.put(x,val);

            if(isConsistent(etat)){
                Map<Variable, Object> R = macHeuristic(etat, V, domains);
                
                if(R != null){
                    return R;
                }
            }
        }

        V.add(x);
        return null;
    }
}