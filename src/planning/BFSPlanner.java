package planning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


import modelling.Variable;

/**
 * Planificateur utilisant une recherche en largeur (Breadth-First Search).
 * <p>
 * Ce planificateur explore les états par couches croissantes de distance
 * à partir de l'état initial, ce qui garantit un plan de longueur minimale
 * quand toutes les actions ont le même coût.
 */
public class BFSPlanner implements Planner{
    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goals;
    private int nbNoeuds = 0;
    private boolean activateNodeC = false;

    /**
     * Crée un planificateur BFS pour un problème donné.
     *
     * @param initialState état initial
     * @param actions ensemble des actions disponibles
     * @param goals but à atteindre
     */
    public BFSPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goals) {
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
     * Renvoie l'ensemble des actions disponibles.
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
     * Renvoie le nombre de nœuds explorés lors de la dernière recherche,
     * si le comptage a été activé.
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
     * Effectue une recherche en largeur pour trouver un plan vers un état but.
     *
     * @return une liste d'actions formant un plan, ou null si aucun plan n'est trouvé
     */
    @Override
    public List<Action> plan() {
        Map<Map<Variable, Object>,Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>,Action> plan = new HashMap<>();
        Set<Map<Variable, Object>> closed = new HashSet<>();
        Queue<Map<Variable, Object>> open = new LinkedList<>();
        open.add(this.initialState);
        father.put(this.initialState, null);

        if(this.goals.isSatisfiedBy(initialState)){
            if(this.activateNodeC) nbNoeuds = 1;
            return new ArrayList<Action>();

        }
        while (!open.isEmpty()) {
            Map<Variable, Object> instantiation = open.poll();
            closed.add(instantiation);
            for(Action act : this.actions){
                if(act.isApplicable(instantiation)){
                    Map<Variable, Object> next = act.successor(instantiation);
                    if(!closed.contains(next) && !open.contains(next)){
                        father.put(next, instantiation);
                        plan.put(next, act);

                        if (this.goals.isSatisfiedBy(next)){
                            return getBfsPlan(father, plan, next);
                        }else{
                            open.add(next);
                        }

                    }

                }
            }
            
        }
        return null;
    }

    /**
     * Construit un plan à partir des informations de père et d'action associée
     * à chaque état, typiquement après une recherche BFS.
     *
     * @param father dictionnaire associant à chaque état son état précédent
     * @param plan dictionnaire associant à chaque état l'action qui a permis de l'atteindre
     * @param goal état but atteint
     * @return liste d'actions formant le plan de l'état initial jusqu'à l'état but
     */
    public static List<Action> getBfsPlan(Map< Map<Variable, Object>, Map<Variable, Object>> father, Map< Map<Variable, Object>,Action> plan, Map<Variable,Object> goal ){
        List<Action> planList = new ArrayList<>();
        while(father.get(goal)!=null){
            planList.add(plan.get(goal));
            goal = father.get(goal);
        }
        Collections.reverse(planList);
        return planList;
    }

      
       
          
}
