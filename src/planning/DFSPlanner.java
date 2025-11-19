package planning;

import java.util.*;

import modelling.Variable;

/**
 * Planificateur utilisant une recherche en profondeur (Depth-First Search).
 * <p>
 * Ce planificateur explore les états en profondeur à partir de l'état initial,
 * jusqu'à trouver un état but ou jusqu'à épuisement des possibilités.
 */
public class DFSPlanner implements Planner{
    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goals;
    private boolean activateNodeC = false;
    private int nbNoeuds = 0;
    
    /**
     * Crée un planificateur DFS pour un problème donné.
     *
     * @param initialState état initial
     * @param actions ensemble des actions disponibles
     * @param goals but à atteindre
     */
    public DFSPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goals) {
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
     * Renvoie l'ensemble des actions du problème.
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
     * Effectue une recherche en profondeur à partir de l'état initial
     * pour trouver un plan vers un état but.
     * <p>
     * Le plan est retourné sous la forme d'une pile d'actions, l'action
     * du début du plan étant au bas de la pile.
     *
     * @return une pile d'actions formant un plan, ou null si aucun plan n'est trouvé
     */
    @Override
    public Stack<Action> plan() {
        return DFS(this,this.initialState,new Stack<Action>(),new HashSet<Map<Variable,Object>>());
    }

    /**
     * Méthode récursive de recherche en profondeur.
     *
     * @param problem problème de planification considéré
     * @param state état courant
     * @param plan pile représentant le plan construit jusqu'ici
     * @param closed ensemble des états déjà explorés
     * @return une pile d'actions formant un plan, ou null si aucun plan n'est trouvé
     */
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
