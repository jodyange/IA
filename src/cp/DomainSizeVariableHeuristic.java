package cp;

import java.util.Map;
import java.util.Set;

import modelling.Variable;

public class DomainSizeVariableHeuristic implements VariableHeuristic {

    private boolean preferDomain;

    public DomainSizeVariableHeuristic(boolean preferDomain) {
        this.preferDomain = preferDomain;
    }

    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domaines) {

        if (domaines == null || domaines.size() == 0) {
            return null;
        }
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        Variable maxVariable = null;
        Variable minVariable = null;

        for (Variable var : variables) {
            //les variables dans variables ont toutes leur domaine dans domains
            int domainSize = domaines.get(var).size();
            if (domainSize > max) {
                max = domainSize;
                maxVariable = var;
            }
            if (domainSize < min) {
                min = domainSize;
                minVariable = var;
            }

        }
        if (this.preferDomain) {
            return maxVariable;
        }
        return minVariable;
    }
 
    
}
