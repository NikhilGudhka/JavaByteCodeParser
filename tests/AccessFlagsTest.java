/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class AccessFlagsTest
{
    @Test
    public void classAccessFlags() throws Exception
    {
        ClassFile cf = new ClassFile("Test.class");
        List<String> test = cf.getAccessFlags();
        assertEquals("public",test.get(0));
        assertEquals("super",test.get(1));
    }

    @Test
    public void fieldAccessFlags() throws Exception
    {
        ClassFile cf = new ClassFile("Test.class");
        List<String> test = cf.getFields().get(0).getAccessFlags();
        assertEquals("private",test.get(0));
        test = cf.getFields().get(1).getAccessFlags();
        assertEquals("private",test.get(0));
         test = cf.getFields().get(2).getAccessFlags();
        assertEquals("protected",test.get(0));
    }

    @Test
    public void methodAccessFlags() throws Exception
    {
        ClassFile cf = new ClassFile("Test.class");
        List<String> test = cf.getMethods().get(0).getAccessFlags();
        assertEquals("public",test.get(0));
        test = cf.getMethods().get(1).getAccessFlags();
        assertEquals("public",test.get(0));
        test = cf.getMethods().get(2).getAccessFlags();
        assertEquals("public",test.get(0));
        test = cf.getMethods().get(3).getAccessFlags();
        assertEquals("public",test.get(0));
        test = cf.getMethods().get(4).getAccessFlags();
        assertEquals("private",test.get(0));
        test = cf.getMethods().get(5).getAccessFlags();
        assertEquals("private",test.get(0));
        test = cf.getMethods().get(6).getAccessFlags();
        assertEquals("private",test.get(0));
        test = cf.getMethods().get(7).getAccessFlags();
        assertEquals("public",test.get(0));
    }

}