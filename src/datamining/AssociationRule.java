package datamining;

import java.util.Set;

import modelling.BooleanVariable;

/**
 * Représente une règle d'association composée d'une prémisse,
 * d'une conclusion, d'une fréquence et d'une confiance.
 */
public class AssociationRule {

    private Set<BooleanVariable> premise;
    private Set<BooleanVariable> conclusion;
    private float frequency;
    private float confidence;


    /**
     * Crée une règle d'association.
     *
     * @param premise ensemble d'items dans la prémisse
     * @param conclusion ensemble d'items dans la conclusion
     * @param frequency fréquence de la règle
     * @param confidence confiance de la règle
     */
    public AssociationRule(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, float frequency, float confidence) {
        this.premise = premise;
        this.conclusion = conclusion;
        this.frequency = frequency;
        this.confidence = confidence;
    }

    /** @return prémisse de la règle */
    public Set<BooleanVariable> getPremise() {
        return this.premise;
    }

    /** @return conclusion de la règle */
    public Set<BooleanVariable> getConclusion() {
        return this.conclusion;
    }

    /** @return fréquence de la règle */
    public float getFrequency() {
        return this.frequency;
    }

    /** @return confiance de la règle */
    public float getConfidence() {
        return this.confidence;
    }

    @Override
    public String toString() {
        return "Prémise = " + premise + "Conclusion = " + conclusion + "Frequence = " + frequency + "Confiance = " + confidence;
    }

    

    
    
}
