package planning;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import modelling.Variable;

/**
 * Planificateur utilisant l'algorithme A*.
 * <p>
 * Ce planificateur combine coûts réellement parcourus et estimation heuristique
 * du coût restant pour guider la recherche vers un plan de coût minimal.
 */
public class AStarPlanner implements Planner  {

    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goals;
    private Heuristic heuristic;
    private int nbNoeuds = 0;
    private boolean activateNodeC = false;

    /**
     * Crée un planificateur A* pour un problème donné.
     *
     * @param initialState état initial
     * @param actions ensemble des actions disponibles
     * @param goals but à atteindre
     * @param heuristic heuristique utilisée pour estimer le coût restant
     */    
    public AStarPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goals, Heuristic heuristic) {
        this.initialState = initialState;
        this.actions = actions;
        this.goals = goals;
        this.heuristic = heuristic;
    }

    /**
     * Renvoie l'état initial du problème.
     *
     * @return état initial
     */
    @Override
    public Map<Variable, Object> getInitialState() {
        return initialState;
    }

    /**
     * Renvoie l'ensemble des actions.
     *
     * @return ensemble d'actions
     */
    @Override
    public Set<Action> getActions() {
        return actions;
    }

    /**
     * Renvoie le but du problème.
     *
     * @return but à atteindre
     */
    @Override
    public Goal getGoal() {
        return goals;
    }

    /**
     * Renvoie le nombre de nœuds explorés lors de la dernière exécution
     * de l'algorithme, si le comptage est activé.
     *
     * @return nombre de nœuds explorés
     */
    public int getNbNoeuds() {
        return nbNoeuds;
    }

    /**
     * Active ou désactive le comptage du nombre de nœuds explorés.
     *
     * @param activate vrai pour activer le comptage, faux sinon
     */
    @Override
    public void activateNodeCount(boolean activate) {
        this.activateNodeC = activate;
    }

    /**
     * Calcule un plan de coût minimal estimé à l'aide de l'algorithme A*.
     *
     * @return une liste d'actions formant un plan, ou null si aucun plan n'est trouvé
     */
    @Override
    public List<Action> plan() {
        Map<Map<Variable, Object>,Action> plan = new HashMap<>();
        Map<Map<Variable, Object>,Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Float> distance = new HashMap<>();
        Map<Map<Variable, Object>, Float> value = new HashMap<>();
        

        //PriorityQueue pour gérer les ajouts et accès dans open
        PriorityQueue<StateDist> open = new PriorityQueue<StateDist>(new MyComparator());
        //Set<Map<Variable, Object>> goals = new HashSet<>();

        //Initialisation
        father.put(this.initialState, null);
        distance.put(this.initialState,0f);
        open.add(new StateDist(initialState, heuristic.estimate(initialState)));
        value.put(this.initialState, heuristic.estimate(this.initialState));


        while (!open.isEmpty()) {
            StateDist instantiation = open.remove();
            if (this.activateNodeC){
                this.nbNoeuds++;
            }
            if(this.goals.isSatisfiedBy(instantiation.getState())){
                // if (this.activateNodeC)
                //     System.out.println("le nombre de noeuds explorés : " + nbNoeuds);
                // goals.add(instantiation);
                return BFSPlanner.getBfsPlan(father, plan, instantiation.getState());
            }
            for(Action act : this.actions){
                if(act.isApplicable(instantiation.getState())){
                    Map<Variable, Object> next = act.successor(instantiation.getState());
                    if(!distance.containsKey(next)){
                        distance.put(next, Float.MAX_VALUE);
                    }

                    if(distance.get(next)>distance.get(instantiation.getState())+act.getCost()){
                        Float newDistance = distance.get(instantiation.getState()) + act.getCost();
                        distance.put(next, newDistance);
                        Float newVal = newDistance + heuristic.estimate(next);
                        value.put(next, newVal);
                        father.put(next,instantiation.getState());
                        plan.put(next,act);
                    
                        open.add(new StateDist(next, newVal));
                    }
                }
            }
            
        }
        return null;
    }



}
