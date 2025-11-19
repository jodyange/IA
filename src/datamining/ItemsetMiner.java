package datamining;

import java.util.Set;

/**
 * Interface commune à tous les extracteurs d'itemsets fréquents.
 */
public interface ItemsetMiner {
    /**
     * Renvoie la base de données utilisée par l'extracteur.
     *
     * @return base de données booléenne
     */
    public BooleanDatabase getDatabase();

    /**
     * Extrait tous les itemsets non vides dont la fréquence
     * atteint au moins la fréquence minimale donnée.
     *
     * @param minFrequency fréquence minimale
     * @return ensemble des itemsets fréquents
     */
    public Set<Itemset> extract(float minFrequency);
}
