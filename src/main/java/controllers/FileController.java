package controllers;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mackenzie on 07/08/17.
 *
 * Handles file operations, opening, reading and validation
 */
public class FileController {

    private Pattern pattern;
    private Matcher matcher;

    private static final String CSV_PATTERN =
           "([^\\s]+(\\.(?i)(csv))$)";

    /**
     * For performing matching and operations on files
     */
    public FileController(){
        pattern = Pattern.compile(CSV_PATTERN);
    }

    public boolean isValid(final String filename){
        matcher = pattern.matcher(filename);
        if(!matcher.matches()){
            return false;
        } else if (new File(filename).exists()){
            return true;
        } else{
            return false;
        }

    }
}
