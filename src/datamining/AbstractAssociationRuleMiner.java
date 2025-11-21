package datamining;

import java.util.HashSet;
import java.util.Set;

import modelling.BooleanVariable;

/**
 * Classe abstraite factorisant les éléments communs aux algorithmes
 * d'extraction de règles d'association.
 */
public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner {

    protected BooleanDatabase database;

    /**
     * Construit un extracteur de règles utilisant la base donnée.
     *
     * @param database base de données utilisée
     */
    public AbstractAssociationRuleMiner(BooleanDatabase database) {
        this.database = database;
    }

    @Override
    public BooleanDatabase getDatabase() {
       return this.database;
    }

    /**
     * Renvoie la fréquence d'un ensemble d'items parmi l'ensemble
     * des itemsets fréquents fournis.
     *
     * @param items ensemble d'items
     * @param itemSet itemsets fréquents calculés auparavant
     * @return fréquence trouvée
     */
    public static float frequency(Set<BooleanVariable> items,Set<Itemset> itemSet){

        float f = 0;
        boolean find = false;

        for (Itemset item : itemSet) {
            Set<BooleanVariable> it = item.getItems();
            if( it.size() == items.size() && it.containsAll(items)){
                f = item.getFrequency();
                find = true;
            }
        }
        if(!find){
            throw new IllegalArgumentException("L'ensemble d'items n'est pas présent dans l'ensemble d'ItemSet");
        }
        return f;
    }

    /**
     * Calcule la confiance d'une règle candidate.
     *
     * @param premise prémisse
     * @param conclusion conclusion
     * @param itemsets ensemble des itemsets fréquents
     * @return confiance de la règle
     */
    public static float confidence(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, Set<Itemset> itemsets){
        Set<BooleanVariable> union = new HashSet<>();
        union.addAll(premise);
        union.addAll(conclusion);
        float freqPremise = AbstractAssociationRuleMiner.frequency(premise, itemsets);
        float freqUnion = AbstractAssociationRuleMiner.frequency(union, itemsets);
        return freqUnion / freqPremise;
    }
    
}
