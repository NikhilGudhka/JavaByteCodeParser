/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

 /**
 * Prints parsed classfile information contents to terminal
 */
public class PrintToTerminal
{
  /**
  * Method to print to terminal for each class file
  */
    public void printToTerminal(ClassFile cf)
    {
        System.out.println("===================================================================================================================================");
        System.out.println("\t\t\t\t\t" + cf.getFilename());  //print class file name
        System.out.println();
        /*Print the class access flags*/
        for (int i = 0; i < cf.getAccessFlags().size(); i++)
        {
            System.out.print(cf.getAccessFlags().get(i) + " ");
        }

        System.out.print("class " + cf.getThisClass() + " "); // print the class name after printing "class "

        System.out.print("extends " + cf.getSuperClass() + " "); // print the super class name after printing "extends "

        /*loop for the number of interfaces*/
        for (int i = 0; i < cf.getInterfaces().size(); i++)
        {
          /*print "implements " once before printing interface class names*/
            if (i == 0)
            {
                System.out.print("implements ");
            }
            /*print the last interface class and dont print the ","*/
            if ((cf.getInterfaceCount() - 1) == i)
            {
                System.out.print(cf.getInterfaces().get(i));
            }
            /*print the interface classes*/
            else
            {
                System.out.print(cf.getInterfaces().get(i) + ", ");
            }
        }
        System.out.println("\n");

        System.out.println("Fields: ");
        for (int i = 0; i < cf.getFieldCount(); i++) //loop for the number of fields
        {
            String s = "\t";
            for (int j = 0; j < cf.getFields().get(i).getAccessFlags().size(); j++) //for each field add the access flags to string s
            {
                s += String.format(cf.getFields().get(i).getAccessFlags().get(j) + " ");
            }
            s += String.format(cf.getFields().get(i).getDescriptorType() + " "); //add field descriptorType to string s
            s += String.format(cf.getFields().get(i).getFieldName() + ";"); //add field name to string s
            System.out.println(s); //print the s string
        }
        System.out.println("\n");

        System.out.println("Methods: ");
        for (int i = 0; i < cf.getMethodCount(); i++)  //loop for the number of methods
        {
            String s = "\t";
            for (int j = 0; j < cf.getMethods().get(i).getAccessFlags().size(); j++) //for each method add the access flags to string s
            {
                s += String.format(cf.getMethods().get(i).getAccessFlags().get(j) + " ");
            }
            s += String.format(cf.getMethods().get(i).getReturnType() + " "); //add method retuenType to string s
            s += String.format(cf.getMethods().get(i).getNameIndexData() + " "); //add method name to string s

            int methodParameterSIZECount = cf.getMethods().get(i).getParameters().size(); //get the method parameter count
            /*add to sting s the method parameters in the correct format*/
            for (int j = 0; j < methodParameterSIZECount; j++)
            {
                if (j == 0)
                {
                    s += String.format(cf.getMethods().get(i).getParameters().get(0));
                }
                else if (j == cf.getMethods().get(i).getParameters().size() - 1)
                {
                    s += String.format(cf.getMethods().get(i).getParameters().get(cf.getMethods().get(i).getParameters().size() - 1));
                }
                else if (j == cf.getMethods().get(i).getParameters().size() - 2)
                {
                    s += String.format(cf.getMethods().get(i).getParameters().get(j));
                }
                else
                {
                    s += String.format(cf.getMethods().get(i).getParameters().get(j) + ", ");
                }

            }

            System.out.println(s); //print string s
        }

        /*check if there was no error parsing the classfile*/
        if (cf.isSuccess())
        {
            if (cf.getAccessFlags().contains("interface")) //check if class is an interface
            {
                System.out.println("\nLCOM4: Is Interface, Cannot calculate LCOM4 for interfaces as per assignment specification \n");
            }
            else //for non interface classes print LCOM4 value
            {
                System.out.println("\nLCOM4: " + cf.getLcom4Value() + "\n");
            }
        }
        else
        {
            System.out.println("\nLCOM4: Parsing Error Therefore cannot calculate LCOM4 for this class \n");
        }
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println();
    }
}
