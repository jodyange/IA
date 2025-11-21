package blocksworld;

import java.util.*;
import planning.*;
import modelling.*;

/**
 * Représente une action dans le monde des blocs.
 *
 * Une action correspond à un déplacement d’un bloc vers une nouvelle position
 *
 * Cette classe ne valide pas une action par elle-même, c'est un planificateur qui
 * viendra fait executer l'action de deplacement
 */
public class BlocksWorldAction extends BlocksWorldVariables{
    
    protected Set<Action> actions;

    /**
     * Construit une action du monde des blocs.
     *
     * @param nbBlocks le nombre de blocks
     * @param nbPiles le nombre de piles voulus d’effets atomiques
     * @param name nom lisible de l’action
     */
    public BlocksWorldAction(int nbBlocks, int nbPiles){
        super(nbBlocks, nbPiles);
         this.actions = new HashSet<>();
        buildAllActions();
    }

    private void buildAllActions() {
        addType1Actions();
        addType2Actions();
        addType3Actions();
        addType4Actions();
    }

    public Set<Action> getAllActions() {
        return actions;
    }

    /**
     * Type 1 :
     * déplacer un bloc b du dessus d’un bloc b' vers le dessus d’un bloc b''
     *
     * Préconditions :
     *  - on_b = b'
     *  - fixed_b = false
     *  - fixed_b'' = false
     *
     * effectsets :
     *  - on_b := b''
     *  - fixed_b' := false
     *  - fixed_b'' := true
     */
    private void addType1Actions() {
        int n = onVariables.size();

        for (int b = 0; b < n; b++) {
            Variable onB = onVariables.get(b);
            Variable fixedB = fixedVariables.get(b);
            for (int bPrime = 0; bPrime < n; bPrime++) {
                if (b != bPrime) {
                    for (int bDoublePrime = 0; bDoublePrime < n; bDoublePrime++) {
                        if (bDoublePrime == b || bDoublePrime == bPrime) continue;

                        Variable fixedBPrime = fixedVariables.get(bPrime);
                        Variable fixedbDoublePrime = fixedVariables.get(bDoublePrime);

                        Map<Variable,Object> preconditions = new HashMap<>();
                        Map<Variable,Object> effects = new HashMap<>();

                        // Préconditions
                        preconditions.put(onB, bPrime);          // b est sur b'
                        preconditions.put(fixedB, false);        // b est déplaçable
                        preconditions.put(fixedbDoublePrime, false);     // b'' est libre

                        // effects
                        effects.put(onB, bDoublePrime);             // b va sur b''
                        effects.put(fixedBPrime, false);    // b' devient sans bloc au-dessus
                        effects.put(fixedbDoublePrime, true);
                        
                        actions.add(new BasicAction(preconditions, effects, 1)); 
                            }
                    }
                }
            }
        }    

    /**
     * Type 2 :
     * déplacer un bloc b du dessus d’un bloc b' vers une pile vide p
     *
     * Préconditions :
     *  - on_b = b'
     *  - fixed_b = false
     *  - free_p = true
     *
     * effectsets :
     *  - on_b := -(p+1)
     *  - fixed_b' := false
     *  - free_p := false
     */
    private void addType2Actions() {
        int n = onVariables.size();

        for (int b = 0; b < n; b++) {
            Variable onB = onVariables.get(b);
            Variable fixedB = fixedVariables.get(b);
            for (int bPrime = 0; bPrime < n; bPrime++) {
                if (b != bPrime) {

                    Variable fixedBPrime = fixedVariables.get(bPrime);
                    for (int p = 0; p < nbPiles; p++) {
                        Variable freeP = freeVariables.get(p);

                        Map<Variable,Object> preconditions = new HashMap<>();
                        Map<Variable,Object> effects = new HashMap<>();

                        // Préconditions
                        preconditions.put(onB, bPrime);          // b est sur b'
                        preconditions.put(fixedB, false);        // b est déplaçable
                        preconditions.put(freeP, true);          // pile p vide

                        // effectsets
                        effects.put(onB, -(p + 1));         // b va sur la pile p
                        effects.put(fixedBPrime, false);    // b' libéré
                        effects.put(freeP, false);          // pile p non vide

                        actions.add(new BasicAction(preconditions, effects, 1)); 
                    }
                }
            }
        }
    }

