package datamining;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import modelling.BooleanVariable;

public abstract class AbstractItemsetMiner implements ItemsetMiner{

    public static final Comparator<BooleanVariable> COMPARATOR = (var1, var2) -> var1.getName().compareTo(var2.getName());
    protected BooleanDatabase database;


    public AbstractItemsetMiner(BooleanDatabase database){
        this.database = database;
    }

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
    @Override
    public BooleanDatabase getDatabase() {
        return this.database;
    }

}


    
