/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.*;

public class PrintToTerminalTest
{
    @Test
    public void printToTerminal() throws Exception
    {
        ClassFile cf = new ClassFile("Author.class");
        PrintToTerminal printToTerminal = new PrintToTerminal();
        printToTerminal.printToTerminal(cf);
    }

    @Test (expected = NullPointerException.class)
    public void printToTerminalEx() throws Exception
    {
        ClassFile cf = new ClassFile("");
        PrintToTerminal printToTerminal = new PrintToTerminal();
        printToTerminal.printToTerminal(cf);
    }
}