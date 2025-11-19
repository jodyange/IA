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
     * @param itemsets itemsets fréquents calculés auparavant
     * @return fréquence trouvée
     */
    public static float frequency(Set<BooleanVariable> items, Set<Itemset> itemsets){
        
        for (Itemset itemset : itemsets) {
            if(itemset.getItems().equals(items)){
                return itemset.getFrequency();
            }
        }
        throw new IllegalArgumentException("Fréquence introuvable pour l'ensemble d'items : " + items);
        
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
