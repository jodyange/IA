package planning;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import modelling.Variable;

public class AStarPlanner implements Planner  {

    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goals;
    private Heuristic heuristic;
    private int nbNoeuds = 0;
    private boolean activateNodeC = false;


    
    public AStarPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goals, Heuristic heuristic) {
        this.initialState = initialState;
        this.actions = actions;
        this.goals = goals;
        this.heuristic = heuristic;
        
    }

    

      @Override
    public Map<Variable, Object> getInitialState() {
        return initialState;
    }

    @Override
    public Set<Action> getActions() {
        return actions;
    }

    @Override
    public Goal getGoal() {
        return goals;
    }

    public int getNbNoeuds() {
        return nbNoeuds;
    }

    @Override
    public void activateNodeCount(boolean activate) {
        this.activateNodeC = activate;
    }



    @Override
    public List<Action> plan() {
        Map<Map<Variable, Object>,Action> plan = new HashMap<>();
        Map<Map<Variable, Object>,Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Float> distance = new HashMap<>();
        Map<Map<Variable, Object>, Float> value = new HashMap<>();

        //PriorityQueue pour gérer les ajouts et accès dans open
        PriorityQueue<Map<Variable, Object>> open = new PriorityQueue<>(Comparator.comparing(distance::get));
        Set<Map<Variable, Object>> goals = new HashSet<>();

        //Initialisation
        father.put(this.initialState, null);
        distance.put(this.initialState,0f);
        open.add(this.initialState);
        value.put(this.initialState, heuristic.estimate(this.initialState));


        while (!open.isEmpty()) {
            this.nbNoeuds+=1;
            Map<Variable, Object> instantiation = open.poll();
            if(this.goals.isSatisfiedBy(instantiation)){
                if (this.activateNodeC)
                    System.out.println("le nombre de noeuds explorés : " + nbNoeuds);
                goals.add(instantiation);
                return BFSPlanner.getBfsPlan(father, plan, instantiation);
            }
            for(Action act : this.actions){
                if(act.isApplicable(instantiation)){
                    Map<Variable, Object> next = act.successor(instantiation);
                    if(!distance.containsKey(next)){
                        distance.put(next, Float.MAX_VALUE);
                    }

                    if(distance.get(next)>distance.get(instantiation)+act.getCost()){
                        Float newDistance = distance.get(instantiation) + act.getCost();
                        distance.put(next, newDistance);
                        Float newValue = newDistance + heuristic.estimate(next);
                        value.put(next, newValue);
                        father.put(next,instantiation);
                        plan.put(next,act);
                    
                        open.add(next);
                    }
                }
            }
            
        }
        return null;
    }



}
