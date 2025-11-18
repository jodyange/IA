package blocksworld;

import modelling.Variable;
import modelling.BooleanVariable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BlocksWorldVariables {
    private final int nbBlocks;
    private final int nbPiles;
    // la set ou je mets(on_b, fixed_b, free_p)
    private final Set<Variable> allVariables = new HashSet<>();

    protected Map<Integer, Variable> onVariables = new HashMap<>();
    protected Map<Integer, BooleanVariable> fixedVariables = new HashMap<>();
    protected Map<Integer, BooleanVariable> freeVariables = new HashMap<>();

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

    public Map<Integer,Variable> getOns(){return onVariables;}

    public Map<Integer,BooleanVariable> getFixeds(){return fixedVariables;}

    public Map<Integer,BooleanVariable> getFrees(){return freeVariables;}


    //renvoie toutes les variables du monde des blocs
    public Set<Variable> getAllVariables() {
        allVariables.addAll(onVariables.values());
        allVariables.addAll(fixedVariables.values());
        allVariables.addAll(freeVariables.values());
        return allVariables;
    }

    @Override
    public String toString() {
       
        String res = " \n BlocksWorld Variables : " + "\n";
        res+= "\n onVariables : "+onVariables + "\n";
        res+= "\n fixedVariables : "+fixedVariables + "\n";
        res+= "\n freeVariables : "+freeVariables + "\n";
        
        return res;
    }

    public static void main(String[] args) {
        BlocksWorldVariables myWorld = new BlocksWorldVariables(3, 3);
        System.out.println(myWorld);
    }
    
}
