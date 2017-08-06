/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses Fields in a java .class file, as per the java virtual machine specification 8.
 * More information can be found in chapter 4.
 */
public class Fields
{
    private List<String> accessFlag; //list to hold field access flags
    private String fieldName; //string variable to hold the field name
    private String descriptorData; // variable to hold JVM form of field descriptors (more info in chapter 4.3.2)
    private String descriptorType; //variable to hold java form of field descriptors (more info in chapter 4.3.2)
    private int attributesCount;
    private List<Attributes> attributes = new ArrayList<Attributes>();
    private boolean success = false; //boolean variable used to check if execution was successful

    /**
     * Method to parse the stream by calling method fieldInfo and retrieve the fields in the java .class file, storing them in an ArrayList
     * @param fieldCount - Count representing the number of fields in the java .class file
     * @param dis - Data Input Stream containing the class file information to read from.
     * @param constantPool - The Constant pool of the java .class file
     * @return fieldsList  - A list containing all the fields in the java .class file
     */
    public List<Fields> parse(int fieldCount, DataInputStream dis, ConstantPool constantPool)
    {
        List<Fields> fieldsList = new ArrayList<Fields>(); //initialize list
        try
        {
            this.success = true;
            for (int i = 0; i < fieldCount; i++) //for each field, call fieldInfo() and store the result in the fieldsList
            {
                fieldsList.add(fieldInfo(dis, constantPool));
            }
        }
        catch (IOException e)
        {
            System.out.println("Error parsing Fields in ClassFile: "+ e.getMessage());
            this.success = false; //assign false to success if IO exception occurs
        }
        return fieldsList;
    }

    /**
     * Parses and stores information about fields into a new field instance and returns it.
     * @param dis - Data Input Stream containing the class file information to read from.
     * @param constantPool - The Constant pool of the java .class file
     * @return field - A field object containing all the information about that field
     * @throws IOException
     */
    private Fields fieldInfo(DataInputStream dis, ConstantPool constantPool) throws IOException
    {
        Fields field = new Fields(); //new instance of field
        AccessFlags accessFlags = new AccessFlags(); //new instance of AccessFlags
        field.accessFlag = accessFlags.fieldAccessFlags(dis); //assign access flags after parsing to the new instance of field
        int nameIndex = dis.readUnsignedShort(); //get name index
        int descriptorIndex = dis.readUnsignedShort(); //get descriptor index
        field.attributesCount = dis.readUnsignedShort(); //get attributes count
        for (int i = 0; i < field.attributesCount; i++) //loop for attributes count of this field
        {
            Attributes attributes = new Attributes();
            if (field.attributesCount!=0) //if attributes count is not zero store the results from attributes method to the attributes list
            {
                field.attributes.add(attributes.parse(dis, constantPool));
            }
        }

        CPEntry[] entries = constantPool.getEntries();

        if(entries[nameIndex] instanceof ConstantUtf8) //use name index to check constant pool if instance of Constant UTF8
        {
            field.fieldName = ((ConstantUtf8) entries[nameIndex]).getBytes(); //get the field name using the name index from the constant pool entries
        }
        if(entries[descriptorIndex] instanceof ConstantUtf8) //use descriptor index to check constant pool if instance of Constant UTF8
        {
            field.descriptorData = ((ConstantUtf8) entries[descriptorIndex]).getBytes(); //get descriptor data from constant pool entries using descriptor index
            Descriptors descriptors = new Descriptors(((ConstantUtf8) entries[descriptorIndex]).getBytes()); //get descriptors from constant pool entries using descriptor index
            field.descriptorType = descriptors.getValues().get(0); //get zero index value from the list returned by getValues()
        }
        field.success = true;
        return field;

}

    public int getFieldCount(DataInputStream dis) throws IOException
    {
        return dis.readUnsignedShort();
    }
    public List<String> getAccessFlags()
    {
        return accessFlag;
    }

    public String getFieldName()
    {
        return fieldName;
    }

    public String getDescriptorType()
    {
        return descriptorType;
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
