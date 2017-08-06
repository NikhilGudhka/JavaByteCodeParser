/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class CBOTest
{
    @Test
    public void calculateCBO() throws Exception
    {
        List<ClassFile> list = new ArrayList<>();
        ClassFile cf = new ClassFile("Author.class");
        list.add(cf);
        cf = new ClassFile("Snow.class");
        list.add(cf);
        cf = new ClassFile("Swan.class");
        list.add(cf);
        cf = new ClassFile("Evil.class");
        list.add(cf);
        cf = new ClassFile("Charming.class");
        list.add(cf);

        CBO cbo = new CBO();
        assertEquals(1.4,cbo.calculateCBO(list),0);
    }

    @Test
    public void zeroLinks() throws Exception
    {
        CBO cbo = new CBO();
        assertEquals(0,cbo.getLinks(),0);
    }

    @Test
    public void zeroNumOfClasses() throws Exception
    {
        CBO cbo = new CBO();
        assertEquals(0,cbo.getNumOfClasses());
    }

    @Test
    public void emptyReferList() throws Exception
    {
        CBO cbo = new CBO();
        assertEquals(null,cbo.getReferList());
    }

    @Test
    public void emptyClassNames() throws Exception
    {
        CBO cbo = new CBO();
        assertEquals(null,cbo.getAllClassnames());
    }



}