/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vjezba2;

import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 *
 * @author zvone
 */
public class myCRC32NGTest {
    
    public myCRC32NGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of update method, of class myCRC32.
     */
    @Test
    public void testUpdate_int() {
        System.out.println("update");
        int b = 0;
        myCRC32 instance = new myCRC32();
        instance.update(b);
        
        CRC32 org = new CRC32();
        org.update(b);
        
        assertEquals(instance.getValue(), org.getValue());
    }

    /**
     * Test of update method, of class myCRC32.
     */
    @Test
    public void testUpdate_3args() {
        System.out.println("update");
        String str = "Generate CRC32 Checksum For Byte Array Example";
        byte bytes[] = str.getBytes();
        
        myChecksum checksum = new myCRC32();
        myCRC32 instance = new myCRC32();
        instance.update(bytes, 0, bytes.length);
        
        Checksum checksum2 = new CRC32();
        CRC32 org = new CRC32();
        org.update(bytes, 0, bytes.length);
        
        assertEquals(instance.getValue(), org.getValue());
    }

    /**
     * Test of update method, of class myCRC32.
     */
    
    @Test
    public void testUpdate_byteArr() { 
        System.out.println("update");
        String str = "Generate CRC32 Checksum For Byte Array Example";
        byte b[] = str.getBytes();
        myCRC32 instance = new myCRC32();
        instance.update(b);
        
        CRC32 org = new CRC32();
        org.update(b);
        
        assertEquals(instance.getValue(), org.getValue());
    }

    /**
     * Test of reset method, of class myCRC32.
     */
    
    @Test
    public void testReset() {
        System.out.println("reset");
        myCRC32 instance = new myCRC32();
        instance.reset();
        
        CRC32 org = new CRC32();
        org.reset();
        
        assertEquals(instance.getValue(), org.getValue());
    }

    /**
     * Test of getValue method, of class myCRC32.
     */
    
    @Test
    public void testGetValue() {
        System.out.println("getValue");
        myCRC32 instance = new myCRC32();
        long expResult = 0L;
        long result = instance.getValue();
        
        assertEquals(result, expResult);
    }

    
}
