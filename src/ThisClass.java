/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Parses and stores the information about This Class.
 * Gets the nameIndex which is used to get the name for this class from the constant pool.
 * The class name is stored in a private field of type String
 */
public class ThisClass
{
    private String className;
    private boolean success = false; //boolean variable used to check if execution was successful

    /**
     * Retrieves the this class name from the constant pool using the this class index retrieved from the constant pool
     * entries array.
     * This class index is retrieved after parsing the java. class file and using the value to access the constant pool
     * entries array as an index.
     * CPEntry array of entries is used to retrieve name index and name.
     * @param dis - Data Input Stream containing the class file information to read from.
     * @param constantPool - The Constant pool of the java .class file
     */
    public void parse (DataInputStream dis, ConstantPool constantPool)
    {
        try
        {
            this.success = true;
            int thisClass = dis.readUnsignedShort();
            CPEntry[] entries = constantPool.getEntries();

            if (entries[thisClass] instanceof ConstantClass) //check if instance of Constant Class
            {
                int nameIndex = ((ConstantClass) entries[thisClass]).getNameIndex(); //get name index
                if (entries[nameIndex] instanceof ConstantUtf8) //use name index to check constant pool if instance of Constant UTF8
                {
                    this.className = ((ConstantUtf8) entries[nameIndex]).getBytes(); //get class name
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Error parsing this_class in ClassFile: "+ e.getMessage());
            this.success = false; //assign false to success if exception occurs
        }
    }

    /**
     * Retrieves the name of the class whose class file is being parsed
     * @return className - String containing the class name of the java .class file being parsed
     */
    public String getClassName()
    {
        return className;
    }

    public boolean isSuccess()
    {
        return this.success;
    }
}
