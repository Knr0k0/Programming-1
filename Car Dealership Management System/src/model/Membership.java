package model;

import model.user.Client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Membership {
    private final String silver="5";
    private final String gold="10";
    private final String platinum="15";

    public String setMembership(String clientID) {
        int totalSpending = 0;
        String mem;
        String filePath = "src/database/Transactions.txt";

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] transactionDetails = line.split(",");
                if (clientID.equals(transactionDetails[2])) {
                    totalSpending += Integer.parseInt(transactionDetails[6]);
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }

        if (totalSpending > 250000000) {
            mem = "platinum";
            return mem;
        } else if (totalSpending > 100000000) {
            mem = "gold";
            return mem;
        } else if (totalSpending > 30000000) {
            mem = "silver";
            return mem;
        } else {
            mem = "none";
            return mem;
        }
    }
}
