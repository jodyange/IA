package cp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import modelling.Variable;

public class RandomValueHeuristic implements ValueHeuristic{

    private Random generator;

    public RandomValueHeuristic(Random generator){
        this.generator = generator;
    }

    @Override
    public List<Object> ordering(Variable variable, Set<Object> domaine) {
        List<Object> value = new ArrayList<>(domaine);
        Collections.shuffle(value, generator);
        return value;
        
        
    }
    
}
