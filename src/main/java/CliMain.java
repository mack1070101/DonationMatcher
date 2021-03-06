import controllers.DatabaseController;
import controllers.FileController;
import controllers.LogicController;
import models.Pickup;
import models.Recipient;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by mackenzie on 07/08/17.
 *
 *
 * Handles the very limited CLI interface, as well as most app functionality
 */

@SuppressWarnings("WeakerAccess")
public class CliMain {

    public static void main(String args[]) throws IOException, SQLException {
        String defaultOutputFilename = "result.csv";
        String customerCSV;
        String recipientCSV;

        System.out.println("Matching food pickups and donations");
        System.out.println("A programming challenge by Mackenzie Bligh");
        System.out.println("See https://github.com/mack1070101/copiaTest/wiki for docs and details");


        Scanner scanner = new Scanner(System.in);
        FileController fileController = new FileController();

        //TODO validate first line of file to ensure that they are the correct file
        while(true) {
            System.out.print("Enter filename of csv containing customer data: ");
            customerCSV = scanner.nextLine();

            if (fileController.isValid(customerCSV)){
                break;
            }
            else{
                System.out.println("Please enter a valid filename");
            }
        }

        while(true){
            System.out.print("Enter filename csv containing recipient data: ");
            recipientCSV = scanner.nextLine();
            if(fileController.isValid(recipientCSV)) {
                break;
            } else {
                System.out.println("Please enter a valid filename");
            }
        }
        System.out.print("NOTE: This will overwrite the file if it exists.\nEnter desired filename of output or leave blank for " + defaultOutputFilename + ": ");
        String outputFile = scanner.nextLine();
        if (outputFile.equals("")){
            outputFile = defaultOutputFilename;
        }
        File o = new File(outputFile);
        o.delete();


        FileController cFC = new FileController(customerCSV);
        FileController rFC = new FileController(recipientCSV);
        DatabaseController dbC = new DatabaseController();
        dbC.createPersonsTable();
        dbC.createPickupTable();
        dbC.createRecipientTable();

        //TODO build into db controller
        while (true) {
            try {
                int i = 0;
                String[] line = cFC.readLine();
                for (String str : line) {
                    line[i] = str.replaceAll("'", "");
                    i++;
                }
                dbC.insertIntoPersonTable(Arrays.copyOfRange(line, 0, 9));
                dbC.insertIntoPickupTable(Arrays.copyOfRange(line, 8, 14));
            } catch (NullPointerException e) {
                break;
            }
        }
        while (true) {
            try {
                int i = 0;
                String[] line = rFC.readLine();
                for (String str : line) {
                    line[i] = str.replaceAll("'", "");
                    i++;
                }
                dbC.insertIntoPersonTable(Arrays.copyOfRange(line, 0, 9));
                dbC.insertIntoRecipientTable(Arrays.copyOfRange(line, 8, 19));
            } catch (NullPointerException e) {
                break;
            }
        }

        LogicController lc = new LogicController(dbC);
        HashMap<String, ArrayList<Recipient>> map = lc.findMatches();

        Iterator it = map.entrySet().iterator();

        FileController outputFileController = new FileController(outputFile);
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            Pickup pickup = dbC.getPickup((String) pair.getKey());
            try{
                for(Recipient recipient: map.get(pair.getKey())){
                    outputFileController.writeLine(pickup,recipient, dbC);
                }
            }catch (Exception e){
                outputFileController.writeLine(pickup, dbC);
            }
        }

        outputFileController.close();
        cFC.close();
        rFC.close();
        dbC.close();

        System.out.println("Program Complete");
    }
}
