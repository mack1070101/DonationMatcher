package controllers;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mackenzie on 07/08/17.
 *
 * Handles file operations, opening, reading and validation
 *
 * @TODO document stuff
 */
public class FileController {

    private boolean skippedFirstLine = false;
    private String filename;
    private BufferedReader br;
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

    public FileController(String name) throws FileNotFoundException {
        pattern = Pattern.compile(CSV_PATTERN);
        matcher = pattern.matcher(name);
        if (!matcher.matches()) {
            throw new IllegalArgumentException();
        } else{
            this.filename = name;
            this.br = new BufferedReader(new FileReader(this.filename));
        }

    }

    public String[] readLine() throws IOException {
        String[] line;
        if(!this.skippedFirstLine){
           line = br.readLine().split(",");
           line = br.readLine().split(",");
        }else{
            line = br.readLine().split(",");
        }
        return line;
    }

    public void closeReader() throws IOException {
        br.close();
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
