/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Calculates Coupling Between Objects(CBO) for all the java .class files passed in as arguments to the program.
 */
public class CBO
{
    private double links = 0;
    private int numOfClasses = 0;
    private List<ArrayList<String>> referList;
    private List<String> allClassnames;

    /**
     * Calculates the CBO for all java .class files.
     * @param cf - List of ClassFile objects
     * @return - a double value which is the cbo value
     */
    public double calculateCBO(List<ClassFile> cf) throws ArithmeticException
    {
        String currentClassName;
        referList = new ArrayList<ArrayList<String>>();
        allClassnames = new ArrayList<String>();
        /*Adds all class Names in the cf List to an ArrayList called allClassnames*/
        for (int i = 0; i < cf.size(); i++)
        {
            allClassnames.add(cf.get(i).getThisClass());
        }
        numOfClasses = allClassnames.size(); //get num of classes using the list size
        for (int i = 0; i < cf.size(); i++) //for each class file object in the cf list
        {
            List<String> list = new ArrayList<String>();
            currentClassName = cf.get(i).getThisClass();
            list.add(currentClassName);
            for (int j = 0; j < cf.get(i).getMethods().size(); j++) //for each class in cf list get the number of methods
            {
                for (int k = 0; k < cf.get(i).getMethods().get(j).getAttributes().size(); k++) //get the number of attributes of these methods
                {
                    if (cf.get(i).getMethods().get(j).getAttributes().get(k) instanceof Code) //check if instance of Code
                    {
                        /*Get the class names for these methods*/
                        List<String> methodClasses = ((Code)(cf.get(i).getMethods().get(j).getAttributes().get(k))).getMethodCallsClassnames();
                        for (int l = 0; l < methodClasses.size(); l++)
                        {
                            /*check if the class is in the allClassnames list and is not already in the list of classes linked together for the current class*/
                            if ((allClassnames.contains(methodClasses.get(l))) && (!(list.contains(methodClasses.get(l)))))
                            {
                                boolean doesExist=false;
                                for (int m = 0; m < referList.size() ; m++)
                                {
                                    /*check if class exists in the refer list, if it does set doesExist to true hence no duplicate values in the refer list*/
                                    if((referList.get(m).get(0).equals(methodClasses.get(l)))&&(referList.get(m).contains(currentClassName)))
                                    {
                                        doesExist=true;
                                    }
                                }
                                if(doesExist==false) //only add to the list if it doesn't exist
                                {
                                    list.add(methodClasses.get(l));
                                }
                            }
                        }                                                                                                           
                    }
                }
            }
            for (int j = 0; j < cf.get(i).getFields().size() ; j++)
            {
                if ((allClassnames.contains(cf.get(i).getFields().get(j).getDescriptorType())) && (!(list.contains(cf.get(i).getFields().get(j).getDescriptorType()))))
                {
                    boolean doesExist=false;
                    for (int m = 0; m < referList.size() ; m++)
                    {
                                    /*check if class exists in the refer list, if it does set doesExist to true hence no duplicate values in the refer list*/
                        if((referList.get(m).get(0).equals(cf.get(i).getFields().get(j).getDescriptorType()))&&(referList.get(m).contains(currentClassName)))
                        {
                            doesExist=true;
                        }
                    }
                    if(doesExist==false) //only add to the list if it doesn't exist
                    {
                        list.add(cf.get(i).getFields().get(j).getDescriptorType());
                    }
                }
            }
            links+=list.size()-1; //number of links is list size -1
            referList.add((ArrayList<String>) list); //add to the refer list
        }
        return (links/numOfClasses); //return cbo value
    }

    public double getLinks()
    {
        return links;
    }

    public int getNumOfClasses()
    {
        return numOfClasses;
    }

    public List<ArrayList<String>> getReferList()
    {
        return referList;
    }

    public List<String> getAllClassnames()
    {
        return allClassnames;
    }
}
