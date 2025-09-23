package planning;


import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;


import modelling.Variable;

public class DijkstraPlanner implements Planner {

    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goals;

    public DijkstraPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goals) {
        this.initialState = initialState;
        this.actions = actions;
        this.goals = goals;
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
    
    
    @Override
    public List<Action> plan() {
        
        Map<Map<Variable, Object>,Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Float> distance = new HashMap<>();
        Map<Map<Variable, Object>,Map<Variable, Object>> father = new HashMap<>();

        //PriorityQueue pour gérer les ajouts et accès dans open
        PriorityQueue<Map<Variable, Object>> open = new PriorityQueue<>(Comparator.comparing(distance::get));
        Set<Map<Variable, Object>> goals = new HashSet<>();

        //Initialisation
        father.put(this.initialState, null);
        distance.put(this.initialState,0f);
        open.add(this.initialState);

        while (!open.isEmpty()) {
            Map<Variable, Object> instantiation = open.poll();
            if(this.goals.isSatisfiedBy(instantiation)){
                goals.add(instantiation);
                //System.out.println("test goals:" + goals);
                return getDijkstraPlan(father, plan, instantiation);
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
                        father.put(next,instantiation);
                        plan.put(next,act);
                    
                        open.add(next);
                    }
                }
            }
            
        }
        return null;
    }

    private List<Action> getDijkstraPlan(Map< Map<Variable, Object>, Map<Variable, Object> >father,Map< Map<Variable, Object>, Action >plan, Map<Variable,Object> goals ){
        List<Action> dijPlanList = new LinkedList<>();
        while(father.get(goals)!=null){
            dijPlanList.add(plan.get(goals));
            goals = father.get(goals);
        }
        Collections.reverse(dijPlanList);
        return dijPlanList;



    }





   

    
}
