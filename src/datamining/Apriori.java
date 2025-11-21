package datamining;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import modelling.BooleanVariable;

/**
 * Implémentation de l'algorithme Apriori pour extraire
 * les itemsets fréquents dans une base de données booléenne.
 */
public class Apriori extends AbstractItemsetMiner{

    /**
     * Crée un extracteur Apriori pour la base donnée.
     *
     * @param database base de données utilisée
     */
    public Apriori(BooleanDatabase database) {
        super(database);
    }

     /**
     * Extrait tous les itemsets non vides dont la fréquence
     * atteint au moins la fréquence minimale donnée.
     *
     * @param minFrequency fréquence minimale
     * @return ensemble des itemsets fréquents
     */
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
        while (listLenFrequent.size() > 0) {
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

    /**
     * Renvoie l'ensemble des itemsets de taille 1 dont la fréquence
     * atteint la fréquence minimale donnée.
     *
     * @param minFrequency seuil minimal
     * @return ensemble des itemsets fréquents de taille 1
     */
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

    /**
     * Combine deux itemsets triés pour former un candidat
     * d'itemset de taille supérieure, si les conditions
     * de compatibilité sont remplies.
     *
     * @param s1 premier ensemble trié
     * @param s2 second ensemble trié
     * @return ensemble combiné ou null si non combinable
     */
    public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> s1,SortedSet<BooleanVariable> s2){

        if(s1.size() != s2.size()){return null;}
        if(s1.isEmpty()){return null;}
        if(s1.last().equals(s2.last())){return null;}
        //on compare les k-1 premiers éléments des deux listes
        if(!s1.headSet(s1.last()).equals(s2.headSet(s2.last()))){return null;}

        SortedSet<BooleanVariable> resultat = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);

        resultat.addAll(s1);
        resultat.add(s2.last());

        return resultat;
    }

    /**
     * Teste si tous les sous-ensembles d'un certain ensemble d'items,
     * obtenus en retirant un élément, sont fréquents.
     *
     * @param items ensemble dont on teste les sous-ensembles
     * @param frequents collection des itemsets fréquents de taille inférieure
     * @return vrai si tous les sous-ensembles sont présents
     */
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
