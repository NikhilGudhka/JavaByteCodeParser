/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

import org.junit.Test;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import static org.junit.Assert.*;

public class MethodsTest
{
    @Test(expected = InvalidTagException.class)
    public void parse() throws Exception
    {
        DataInputStream dis = new DataInputStream(new FileInputStream("Test.class"));
        ConstantPool cp = new ConstantPool(dis);
        Methods methods = new Methods();
        methods.parse(0,dis,cp);

    }

    @Test(expected = IOException.class)
    public void getMethodCountEx() throws Exception
    {
        DataInputStream dis = new DataInputStream(new FileInputStream(""));
        Methods methods = new Methods();
        methods.getMethodCount(dis);
    }

    @Test
    public void isSuccessFalse() throws Exception
    {
        Methods methods = new Methods();
        assertEquals(false,methods.isSuccess());
    }

    @Test
    public void isSuccess() throws Exception
    {
        ClassFile cf = new ClassFile("Test.class");

        assertFalse(cf.getMethods().isEmpty());

        for (int i = 0; i < cf.getMethods().size(); i++)
        {
            assertEquals(true,cf.getMethods().get(i).isSuccess());
        }

    }
}