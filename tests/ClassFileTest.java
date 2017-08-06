/**
 * Created By Nikhil Gudhka 
 * Copyright © Nikhil Gudhka. All rights reserved.
 */

import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class ClassFileTest
{
    private ClassFile cf;

    public ClassFileTest() throws IOException, ClassFileParserException
    {
        cf  = new ClassFile("SGMXBeanImpl.class");
    }

    @Test
    public void getThisClass() throws Exception
    {
        assertEquals("com/oracle/javafx/jmx/SGMXBeanImpl",cf.getThisClass());
    }

    @Test
    public void getAccessFlags() throws Exception
    {
        List<String> testList = new ArrayList<String>();
        testList.add("public");
        testList.add("super");
        assertEquals(2,cf.getAccessFlags().size());
        for (int i = 0; i < cf.getAccessFlags().size(); i++)
        {
            assertEquals(testList.get(i),cf.getAccessFlags().get(i));
        }
    }

    @Test
    public void getFields() throws Exception
    {
        /*Test Field Names*/
        List<String> testFieldNameList = new ArrayList<String>();
        testFieldNameList.add("SGMX_NOT_PAUSED_TEXT");
        testFieldNameList.add("SGMX_CALL_GETSGTREE_FIRST");
        testFieldNameList.add("paused");
        testFieldNameList.add("windowMap");
        testFieldNameList.add("jwindows");
        testFieldNameList.add("nodeMap");
        testFieldNameList.add("jsceneGraphs");
        testFieldNameList.add("scene2Image");
        testFieldNameList.add("playersToResume");
        for (int i = 0; i < cf.getFields().size(); i++)
        {
            assertEquals(testFieldNameList.get(i),cf.getFields().get(i).getFieldName());
        }

        /*Test Field Types*/
        List<String> testFieldDescriptorTypeList = new ArrayList<>();
        testFieldDescriptorTypeList.add("java/lang/String");
        testFieldDescriptorTypeList.add("java/lang/String");
        testFieldDescriptorTypeList.add("boolean");
        testFieldDescriptorTypeList.add("java/util/Map");
        testFieldDescriptorTypeList.add("com/oracle/javafx/jmx/json/JSONDocument");
        testFieldDescriptorTypeList.add("java/util/Map");
        testFieldDescriptorTypeList.add("com/oracle/javafx/jmx/json/JSONDocument[]");
        testFieldDescriptorTypeList.add("java/util/Map");
        testFieldDescriptorTypeList.add("java/util/List");
        for (int i = 0; i < cf.getFields().size(); i++)
        {
            assertEquals(testFieldDescriptorTypeList.get(i),cf.getFields().get(i).getDescriptorType());
        }

        /*Test Field Types JVM Format*/
        List<String> testFieldDescriptorDataList = new ArrayList<>();
        testFieldDescriptorDataList.add("Ljava/lang/String;");
        testFieldDescriptorDataList.add("Ljava/lang/String;");
        testFieldDescriptorDataList.add("Z");
        testFieldDescriptorDataList.add("Ljava/util/Map;");
        testFieldDescriptorDataList.add("Lcom/oracle/javafx/jmx/json/JSONDocument;");
        testFieldDescriptorDataList.add("Ljava/util/Map;");
        testFieldDescriptorDataList.add("[Lcom/oracle/javafx/jmx/json/JSONDocument;");
        testFieldDescriptorDataList.add("Ljava/util/Map;");
        testFieldDescriptorDataList.add("Ljava/util/List;");
        for (int i = 0; i < cf.getFields().size(); i++)
        {
            assertEquals(testFieldDescriptorDataList.get(i),cf.getFields().get(i).getDescriptorData());
        }

        List<ArrayList<String>> testFieldAccessFlagsList = new ArrayList<>();

        List<String> test = new ArrayList<>();
        test.add("private");
        test.add("static");
        test.add("final");
        testFieldAccessFlagsList.add((ArrayList<String>) test);
        test = new ArrayList<>();
        test.add("private");
        test.add("static");
        test.add("final");
        testFieldAccessFlagsList.add((ArrayList<String>) test);
        for (int i = 0; i < 7; i++)
        {
            test = new ArrayList<>();
            test.add("private");
            testFieldAccessFlagsList.add((ArrayList<String>) test);
        }


        for (int i = 0; i < cf.getFields().size(); i++)
        {
            for (int j = 0; j < cf.getFields().get(i).getAccessFlags().size(); j++)
            {
                assertEquals(testFieldAccessFlagsList.get(i).get(j),cf.getFields().get(i).getAccessFlags().get(j));
            }
        }
    }

    @Test
    public void getMethods() throws Exception
    {
        /*Test Method Names*/
        List<String> methodsTestList = new ArrayList<>();
        methodsTestList.add("<init>");
        methodsTestList.add("pause");
        methodsTestList.add("resume");
        methodsTestList.add("step");
        methodsTestList.add("getWindows");
        methodsTestList.add("getSGTree");
        methodsTestList.add("addHighlightedNode");
        methodsTestList.add("removeHighlightedNode");
        methodsTestList.add("addHighlightedRegion");
        methodsTestList.add("removeHighlightedRegion");
        methodsTestList.add("getNode");
        methodsTestList.add("getScene");
        methodsTestList.add("createHighlightRegion");
        methodsTestList.add("makeScreenShot");
        methodsTestList.add("makeScreenShot");
        methodsTestList.add("getScreenShotPath");
        methodsTestList.add("releaseAllStateObject");
        methodsTestList.add("clearWindowMap");
        methodsTestList.add("clearNodeMap");
        methodsTestList.add("clearScene2Image");
        methodsTestList.add("importWindowsIfNeeded");
        methodsTestList.add("importWindows");
        methodsTestList.add("importSGTree");
        methodsTestList.add("getCSSInfo");
        methodsTestList.add("processCssMetaData");
        methodsTestList.add("upcaseFirstLetter");
        methodsTestList.add("getBounds");
        methodsTestList.add("processLeafNode");
        methodsTestList.add("processContainerNode");
        methodsTestList.add("createJSONDocument");
        methodsTestList.add("pauseMedia");
        methodsTestList.add("resumeMedia");
        for (int i = 0; i < cf.getMethods().size(); i++)
        {
            assertEquals(methodsTestList.get(i), cf.getMethods().get(i).getNameIndexData());
        }

        List<String> testReturnTypeList = new ArrayList<>();
        testReturnTypeList.add("void");
        testReturnTypeList.add("void");
        testReturnTypeList.add("void");
        testReturnTypeList.add("void");
        testReturnTypeList.add("java/lang/String");
        testReturnTypeList.add("java/lang/String");
        testReturnTypeList.add("void");
        testReturnTypeList.add("void");
        testReturnTypeList.add("void");
        testReturnTypeList.add("void");
        testReturnTypeList.add("javafx/scene/Node");
        testReturnTypeList.add("javafx/scene/Scene");
        testReturnTypeList.add("com/sun/javafx/jmx/HighlightRegion");
        testReturnTypeList.add("java/lang/String");
        testReturnTypeList.add("java/lang/String");
        testReturnTypeList.add("java/lang/String");
        testReturnTypeList.add("void");
        testReturnTypeList.add("void");
        testReturnTypeList.add("void");
        testReturnTypeList.add("void");
        testReturnTypeList.add("void");
        testReturnTypeList.add("void");
        testReturnTypeList.add("void");
        testReturnTypeList.add("java/lang/String");
        testReturnTypeList.add("void");
        testReturnTypeList.add("java/lang/String");
        testReturnTypeList.add("java/lang/String");
        testReturnTypeList.add("java/lang/Object");
        testReturnTypeList.add("java/lang/Object");
        testReturnTypeList.add("com/oracle/javafx/jmx/json/JSONDocument");
        testReturnTypeList.add("void");
        testReturnTypeList.add("void");
        for (int i = 0; i < cf.getMethods().size(); i++)
        {
            assertEquals(testReturnTypeList.get(i), cf.getMethods().get(i).getReturnType());
        }

    }

    @Test
    public void getLcom4Value() throws Exception
    {
        assertEquals(5, cf.getLcom4Value());
    }

    @Test
    public void getFilename() throws Exception
    {
        assertEquals("SGMXBeanImpl.class",cf.getFilename());
    }

    @Test
    public void getSuperClass() throws Exception
    {
        assertEquals("java/lang/Object",cf.getSuperClass());
    }

    @Test
    public void getInterfaces() throws Exception
    {
        List<String> interfaceTestList = new ArrayList<>();
        interfaceTestList.add("com/oracle/javafx/jmx/SGMXBean");
        interfaceTestList.add("com/sun/javafx/jmx/MXNodeAlgorithm");

        for (int i = 0; i < cf.getInterfaces().size(); i++)
        {
            assertEquals(interfaceTestList.get(i),cf.getInterfaces().get(i));
        }
    }

    @Test
    public void getInterfaceCount() throws Exception
    {
        assertEquals(2,cf.getInterfaceCount());
    }

    @Test
    public void getMajorVersion() throws Exception
    {
        assertEquals(52,cf.getMajorVersion());
    }

    @Test
    public void getFieldCount() throws Exception
    {
        assertEquals(9,cf.getFieldCount());
    }

    @Test
    public void getMethodCount() throws Exception
    {
        assertEquals(32, cf.getMethodCount());
    }

    @Test
    public void getAttributesCount() throws Exception
    {
        assertEquals(2,cf.getAttributesCount());
    }

    @Test
    public void getAttributeInfo() throws Exception
    {
        assertEquals(null,cf.getAttributeInfo().get(0));
        assertEquals(null,cf.getAttributeInfo().get(1));
    }

    @Test
    public void isSuccess() throws Exception
    {
        assertEquals(true,cf.isSuccess());
    }

}