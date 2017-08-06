/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to output DOT files using the data from parsing all the java .class files
 * DOT file output is as per the Graph visualization software (Graphviz)
 */
public class GraphvizOutput
{
    /**
     * Writes each java .class file passed in as arguments into a DOT file with the LCOM4 value calculated for the .class file.
     * @param cf - classfile object
     */
    public void lCOM4Output(ClassFile cf,String fileName)
    {
        try
        {
            int count = 1;
            List<String> methodsAndFields = new ArrayList<>();
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName + ".dot")); //write new file with classfile name and ending with .dot
            bw.write("graph G\n"); //initial output to file
            bw.write("{\n");
            bw.write("\toverlap=scalexy;\n");
            bw.write("\tlabelloc=\"t\";\n");
            bw.write("\tlabel=\"");
            /*loop for number of class access flags and write them to the file */
            for (int i = 0; i < cf.getAccessFlags().size(); i++)
            {
                bw.write(cf.getAccessFlags().get(i) + " ");
            }
            bw.write("class " + cf.getThisClass() + " \\n "); //write "class" after access flags and write the class name after
            bw.write("LCOM4 = " + cf.getLcom4Value() + "\";" + "\n"); //write the LCOM4 value of this .class

            bw.write("\n\tnode[shape=ellipse,color=blue]"); //type of node used to represent the fields below
            /*write each field in the .class file in the graphviz format, along with an index number*/
            for (int i = 0; i < cf.getFields().size(); i++)
            {
                bw.write("\n");
                bw.write("\t\t" + (count) + "[label=\"" + cf.getFields().get(i).getFieldName() + "\"]");
                methodsAndFields.add(cf.getFields().get(i).getFieldName().concat(cf.getFields().get(i).getDescriptorData()));
                count++;
            }
            bw.write(";\n"); //write semi colon to the last field

            bw.write("\n\tnode[shape=rectangle,color=black]"); //type of node ised to represent the methods below
            /*write each method in the .class file in the graphviz format, along with an index number*/
            for (int i = 0; i < cf.getMethods().size(); i++)
            {
                /* if condition to write method apart from init, clinit, tostring, equals*/
                if (!((cf.getMethods().get(i).getNameIndexData().toLowerCase().contains("<init>")) || (cf.getMethods().get(i).getNameIndexData().toLowerCase().contains("<clinit>"))
                        || (cf.getMethods().get(i).getNameIndexData().toLowerCase().contains("tostring")) || (cf.getMethods().get(i).getNameIndexData().toLowerCase().contains("equals"))))
                {
                    bw.write("\n");
                    bw.write("\t\t" + (count) + "[label=\"" + cf.getMethods().get(i).getNameIndexData());
                    /* write the parameters for each method to the file*/
                    for (int j = 0; j < cf.getMethods().get(i).getParameters().size(); j++)
                    {
                        if (j == 0)
                        {
                            bw.write(cf.getMethods().get(i).getParameters().get(0));
                        }
                        else if (j == cf.getMethods().get(i).getParameters().size() - 1)
                        {
                            bw.write(cf.getMethods().get(i).getParameters().get(cf.getMethods().get(i).getParameters().size() - 1));
                        }
                        else if (j == cf.getMethods().get(i).getParameters().size() - 2)
                        {
                            bw.write(cf.getMethods().get(i).getParameters().get(j));
                        }
                        else
                        {
                            bw.write(cf.getMethods().get(i).getParameters().get(j) + ", ");
                        }

                    }
                    bw.write("\"]");
                    methodsAndFields.add(cf.getMethods().get(i).getNameIndexData().concat(cf.getMethods().get(i).getDescriptorData()));
                    count++;
                }
            }
            bw.write(";\n\n"); //write semi colon to the last method

