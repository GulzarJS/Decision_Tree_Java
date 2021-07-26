/*
 *  Created by Gulzar Safar on 11/13/2020
 */

package model;

import util.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Group {

    private String name;
    private ArrayList<ArrayList<Float>> instances;

    private int versicolor;
    private int virginica;
    private int setosa;
    private double entropy;


    public Group(String name) {
        this.instances  = new ArrayList<ArrayList<Float>>();
        this.versicolor = 0;
        this.virginica  = 0;
        this.setosa     = 0;
        this.entropy = 0.f;
        this.name = name;
    }

    // Function to get number of instances
    public int getGroupSize(){
        return instances.size();
    }


    // Setter function for instance variable
    public void initGroup(List<ArrayList<Float>> list){
        this.instances = new ArrayList<ArrayList<Float>>(list);
    }

    // Method to find occurencies of setosa versicolor and vircinica
    public void speciesOccurrence() {

        ListIterator<ArrayList<Float>> iterator = this.instances.listIterator();
        ArrayList<Float> instance = null;

        while (iterator.hasNext()) {

            instance = iterator.next();

            if (instance.get(4) == 0) setosa++;
            else if (instance.get(4) == 1) versicolor++;
            else if (instance.get(4)== 2) virginica++;
        }

        this.setEntropy();
    }

    // Method to find entropy of group
    private void setEntropy(){
            int groupSize = this.instances.size();
            double entropy = 0.f;

            try {
                double proba_occu_versicolor = (double)this.versicolor / (double)groupSize;
                double proba_occu_virginica = (double)this.virginica / (double) groupSize;
                double proba_occu_setosa = (double)this.setosa / (double) groupSize;

                entropy = proba_occu_versicolor * Utility.log2(proba_occu_versicolor) +
                          proba_occu_virginica * Utility.log2(proba_occu_virginica) +
                          proba_occu_setosa * Utility.log2(proba_occu_setosa);

            }catch (ArithmeticException e) {
                System.out.println(e.getMessage());
                System.err.println("There is no instance in Group");
            }

            this.entropy = -entropy;
    }


    // Getter method of entropy
    public double getEntropy(){
        return this.entropy;
    }


    // Method to print species occurrences of group
    public void printGroup(){
        System.out.println("Group " + this.name + " => ["+this.instances.size()+ "]  Setosa: "+ this.setosa +
                "   Versicolor: " + this.versicolor + "   Virginica: " + this.virginica +
                "   Entropy=" + this.entropy);
    }


}


