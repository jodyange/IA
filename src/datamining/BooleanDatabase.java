package datamining;

import java.util.*;

import modelling.BooleanVariable;

public class BooleanDatabase {

    private Set<BooleanVariable> items;
    private List<Set<BooleanVariable>> transactions;

    public BooleanDatabase(Set<BooleanVariable> items) {
        this.items = items;
        this.transactions = new ArrayList<>();
    }
    

    public void add(Set<BooleanVariable> transaction) {
        this.transactions.add(transaction);
    }

    public Set<BooleanVariable> getItems() {
        return items;
    }

    public List<Set<BooleanVariable>> getTransactions() {
        return transactions;
    }

    
    
}
