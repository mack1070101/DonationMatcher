package controllers;

import models.Person;
import models.Pickup;
import models.Recipient;

import java.io.*;
import java.sql.SQLException;
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
    private boolean writtenFirstLine = false;
    private String filename;
    private BufferedReader br;
    private PrintWriter pw;
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

    /**
     * File controller to handle IO on a given file
     * @param name
     * @throws FileNotFoundException
     */
    public FileController(String name) throws FileNotFoundException {
        pattern = Pattern.compile(CSV_PATTERN);
        matcher = pattern.matcher(name);
        if (!matcher.matches()) {
            throw new IllegalArgumentException();
        } else{
            this.filename = name;
            try{
                this.br = new BufferedReader(new FileReader(this.filename));
            }catch(FileNotFoundException e){
                this.pw = new PrintWriter(new File(this.filename));
            }
        }

    }

    /**
     * Reads a line from the inputted CSV, while skipping the templating in the
     * first line
     * @return An array of strings containing the contents of one line in the file
     * @throws IOException
     */
    public String[] readLine() throws IOException {
        String[] line;
        if(!this.skippedFirstLine){
            this.skippedFirstLine = true;
            line = br.readLine().split(",");
            line = br.readLine().split(",");
        }else{
            line = br.readLine().split(",");
        }
        return line;
    }

    /**
     * Close out the file reader
     * @throws IOException
     */
    public void close() throws IOException {
        try{
            br.close();
        } catch (Exception e){
            pw.close();
        }

    }

    /**
     * Uses regex to check that a filename is valid and that it exists
     * @param filename
     * @return
     */
    public boolean isValid(final String filename){
        matcher = pattern.matcher(filename);
        if(!matcher.matches()){
            return false;
        } else return new File(filename).exists();
    }

    public void writeLine(Pickup pickup, Recipient recipient, DatabaseController dbc) throws SQLException {
        Person pickupOwner = dbc.getPerson(pickup.getPersonId());
        Person recipientOwner = dbc.getPerson(recipient.getPersonId());
        String writeString =pickupOwner.toCsv() +"," +pickup.toCsv() +","+ recipientOwner.toCsv()+"," + recipient.toCsv() +"\n";
        pw.write(writeString);
    }
    public void writeLine(Pickup pickup, DatabaseController dbc) throws SQLException {
        Person pickupOwner = dbc.getPerson(pickup.getPersonId());
        String writeString =pickupOwner.toCsv() +"," +pickup.toCsv() +",,,,,,,,,,,,,,,,,,,,\n";
        pw.write(writeString);
    }
}
