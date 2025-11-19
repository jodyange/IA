package datamining;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import modelling.BooleanVariable;

/**
 * Classe abstraite factorisant les éléments communs aux algorithmes
 * d'extraction d'itemsets fréquents.
 */
public abstract class AbstractItemsetMiner implements ItemsetMiner{

    public static final Comparator<BooleanVariable> COMPARATOR = (var1, var2) -> var1.getName().compareTo(var2.getName());
    protected BooleanDatabase database;

    /**
     * Crée un extracteur opérant sur la base donnée.
     *
     * @param database base de données booléenne
     */
    public AbstractItemsetMiner(BooleanDatabase database){
        this.database = database;
    }

    /**
     * Calcule la fréquence d'un ensemble d'items dans la base.
     *
     * @param items ensemble d'items dont on veut connaître la fréquence
     * @return fréquence entre 0 et 1
     */
    public float frequency(Set<BooleanVariable> items){
        List<Set<BooleanVariable>> dbTrans = this.database.getTransactions();
        int dbSize = dbTrans.size();
        if (dbSize==0) {
            return 0;
        }
        int count = 0;
        for(Set<BooleanVariable> transaction : dbTrans){
            if(transaction.containsAll(items)){
                count++;
            }
        }
        return (float)count/dbSize;
    }

    /**
     * Renvoie la base de données associée à cet extracteur.
     *
     * @return base de données
     */
    @Override
    public BooleanDatabase getDatabase() {
        return this.database;
    }

}


    
