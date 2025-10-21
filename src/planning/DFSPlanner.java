package planning;

import java.util.*;

import modelling.Variable;

public class DFSPlanner implements Planner{
    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goals;
    private boolean activateNodeC = false;
    private int nbNoeuds = 0;
    

    public DFSPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goals) {
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

    public int getNbNoeuds() {
        return nbNoeuds;
    }

    @Override
    public void activateNodeCount(boolean activate) {
        this.activateNodeC = activate;
    }





    @Override
    public Stack<Action> plan() {
        return DFS(this,this.initialState,new Stack<Action>(),new HashSet<Map<Variable,Object>>());
    }
    private Stack<Action> DFS(DFSPlanner problem , Map<Variable, Object> state,Stack<Action> plan,Set<Map<Variable, Object>> closed){

        closed.add(state);
        //tester le but 
        if(problem.getGoal().isSatisfiedBy(state)){
            if(this.activateNodeC) this.nbNoeuds = closed.size();
            return plan;        //on retourne le plan si on a atteint un but
        }else{
            for(Action act : this.actions){
                if(act.isApplicable(state)){
                    Map<Variable, Object> next = act.successor(state);
                    if(!closed.contains(next)){
                        plan.add(act);   //ajouter le plan
                        //closed.add(next);
                        Stack<Action> subPlan = DFS(this, next, plan, closed);
                        if(subPlan != null){
                            return subPlan;        //solution trouvée
                        }else{
                            plan.pop();
                        }
                        

                    }
                }
            }
            return null;

        }
        

    }





    
}
