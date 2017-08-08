import controllers.DatabaseController;
import controllers.FileController;
import controllers.LogicController;
import models.Pickup;
import models.Recipient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by mackenzie on 07/08/17.
 */
public class CliMain {

    public static void main(String args[]) throws IOException, SQLException {
        String defaultOutputFilename = "result.csv";
        String customerCSV = new String();
        String recipientCSV = new String();

        System.out.println(" _______  _____   _____  _____ _______      _______ _______ _______ _______");
        System.out.println(" |       |     | |_____]   |   |_____|         |    |______ |______    |   ");
        System.out.println(" |_____  |_____| |       __|__ |     |         |    |______ ______|    |   ");

        System.out.println("\nA programming challenge by Mackenzie Bligh");
        System.out.println("See https://github.com/mack1070101/copiaTest/wiki for docs and details");

        Scanner scanner = new Scanner(System.in);
        FileController fileController = new FileController();
        /*
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
        System.out.print("Enter desired filename of output or leave blank for" + defaultOutputFilename + ": ");

        String outputFile = scanner.nextLine();
        if (outputFile.equals("")) outputFile = defaultOutputFilename;
        */
        customerCSV = "/home/mackenzie/workspace/copiaTest/src/test/java/testAssets/Customers.csv";
        recipientCSV = "/home/mackenzie/workspace/copiaTest/src/test/java/testAssets/Recipients.csv";

        FileController cFC = new FileController(customerCSV);
        FileController rFC = new FileController(recipientCSV);
        DatabaseController dbC = new DatabaseController();
        dbC.createPersonsTable();
        dbC.createPickupTable();
        dbC.createRecipientTable();

        //@TODO build into db controller
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
        HashMap<Pickup, ArrayList<Recipient>> map = lc.findMatches();

        Iterator it = map.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(map.get(pair.getKey()).get(0).getPersonId());
        }
    }
}
