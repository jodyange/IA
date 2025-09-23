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

        /*if (this == obj) {
            return true;
            
        }
        if(obj == null) return false;
        if(obj.getClass() != nom.getClass()){
            return false;
        } 
        Variable other = (Variable) obj;
        if(this.nom != null){
            return true;
        }
        return this.nom.equals(other.nom);*/
        
       
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
