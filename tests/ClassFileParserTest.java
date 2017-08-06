/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

import org.junit.Test;
import static org.junit.Assert.*;

public class ClassFileParserTest
{
    @Test
    public void main() throws Exception
    {
        String[] s= {"TestDel.class"};
        ClassFileParser.main(s);
        String[] str= {"TestDel.clas"};
        ClassFileParser.main(str);
        String[] strEmpty= {""};
        ClassFileParser.main(strEmpty);
    }

}