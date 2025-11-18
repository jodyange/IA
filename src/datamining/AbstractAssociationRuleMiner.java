package datamining;

import java.util.HashSet;
import java.util.Set;

import modelling.BooleanVariable;

public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner {

    protected BooleanDatabase database;

    public AbstractAssociationRuleMiner(BooleanDatabase database) {
        this.database = database;
    }

    @Override
    public BooleanDatabase getDatabase() {
       return this.database;
    }

    public static float frequency(Set<BooleanVariable> items, Set<Itemset> itemsets){
        
        for (Itemset itemset : itemsets) {
            if(itemset.getItems().equals(items)){
                return itemset.getFrequency();
            }
        }
        throw new IllegalArgumentException("Fréquence introuvable pour l'ensemble d'items : " + items);
        
    }

    public static float confidence(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, Set<Itemset> itemsets){
        Set<BooleanVariable> union = new HashSet<>();
        union.addAll(premise);
        union.addAll(conclusion);
        float freqPremise = AbstractAssociationRuleMiner.frequency(premise, itemsets);
        float freqUnion = AbstractAssociationRuleMiner.frequency(union, itemsets);
        return freqUnion / freqPremise;
    }
    
}
