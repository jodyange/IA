package datamining;

import java.util.Set;

import modelling.BooleanVariable;

/**
 * Représente un itemset, c'est-à-dire un ensemble d'items accompagné
 * de sa fréquence dans la base.
 */
public class Itemset {

    private Set<BooleanVariable> items;
    private float frequency;

    /**
     * Construit un itemset contenant les items indiqués et une fréquence donnée.
     *
     * @param items ensemble d'items constituant l'itemset
     * @param frequency fréquence de l'itemset dans la base
     */
    public Itemset(Set<BooleanVariable> items, float frequency) {
        if(!(frequency<=1 && frequency>=0)){
            throw new IllegalArgumentException("Item frequency must be between 0 and 1");
        }
        this.items = items;
        this.frequency = frequency;
    }

    /**
     * Renvoie les items constituant cet itemset.
     *
     * @return ensemble d'items
     */
    public Set<BooleanVariable> getItems() {
        return items;
    }

    /**
     * Renvoie la fréquence de cet itemset.
     *
     * @return fréquence dans la base
     */
    public float getFrequency() {
        return frequency;
    }

    @Override
    public String toString() {
        return "Itemset: " + "\n"+ "items = " + this.items +", frequency = " + this.frequency;
    }
    
}
