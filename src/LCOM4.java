/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

import java.util.ArrayList;
import java.util.List;

 /**
 *  calculate lack of cohesion of methods 4 for each java .class file. Takes a list of methods and fields called by the class
 */
public class LCOM4
{
    /**
    * Calculates the  LCOM4 for the class.
    * @param methods - List of methods called in the class
    * @param fields - List of fields called in the class
    * @return - an integer value which is the lcom4 value
    */
    public int calculateLCOM4(List<Methods> methods, List<Fields> fields)
    {
        List<ArrayList<String>> referesList = new ArrayList<ArrayList<String>>();
        List<ArrayList<String>> methodsCallsList = new ArrayList<ArrayList<String>>();
        List<String> fieldList = new ArrayList<String>();

        for (int i = 0; i < methods.size() ; i++)  //for the number of methods
        {
            List<String> methodsFieldsNameAndType = new ArrayList<String>();
            for (int j = 0; j < methods.get(i).getAttributes().size(); j++) //get the number of attributes of this method
            {
                if (methods.get(i).getAttributes().get(j) instanceof Code) //check if instance of Code
                {
                    for (int k = 0; k < ((Code) (methods.get(i).getAttributes().get(j))).getfieldCallsName().size(); k++) //get the number of fields referenced by this method
                    {
                        String fieldNameString = (((Code) (methods.get(i).getAttributes().get(j))).getfieldCallsName()).get(k);  //get the field name
                        String fieldTypeString = (((Code) (methods.get(i).getAttributes().get(j))).getfieldCallsType()).get(k);  //get the field type
                        if(!fieldTypeString.toLowerCase().contains("printstream")) // check if the field type is not of printstream
                        {
                          /*check if field exists in the methodsFieldsNameAndType list, if it doesn't add it to the list, hence no duplicate values in the methodsFieldsNameAndType list*/
                            if (!methodsFieldsNameAndType.contains(fieldNameString.concat(fieldTypeString)))
                            {
                                methodsFieldsNameAndType.add(fieldNameString.concat(fieldTypeString));
                            }
                        }
                    }
                    for (int k = 0; k < ((Code) (methods.get(i).getAttributes().get(j))).getmethodCallsName().size(); k++)  //get the number of methods referenced by this method
                    {
                        String methodNameString = (((Code) (methods.get(i).getAttributes().get(j))).getmethodCallsName()).get(k);  //get the method name
                        String methodTypeString = (((Code) (methods.get(i).getAttributes().get(j))).getMethodCallsType()).get(k);  //get the method type
                        /*Check if the method name is not either of "<init>","<clinit>"","tostring","equals","println","print"*/
                        if (!((methodNameString.toLowerCase().contains("<init>")) || (methodNameString.toLowerCase().contains("<clinit>")) || (methodNameString.toLowerCase().contains("tostring")) || (methodNameString.toLowerCase().contains("equals")) || (methodNameString.toLowerCase().contains("println")) || (methodNameString.toLowerCase().contains("print"))))
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
            methodsCallsList.add((ArrayList<String>) methodsFieldsNameAndType); //add the list of fields and methods called by this method to methodsCallsList
        }
        for (int i = 0; i < methods.size() ; i++)  //for the number of methods
        {
            List<String> methodsFieldsNameAndType = new ArrayList<String>();
            methodsFieldsNameAndType.add(methods.get(i).getNameIndexData().concat(methods.get(i).getDescriptorData()));  //add the current method name and type to the methodsFieldsNameAndType Array
            for (int j = 0; j < methods.get(i).getAttributes().size() ; j++) //get the number of attributes of this method
            {
                if(methods.get(i).getAttributes().get(j) instanceof Code)  //check if instance of Code
                {
                    for (int k = 0; k < ((Code)(methods.get(i).getAttributes().get(j))).getfieldCallsName().size() ; k++) //get the number of fields referenced by this method
                    {
                        String fieldNameString = (((Code)(methods.get(i).getAttributes().get(j))).getfieldCallsName()).get(k); //get the field name
                        String fieldTypeString = (((Code)(methods.get(i).getAttributes().get(j))).getfieldCallsType()).get(k); //get the field type
                        if(!fieldTypeString.toLowerCase().contains("printstream"))  // check if the field type is not of printstream
                        {
                          /*check if field exists in the methodsFieldsNameAndType list, if it doesn't add it to the list, hence no duplicate values in the methodsFieldsNameAndType list*/
                            if (!methodsFieldsNameAndType.contains(fieldNameString.concat(fieldTypeString)))
                            {
                                methodsFieldsNameAndType.add(fieldNameString.concat(fieldTypeString));
                            }
                        }
                    }
                    for (int k = 0; k < ((Code)(methods.get(i).getAttributes().get(j))).getmethodCallsName().size() ; k++) //get the number of methods referenced by this method
                    {
                        String methodNameString = (((Code)(methods.get(i).getAttributes().get(j))).getmethodCallsName()).get(k); //get the method name
                        String methodTypeString = (((Code)(methods.get(i).getAttributes().get(j))).getMethodCallsType()).get(k); //get the method type
                        /*Check if the method name is not either of "<init>","<clinit>"","tostring","equals","println","print"*/
                        if(!((methodNameString.toLowerCase().contains("<init>")) || (methodNameString.toLowerCase().contains("<clinit>")) || (methodNameString.toLowerCase().contains("tostring")) || (methodNameString.toLowerCase().contains("equals")) || (methodNameString.toLowerCase().contains("println")) || (methodNameString.toLowerCase().contains("print"))))
                        {
                          /*check if method exists in the methodsFieldsNameAndType list, if it doesn't add it to the list, hence no duplicate values in the methodsFieldsNameAndType list*/
                            if(!methodsFieldsNameAndType.contains(methodNameString.concat(methodTypeString)))
                            {
                                methodsFieldsNameAndType.add(methodNameString.concat(methodTypeString));
                            }
                        }
                    }
                }
            }

            /*check if the method is not either of "<init>","<clinit>"","tostring","equals"*/
            if(!((methods.get(i).getNameIndexData().toLowerCase().contains("<init>")) || (methods.get(i).getNameIndexData().toLowerCase().contains("<clinit>"))
                    || (methods.get(i).getNameIndexData().toLowerCase().contains("tostring")) || (methods.get(i).getNameIndexData().toLowerCase().contains("equals")) ))
            {
                if (referesList.size() > 0) //check if there is a list in the referlist
                {
                    boolean doesExist=false;
                    for (int j = 0; j < methodsFieldsNameAndType.size(); j++) //loop over the methodsFieldsNameAndType
                    {
                        for (int k = 0; k < referesList.size(); k++) //loop for the size of the refersList
                        {
                          /*check if the refersList list contains this method or any field or method this method refers to*/
                            if (referesList.get(k).contains(methodsFieldsNameAndType.get(j)))
                            {
                              //set does exist to tru if there is a link between the current method and any other method in the refersList
                                doesExist=true;
                                for (int l = 0; l < methodsFieldsNameAndType.size(); l++) //loop over the methodsFieldsNameAndType
                                {
                                  /*if the method or field is not in the list add it to the list*/
                                    if (!(referesList.get(k).contains(methodsFieldsNameAndType.get(l))))
                                    {
                                       referesList.get(k).add(methodsFieldsNameAndType.get(l));
                                    }
                                }
                            }
                        }
                    }
                    /*if the existing lists in refersList do not reference the current class or any of its methods or fields make a new list in refersList*/
                    if(doesExist==false)
                    {
                        ArrayList<String> list = new ArrayList<String>();
                        list = (ArrayList<String>) methodsFieldsNameAndType;
                        referesList.add(list);
                    }
                }
                /*if there is no list in referesList make a new list and add it to the refersList*/
                else
                {
                    ArrayList<String> list = new ArrayList<String>();
                    list = (ArrayList<String>) methodsFieldsNameAndType;
                    referesList.add(list);
                }
            }

        }
        /*loop over the number of fields and add the name and type to the fields list*/
        for (int i = 0; i < fields.size() ; i++)
        {
            fieldList.add(fields.get(i).getFieldName().concat(fields.get(i).getDescriptorData()));
        }

        for (int i = 0; i < fieldList.size() ; i++) //loop for the number of fields
        {
            boolean doesExist=false;
            for (int j = 0; j < referesList.size() ; j++) //loop for the size of the refersList
            {
              /*check if field exists in the refer list, if it does set doesExist to true hence no duplicate values in the refer list*/
                if(referesList.get(j).contains(fieldList.get(i)))
                {
                    doesExist=true;
                }
            }
            /*For each field that does not exist in any list in the refersList make a new list*/
            if(doesExist==false)
            {
                ArrayList<String> list = new ArrayList<String>();
                list.add(fieldList.get(i));
                referesList.add(list);
            }
        }
        /*return the LCOM4 values which is equal to the refersList size*/
        return referesList.size();
    }

}
