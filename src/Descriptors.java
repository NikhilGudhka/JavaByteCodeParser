/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Converts the JVM Field, Method and Parameter descriptors into java source format descriptors.
 * I.e. I is converted to int, D to double, etc.
 * Entire list can be found in the java virtual machine specification 8 under chapter 4.
 */
public class Descriptors
{
    private int var=0;
    private List<String> values;

    /**
     * Constructor with one string parameter.
     * Creates an array list of strings and calls method types, adding the string returned by the method to the list.
     * Loops for input length, but each time creates a substring with index var to str.length, stripping anything before index var
     * @param input - String containing the descriptors in JVM format to parse to java format
     */
    public Descriptors(String input) throws IOException
    {
        try
        {
            values = new ArrayList<String>();
            String str = input;
            for (int i = 0; i < input.length(); i = i + var)
            {
                values.add(types(str));
                str = str.substring(var, str.length());
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            throw new IOException("Error parsing Descriptors, ClassFile not in correct format");
        }
    }

    private String types(String str) throws IndexOutOfBoundsException
    {
        var = 0; //variable incremented each time if is successful
        String descriptor = "";
        if (str.charAt(0) =='(') //if open parentheses, add open parentheses
        {
            descriptor = "(";
            var=1;
        }
        else if (str.charAt(0) ==')') //if closing parentheses, add closing parentheses
        {
            descriptor = ")";
            var=1;
        }
        else if (str.charAt(0) =='V') //if character is V, set descriptor to void
        {
            descriptor = "void";
            var=1;
        }
        else if (str.charAt(0) =='B') //if character is B set descriptor to byte
        {
            descriptor = "byte";
            var=1;
        }
        else if (str.charAt(0) =='C') //if character is C set descriptor to char
        {
            descriptor = "char";
            var=1;
        }
        else if (str.charAt(0) =='D') //if character is D set descriptor to double
        {
            descriptor = "double";
            var=1;
        }
        else if (str.charAt(0) =='F') //if character is F set descriptor to float
        {
            descriptor = "float";
            var=1;
        }
        else if (str.charAt(0) =='I') //if character is I set descriptor to int
        {
            descriptor = "int";
            var=1;
        }
        else if (str.charAt(0) =='J') //if character is J set descriptor to long
        {
            descriptor = "long";
            var=1;
        }
        else if (str.charAt(0) =='S') //if character is S set descriptor to short
        {
            descriptor = "short";
            var=1;
        }
        else if (str.charAt(0) =='Z') //if character is Z set descriptor to boolean
        {
            descriptor = "boolean";
            var=1;
        }
        else if (str.charAt(0) =='[') //if character is { set descriptor to [] for array
        {
            String substr = types(str.substring(1,str.length())); //recursively find the type of array, i.e find the data type of the array and the dimensions of the array using recursion
            descriptor = substr+"[]";
            var+=1;
        }
        else if (str.charAt(0) =='L') //if character is L set descriptor to the substring that will be the object name
        {
            int a = str.indexOf(";", 0); //find the index of the first semi colon
            descriptor = str.substring(1, a); //create substring from index 1 to index of semi colon
            var+=(a+1); //set var to a + 1
        }
        return descriptor;
    }

    public List<String> getValues()
    {
        return values;
    }
}
