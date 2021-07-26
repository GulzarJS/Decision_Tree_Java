/*
 *  Created by Gulzar Safar on 11/13/2020
 */

package model;

import util.Utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DecisionTree {

    private ArrayList<ArrayList<Float>> allData;
    private int sortingAttribute = 0;
    private double entropy = 0;

    private int totalVersicolor = 0;
    private int totalVirginica  = 0;
    private int totalSetosa     = 0;

    private ArrayList<Group> groups;


    // Constructor for DecisionTree => Method to create decision tree
    public DecisionTree(String filename, String separator, int nbAttributes, ArrayList<String> nameOfGroups){
        this.allData = new ArrayList<ArrayList<Float>>();
        this.groups  = new ArrayList<Group>(nameOfGroups.size());
        for (int i = 0; i < nameOfGroups.size(); i++) this.groups.add(new Group(nameOfGroups.get(i)));


        // Getting data from file
        ArrayList<ArrayList<String>> data = Utility.importCSV(filename, separator);
        for (ArrayList<String> entry : data) {
            ArrayList<Float> attributes = new ArrayList<Float>();
            int i;

            // Get first four attributes (SL SW PL PW)
            for (i = 0; i < nbAttributes-1; i++)
                attributes.add(Float.parseFloat(entry.get(i)));

            // Get label => species
            switch (entry.get(i)) {
                case "Iris-setosa":
                    attributes.add(0.f); // setosa

                    totalSetosa++;
                    break;
                case "Iris-versicolor":
                    attributes.add(1.f); // versicolor

                    totalVersicolor++;
                    break;
                case "Iris-virginica":
                    attributes.add(2.f); // virginica

                    totalVirginica++;
                    break;
                default:
                    throw new RuntimeException("There was an error retrieving the label " + entry.get(i));
            }


            // Add instance
            this.allData.add(attributes);
        }
    }

    public void setSortingAttribute(int sortingAttributes) {
        this.sortingAttribute = sortingAttributes;
    }


    // Method to find entropy of decision tree
    private void setEntropy(){
        double entropy = 0.0;

        try {

            double proba_occu_versicolor = (double) this.totalVersicolor / (double) allData.size();
            double proba_occu_virginica = (double) this.totalVirginica / (double) allData.size();
            double proba_occu_setosa = (double) this.totalSetosa / (double) allData.size();

            entropy =  (proba_occu_versicolor * Utility.log2(proba_occu_versicolor)) +
                       (proba_occu_virginica * Utility.log2(proba_occu_virginica)) +
                       (proba_occu_setosa * Utility.log2(proba_occu_setosa));
        }catch (ArithmeticException e){
            System.out.println(e.getMessage());
            System.err.println("There is no data in Decision Tree");
        }

        this.entropy = -entropy;
    }

    // Method to compare instances regarding given attribute
    private static Comparator<ArrayList<Float>> instanceComparator(int sortingAttribute){
        Comparator<ArrayList<Float>> comparator = new Comparator<ArrayList<Float>>(){
            @Override
            public int compare(ArrayList<Float> i1, ArrayList<Float> i2){


                return i1.get(sortingAttribute).compareTo(i2.get(sortingAttribute));
            }
        };
        return comparator;
    }

    public void sortAllData(){
        Collections.sort(this.allData, instanceComparator(this.sortingAttribute));
    }



    // Method to divide data to 3 parts and import them into groups
    public void setGroups(){
        int start = 0;
        int nbInstances = allData.size()/groups.size(); //50;
        for (Group g: groups){
            g.initGroup(this.allData.subList(start, start+nbInstances));
            g.speciesOccurrence();
            start += nbInstances;
        }
        this.setEntropy();
    }

    // Method to find Discriminative power of Decision Tree
    public double disc() {
        double disc = 0.0;
        for(Group g: groups){

            double prob = (double) g.getGroupSize() / (double) this.allData.size();
            disc += (prob * g.getEntropy());

        }

        disc = this.entropy - disc;
        return disc;
    }



    public double getEntropy(){ return this.entropy; }


    public void printAllData() {
        for (ArrayList<Float> instance: this.allData){
            System.out.println(instance);
        }
    }


    public void printGroups(){
        System.out.println("Data entropy: "+this.entropy + "  Disc=" + this.disc());
        for (Group g: this.groups)
            g.printGroup();
    }
}
