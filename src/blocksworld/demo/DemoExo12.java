package blocksworld.demo;

import bwgeneratordemo.*;
import java.util.List;
import java.util.Random;
import java.util.Set;

import blocksworld.BlocksWorldDatabase;
import datamining.*;
import modelling.*;


public class DemoExo12 {

    public static void main(String[] args) {

        BlocksWorldDatabase database = new BlocksWorldDatabase(5, 3);
        BooleanDatabase bool = new BooleanDatabase(database.getVariables());

            // Génèration aléatoire de 10000 états
            for (int i = 0; i < 10000; i++) {
                List<List<Integer>> state = Demo.getState(new Random());
                Set<BooleanVariable> instances = database.getInstance(state);
                bool.add(instances);
                }
            
            Apriori apriori = new Apriori(bool);
            Set<Itemset> itemset = apriori.extract((float) 2 / 3);

            AssociationRuleMiner bruteForce = new BruteForceAssociationRuleMiner(bool);
            Set<AssociationRule> regles = bruteForce.extract((float) 2 / 3, (float) 95 / 100);

            System.out.println("\n-------------------<ITEMSETS FREQUENCIE>---------------\n");
            System.out.println(itemset);
            System.out.println("\n-------------------<ASSOCIATION RULES>----------------\n");
            System.out.println(regles);
    }
}
