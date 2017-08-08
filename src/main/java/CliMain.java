import controllers.FileController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by mackenzie on 07/08/17.
 */
public class CliMain {

    public static void main(String args[]) throws IOException {
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
        customerCSV = "/home/mackenzie/workspace/copiaTest/src/main/java/Customers.csv";

        FileController cFC = new FileController(customerCSV);

        while(true){
            String line = Arrays.toString(cFC.readLine());
            if(line == null){
                break;
            }
            System.out.println(line);
        }

    }
}

