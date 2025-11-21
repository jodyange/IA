package blocksworld;

import java.util.List;
import java.util.Map;

import javax.swing.*;
import java.awt.*;

import bwmodel.BWState;
import bwmodel.BWStateBuilder;
import bwui.BWIntegerGUI;
import bwui.BWComponent;

import modelling.*;
import planning.Action;

public class BlocksWorldDisplay {
    
    
    private BlocksWorldVariables blocksWorld;
    private int n;
    private BWIntegerGUI gui;
    private BWComponent<Integer> component;
    private JFrame frame;
    private String plannerType;

    public BlocksWorldDisplay(int n, BlocksWorldVariables blocksWorld,String plannerType,String location){
        this.gui = new BWIntegerGUI(n);
        this.frame = new JFrame("BlocksWorld Actions Plan for : " + plannerType);
        this.blocksWorld = blocksWorld;
        this.n= n;
        this.plannerType = plannerType;
        setLocationFrame(location);
    }


    public void setLocationFrame(String location){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        switch (location) {
            case "topLeft":
                frame.setLocation(0, 0);
                break;
            case "topRight":
                frame.setLocation(screenWidth - frame.getWidth(), 0);
                break;
            case "bottomLeft":
                frame.setLocation(0, screenHeight - frame.getHeight());
                break;
            default:
                frame.setLocation(screenWidth - frame.getWidth(), screenHeight - frame.getHeight());
                break;
        }
    }


    public BWState<Integer> bwState(Map<Variable, Object> state){
        BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(n);
        for (int b = 0; b < n; b++) {
            Variable onB = blocksWorld.onVariables.get(b); // get instance of Variable for "on_b"
            int under = (int) state.get(onB);
            if (under >= 0) { // if the value is a block (as opposed to a stack)
                builder.setOn(b, under);
            }
        }
        return builder.getState();
    }

    public void displayState(Map<Variable, Object> state){
        BWState<Integer> bwState = bwState(state);
        if(this.component == null) {
            this.component = gui.getComponent(bwState);
        }else{
            this.component.setState(bwState);
        }
        
        frame.add(component);
        frame.pack();
        frame.setSize(900,550);
        frame.setVisible(true);
    }

    public void setFrame(JFrame f){
        this.frame=f;
    }

    public void showPlan(Map<Variable, Object> initialState, List<Action> plan){

        System.out.println("******************************************SIMULATION OF "+plannerType+"*********************************************************");
        
        displayState(initialState);

        Map<Variable,Object> state = initialState;
        for (Action a: plan) {
            try { Thread.sleep(1_100); }
            catch (InterruptedException e) { e.printStackTrace(); }
            state=a.successor(state);
            component.setState(bwState(state));
        }
        System.out.println("Simulation of plan: done.");
       
    }

 
}
