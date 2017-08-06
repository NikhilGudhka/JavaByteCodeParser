/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.*;

public class DescriptorsTest
{
    @Test
    public void getValues() throws Exception
    {
        String s = "(";
        Descriptors descriptors = new Descriptors(s);
        assertEquals("(",descriptors.getValues().get(0));
        s= ")";
        descriptors = new Descriptors(s);
        assertEquals(")",descriptors.getValues().get(0));
        s= "V";
        descriptors = new Descriptors(s);
        assertEquals("void",descriptors.getValues().get(0));
        s= "B";
        descriptors = new Descriptors(s);
        assertEquals("byte",descriptors.getValues().get(0));
        s= "C";
        descriptors = new Descriptors(s);
        assertEquals("char",descriptors.getValues().get(0));
        s= "D";
        descriptors = new Descriptors(s);
        assertEquals("double",descriptors.getValues().get(0));
        s= "F";
        descriptors = new Descriptors(s);
        assertEquals("float",descriptors.getValues().get(0));
        s= "I";
        descriptors = new Descriptors(s);
        assertEquals("int",descriptors.getValues().get(0));
        s= "J";
        descriptors = new Descriptors(s);
        assertEquals("long",descriptors.getValues().get(0));
        s= "S";
        descriptors = new Descriptors(s);
        assertEquals("short",descriptors.getValues().get(0));
        s= "Z";
        descriptors = new Descriptors(s);
        assertEquals("boolean",descriptors.getValues().get(0));
        s= "[I";
        descriptors = new Descriptors(s);
        assertEquals("int[]",descriptors.getValues().get(0));
        s= "Ljava/lang/String;";
        descriptors = new Descriptors(s);
        assertEquals("java/lang/String",descriptors.getValues().get(0));
    }

    @Test (expected = IOException.class)
    public void invalidArrayDescriptor() throws Exception
    {
        String s= "[";
        Descriptors descriptors = new Descriptors(s);
    }

    @Test (expected = IOException.class)
    public void invalidObjectDescriptor() throws Exception
    {
        String s= "L";
        Descriptors descriptors = new Descriptors(s);
    }

    @Test
    public void complexArrayDescriptor() throws Exception
    {
        String s= "[[[[I";
        Descriptors descriptors = new Descriptors(s);
        assertEquals("int[][][][]",descriptors.getValues().get(0));
    }

    @Test
    public void complexDescriptor() throws Exception
    {
        String s= "IDCJBZLjava/lang/String;[[I";
        Descriptors descriptors = new Descriptors(s);

        assertEquals("int",descriptors.getValues().get(0));
        assertEquals("double",descriptors.getValues().get(1));
        assertEquals("char",descriptors.getValues().get(2));
        assertEquals("long",descriptors.getValues().get(3));
        assertEquals("byte",descriptors.getValues().get(4));
        assertEquals("boolean",descriptors.getValues().get(5));
        assertEquals("java/lang/String",descriptors.getValues().get(6));
        assertEquals("int[][]",descriptors.getValues().get(7));
    }
}