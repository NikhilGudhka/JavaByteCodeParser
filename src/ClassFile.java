/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses and stores a Java .class file. Parsing is currently complete for performing LCOM4 and CBO calculations
 * for a java .class file.
 */
public class ClassFile
{
    private String filename;
    private long magic;
    private int minorVersion;
    private int majorVersion;
    private ConstantPool constantPool;
    private List<String> accessFlags;
    private String thisClass;
    private String superClass;
    private int interfaceCount;
    private List<String> interfaces;
    private int fieldCount;
    private List<Fields> fields;
    private int methodCount;
    private List<Methods> methods;
    private int attributesCount;
    private List<Attributes> attributeInfo;
    private int lcom4Value;
    private boolean success = false;


    /**
     * Parses a class file an constructs a ClassFile object. At present, this
     * only parses the header and constant pool.
     */
    public ClassFile(String filename)
    {
        try
        {
            DataInputStream dis =
                    new DataInputStream(new FileInputStream(filename));

            this.filename = filename;
            magic = (long) dis.readUnsignedShort() << 16 | dis.readUnsignedShort();
            minorVersion = dis.readUnsignedShort();
            majorVersion = dis.readUnsignedShort();
            if (majorVersion >= 51.0)
            {
                constantPool = new ConstantPool(dis);

                // Parse Access Flags
                AccessFlags accessFlags = new AccessFlags();
                this.accessFlags = accessFlags.classAccessFlags(dis);

                // Parse This Class if parsing Access Flags was successful
                if (accessFlags.isSuccess())
                {
                    ThisClass thisClass = new ThisClass(); //create instance of this class
                    thisClass.parse(dis, constantPool); //parse this class
                    this.thisClass = thisClass.getClassName(); //store results in class field thisClass

                    // Parse Super Class if parsing this class was successful
                    if (thisClass.isSuccess())
                    {
                        SuperClass superClass = new SuperClass();
                        superClass.parse(dis, constantPool); //get super class
                        this.superClass = superClass.getSuperclassName(); //store result in class field superClass

                        // Parse Interfaces if parsing Super Class was successful
                        if (superClass.isSuccess())
                        {
                            ImplementedInterfaces implementedInterfaces = new ImplementedInterfaces();
                            this.interfaces = implementedInterfaces.parse(dis, constantPool); //parse interfaces
                            this.interfaceCount = implementedInterfaces.getInterfaceCount(); //store interface count in class field interfaceCount

                            // Parse Fields if parsing Interfaces was successful
                            if (implementedInterfaces.isSuccess())
                            {
                                Fields fields = new Fields();
                                this.fieldCount = fields.getFieldCount(dis);  //parse and get field count
                                this.fields = fields.parse(this.fieldCount, dis, constantPool); //parse fields

                                // Parse Methods if parsing Fields was successful
                                if (fields.isSuccess())
                                {
                                    Methods methods = new Methods();
                                    this.methodCount = methods.getMethodCount(dis);  //parse and get method count
                                    this.methods = methods.parse(this.methodCount, dis, constantPool); //parse methods

                                    // Parse Class Attributes if parsing Methods was successful
                                    if (methods.isSuccess())
                                    {
                                        Attributes attributes = new Attributes();
                                        this.attributesCount = attributes.getAttributeCount(dis); //parse and get attributes count
                                        this.attributeInfo = new ArrayList<Attributes>();
                                        for (int i = 0; i < this.attributesCount; i++)
                                        {
                                            this.attributeInfo.add(attributes.parse(dis, constantPool)); //parse attributes, store in an ArrayList
                                        }
                                        // Calculate LCOM4 if parsing Attributes was successful, hence everything else was successful
                                        if (attributes.isSuccess())
                                        {
                                            LCOM4 lcom4 = new LCOM4();
                                            this.lcom4Value = lcom4.calculateLCOM4(this.methods, this.fields);
                                            this.success = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else
            {
                System.out.println("===================================================================================================================================");
                System.out.println(this.filename + ": Class File version is not supported, please use version 51.0 or greater");
                System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
            }
        }
        catch (IOException e)
        {
            System.out.printf("\nCannot read \"%s\": %s\n", this.filename, e.getMessage());
            System.out.println();
        }
        catch (ClassFileParserException e)
        {
            System.out.printf("\nClass file format error in \"%s\": %s\n", this.filename, e.getMessage());
            System.out.println();
        }
    }

    /** Returns the contents of the class file as a formatted String. */
    public String toString()
    {
        return String.format(
            "Filename: %s\n" +
            "Magic: 0x%08x\n" +
            "Class file format version: %d.%d\n\n" +
            "Constant pool:\n\n%s",
            filename, magic, majorVersion, minorVersion, constantPool);
    }

    public String getThisClass()
    {
        return thisClass;
    }

    public List<String> getAccessFlags()
    {
        return accessFlags;
    }

    public List<Fields> getFields()
    {
        return fields;
    }

    public List<Methods> getMethods()
    {
        return methods;
    }

    public int getLcom4Value()
    {
        return lcom4Value;
    }

    public String getFilename()
    {
        return filename;
    }

    public String getSuperClass()
    {
        return superClass;
    }

    public List<String> getInterfaces()
    {
        return interfaces;
    }

    public int getInterfaceCount()
    {
        return interfaceCount;
    }

    public int getMajorVersion()
    {
        return majorVersion;
    }

    public int getFieldCount()
    {
        return fieldCount;
    }

    public int getMethodCount()
    {
        return methodCount;
    }

    public int getAttributesCount()
    {
        return attributesCount;
    }

    public List<Attributes> getAttributeInfo()
    {
        return attributeInfo;
    }

    public boolean isSuccess()
    {
        return this.success;
    }
}