            /*for each method, find what fields and methods refer to each other and write them to the file with a "--" in between them to denote that one refers to another*/
            for (int i = 0; i < cf.getMethods().size(); i++)
            {
                String methodNameAndType = cf.getMethods().get(i).getNameIndexData().concat(cf.getMethods().get(i).getDescriptorData()); //store the current method name and type to methodNameAndType
                List<String> methodsFieldsNameAndType = new ArrayList<String>();
                for (int j = 0; j < cf.getMethods().get(i).getAttributes().size(); j++) //get the number of attributes for this method
                {
                    if (cf.getMethods().get(i).getAttributes().get(j) instanceof Code) //check if instance of Code
                    {
                        methodsFieldsNameAndType = new ArrayList<String>();
                        for (int k = 0; k < ((Code) (cf.getMethods().get(i).getAttributes().get(j))).getfieldCallsName().size(); k++) //get the number of fields referenced by this method
                        {
                            String fieldNameString = (((Code) (cf.getMethods().get(i).getAttributes().get(j))).getfieldCallsName()).get(k); //get the field name
                            String fieldTypeString = (((Code) (cf.getMethods().get(i).getAttributes().get(j))).getfieldCallsType()).get(k); //get the field type
                            /*check if field exists in the methodsFieldsNameAndType list, if it doesn't add it to the list, hence no duplicate values in the methodsFieldsNameAndType list*/
                            if (!methodsFieldsNameAndType.contains(fieldNameString.concat(fieldTypeString)))
                            {
                                methodsFieldsNameAndType.add(fieldNameString.concat(fieldTypeString));
                            }
                        }
                        for (int k = 0; k < ((Code) (cf.getMethods().get(i).getAttributes().get(j))).getmethodCallsName().size(); k++) //get the number of methods referenced by this method
                        {
                            String methodNameString = (((Code) (cf.getMethods().get(i).getAttributes().get(j))).getmethodCallsName()).get(k); //get the method name
                            String methodTypeString = (((Code) (cf.getMethods().get(i).getAttributes().get(j))).getMethodCallsType()).get(k); //get the method type
                            /*Check if the method name is not either of "<init>","<clinit>"","tostring","equals"*/
                            if (!((methodNameString.toLowerCase().contains("<init>")) || (methodNameString.toLowerCase().contains("<clinit>")) || (methodNameString.toLowerCase().contains("tostring")) || (methodNameString.toLowerCase().contains("equals"))))
                            {
                              /*check if method exists in the methodsFieldsNameAndType list, if it doesn't add it to the list, hence no duplicate values in the methodsFieldsNameAndType list*/
                                if (!methodsFieldsNameAndType.contains(methodNameString.concat(methodTypeString)))
                                {
                                    methodsFieldsNameAndType.add(methodNameString.concat(methodTypeString));
                                }
                            }
                        }
                    }
                }
                /*Check if this method name is not either of "<init>","<clinit>"","tostring","equals"*/
                if (!((cf.getMethods().get(i).getNameIndexData().toLowerCase().contains("<init>")) || (cf.getMethods().get(i).getNameIndexData().toLowerCase().contains("<clinit>"))
                        || (cf.getMethods().get(i).getNameIndexData().toLowerCase().contains("tostring")) || (cf.getMethods().get(i).getNameIndexData().toLowerCase().contains("equals"))))
                {
                    int index = 0;
                    /*loop over the methodsAndFields list to get the index of this method in the list*/
                    for (int j = 0; j < methodsAndFields.size(); j++)
                    {
                        if (methodsAndFields.get(j).equals(methodNameAndType))
                        {
                            index = j + 1;
                        }
                    }
                    /*loop over the methodsAndFields list to get the index of the methods and fields linked with this method and print to file*/
                    for (int j = 0; j < methodsFieldsNameAndType.size(); j++)
                    {
                        for (int k = 0; k < methodsAndFields.size(); k++)
                        {
                          /*check if the method or field exists in the methodsAndFields list*/
                            if (methodsFieldsNameAndType.get(j).equals(methodsAndFields.get(k)))
                            {
                                bw.write("\t" + index + " -- " + (k + 1) + ";\n");
                            }
                        }
                    }
                }
            }
            bw.write("}\n");
            bw.flush();   //make sure writing has completed
            bw.close();   //close the file
        }
        catch (IOException e)
        {
            System.out.println("Error while writing lCOM4 to file: " + e.getMessage());
        }
    }

    /**
     * writes the CBO into a DOT file with the CBO value calculated for all classes
     * @param cbo -  object that holds the links between classes and the CBO
     */
    public void cboOutput(CBO cbo)
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter("CBO.dot")); //write new CBO.dot file
            bw.write("graph G\n"); //initial output to file
            bw.write("{\n");
            bw.write("\toverlap=scalexy;\n");
            bw.write("\tlabelloc=\"t\";\n");
            bw.write("\tlabel=\"CBO = " + cbo.getLinks() + "/" + cbo.getNumOfClasses() + " = " + (cbo.getLinks() / cbo.getNumOfClasses() + "\"\n"));

            bw.write("\n\tnode[shape=rectangle]");
            /*loop for the number of classes and write them to file*/
            for (int i = 0; i < cbo.getAllClassnames().size(); i++)
            {
                bw.write("\n");
                bw.write("\t" + (i + 1) + " [label=\"" + cbo.getAllClassnames().get(i) + "\"]");
            }
            bw.write(";\n\n"); //write semi colon to the last class

            /*for each class, find what classes refer to each other and write them to the file with a "--" in between them to denote that one refers to another*/
            for (int i = 0; i < cbo.getReferList().size(); i++)
            {
                int index = 0;
                /*loop over the AllClassnames list to get the index of this class in the list*/
                for (int k = 0; k < cbo.getAllClassnames().size(); k++)
                {
                    if (cbo.getReferList().get(i).get(0).equals(cbo.getAllClassnames().get(k)))
                    {
                        index = k + 1;
                    }
                }
                /*loop over the ReferList list to get the index of the classes linked to this class and print to file*/
                for (int j = 1; j < cbo.getReferList().get(i).size(); j++)
                {
                    for (int k = 0; k < cbo.getAllClassnames().size(); k++)
                    {
                      /*check if the class exists in the AllClassnames list*/
                        if (cbo.getReferList().get(i).get(j).equals(cbo.getAllClassnames().get(k)))
                        {
                            bw.write("\t" + index + " -- " + (k + 1) + ";\n");
                        }
                    }
                }
            }
            bw.write("}\n");
            bw.flush();  //make sure writing has completed
            bw.close();  //close the file
        }
        catch (IOException e)
        {
            System.out.println("Error writing cbo: "+ e.getMessage());
        }
    }
}
