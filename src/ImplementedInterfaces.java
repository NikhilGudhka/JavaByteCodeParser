/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Interfaces implemented by a java .class file
 */
public class ImplementedInterfaces
{
    private int interfaceCount; //number of interfaces implemented
    private boolean success = false; // boolean variable to check if execution was successful

    /**
     * Method to parse the stream and retrieve the interface count and the interfaces implemented by a java .class file.
     * Stores the interfaces that are implemented in an ArrayList
     * @param dis - The data input stream to read from
     * @param constantPool - the constant pool to check against using the index values retrieved by reading the stream
     * @return interfaceEntires - list containing all the iterfaces implemented by the java .class file being parsed.
     * @throws InvalidTagException
     * @throws InvalidConstantPoolIndex
     */
    public List<String> parse(DataInputStream dis, ConstantPool constantPool) throws InvalidTagException, InvalidConstantPoolIndex
    {
        List<String> interfaceEntries = null; //list to hold the interfaces implemented
        try
        {
            this.success = true;
            interfaceEntries = new ArrayList<String>();
            this.interfaceCount = dis.readUnsignedShort(); //read stream to get interface count
            CPEntry[] entries = constantPool.getEntries(); //get the array of entries
            for (int i = 0; i < this.interfaceCount; i++) //loop for every interface
            {
                int interfaceEntry = dis.readUnsignedShort();
                if (entries[interfaceEntry] instanceof ConstantClass) //check if instance of Constant Class
                {
                    int nameIndex = ((ConstantClass) entries[interfaceEntry]).getNameIndex(); //get name index
                    if (entries[nameIndex] instanceof ConstantUtf8) //use name index to check constant pool if instance of Constant UTF8
                    {
                        interfaceEntries.add(((ConstantUtf8) entries[nameIndex]).getBytes()); //get interface name
                    }
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Error parsing interfaces in ClassFile: " + e.getMessage());
            this.success = false; //assign false to success if IO exception occurs
        }
        return interfaceEntries;
    }

    public int getInterfaceCount()
    {
        return this.interfaceCount;
    }

    public boolean isSuccess()
    {
        return this.success;
    }
}
