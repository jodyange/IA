package datamining;

import java.util.Set;

import modelling.BooleanVariable;

public class Itemset {

    private Set<BooleanVariable> items;
    private float frequency;

    public Itemset(Set<BooleanVariable> items, float frequency) {
        if(!(frequency<=1 && frequency>=0)){
            throw new IllegalArgumentException("Item frequency must be between 0 and 1");
        }
        this.items = items;
        this.frequency = frequency;
    }

    public Set<BooleanVariable> getItems() {
        return items;
    }

    public float getFrequency() {
        return frequency;
    }

    @Override
    public String toString() {
        return "Itemset: " + "\n"+ "items = " + this.items +", frequency = " + this.frequency;
    }
    
}
