package com.lx.demo;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ServiceLoader;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{

    public void testDynamicDatasourceDriver(){
        ServiceLoader<DatabaseDriver> load = ServiceLoader.load(DatabaseDriver.class);
        for (DatabaseDriver databaseDriver : load) {
            System.out.println(databaseDriver.connect("localhost"));
        }
    }

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
