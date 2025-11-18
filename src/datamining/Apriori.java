package datamining;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import modelling.BooleanVariable;

public class Apriori extends AbstractItemsetMiner{


    public Apriori(BooleanDatabase database) {
        super(database);
    }

    @Override
    public Set<Itemset> extract(float minFrequency) {
        List<SortedSet<BooleanVariable>> listLenFrequent = new ArrayList<>();
        Set<Itemset> frequentItem = new HashSet<>();
        Set<Itemset> frequentSingletons = this.frequentSingletons(minFrequency);
        frequentItem.addAll(frequentSingletons);
        for (Itemset itemset : frequentSingletons){
            SortedSet<BooleanVariable> item = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
            item.addAll(itemset.getItems());
            listLenFrequent.add(item);

        }
        while (listLenFrequent.size() > 1) {
            List<SortedSet<BooleanVariable>> candidats = new ArrayList<>();

            for(SortedSet<BooleanVariable> item1 : listLenFrequent){
                for (SortedSet<BooleanVariable> item2 : listLenFrequent){
                    if (!item1.equals(item2)){
                        SortedSet<BooleanVariable> combinaison = Apriori.combine(item1, item2);
                        if (combinaison != null) {
                            if (Apriori.allSubsetsFrequent(combinaison, listLenFrequent)) {
                                if (!candidats.contains(combinaison)) {
                                    candidats.add(combinaison);
                                }
                            }
                        }
                    }
                }
            }
            listLenFrequent.clear();

            // Calculer la fréquence des candidats et garder que les bons
            for (SortedSet<BooleanVariable> candidat : candidats) {
                float candidatFrequent = this.frequency(candidat);
                if (candidatFrequent >= minFrequency) {
                    frequentItem.add(new Itemset(candidat, candidatFrequent));
                    listLenFrequent.add(candidat);
                }
            }
            
            
        }
        return frequentItem;
    }


    public Set<Itemset> frequentSingletons(float minFrequency){
        if (!(minFrequency >= 0 && minFrequency <= 1)) {
            throw new IllegalArgumentException("The value of the frequency must be between 0 and 1");
        }
        Set<Itemset> itemsets = new HashSet<>();

        for (BooleanVariable item : this.database.getItems()) {
            Set<BooleanVariable> transaction = Collections.singleton(item);
            float transactionFrequency = this.frequency(transaction);
            if (transactionFrequency >= minFrequency) {
                itemsets.add(new Itemset(transaction,transactionFrequency));
            }
        }
        return itemsets;

    }



    public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> item1,
            SortedSet<BooleanVariable> item2) {
        if (item1.size() != 0 && !item1.equals(item2) && item1.size() == item2.size()) {
            int length = item1.size();
            Iterator<BooleanVariable> iteratorItem1 = item1.iterator();
            Iterator<BooleanVariable> iteratorItem2 = item2.iterator();
            boolean sameElements = true;
            // On vérifie si les deux items ont les mêmes k-1 premiers élément
            for (int i = 1; i < length; i++) {
                BooleanVariable v1 = iteratorItem1.next();
                BooleanVariable v2 = iteratorItem2.next();
                if (!v1.getName().equals(v2.getName())) {
                    sameElements = false;
                    return null;
                }
            }
            // Si oui sont-ils différents sur le dernier élément ?
            if (sameElements && !item1.last().equals(item2.last())) {
                SortedSet<BooleanVariable> combinaison = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
                combinaison.addAll(item1);
                combinaison.add(item2.last());
                return combinaison;
            }
            return null;
        }
        return null;
    }

    public static boolean allSubsetsFrequent(Set<BooleanVariable> item,Collection<SortedSet<BooleanVariable>> itemsCollection) {
        for (BooleanVariable i : item) {
            SortedSet<BooleanVariable> copy = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
            copy.addAll(item);
            copy.remove(i);
            if (!itemsCollection.contains(copy)) {
                return false;
            }
        }
        return true;
    }

    
    
}
