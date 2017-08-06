/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Parses and stores the information about the Super Class for this class.
 * Gets the nameIndex which is used to get the name of the super class from the constant pool.
 * The super class name is stored in a private field of type String
 */
public class SuperClass
{
    private String superClassName;
    private boolean success = false; //boolean variable used to check if execution was successful till here

    /**
     * Retrieves the super class name from the constant pool using the super class index retrieved from the constant pool
     * entries array.
     * Super class index is retrieved after parsing the java. class file and using the value to access the constant pool
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
            int superclass = dis.readUnsignedShort();
            CPEntry[] entries = constantPool.getEntries();

            if (entries[superclass] instanceof ConstantClass) //check if instance of Constant Class
            {
                int nameIndex = ((ConstantClass) entries[superclass]).getNameIndex(); //get name index
                if (entries[nameIndex] instanceof ConstantUtf8) // use name index to check constant pool if instance of Constant UTF8
                {
                    this.superClassName = ((ConstantUtf8) entries[nameIndex]).getBytes(); //get super class name
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Error parsing super_class in ClassFile: "+ e.getMessage());
            this.success = false; //assign false to success if exception occurs
        }
    }

    /**
     * Retrieves the name of the super class of the class file that is being parsed
     * @return superClassName - String containing the super class name of the java .class file being parsed
     */
    public String getSuperclassName()
    {
        return this.superClassName;
    }

    public boolean isSuccess()
    {
        return this.success;
    }
}
