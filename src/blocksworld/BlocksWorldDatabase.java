package blocksworld;

import java.util.*;

import modelling.*;

/**
 * Représente une base de données booléenne d’états du monde des blocs.
 *
 * Cette classe est utilisée dans l’exercice d’extraction de connaissances (Apriori,
 * règles d’association).
 */
public class BlocksWorldDatabase {

    private int nbBlocks,nbPiles;
    private Set<BooleanVariable> database;


    /**
     * Construit un modèle de base de données pour un monde des blocs.
     *
     * Le constructeur génère toutes les variables booléennes nécessaires     *
     * @param nbBlocks nombre de blocs
     * @param nbPiles nombre de piles
     */
    public BlocksWorldDatabase(int nbBlocks,int nbPiles){
        this.nbBlocks=nbBlocks;
        this.nbPiles = nbPiles;
        this.database = new HashSet<>();
     

        generateVariables();
    }


    /**
     * Génère toutes les variables booléennes :
     * Les variables sont stockées dans des ensembles et tables internes.
     */
     private void generateVariables(){

        for (int j = 0; j < nbBlocks; j++) {

            //onbb' variables
            for (int bPrime = 0; bPrime < nbBlocks; bPrime++) {
                
                if(j != bPrime){
                    database.add(new BooleanVariable("on" + j + bPrime));
                }
            }

            //on-tablebp et freep variables
            for (int i = 0; i <= nbPiles; i++) {
                database.add(new BooleanVariable("on-table" + j + i));
                database.add(new BooleanVariable("free" + i));
            }

            //fixedb variables
            database.add(new BooleanVariable("fixed" + j));
            
        }
    }
    /**
     * Retourne toutes les variables booléennes créées pour ce modèle de base de données.
     * @return les Booleanvariables de ma database 
     */
    public Set<BooleanVariable> getVariables(){ return database;}


    /**
     * Convertit un état concret du monde des blocs en une instance booléenne.
     * @param piles liste des piles
     * @return une instance du monde des blocs 
     */
    public Set<BooleanVariable> getInstance(List<List<Integer>> piles){

        Set<BooleanVariable> instance = new HashSet<>();

        for (int p=0;p<piles.size();p++) {

            List<Integer> pile = piles.get(p);

            if(pile.isEmpty()){
                instance.add(new BooleanVariable("free"+p));
            }
            else{
                for (int b = 0; b < pile.size(); b++) {

                        int blockB = pile.get(b);
                    
                        if(b==0){
                            instance.add(new BooleanVariable("on-table"+blockB+"_"+p));
                        }else{

                            int blockBPrime = pile.get(b-1);
                            instance.add(new BooleanVariable("on"+blockB+"_"+blockBPrime));
                        }
                        
                        if(b < pile.size()-1){
                        instance.add(new BooleanVariable("fixed"+blockB));
                        }
                    }     
                
            }
        }
        return instance;
    }
}
