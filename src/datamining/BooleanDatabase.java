package datamining;

import java.util.*;

import modelling.BooleanVariable;

/**
 * Représente une base de données transactionnelle composée d'items booléens.
 * Une transaction est un ensemble d'items présents dans l'observation.
 */
public class BooleanDatabase {

    private Set<BooleanVariable> items;
    private List<Set<BooleanVariable>> transactions;

    /**
     * Crée une base de données vide contenant les items spécifiés.
     *
     * @param items ensemble des items possibles
     */
    public BooleanDatabase(Set<BooleanVariable> items) {
        this.items = items;
        this.transactions = new ArrayList<>();
    }
    
    /**
     * Ajoute une transaction à la base.
     *
     * @param transaction ensemble des items présents dans la transaction
     */
    public void add(Set<BooleanVariable> transaction) {
        this.transactions.add(transaction);
    }

    /**
     * Renvoie l'ensemble des items possibles.
     *
     * @return ensemble des items
     */
    public Set<BooleanVariable> getItems() {
        return items;
    }

    /**
     * Renvoie la liste des transactions.
     *
     * @return liste des transactions
     */
    public List<Set<BooleanVariable>> getTransactions() {
        return transactions;
    }

    
    
}
