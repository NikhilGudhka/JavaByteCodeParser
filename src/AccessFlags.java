/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Containing Class, Field and Method Access and Property flags as per the java virtual machine specification (se8).
 */
public class AccessFlags
{
    /*
        Access and property flags marked final hence cant be modified.
     */
    private static final int ACC_PUBLIC = 0x0001;
    private static final int ACC_PRIVATE = 0x0002;
    private static final int ACC_PROTECTED = 0x0004;
    private static final int ACC_STATIC = 0x0008;
    private static final int ACC_FINAL = 0x0010;
    private static final int ACC_SUPER = 0x0020;
    private static final int ACC_SYNCHRONIZED = 0x0020;
    private static final int ACC_VOLATILE = 0x0040;
    private static final int ACC_BRIDGE = 0x0040;
    private static final int ACC_TRANSIENT = 0x0080;
    private static final int ACC_VARARGS = 0x0080;
    private static final int ACC_NATIVE = 0x0100;
    private static final int ACC_INTERFACE = 0X0200;
    private static final int ACC_ABSTRACT = 0X0400;
    private static final int ACC_STRICT = 0x0800;
    private static final int ACC_SYNTHETIC = 0x1000;
    private static final int ACC_ANNOTATION = 0x2000;
    private static final int ACC_ENUM = 0x4000;
    private List<String> flags;
    private boolean success =false;

    /**
     * Used to get the flags for a class, the data input stream(dis) is read and the data received from the
     * dis is parsed to get all relevant flags for the class.
     * @param dis - Data Input Stream containing the class file information to read from.
     * @return flags - An ArrayList of strings, containing all the flags for the class file
     */
    public List<String> classAccessFlags(DataInputStream dis)
    {
        try
        {
            this.success =true;
            final int accessflag = dis.readUnsignedShort();
            flags = new ArrayList<String>();
            if ((accessflag & ACC_PUBLIC) > 0)
            {
                flags.add("public");
            }
            if ((accessflag & ACC_FINAL) > 0)
            {
                flags.add("final");
            }
            if ((accessflag & ACC_SUPER) > 0)
            {
                flags.add("super");
            }
            if ((accessflag & ACC_INTERFACE) > 0)
            {
                flags.add("interface");
            }
            if ((accessflag & ACC_ABSTRACT) > 0)
            {
                flags.add("abstract");
            }
            if ((accessflag & ACC_SYNTHETIC) > 0)
            {
                flags.add("synthetic");
            }
            if ((accessflag & ACC_ANNOTATION) > 0)
            {
                flags.add("annotation");
            }
            if ((accessflag & ACC_ENUM) > 0)
            {
                flags.add("enum");
            }
        }
        catch (IOException e)
        {
            System.out.println("Error while parsing Class Access Flags " + e.getMessage());
            this.success = false;
        }
        return this.flags;
    }

    /**
     * Used to get the flags for fields, the data input stream(dis) is read and the data received from the
     * dis is parsed to get all relevant flags for the fields of the class.
     * @param dis - Data Input Stream containing the class file information to read from.
     * @return flags - An ArrayList of strings, containing all the flags for the fields of the class file
     */
    public List<String> fieldAccessFlags(DataInputStream dis)
    {
        try
        {
            this.success =true;
            final int accessflag = dis.readUnsignedShort();
            flags = new ArrayList<String>();

            if ((accessflag & ACC_PUBLIC) > 0)
            {
                flags.add("public");
            }
            if ((accessflag & ACC_PRIVATE) > 0)
            {
                flags.add("private");
            }
            if ((accessflag & ACC_PROTECTED) > 0)
            {
                flags.add("protected");
            }
            if ((accessflag & ACC_STATIC) > 0)
            {
                flags.add("static");
            }
            if ((accessflag & ACC_FINAL) > 0)
            {
                flags.add("final");
            }
            if ((accessflag & ACC_VOLATILE) > 0)
            {
                flags.add("volatile");
            }
            if ((accessflag & ACC_TRANSIENT) > 0)
            {
                flags.add("transient");
            }
            if ((accessflag & ACC_SYNTHETIC) > 0)
            {
                flags.add("synthetic");
            }
            if ((accessflag & ACC_ENUM) > 0)
            {
                flags.add("enum");
            }
        }
        catch(IOException e)
        {
            System.out.println("Error while parsing Field Access Flags: " + e.getMessage());
            this.success =false;
        }
        return flags;
    }

    /**
     * Used to get the flags for methods, the data input stream(dis) is read and the data received from the
     * dis is parsed to get all relevant flags for the methods of the class.
     * @param dis - Data Input Stream containing the class file information to read from.
     * @return flags - An ArrayList of strings, containing all the flags for the methods of the class file
     */
    public List<String> methodAccessFlags(DataInputStream dis)
    {
        try
        {
            this.success =true;
            final int accessflag = dis.readUnsignedShort();
            flags = new ArrayList<String>();

            if ((accessflag & ACC_PUBLIC) > 0)
            {
                flags.add("public");
            }
            if ((accessflag & ACC_PRIVATE) > 0)
            {
                flags.add("private");
            }
            if ((accessflag & ACC_PROTECTED) > 0)
            {
                flags.add("protected");
            }
            if ((accessflag & ACC_STATIC) > 0)
            {
                flags.add("static");
            }
            if ((accessflag & ACC_FINAL) > 0)
            {
                flags.add("final");
            }
            if ((accessflag & ACC_SYNCHRONIZED) > 0)
            {
                flags.add("synchronized");
            }
            if ((accessflag & ACC_BRIDGE) > 0)
            {
                flags.add("bridge");
            }
            if ((accessflag & ACC_VARARGS) > 0)
            {
                flags.add("varargs");
            }
            if ((accessflag & ACC_NATIVE) > 0)
            {
                flags.add("native");
            }
            if ((accessflag & ACC_ABSTRACT) > 0)
            {
                flags.add("abstract");
            }
            if ((accessflag & ACC_STRICT) > 0)
            {
                flags.add("strict");
            }
            if ((accessflag & ACC_SYNTHETIC) > 0)
            {
                flags.add("synthetic");
            }
        }
        catch (IOException e)
        {
            System.out.println("Error while parsing Method Access Flags: " + e.getMessage());
            this.success = false;
        }
        return flags;
    }

    public boolean isSuccess()
    {
        return this.success;
    }
}

