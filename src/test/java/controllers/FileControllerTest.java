package controllers;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by mackenzie on 07/08/17.
 */
public class FileControllerTest {
    private final File resources = new File("src/test/java/testAssets");
    private final String absFilePath = resources.getAbsolutePath();
    private final FileController fileController = new FileController();


    @Test
    public void TestValidFileConstructor() throws FileNotFoundException {
        FileController fc = new FileController(absFilePath+"/test.csv");
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestInvalidFileConstructor() throws FileNotFoundException {
        FileController fc = new FileController(absFilePath+"/tst.txt");
    }

    @Test
    public void testValidFile(){
        System.out.println(absFilePath);
        boolean result = fileController.isValid(absFilePath+"/test.csv");
        assertEquals(true, result);
    }

    @Test
    public void testInvalidFile(){
        boolean result = fileController.isValid(absFilePath+"/testfile.txt");
        assertEquals(result, false);
    }

    @Test
    public void testNonexistantFile(){
        boolean result = fileController.isValid(absFilePath+"/lslclkdj.csv");
        assertEquals(result, false);
    }

    @Test
    public void testReadline() throws IOException {
        FileController validFc = new FileController(absFilePath+"/Customers.csv");
        validFc.readLine();
    }
}