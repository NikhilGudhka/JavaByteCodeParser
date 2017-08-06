/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

import org.junit.Test;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.*;

public class FieldsTest
{
    @Test
    public void parse() throws Exception
    {
        ClassFile cf = new ClassFile("Test.class");
        List<Fields> fieldsTest = cf.getFields();
        assertEquals("I",fieldsTest.get(0).getDescriptorData());
        assertEquals("D",fieldsTest.get(1).getDescriptorData());
        assertEquals("Ljava/lang/String;",fieldsTest.get(2).getDescriptorData());
        assertEquals("int",fieldsTest.get(0).getDescriptorType());
        assertEquals("double",fieldsTest.get(1).getDescriptorType());
        assertEquals("java/lang/String",fieldsTest.get(2).getDescriptorType());
    }

    @Test
    public void getFieldCount() throws Exception
    {
        DataInputStream dis = new DataInputStream(new FileInputStream("Author.class"));
        Fields fields = new Fields();
        fields.getFieldCount(dis);
    }
    @Test (expected = IOException.class)
    public void getFieldCountEx() throws Exception
    {
        DataInputStream dis = new DataInputStream(new FileInputStream(""));
        Fields fields = new Fields();
        fields.getFieldCount(dis);
    }

    @Test
    public void getAccessFlags() throws Exception
    {
        Fields fields = new Fields();
        assertEquals(null,fields.getAccessFlags());
    }

    @Test
    public void getFieldName() throws Exception
    {
        Fields fields = new Fields();
        assertEquals(null,fields.getFieldName());
        ClassFile cf = new ClassFile("Author.class");
        assertEquals("story",cf.getFields().get(0).getFieldName());
        assertEquals("pen",cf.getFields().get(1).getFieldName());
    }

    @Test
    public void getDescriptorType() throws Exception
    {
        Fields fields = new Fields();
        assertEquals(null,fields.getDescriptorType());
        ClassFile cf = new ClassFile("Author.class");
        assertEquals("java/lang/String",cf.getFields().get(0).getDescriptorType());
        assertEquals("boolean",cf.getFields().get(1).getDescriptorType());

    }

    @Test
    public void getDescriptorData() throws Exception
    {
        Fields fields = new Fields();
        assertEquals(null,fields.getDescriptorType());
        ClassFile cf = new ClassFile("Author.class");
        assertEquals("Ljava/lang/String;",cf.getFields().get(0).getDescriptorData());
        assertEquals("Z",cf.getFields().get(1).getDescriptorData());
    }

}