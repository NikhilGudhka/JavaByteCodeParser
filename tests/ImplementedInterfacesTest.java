/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

import org.junit.Test;
import static org.junit.Assert.*;

public class ImplementedInterfacesTest
{
    @Test
    public void getInterfaceCount() throws Exception
    {
        ImplementedInterfaces implementedInterfaces = new ImplementedInterfaces();
        assertEquals(0,implementedInterfaces.getInterfaceCount());
    }

    @Test
    public void isSuccess() throws Exception
    {
        ImplementedInterfaces implementedInterfaces = new ImplementedInterfaces();
        assertEquals(false,implementedInterfaces.isSuccess());
    }

}