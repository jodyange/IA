package datamining;


import java.util.SortedSet;
import java.util.TreeSet;

import modelling.BooleanVariable;

public class Demo {

    public static void main(String[] args) {

        SortedSet<BooleanVariable> domain = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
        BooleanVariable d1 = new BooleanVariable("A");
        BooleanVariable d2 = new BooleanVariable("B");
        BooleanVariable d3 = new BooleanVariable("C");
        BooleanVariable d4 = new BooleanVariable("D");

        domain.add(d1);
        domain.add(d2);
        domain.add(d3);
        domain.add(d4);
        System.out.println(domain);

        

        
    }
    
}
