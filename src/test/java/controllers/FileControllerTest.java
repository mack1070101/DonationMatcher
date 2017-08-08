package controllers;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by mackenzie on 07/08/17.
 */
public class FileControllerTest {
    private File resources = new File("src/test/java/testAssets");
    private String absFilePath = resources.getAbsolutePath();
    private FileController fileController = new FileController();

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
}