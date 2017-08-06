/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parse Attributes as per the java virtual machine specification 8.
 * More information can be found in chapter 4.
 */
public class Attributes
{
    private boolean success = false;

    /**
     * Parse the attributes of the java .class file
     * @param dis - The data input stream to read from
     * @param constantPool - the constant pool to check against using the index values retrieved by reading the stream
     * @return attributes  - Attributes object containing all the information of the attribute and type of attribute of the .class file
     */
    public Attributes parse (DataInputStream dis, ConstantPool constantPool)
    {
        Attributes attributes = null;
        try
        {
            this.success = true;
            int attributeNameIndex;
            long attributeLength;
            CPEntry[] entries = constantPool.getEntries();
            attributeNameIndex = dis.readUnsignedShort();
            attributeLength = (long) dis.readUnsignedShort() << 16 | dis.readUnsignedShort();

            if (entries[attributeNameIndex] != null)
            {
                if (entries[attributeNameIndex] instanceof ConstantUtf8)
                {
                    String attribute = ((ConstantUtf8) entries[attributeNameIndex]).getBytes().toLowerCase(); //get type of attribute
                    switch (attribute) //switch on the type of attribute
                    {
                        case "constantvalue":
                            attributes = new ConstantValue(dis, entries);
                            break;
                        case "code":
                            attributes = new Code(dis, attributeLength, entries);
                            break;
                        case "stackmaptable":
                            dis.skipBytes((int) attributeLength);
                            break;
                        case "exceptions":
                            dis.skipBytes((int) attributeLength);
                            //attributes = new Exceptions(dis, entries);
                            break;
                        case "innerclasses":
                            dis.skipBytes((int) attributeLength);
                            //attributes = new InnerClasses(dis, entries);
                            break;
                        case "enclosingmethod":
                            dis.skipBytes((int) attributeLength);
                            //attributes = new EnclosingMethod(dis, entries);
                            break;
                        case "synthetic":
                            dis.skipBytes((int) attributeLength);
                            break;
                        case "signature":
                            dis.skipBytes((int) attributeLength);
                            break;
                        case "sourcefile":
                            dis.skipBytes((int) attributeLength);
                            break;
                        case "sourcedebugextension":
                            dis.skipBytes((int) attributeLength);
                            break;
                        case "linenumbertable":
                            dis.skipBytes((int) attributeLength);
                            break;
                        case "localvariabletable":
                            dis.skipBytes((int) attributeLength);
                            break;
                        case "localvariabletypetable":
                            dis.skipBytes((int) attributeLength);
                            break;
                        case "deprecated":
                            dis.skipBytes((int) attributeLength);
                            break;
                        case "runtimevisibleannotations":
                            dis.skipBytes((int) attributeLength);
                            break;
                        case "runtimeinvisibleannotations":
                            dis.skipBytes((int) attributeLength);
                            break;
                        case "runtimevisibleparameterannotations":
                            dis.skipBytes((int) attributeLength);
                            break;
                        case "runtimeinvisibleparameterannotations":
                            dis.skipBytes((int) attributeLength);
                            break;
                        case "runtimevisibletypeannotations":
                            dis.skipBytes((int) attributeLength);
                            break;
                        case "runtimeinvisibletypeannotations":
                            dis.skipBytes((int) attributeLength);
                            break;
                        case "annotationdefault":
                            dis.skipBytes((int) attributeLength);
                            break;
                        case "bootstrapmethods":
                            dis.skipBytes((int) attributeLength);
                            break;
                        case "methodparameters":
                            dis.skipBytes((int) attributeLength);
                            break;
                        default:
                            System.out.println("Invalid Attribute Found! Found: " + attribute);
                            break;
                    }
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Error parsing Attributes in ClassFile: " + e.getMessage());
            this.success = false;
        }
        return attributes;
    }

    /*
    * method to read the attribute count
    * @return - the attribute count after reading from DataInputStream
    */
    public int getAttributeCount(DataInputStream dis) throws IOException
    {
        return dis.readUnsignedShort();
    }

    /**
    * method to verify no error in parsing
    * @return - isSuccess
    */
    public boolean isSuccess()
    {
        return this.success;
    }
}

/**
 * Parse the ConstantValue attribute.
 * Implemented for future use, as of now not needed to calculate LCOM4 and CBO.
 */
class ConstantValue extends Attributes
{
    private int constantValueIndex;

    public ConstantValue(DataInputStream dis, CPEntry[] entries) throws IOException
    {
        this.constantValueIndex = dis.readUnsignedShort();
    }

    public int getIntValue()     { return constantValueIndex; }
    public String getAttributeName()    { return "ConstantValue"; }
    public String getValues()    { return String.format("value=%d", constantValueIndex); }
}

/**
 * Code attributes, parses and retrieves extra information that is not found in the constant pool.
 * Required to perform CBO calculation. More information can be found in chapter 4.7.3 of the JVM 8 specification
 */
class Code extends Attributes
{
    private int maxStack;
    private int maxLocals;
    private long codeLength;
    private List<String> methodCallsName;
    private List<String> methodCallsType;
    private List<String> fieldCallsName;
    private List<String> fieldCallsType;
    private List<String> methodCallsClassnames;

    /**
     * Parses the code attribute of a java .class file
     * @param dis - The data input stream to read from
     * @param attributeLength - the length of the attribute
     * @param entries - The constant pool entries
     * @throws IOException
     */
    public Code(DataInputStream dis, long attributeLength, CPEntry[] entries) throws IOException
    {
        this.maxStack = dis.readUnsignedShort();
        this.maxLocals = dis.readUnsignedShort();
        this.codeLength = (long) dis.readUnsignedShort() << 16 | dis.readUnsignedShort();
        methodCallsName = new ArrayList<String>();
        methodCallsType = new ArrayList<String>();
        fieldCallsName = new ArrayList<String>();
        fieldCallsType = new ArrayList<String>();
        methodCallsClassnames = new ArrayList<String>();

        byte[] code = new byte[(int)codeLength];
        /*Read and store each byte in the code array till code length*/
        for (int i = 0; i < code.length; i++)
        {
            code[i] = dis.readByte();
        }
        /**
        * loop for the lenght of the code and parse it
        * @param op - the opCode
        * @param y - size of bytes to read for the the opCode
        */
        for (int i = 0; i < code.length ; i++)
        {
            Opcode op =Opcode.getOpcode(code[i]);
            int y = op.getSize(code,i);
            /*if op is of type "INVOKEVIRTUAL","INVOKESPECIAL","INVOKESTATIC","INVOKEINTERFACE","INVOKEDYNAMIC" */
            if ((op.toString().equals("INVOKEVIRTUAL"))||(op.toString().equals("INVOKESPECIAL"))||(op.toString().equals("INVOKESTATIC"))||(op.toString().equals("INVOKEINTERFACE"))||(op.toString().equals("INVOKEDYNAMIC")))
            {
                int cpIndex = (int)(code[i+1]) <<8| (int)(code[i+2]);  //read the constant pool index
                /*check if contant pool index is negative then multiply by -1 to get a valid index*/
                if(cpIndex <0)
                {
                    cpIndex*=-1;
                }
                i+=2; //increment loop variable by number of bytes read
                /*check if the constant pool entry at the specified index is of ConstantMethodRef*/
                if (entries[cpIndex] instanceof ConstantMethodRef)
                {
                    String methodName = ((ConstantMethodRef) (entries[cpIndex])).getName();     //get the method name
                    String methodType = ((ConstantMethodRef) (entries[cpIndex])).getType();     //get the method type
                    String className = ((ConstantMethodRef) (entries[cpIndex])).getClassName(); //get the method class
                    /*add the method name,type and class to the specified lists respectively*/
                    methodCallsName.add(methodName);
                    methodCallsType.add(methodType);
                    methodCallsClassnames.add(className);
                }
            }
            /*if op is of type "GETSTATIC","PUTSTATIC","GETFIELD","PUTFIELD"*/
            else if ((op.toString().equals("GETSTATIC"))||(op.toString().equals("PUTSTATIC"))||(op.toString().equals("GETFIELD"))||(op.toString().equals("PUTFIELD")))
            {
                int cpIndex = (int)(code[i+1]) <<8| (int)(code[i+2]); //read the constant pool index
                /*check if contant pool index is negative then multiply by -1 to get a valid index*/
                if(cpIndex <0)
                {
                    cpIndex*=-1;
                }
                i+=2; //increment loop variable by number of bytes read
                /*check if the constant pool entry at the specified index is of ConstantFieldRef*/
                if(entries[cpIndex] instanceof ConstantFieldRef)
                {
                    String fieldName = ((ConstantFieldRef)(entries[cpIndex])).getName();    //get the method name
                    fieldCallsName.add(fieldName); //add the fieldName to the fieldCallsName list
                    String fieldType = ((ConstantFieldRef)(entries[cpIndex])).getType();    //get the method type
                    fieldCallsType.add(fieldType); //add the fieldType to the fieldCallsType list
                }
            }
            /*for all other op code increment loop variable by the size of bytes to skip*/
            else
            {
                i+=(y-1);
            }
        }
        dis.skipBytes(((int)attributeLength)-2-2-4-(int)codeLength); //skip rest of the bytes, rest of the information not needed at the moment
    }
    /*Additional methods implemented for future use as per the JVM8 specification*/
    public int getIntmaxStack()                 { return maxStack; }
    public String getmaxStack()                 { return String.format("value=%d", maxStack); }
    public int getIntmaxLocals()                { return maxLocals; }
    public String getmaxLocals()                { return String.format("value=%d", maxLocals); }
    public Long getLongcodeLength()             { return codeLength; }
    public String getcodeLength()               { return String.format("value=%d", codeLength); }
    public List<String> getmethodCallsName()    { return methodCallsName; }
    public List<String> getMethodCallsType()    { return methodCallsType; }
    public List<String> getfieldCallsName()     { return fieldCallsName; }
    public List<String> getfieldCallsType()     { return fieldCallsType; }
    public List<String> getMethodCallsClassnames() {return methodCallsClassnames; }
}

/**
 * For Future use if program needs to be extended further to parse Exceptions.
 */
class Exceptions extends Attributes
{
    private int numberOfExceptions;
    private List<Integer> exceptionIndexTable;

    public Exceptions(DataInputStream dis, CPEntry[] entries) throws IOException
    {
        exceptionIndexTable = new ArrayList<>();
        this.numberOfExceptions = dis.readUnsignedShort();
        for (int i = 0; i < numberOfExceptions ; i++)
        {
            this.exceptionIndexTable.add(dis.readUnsignedShort());
        }
    }

    public int getIntnumberOfExceptions()               { return numberOfExceptions; }
    public String getnumberOfExceptions()               { return String.format("value=%d", numberOfExceptions); }
    public List<Integer> getexceptionIndexTable()    { return exceptionIndexTable; }
}

/**
 * For Future use if program needs to be extended further to parse EnclosingMethod.
 */
class EnclosingMethod extends Attributes
{
    private int classIndex;
    private int methodIndex;

    public EnclosingMethod(DataInputStream dis, CPEntry[] entries) throws IOException
    {
        this.classIndex = dis.readUnsignedShort();
        this.methodIndex = dis.readUnsignedShort();
    }

    public int getIntclassIndex()               { return classIndex; }
    public String getclassIndex()               { return String.format("value=%d", classIndex); }
    public int getIntmethodIndex()              { return methodIndex; }
    public String getmethodIndex()              { return String.format("value=%d", methodIndex); }
}


/**
 * For Future use if program needs to be extended further to parse InnerClasses.
 */
class InnerClasses extends Attributes
{
    private int numberOfClasses;
    private List<Classes> Classes;

    public InnerClasses(DataInputStream dis, CPEntry[] entries) throws IOException
    {
        Classes = new ArrayList<>();
        Classes classes;
        this.numberOfClasses = dis.readUnsignedShort();
        System.out.println("NUMBER OF INNER CLASSES"+numberOfClasses);
        for (int i = 0; i < numberOfClasses ; i++)
        {
            classes = new Classes(dis);
            Classes.add(classes);
            System.out.println("CLASS INNER CLASS INFO DATA "+String.format("0x%04x",Classes.get(i).getInnerClassInfoIndex()));
        }
    }

    public int getIntnumberOfExceptions()               { return numberOfClasses; }
    public String getnumberOfExceptions()               { return String.format("value=%d", numberOfClasses); }
    public List<Classes> getexceptionIndexTable()       { return Classes; }
}
