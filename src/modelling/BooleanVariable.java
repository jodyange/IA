package modelling;
import java.util.HashSet;
import java.util.Set;


public class BooleanVariable extends Variable {

    public BooleanVariable(String name) {
        super(name, initDomain());
    }

    private static Set<Object> initDomain(){
        Set<Object> domain = new HashSet<>();
        domain.add(Boolean.TRUE);
        domain.add(Boolean.FALSE);
        return domain;
    }
    
}
