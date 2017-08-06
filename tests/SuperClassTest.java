/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

import org.junit.Test;
import static org.junit.Assert.*;

public class SuperClassTest
{
    @Test
    public void getSuperclassName() throws Exception
    {
        SuperClass superClass = new SuperClass();
        assertEquals(null,superClass.getSuperclassName());
    }

    @Test
    public void isSuccess() throws Exception
    {
        SuperClass superClass = new SuperClass();
        assertEquals(false,superClass.isSuccess());
    }

}