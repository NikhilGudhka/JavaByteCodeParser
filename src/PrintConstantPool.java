/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

public class PrintConstantPool
{
    public void printToConsole(ConstantPool cp)
    {
        CPEntry[] cpEntries = cp.getEntries();
        String s = "Index  Entry type          Entry values\n" +
                "---------------------------------------\n";
        for(int i = 0; i < cpEntries.length; i++)
        {
            if(cpEntries[i] != null)
            {
                s += String.format("0x%02x   %-18s  %s\n",
                        i, cpEntries[i].getTagString(), cpEntries[i].getValues());
            }
        }
        System.out.println(s);
    }
}