    /**
     * Type 3 :
     * déplacer un bloc b du dessous d’une pile p vers le dessus d’un bloc b'
     *
     * (Les contraintes garantissent que si b est "en dessous d'une pile p",
     * alors p ne contient QUE b.)
     *
     * Préconditions :
     *  - on_b = -(p+1)
     *  - fixed_b = false
     *  - free_p = false   (p contient b)
     *  - fixed_b' = false (b' libre)
     *
     * effectsets :
     *  - on_b := b'
     *  - free_p := true   (pile p devient vide)
     *  - fixed_b' := true
     */
    private void addType3Actions() {
        int n = onVariables.size();

        for (int b = 0; b < n; b++) {
            Variable onB = onVariables.get(b);
            Variable fixedB = fixedVariables.get(b);
            for (int p = 0; p < nbPiles; p++) {
                Variable freeP = freeVariables.get(p);
                for (int bPrime = 0; bPrime < n; bPrime++) {
                    if (b != bPrime) {

                        Variable fixedBPrime = fixedVariables.get(bPrime);

                        Map<Variable,Object> preconditions = new HashMap<>();
                        Map<Variable,Object> effects = new HashMap<>();

                        // Préconditions
                        preconditions.put(onB, -(p + 1));       // b est sur la pile p
                        preconditions.put(fixedB, false);       // b déplaçable
                        preconditions.put(freeP, false);        // pile p non vide (contient b)
                        preconditions.put(fixedBPrime, false);  // b' libre

                        // effectsets
                        effects.put(onB, bPrime);          // b va sur b'
                        effects.put(freeP, true);          // pile p devient vide
                        effects.put(fixedBPrime, true);    // b' bloqué

                        actions.add(new BasicAction(preconditions, effects, 1)); 
                    }
                }
            }
        }
    }

    /**
     * Type 4 :
     * déplacer un bloc b du dessous d’une pile p vers une pile vide p'
     *
     * Préconditions :
     *  - on_b = -(p+1)
     *  - fixed_b = false
     *  - free_p = false   (p contient b)
     *  - free_p' = true   (p' vide)
     *
     * effectsets :
     *  - on_b := -(p'+1)
     *  - free_p := true   (p devient vide)
     *  - free_p' := false (p' devient non vide)
     */
    private void addType4Actions() {
        int n = onVariables.size();

        for (int b = 0; b < n; b++) {
            Variable onB = onVariables.get(b);
            Variable fixedB = fixedVariables.get(b);
            for (int p = 0; p < nbPiles; p++) {
                Variable freeP = freeVariables.get(p);
                for (int p2 = 0; p2 < nbPiles; p2++) {
                    if (p != p2) {

                        Variable freeP2 = freeVariables.get(p2);

                        Map<Variable,Object> preconditions = new HashMap<>();
                        Map<Variable,Object> effects = new HashMap<>();

                        // Préconditions
                        preconditions.put(onB, -(p + 1));   // b est sur p
                        preconditions.put(fixedB, false);   // b déplaçable
                        preconditions.put(freeP, false);    // p non vide
                        preconditions.put(freeP2, true);    // p' vide

                        // effectsets
                        effects.put(onB, -(p2 + 1));  // b va sur p'
                        effects.put(freeP, true);     // p devient vide
                        effects.put(freeP2, false);   // p' devient non vide

                        actions.add(new BasicAction(preconditions, effects, 1)); 
                    }
                }
            }
        }
    }

}
