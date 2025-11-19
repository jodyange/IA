package planning;


import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;


import modelling.Variable;

/**
 * Planificateur utilisant l'algorithme de Dijkstra.
 * <p>
 * Ce planificateur calcule un plan de coût minimal lorsque les actions
 * peuvent avoir des coûts différents.
 */
public class DijkstraPlanner implements Planner {

    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goals;
    private int nbNoeuds = 0;
    private boolean activateNodeC = false;

    /**
     * Crée un planificateur de type Dijkstra pour un problème donné.
     *
     * @param initialState état initial
     * @param actions ensemble des actions disponibles
     * @param goals but à atteindre
     */
    public DijkstraPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goals) {
        this.initialState = initialState;
        this.actions = actions;
        this.goals = goals;
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
     * Calcule un plan de coût minimal à l'aide de l'algorithme de Dijkstra.
     *
     * @return une liste d'actions formant un plan de coût minimal,
     *         ou null si aucun plan n'est trouvé
     */
    @Override
    public List<Action> plan() {
        
        Map<Map<Variable, Object>,Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Float> distance = new HashMap<>();
        Map<Map<Variable, Object>,Map<Variable, Object>> father = new HashMap<>();

        //PriorityQueue pour gérer les ajouts et accès dans open (State with distance)
        PriorityQueue<StateDist> open = new PriorityQueue<StateDist>(new MyComparator());
        Set<Map<Variable, Object>> goals = new HashSet<>();

        //Initialisation
        father.put(this.initialState, null);
        distance.put(this.initialState,0f);
        open.add(new StateDist (initialState, 0f));

        while (!open.isEmpty()) {
            StateDist instantiation = open.remove();
            if (this.activateNodeC) this.nbNoeuds++;
            if(this.goals.isSatisfiedBy(instantiation.getState())){
                goals.add(instantiation.getState());
                //System.out.println("test goals:" + goals);
                //return getDijkstraPlan(father, plan, instantiation);
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
                        father.put(next,instantiation.getState());
                        plan.put(next,act);
                        StateDist newState = new StateDist(next, newDistance);
                    
                        open.add(newState);
                    }
                }
            }
            
        }
        if (goals.isEmpty()){
            return null;
        }else{
            return getDijkstraPlan(father, plan, goals, distance);
        }
    }

    private List<Action> getDijkstraPlan(Map<Map<Variable,Object>,Map<Variable,Object>> father,
            Map<Map<Variable,Object>,Action> plan, Set<Map<Variable,Object>> goals,
            Map<Map<Variable,Object>,Float> distance) {
        List<Action> dijPlanList = new LinkedList<>();
        Map<Variable, Object> goal = getShortDistance(goals, distance);
        while(father.get(goal)!=null){
            dijPlanList.add(plan.get(goal));
            goal = father.get(goal);
        }
        Collections.reverse(dijPlanList);
        return dijPlanList;
    }




    //Instance with shorter distance
    private Map<Variable,Object> getShortDistance (Set<Map<Variable, Object>>instances,Map<Map<Variable, Object>,Float> distance){
        Float min=Float.MAX_VALUE;
        Map<Variable, Object> minInstance = null;
        for(Map<Variable, Object> instance : instances){
            if(distance.get(instance)<min){
                min = distance.get(instance);
                minInstance = instance;
            }
        }
        return minInstance;
    }


}
