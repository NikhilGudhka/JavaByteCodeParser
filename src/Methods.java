/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses Methods in a java .class file, as per the java virtual machine specification 8.
 * More information can be found in chapter 4.
 */
public class Methods
{
    private List<String> accessFlags; //list to hold method access flags
    private int nameIndex; //string variable to hold the method name
    private String nameIndexData; //string holding the name of the method
    private List<String> parameters; //list to hold the parameters to the method
    private String returnType; //string to hold the method's return type
    private String descriptorData; // variable to hold JVM form of field descriptors (more info in chapter 4.3.3)
    private int attributesCount;
    private List<Attributes> attributes = new ArrayList<Attributes>();
    private boolean success = false; //boolean variable used to check if execution was successful

    /**
     * Method to parse the stream by calling method methodInfo and retrieve the methods in the java .class file, storing them in an ArrayList
     * @param methodCount - Count representing the number of methods in the java .class file
     * @param dis - Data Input Stream containing the class file information to read from.
     * @param constantPool - The Constant pool of the java .class file
     * @return - A list containing all the methods in the java .class file
     */
    public List<Methods> parse(int methodCount, DataInputStream dis, ConstantPool constantPool)
    {
        List<Methods> methodsList = new ArrayList<Methods>(); //initialize list
        try
        {
            this.success = true;
            for (int i = 0; i < methodCount; i++) //for each method, call methodInfo() and store the result in the methodsList
            {
                methodsList.add(methodInfo(dis, constantPool));
            }
        }
        catch (IOException e)
        {
            System.out.println("Error parsing Methods in ClassFile: "+ e.getMessage());
            this.success = false; //assign false to success if IO exception occurs
        }
        return methodsList;
    }

    /**
     * Parses and stores information about fields into a new field instance and returns it.
     * @param dis - Data Input Stream containing the class file information to read from.
     * @param constantPool - The Constant pool of the java .class file
     * @return method - A method object containing all the information about that method
     * @throws IOException
     */
    private Methods methodInfo(DataInputStream dis, ConstantPool constantPool) throws IOException
    {
        Methods method = new Methods(); //new instance of method
        AccessFlags accessFlags = new AccessFlags(); //new instance of access flags
        method.accessFlags = accessFlags.methodAccessFlags(dis); //assign access flags after parsing to the new instance of method
        method.nameIndex = dis.readUnsignedShort(); //get name index
        int descriptorIndex = dis.readUnsignedShort(); //get descriptor index
        method.attributesCount = dis.readUnsignedShort(); //get attributes count
        for (int i = 0; i < method.attributesCount; i++) //loop for attributes count of this method
        {
            Attributes attributes = new Attributes();
            if (method.attributesCount!=0) //if attributes count is not zero store the results from attributes method to the attributes list
            {
                method.attributes.add(attributes.parse(dis, constantPool));
            }
        }

        CPEntry[] entries = constantPool.getEntries();

        if(entries[method.nameIndex] instanceof ConstantUtf8) //use name index to check constant pool if instance of Constant UTF8
        {
            method.nameIndexData = ((ConstantUtf8) entries[method.nameIndex]).getBytes(); //get the method name using the name index from the constant pool entries
        }
        if(entries[descriptorIndex] instanceof ConstantUtf8) //use descriptor index to check constant pool if instance of Constant UTF8
        {
            Descriptors descriptors;
            method.descriptorData = ((ConstantUtf8) entries[descriptorIndex]).getBytes(); //get descriptor data from constant pool entries using descriptor index
            descriptors = new Descriptors(method.descriptorData.substring(0,method.descriptorData.indexOf(')')+1)); //get parameters including opening closing parentheses
            method.parameters = descriptors.getValues(); //store the results into the parameters class field
            descriptors = new Descriptors(method.descriptorData.substring(method.descriptorData.indexOf(')')+1)); //get return type of method
            method.returnType = descriptors.getValues().get(0); //get zero index value from the list returned by getValues()
        }
        method.success = true;
        return method;
    }

    public List<String> getAccessFlags()
    {
        return this.accessFlags;
    }

    public int getMethodCount(DataInputStream dis) throws IOException
    {
        return dis.readUnsignedShort();
    }

    public  int getNameIndex()
    {
        return nameIndex;
    }

    public String getNameIndexData()
    {
        return nameIndexData;
    }

    public List<String> getParameters()
    {
        return this.parameters;
    }

    public String getReturnType()
    {
        return this.returnType;
    }

    public List<Attributes> getAttributes()
    {
        return attributes;
    }

    public int getAttributesCount()
    {
        return attributesCount;
    }

    public String getDescriptorData()
    {
        return descriptorData;
    }

    public boolean isSuccess()
    {
        return this.success;
    }
}
