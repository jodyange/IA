package datamining;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import modelling.BooleanVariable;

/**
 * Extracteur brute-force des règles d'association fréquentes et précises.
 * Énumère toutes les prémisses possibles pour chaque itemset fréquent.
 */
public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner{

    /**
     * Crée un extracteur brute-force pour la base donnée.
     *
     * @param database base de données utilisée
     */
    public BruteForceAssociationRuleMiner(BooleanDatabase database){
        super(database);

    }

    /**
     * Extrait toutes les règles d'association satisfaisant
     * les seuils de fréquence et de confiance.
     *
     * @param minFrequency fréquence minimale
     * @param minConfidence confiance minimale
     * @return ensemble des règles extraites
     */
    @Override
    public Set<AssociationRule> extract(float minFrequency, float minConfiance) {
        Set<AssociationRule> rules = new HashSet<>();
        Apriori apriori = new Apriori(this.database);
        Set<Itemset> frequentItemsets = apriori.extract(minFrequency);
        for (Itemset itemset : frequentItemsets) {
            Set<BooleanVariable> domain = new HashSet<>();
            domain.addAll(itemset.getItems());
            Set<Set<BooleanVariable>> subDomains = BruteForceAssociationRuleMiner.allCandidatePremises(domain);
            for (Set<BooleanVariable> y : subDomains) {
                //On calcul x qui est domain privé de y
                SortedSet<BooleanVariable> x = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
                x.addAll(domain);
                x.removeAll(y);
                //on veut calculer la confiance de la règle x --> y
                //noter que x union y donne domain
                float xFrequency = AbstractAssociationRuleMiner.frequency(x, frequentItemsets);
                float itemFrequency =  itemset.getFrequency();
                float ruleConfiance = itemFrequency/xFrequency;
                if(ruleConfiance>=minConfiance){
                    AssociationRule rule = new AssociationRule(x, y,itemFrequency, ruleConfiance);
                    rules.add(rule);
                }
            }
       }
       return rules;
    }

    /**
     * Génère tous les sous-ensembles non vides et stricts d'un ensemble d'items.
     *
     * @param items ensemble d'items
     * @return ensemble des sous-ensembles candidats
     */
    public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> domain){
        Set<Set<BooleanVariable>> subDomains = new HashSet<>();

        if(domain.size()<=1 || domain.size()==0){
            return subDomains;
        }

        //niveau initial: subDomain de taille 1
        Set<SortedSet<BooleanVariable>> levelK = new HashSet<>();
        for (BooleanVariable item: domain) {
            SortedSet<BooleanVariable> i = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
            i.add(item);
            levelK.add(i);
            subDomains.addAll(levelK);
        }
        Set<SortedSet<BooleanVariable>> nextLevelK = new HashSet<>();

        while (levelK.size() > 1) {
            nextLevelK.clear();

            //combinasion de 2 ensembles du niveau courant
            for (SortedSet<BooleanVariable> item1 : levelK){
                for (SortedSet<BooleanVariable> item2 : levelK){
                    if (!item1.equals(item2)){
                        SortedSet<BooleanVariable> combination = Apriori.combine(item1, item2);

                        if(combination != null && !combination.equals(domain) && !nextLevelK.contains(combination) ){
                            nextLevelK.add(combination);
                        }
                    }
                }
            }

            //Fin de calcul des subDomains de niveau suivant
            //On met tous les domains calculés dans subDomains
            //levelK devient les domains calculés et on reprend le calcul des domaines de niveau suivant

            //Si on a trouvé de nouveaux sous-domaines
            if (!nextLevelK.isEmpty()){
                subDomains.addAll(nextLevelK);
                levelK = new HashSet<>(nextLevelK);  

            }else{
                break;
            }
            
        }
        return subDomains;
    }


    
}
