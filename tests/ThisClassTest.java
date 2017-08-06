/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

import org.junit.Test;
import static org.junit.Assert.*;

public class ThisClassTest
{

    @Test
    public void getClassName() throws Exception
    {
        ThisClass thisClass = new ThisClass();
        assertEquals(null,thisClass.getClassName());
    }

    @Test
    public void isSuccess() throws Exception
    {
        ThisClass thisClass = new ThisClass();
        assertEquals(false,thisClass.isSuccess());
    }

}