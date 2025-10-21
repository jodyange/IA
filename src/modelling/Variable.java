package modelling;

import java.util.Objects;
import java.util.Set;

/**
 *
 */
public class Variable {
    
    
    private String name;
    private Set<Object> domain;

    public Variable(String name, Set<Object> domain) {
        this.name = name;
        this.domain = domain;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        Variable v = (Variable) obj;
        return name.equals(v.getName());
        
       
    }
    
    

    public String getName() {
        return name;
    }

    public Set<Object> getDomain() {
        return domain;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDomain(Set<Object> domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return this.name + "{" +this.domain + "}";
    }

    



    
    
}
