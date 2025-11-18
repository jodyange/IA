package datamining;

import java.util.Set;

import modelling.BooleanVariable;

public class AssociationRule {

    private Set<BooleanVariable> premise;
    private Set<BooleanVariable> conclusion;
    private float frequency;
    private float confidence;



    public AssociationRule(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, float frequency, float confidence) {
        this.premise = premise;
        this.conclusion = conclusion;
        this.frequency = frequency;
        this.confidence = confidence;
    }



    public Set<BooleanVariable> getPremise() {
        return this.premise;
    }

    public Set<BooleanVariable> getConclusion() {
        return this.conclusion;
    }


    public float getFrequency() {
        return this.frequency;
    }


    public float getConfidence() {
        return this.confidence;
    }

    @Override
    public String toString() {
        return "Prémise = " + premise + "Conclusion = " + conclusion + "Frequence = " + frequency + "Confiance = " + confidence;
    }

    

    
    
}
