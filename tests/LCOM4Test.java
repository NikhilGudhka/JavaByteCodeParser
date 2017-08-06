/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class LCOM4Test
{
    @Test
    public void calculateLCOM4() throws Exception
    {
        LCOM4 lcom4 = new LCOM4();
        ClassFile cf = new ClassFile("Author.class");
        assertEquals(1,lcom4.calculateLCOM4(cf.getMethods(),cf.getFields()));
        cf = new ClassFile("Evil.class");
        assertEquals(2,lcom4.calculateLCOM4(cf.getMethods(),cf.getFields()));
        cf = new ClassFile("Swan.class");
        assertEquals(3,lcom4.calculateLCOM4(cf.getMethods(),cf.getFields()));
        cf = new ClassFile("Snow.class");
        assertEquals(2,lcom4.calculateLCOM4(cf.getMethods(),cf.getFields()));
        cf = new ClassFile("Charming.class");
        assertEquals(4,lcom4.calculateLCOM4(cf.getMethods(),cf.getFields()));
        cf = new ClassFile("SGMXBeanImpl.class");
        assertEquals(5,lcom4.calculateLCOM4(cf.getMethods(),cf.getFields()));
    }

}