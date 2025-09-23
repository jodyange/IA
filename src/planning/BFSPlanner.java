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

public class BFSPlanner implements Planner{
    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goals;

    public BFSPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goals) {
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
        Map<Map<Variable, Object>,Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>,Action> plan = new HashMap<>();
        Set<Map<Variable, Object>> closed = new HashSet<>();
        Queue<Map<Variable, Object>> open = new LinkedList<>();
        open.add(this.initialState);
        father.put(this.initialState, null);

        if(this.goals.isSatisfiedBy(initialState)){
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
