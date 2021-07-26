/*
 *  Created by Gulzar Safar on 11/14/2020
 */

package model;

import java.util.ArrayList;

public class Test {


    public static void main(String[] args) {

        DecisionTree dt = null;

        for (int i = 0; i < 4 /*nb of attributes*/; i++){
            System.out.println("Attributes: 0/SL 1/SW 2/PL 3/PW\n***Attribute #"+i);
            ArrayList<String> nameOfGroups =  new ArrayList<String>();
            nameOfGroups.add("Short");
            nameOfGroups.add("Average");
            nameOfGroups.add("Long");

            dt = new DecisionTree("src/dataset/iris.data",
                    ",", /* separator */
                    5 /* nb of columns in the data set */,
                    nameOfGroups /* name of groups (short, average, long) */);
            dt.setSortingAttribute(i); // i is the attribute we want to determine the discriminative power of
            dt.sortAllData();
            dt.setGroups();
            dt.printGroups();
        }
    }

}
