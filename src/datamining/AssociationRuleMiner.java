package datamining;

import java.util.Set;

/**
 * Interface commune aux extracteurs de règles d'association.
 */
public interface AssociationRuleMiner {

     /**
     * Renvoie la base de données utilisée.
     *
     * @return base de données booléenne
     */
    public BooleanDatabase getDatabase();

    /**
     * Extrait les règles d'association dont la fréquence et la confiance
     * atteignent les seuils donnés.
     *
     * @param minFrequency fréquence minimale
     * @param minConfidence confiance minimale
     * @return ensemble des règles extraites
     */
    public Set<AssociationRule> extract(float minFrequency, float minConfience);
    
}
