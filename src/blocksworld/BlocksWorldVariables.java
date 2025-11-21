package blocksworld;

import modelling.Variable;
import modelling.BooleanVariable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Représente l’ensemble des variables d’un monde des blocs.
 * 
 * Cette classe construit :
 * pour chaque bloc b : une variable on_b, 
 * pour chaque bloc b : une variable booléenne fixed_b, 
 * pour chaque pile p : une variable booléenne free_p
 *
 * Les domaines des variables on_b suivent la définition classique :
 */
public class BlocksWorldVariables {
    protected int nbBlocks;
    protected int nbPiles;
    // la set ou je mets(on_b, fixed_b, free_p)
    private final Set<Variable> allVariables = new HashSet<>();
    public Map<Integer, Variable> onVariables = new HashMap<>();
    protected Map<Integer, BooleanVariable> fixedVariables = new HashMap<>();
    protected Map<Integer, BooleanVariable> freeVariables = new HashMap<>();

    /**
     * Construit l’ensemble des variables du monde des blocs.
     *
     * @param nbBlocks nombre total de blocs (doit être ≥ 1)
     * @param nbPiles nombre total de piles (doit être ≥ 1)
     *
     * @throws IllegalArgumentException si nbBlocks ou nbPiles est ≤ 0
     */
    public BlocksWorldVariables(int nbBlocks, int nbPiles) {
        if (nbBlocks <= 0) {
            throw new IllegalArgumentException("nbBlocks doit être >= 1");
        }
        if (nbPiles <= 0) {
            throw new IllegalArgumentException("nbPiles doit être >= 1");
        }
        this.nbBlocks = nbBlocks;
        this.nbPiles = nbPiles;

        generateVariables();
    }

    /**
     * Génère l’ensemble des variables :
     * 
     * on_b pour chaque blo
     * fixed_b pour chaque bloc b ;
     * free_p pour chaque pile p.
     */
    public void generateVariables(){
        for(int i = 0; i < nbBlocks; i++){
            Variable on = new Variable("on"+i, getDomain(i));
            onVariables.put(i, on);
        }
        for(int i = 0; i < nbBlocks; i++){
            BooleanVariable fixed = new BooleanVariable("fixed"+i);
            fixedVariables.put(i, fixed);
        }
        for(int i = 0; i < nbBlocks; i++){
            BooleanVariable free = new BooleanVariable("free"+i);
            freeVariables.put(i, free);
        }
    }


    //Cette méthode renvoie le domaine d'un bloc privé de lui meme. 
    /**
     * Calcule le domaine possible pour la variable on_b,
     * c’est-à-dire toutes les valeurs représentant :
     * les piles : -1, -2, ..., -nbPiles ;
     * les autres blocs sauf b.
     *
     * @param b identifiant du bloc concerné
     * @return un ensemble représentant toutes les valeurs possibles de on_b
     */
    private Set<Object> getDomain(int b){
        Set<Object> domaine = new HashSet<>();

        for(int i = -nbPiles; i < 0; i++){
            domaine.add(i);
        }
        for(int i = 0; i < nbPiles; i++){ 
            if(i != b) domaine.add(i);
        }
        return domaine;
    }

    /**
     * Retourne l’ensemble des variables du monde des blocs.
     *
     * @return un ensemble contenant toutes les variables générées
     */
    public Set<Variable> getAllVariables() {
        allVariables.addAll(onVariables.values());
        allVariables.addAll(fixedVariables.values());
        allVariables.addAll(freeVariables.values());
        return allVariables;
    }

    /**
     * Convertit une configuration du monde des blocs en une instanciation
     * des variables du modèle attribut-valeur.
     * @param piles liste des LIFO
     * @return piles du monde des blocs
     */
    public Map<Variable,Object> getState(List<List<Integer>> piles){
        Map<Variable,Object> state = new HashMap<>();

        for (int pile = 0; pile < piles.size(); pile++) {
            List<Integer> s = piles.get(pile);
            if(s.isEmpty()){
                state.put(freeVariables.get(pile), true);
            }else{
                state.put(freeVariables.get(pile), false);

                for (int b = 0; b < s.size(); b++) {
                    int block = s.get(b);
                    if(b==0){
                        state.put(onVariables.get(block), -(pile+1));
                    }else{
                        int blockPrime = s.get(b-1);
                        state.put(onVariables.get(block), blockPrime);
                    }
                    
                    if(b < s.size()-1){
                        state.put(fixedVariables.get(block), true);
                    }else{
                        state.put(fixedVariables.get(block), false);
                    }
                }
            }
        }
        return state;
    }    
    /**
     * Retourne les variables on_b indexées par bloc.
     *
     * @return variable on_b
     */
    public Map<Integer,Variable> getOns(){return onVariables;}

    /**
     * Retourne les variables fixed_b indexées par bloc.
     *
     * @return variable fixed_b
     */
    public Map<Integer,BooleanVariable> getFixeds(){return fixedVariables;}

    /**
     * Retourne les variables free_p indexées par pile.
     *
     * @return variable free_p
     */
    public Map<Integer,BooleanVariable> getFrees(){return freeVariables;}
}
