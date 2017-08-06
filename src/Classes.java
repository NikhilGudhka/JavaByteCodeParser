/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

import java.io.DataInputStream;
import java.io.IOException;

/**
 * For Future use if program needs to be extended further to parse inner classes.
 */
public class Classes
{
    private int innerClassInfoIndex; //variable to store the inner class index
    private int outerClassInfoIndex; //variable store the outre class index
    private int innerNameIndex; //variable to store the inner name index
    private int innerClassAccessFlags; //variable to store the inner class access flags

    public Classes(DataInputStream dis) throws IOException
    {
        innerClassInfoIndex = dis.readUnsignedShort(); //read inner class index from stream
        outerClassInfoIndex = dis.readUnsignedShort(); //read outer class index from stream
        innerNameIndex = dis.readUnsignedShort(); //read inner name index from stream
        innerClassAccessFlags = dis.readUnsignedShort(); //read inner class access flags from stream
    }

    public int getInnerClassInfoIndex()
    {
        return innerClassInfoIndex;
    }

    public int getOuterClassInfoIndex()
    {
        return outerClassInfoIndex;
    }

    public int getInnerNameIndex()
    {
        return innerNameIndex;
    }

    public int getInnerClassAccessFlags()
    {
        return innerClassAccessFlags;
    }
}
