/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Displayed parsed java .class file to the terminal and displays the LCOM4 value for each class and the total CBO
 * value for all the classes entered in as arguments to the program.
 * Outputs DOT files for each class and an overall CBO DOT file to be used by the Graphviz Program.
 */
public class ClassFileParser
{
    /**
     * Start of execution of program.
     * @param args - The java .class files as arguments to the program that will be parsed by the program
     */
    public static void main(String[] args)
    {
        if(args.length >= 1)
        {
            try
            {
                List<ClassFile> classFilesList = new ArrayList<ClassFile>(); //list to hold all ClassFile objects
                GraphvizOutput graphvizOutput = new GraphvizOutput();
                for (int i = 0; i < args.length; i++)
                {
                    try
                    {
                        File file = new File(args[i]);
                        String path = file.getAbsolutePath(); //get absolute path of file, hence handles both paths and filenames
                        ClassFile cf = new ClassFile(path);
                        if (cf.getMajorVersion() >= 51.0)
                        {
                            PrintToTerminal printToTerminal = new PrintToTerminal();
                            printToTerminal.printToTerminal(cf); //print to terminal

                            classFilesList.add(cf); //add classfile to class file list if the class is version 51 or greater
                            boolean isInterface = false;
                            for (int j = 0; j < cf.getAccessFlags().size(); j++)
                            {
                                if (cf.getAccessFlags().get(j).toLowerCase().equals("interface")) //check if interface
                                {
                                    isInterface = true;
                                }
                            }
                            if (isInterface == false)
                            {
                                graphvizOutput.lCOM4Output(cf,file.getName()); //only output graphviz DOT files for files that are not interfaces
                            }
                        }
                    }
                    catch (NullPointerException e)
                    {
                        System.out.println("Error, Null Pointer while parsing file: " + args[i] + " Message: "+ e.getMessage()+ "\n");
                    }
                    catch (SecurityException e)
                    {
                        System.out.println("Error, File cannot be accessed" + e.getMessage());
                    }
                    catch (Exception e)
                    {
                        System.out.println("Error: "+ e.getMessage());
                    }

                }
                if (classFilesList.size()>0)
                {
                    try
                    {
                        CBO cbo = new CBO();
                        double cboValue = cbo.calculateCBO(classFilesList); //calculate cbo with the classfile list
                        System.out.println("\n\nOverall CBO: " + cboValue); //print cbo value to terminal
                        graphvizOutput.cboOutput(cbo); //output graphviz cbo file
                    }
                    catch (ArithmeticException e)
                    {
                        System.out.println("Error calculating CBO: "+ e.getMessage());
                    }
                }
            }
            catch (NullPointerException e)
            {
                System.out.println("Error, NullPointerException found: "+ e.getMessage());
            }
        }
        else
        {
            System.out.println("Usage: java ClassFileParser <class-file>");
        }
    }
}
