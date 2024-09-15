package service;

import model.user.Mechanic;
import model.user.Salesperson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ListOperation {

    // List all the cars sold in a day/week/month
    public List<String> carSoldInDay() {
        String filePath = "src/database/Transactions.txt";
        List<String> carSoldIDList = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fmd = now.format(dateTimeFormatter);

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] transactionDetails = line.split(",");
                if (fmd.equals(transactionDetails[1])) {
                    String[] items = transactionDetails[4].split(";");
                    for (String item: items) {
                        if (item.contains("c")) {
                            carSoldIDList.add(item);
                        }
                    }
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return carSoldIDList;
    }

    public List<String> carSoldInWeek() {
        String filePath = "src/database/Transactions.txt";
        List<String> carSoldIDList = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime sevenDayAgo = now.minusDays(7);

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] transactionDetails = line.split(",");
                LocalDate dateFile = LocalDate.parse(transactionDetails[1], dateTimeFormatter);
                if (dateFile.isAfter(ChronoLocalDate.from(sevenDayAgo)) && dateFile.isBefore(ChronoLocalDate.from(now)) || dateFile.isEqual(ChronoLocalDate.from(now))) {
                    String[] items = transactionDetails[4].split(";");
                    for (String item: items) {
                        if (item.contains("c")) {
                            carSoldIDList.add(item);
                        }
                    }
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return carSoldIDList;
    }

    public List<String> carSoldInMonth() {
        String filePath = "src/database/Transactions.txt";
        List<String> carSoldIDList = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime monthAgo = now.minusDays(30);

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] transactionDetails = line.split(",");
                LocalDate dateFile = LocalDate.parse(transactionDetails[1], dateTimeFormatter);
                if (dateFile.isAfter(ChronoLocalDate.from(monthAgo)) && dateFile.isBefore(ChronoLocalDate.from(now)) || dateFile.isEqual(ChronoLocalDate.from(now))) {
                    String[] items = transactionDetails[4].split(";");
                    for (String item: items) {
                        if (item.contains("c")) {
                            carSoldIDList.add(item);
                        }
                    }
                }

            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return carSoldIDList;
    }

    public List<String> carSoldInfo(List<String> carSoldIDList) {
        String filePath = "src/database/Cars.txt";

        List<String> carSoldList = new ArrayList<>();
        String carInfo;

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] carDetails = line.split(",");
                for (String carID: carSoldIDList) {
                    if (carID.equals(carDetails[0])) {
                        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
                        int price = Integer.parseInt(carDetails[7]);
                        String priceFormat = numberFormat.format(price);

                        carInfo = "Car ID: " + carDetails[0] + " - " +
                                "Make: " + carDetails[1] + " - " +
                                "Model: " + carDetails[2] + " - " +
                                "Year: " + carDetails[3] + " - " +
                                "Mileage: " + carDetails[4] + " - " +
                                "Color: " + carDetails[5] + " - " +
                                "Price: " + priceFormat + " VND";

                        carSoldList.add(carInfo);
                    }
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }

        return carSoldList;
    }
    // -------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------


    // List all the transactions in a day/week/month
    public List<String> transactionInDay() {
        String filePath = "src/database/Transactions.txt";
        List<String> transactionList = new ArrayList<>();
        String transactionInfo;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fmd = now.format(dateTimeFormatter);

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] transactionDetails = line.split(",");
                if (fmd.equals(transactionDetails[1])) {
                    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
                    int totalAmount = Integer.parseInt(transactionDetails[6]);
                    String formattedTotalAmount = numberFormat.format(totalAmount);

                    transactionInfo = "Transaction ID: " + transactionDetails[0] + " - " +
                            "Client ID: " + transactionDetails[2] + " - " +
                            "Salesperson ID: " + transactionDetails[3] + " - " +
                            "Purchased Items: " + transactionDetails[4] + " - " +
                            "Discount: " + transactionDetails[5] + "%" + " - " +
                            "Total amount: " + formattedTotalAmount + " VND";

                    transactionList.add(transactionInfo);
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return transactionList;
    }

    public List<String> transactionInWeek() {
        String filePath = "src/database/Transactions.txt";
        List<String> transactionList = new ArrayList<>();
        String transactionInfo;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime sevenDayAgo = now.minusDays(7);

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] transactionDetails = line.split(",");
                LocalDate dateFile = LocalDate.parse(transactionDetails[1], dateTimeFormatter);
                if (dateFile.isAfter(ChronoLocalDate.from(sevenDayAgo)) && dateFile.isBefore(ChronoLocalDate.from(now)) || dateFile.isEqual(ChronoLocalDate.from(now))) {
                    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
                    int totalAmount = Integer.parseInt(transactionDetails[6]);
                    String formattedTotalAmount = numberFormat.format(totalAmount);

                    transactionInfo = "Transaction ID: " + transactionDetails[0] + " - " +
                            "Client ID: " + transactionDetails[2] + " - " +
                            "Salesperson ID: " + transactionDetails[3] + " - " +
                            "Purchased Items: " + transactionDetails[4] + " - " +
                            "Discount: " + transactionDetails[5] + "%" + " - " +
                            "Total amount: " + formattedTotalAmount + " VND";

                    transactionList.add(transactionInfo);
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return transactionList;
    }

    public List<String> transactionInMonth() {
        String filePath = "src/database/Transactions.txt";
        List<String> transactionList = new ArrayList<>();
        String transactionInfo;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime monthAgo = now.minusDays(30);

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] transactionDetails = line.split(",");
                LocalDate dateFile = LocalDate.parse(transactionDetails[1], dateTimeFormatter);
                if (dateFile.isAfter(ChronoLocalDate.from(monthAgo)) && dateFile.isBefore(ChronoLocalDate.from(now)) || dateFile.isEqual(ChronoLocalDate.from(now))) {
                    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
                    int totalAmount = Integer.parseInt(transactionDetails[6]);
                    String formattedTotalAmount = numberFormat.format(totalAmount);

                    transactionInfo = "Transaction ID: " + transactionDetails[0] + " - " +
                            "Client ID: " + transactionDetails[2] + " - " +
                            "Salesperson ID: " + transactionDetails[3] + " - " +
                            "Purchased Items: " + transactionDetails[4] + " - " +
                            "Discount: " + transactionDetails[5] + "%" + " - " +
                            "Total amount: " + formattedTotalAmount + " VND";

                    transactionList.add(transactionInfo);
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return transactionList;
    }
    // -------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------

    // List all the services done in a day/week/month
    public List<String> serviceDoneInDay() {
        String filePath = "src/database/Services.txt";
        List<String> serviceDoneList = new ArrayList<>();
        String serviceDoneInfo;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fmd = now.format(dateTimeFormatter);

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] serviceDetails = line.split(",");
                if (fmd.equals(serviceDetails[1])) {
                    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
                    int serviceCost = Integer.parseInt(serviceDetails[6]);
                    String formattedServiceCost = numberFormat.format(serviceCost);

                    serviceDoneInfo = "Service ID: " + serviceDetails[0] + " - " +
                            "Client ID: " + serviceDetails[2] + " - " +
                            "Mechanic ID: " + serviceDetails[3] + " - " +
                            "Service type: " + serviceDetails[4] + " - " +
                            "Replaced parts: " + serviceDetails[5] + " - " +
                            "Service cost: " + formattedServiceCost + " VND";

                    serviceDoneList.add(serviceDoneInfo);
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return serviceDoneList;
    }

    public List<String> serviceDoneInWeek() {
        String filePath = "src/database/Services.txt";
        List<String> serviceDoneList = new ArrayList<>();
        String serviceDoneInfo;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime sevenDayAgo = now.minusDays(7);

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] serviceDetails = line.split(",");
                LocalDate dateFile = LocalDate.parse(serviceDetails[1], dateTimeFormatter);
                if (dateFile.isAfter(ChronoLocalDate.from(sevenDayAgo)) && dateFile.isBefore(ChronoLocalDate.from(now)) || dateFile.isEqual(ChronoLocalDate.from(now))) {
                    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
                    int serviceCost = Integer.parseInt(serviceDetails[6]);
                    String formattedServiceCost = numberFormat.format(serviceCost);

                    serviceDoneInfo = "Service ID: " + serviceDetails[0] + " - " +
                            "Client ID: " + serviceDetails[2] + " - " +
                            "Mechanic ID: " + serviceDetails[3] + " - " +
                            "Service type: " + serviceDetails[4] + " - " +
                            "Replaced parts: " + serviceDetails[5] + " - " +
                            "Service cost: " + formattedServiceCost + " VND";

                    serviceDoneList.add(serviceDoneInfo);
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return serviceDoneList;
    }

    public List<String> serviceDoneInMonth() {
        String filePath = "src/database/Services.txt";
        List<String> serviceDoneList = new ArrayList<>();
        String serviceDoneInfo;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime monthAgo = now.minusDays(30);

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] serviceDetails = line.split(",");
                LocalDate dateFile = LocalDate.parse(serviceDetails[1], dateTimeFormatter);
                if (dateFile.isAfter(ChronoLocalDate.from(monthAgo)) && dateFile.isBefore(ChronoLocalDate.from(now)) || dateFile.isEqual(ChronoLocalDate.from(now))) {
                    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
                    int serviceCost = Integer.parseInt(serviceDetails[6]);
                    String formattedServiceCost = numberFormat.format(serviceCost);

                    serviceDoneInfo = "Service ID: " + serviceDetails[0] + " - " +
                            "Client ID: " + serviceDetails[2] + " - " +
                            "Mechanic ID: " + serviceDetails[3] + " - " +
                            "Service type: " + serviceDetails[4] + " - " +
                            "Replaced parts: " + serviceDetails[5] + " - " +
                            "Service cost: " + formattedServiceCost + " VND";

                    serviceDoneList.add(serviceDoneInfo);
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return serviceDoneList;
    }
    // -------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------

    // List all the auto parts sold in a day/week/month
    public List<String> autoPartSoldInDay() {
        String filePath = "src/database/Transactions.txt";
        List<String> autoPartSoldIDList = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fmd = now.format(dateTimeFormatter);

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] transactionDetails = line.split(",");
                if (fmd.equals(transactionDetails[1])) {
                    String[] items = transactionDetails[4].split(";");
                    for (String item: items) {
                        if (item.contains("p")) {
                            autoPartSoldIDList.add(item);
                        }
                    }
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return autoPartSoldIDList;
    }

    public List<String> autoPartSoldInWeek() {
        String filePath = "src/database/Transactions.txt";
        List<String> autoPartSoldIDList = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime sevenDayAgo = now.minusDays(7);

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] transactionDetails = line.split(",");
                LocalDate dateFile = LocalDate.parse(transactionDetails[1], dateTimeFormatter);
                if (dateFile.isAfter(ChronoLocalDate.from(sevenDayAgo)) && dateFile.isBefore(ChronoLocalDate.from(now)) || dateFile.isEqual(ChronoLocalDate.from(now))) {
                    String[] items = transactionDetails[4].split(";");
                    for (String item: items) {
                        if (item.contains("p")) {
                            autoPartSoldIDList.add(item);
                        }
                    }
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return autoPartSoldIDList;
    }

    public List<String> autoPartSoldInMonth() {
        String filePath = "src/database/Transactions.txt";
        List<String> autoPartSoldIDList = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime monthAgo = now.minusDays(30);

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] transactionDetails = line.split(",");
                LocalDate dateFile = LocalDate.parse(transactionDetails[1], dateTimeFormatter);
                if (dateFile.isAfter(ChronoLocalDate.from(monthAgo)) && dateFile.isBefore(ChronoLocalDate.from(now)) || dateFile.isEqual(ChronoLocalDate.from(now))) {
                    String[] items = transactionDetails[4].split(";");
                    for (String item: items) {
                        if (item.contains("p")) {
                            autoPartSoldIDList.add(item);
                        }
                    }
                }

            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return autoPartSoldIDList;
    }

    public List<String> autoPartSoldInfo(List<String> autoPartSoldIDList) {
        String filePath = "src/database/Parts.txt";
        List<String> autoPartSoldList = new ArrayList<>();
        String autoPartInfo;

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] autoPartDetails = line.split(",");
                for (String autoPartID : autoPartSoldIDList) {
                    if (autoPartID.trim().equals(autoPartDetails[0])) {
                        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
                        int cost = Integer.parseInt(autoPartDetails[6]);
                        String costFormat = numberFormat.format(cost);

                        autoPartInfo = "Part ID: " + autoPartDetails[0] + " - " +
                                "Part name: " + autoPartDetails[1] + " - " +
                                "Manufacturer: " + autoPartDetails[2] + " - " +
                                "Part number: " + autoPartDetails[3] + " - " +
                                "Condition: " + autoPartDetails[4] + " - " +
                                "Warranty: " + autoPartDetails[5] + " - " +
                                "Cost: " + costFormat + " VND";

                        autoPartSoldList.add(autoPartInfo);
                    }
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }

        return autoPartSoldList;
    }
    // -------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------

    // Menu list
    public void listMenu() {
        while (true) {
            System.out.println("--- List Menu ---");
            System.out.println("1. List all the cars sold in a day");
            System.out.println("2. List all the cars sold in a week");
            System.out.println("3. List all the cars sold in a month");
            System.out.println("4. List all the transactions in a day");
            System.out.println("5. List all the transactions in a week");
            System.out.println("6. List all the transactions in a month");
            System.out.println("7. List all the services done in a day");
            System.out.println("8. List all the services done in a week");
            System.out.println("9. List all the services done in a month");
            System.out.println("10. List all the auto parts sold in a day");
            System.out.println("11. List all the auto parts sold in a week");
            System.out.println("12. List all the auto parts sold in a month");
            System.out.println("13. Back");
            System.out.print("Enter your choice: ");

            ListOperation list = new ListOperation();
            Scanner scanner = new Scanner(System.in);

//            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
//            int revenueInDay = cal.revenueInDay();
//            String formattedRevenueInDay = numberFormat.format(revenueInDay);
//            int revenueInWeek = cal.revenueInWeek();
//            String formattedRevenueInWeek = numberFormat.format(revenueInWeek);
//            int revenueInMonth = cal.revenueInMonth();
//            String formattedRevenueInMonth = numberFormat.format(revenueInMonth);

            int mainChoice = scanner.nextInt();
            scanner.nextLine();
            switch (mainChoice) {
                case 1 -> {
                    System.out.println("-------------------------");
                    for (String car: carSoldInfo(carSoldInDay())) {
                        System.out.println(car);
                    }
                    System.out.println("-------------------------");
                }
                case 2 -> {
                    System.out.println("-------------------------");
                    for (String car: carSoldInfo(carSoldInWeek())) {
                        System.out.println(car);
                    }
                    System.out.println("-------------------------");
                }
                case 3 -> {
                    System.out.println("-------------------------");
                    for (String car: carSoldInfo(carSoldInMonth())) {
                        System.out.println(car);
                    }
                    System.out.println("-------------------------");
                }
                case 4 -> {
                    System.out.println("-------------------------");
                    for (String transaction: transactionInDay()) {
                        System.out.println(transaction);
                    }
                    System.out.println("-------------------------");
                }
                case 5 -> {
                    System.out.println("-------------------------");
                    for (String transaction: transactionInWeek()) {
                        System.out.println(transaction);
                    }
                    System.out.println("-------------------------");
                }
                case 6 -> {
                    System.out.println("-------------------------");
                    for (String transaction: transactionInMonth()) {
                        System.out.println(transaction);
                    }
                    System.out.println("-------------------------");
                }
                case 7 -> {
                    System.out.println("-------------------------");
                    for (String serviceDone: serviceDoneInDay()) {
                        System.out.println(serviceDone);
                    }
                    System.out.println("-------------------------");
                }
                case 8 -> {
                    System.out.println("-------------------------");
                    for (String serviceDone: serviceDoneInWeek()) {
                        System.out.println(serviceDone);
                    }
                    System.out.println("-------------------------");
                }
                case 9 -> {
                    System.out.println("-------------------------");
                    for (String serviceDone: serviceDoneInMonth()) {
                        System.out.println(serviceDone);
                    }
                    System.out.println("-------------------------");
                }
                case 10 -> {
                    System.out.println("-------------------------");
                    for (String autoPart : autoPartSoldInfo(autoPartSoldInDay())) {
                        System.out.println(autoPart);
                    }
                    System.out.println("-------------------------");
                }
                case 11 -> {
                    System.out.println("-------------------------");
                    for (String autoPart : autoPartSoldInfo(autoPartSoldInWeek())) {
                        System.out.println(autoPart);
                    }
                    System.out.println("-------------------------");
                }
                case 12 -> {
                    System.out.println("-------------------------");
                    for (String autoPart : autoPartSoldInfo(autoPartSoldInMonth())) {
                        System.out.println(autoPart);
                    }
                    System.out.println("-------------------------");
                }
                case 13 -> {
                    System.out.println("-------------------------");
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }
    //--------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------
    public int mechanicServiceDoneInDay(Mechanic mechanic) {
        String filePath = "src/database/Services.txt";

        int count = 0;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fmd = now.format(dateTimeFormatter);

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] serviceDetails = line.split(",");
                if (mechanic.getUserID().equals(serviceDetails[3]) && fmd.equals(serviceDetails[1])) {
                    count += 1;
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return count;
    }

    public int mechanicServiceDoneInWeek(Mechanic mechanic) {
        String filePath = "src/database/Services.txt";

        int count = 0;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime sevenDayAgo = now.minusDays(7);

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] serviceDetails = line.split(",");
                LocalDate dateFile = LocalDate.parse(serviceDetails[1], dateTimeFormatter);
                if (mechanic.getUserID().equals(serviceDetails[3]) && dateFile.isAfter(ChronoLocalDate.from(sevenDayAgo)) && dateFile.isBefore(ChronoLocalDate.from(now)) || dateFile.isEqual(ChronoLocalDate.from(now))) {
                    count += 1;
                }

            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return count;
    }

    public int mechanicServiceDoneInMonth(Mechanic mechanic) {
        String filePath = "src/database/Services.txt";

        int count = 0;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime monthAgo = now.minusDays(30);

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] serviceDetails = line.split(",");
                LocalDate dateFile = LocalDate.parse(serviceDetails[1], dateTimeFormatter);
                if (mechanic.getUserID().equals(serviceDetails[3]) && dateFile.isAfter(ChronoLocalDate.from(monthAgo)) && dateFile.isBefore(ChronoLocalDate.from(now)) || dateFile.isEqual(ChronoLocalDate.from(now))) {
                    count += 1;
                }

            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return count;
    }

    public void mechanicListMenu(Mechanic mechanic) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("--- List Menu ---");
            System.out.println("1. List the number of services in a day");
            System.out.println("2. List the number of services in a week");
            System.out.println("3. List the number of services in a month");
            System.out.println("4. Back");
            System.out.print("Enter your choice: ");
            int mainChoice = scanner.nextInt();

            Calculate cal = new Calculate();

//            scanner.nextLine();
            switch (mainChoice) {
                case 1 -> {
                    System.out.println("-------------------------");
                    System.out.printf("Number of service done in a day: %d\n", mechanicServiceDoneInDay(mechanic));
                    System.out.println("-------------------------");
                }
                case 2 -> {
                    System.out.println("-------------------------");
                    System.out.printf("Number of service done in a week: %d\n", mechanicServiceDoneInWeek(mechanic));
                    System.out.println("-------------------------");
                }
                case 3 -> {
                    System.out.println("-------------------------");
                    System.out.printf("Number of service done in a month: %d\n", mechanicServiceDoneInMonth(mechanic));
                    System.out.println("-------------------------");
                }
                case 4 -> {
                    System.out.println("-------------------------");
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }
    //--------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------
    public int salespersonCarSoldInDay(Salesperson salesperson) {
        String filePath = "src/database/Transactions.txt";

        int count = 0;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fmd = now.format(dateTimeFormatter);

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] transactionDetails = line.split(",");
                if (salesperson.getUserID().equals(transactionDetails[3]) && fmd.equals(transactionDetails[1])) {
                    count += 1;
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return count;
    }

    public int salespersonCarSoldInWeek(Salesperson salesperson) {
        String filePath = "src/database/Transactions.txt";

        int count = 0;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime sevenDayAgo = now.minusDays(7);

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] transactionDetails = line.split(",");
                LocalDate dateFile = LocalDate.parse(transactionDetails[1], dateTimeFormatter);
                if (salesperson.getUserID().equals(transactionDetails[3]) && dateFile.isAfter(ChronoLocalDate.from(sevenDayAgo)) && dateFile.isBefore(ChronoLocalDate.from(now)) || dateFile.isEqual(ChronoLocalDate.from(now))) {
                    count += 1;
                }

            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return count;
    }

    public int salespersonCarSoldInMonth(Salesperson salesperson) {
        String filePath = "src/database/Transactions.txt";

        int count = 0;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime monthAgo = now.minusDays(30);

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] transactionDetails = line.split(",");
                LocalDate dateFile = LocalDate.parse(transactionDetails[1], dateTimeFormatter);
                if (salesperson.getUserID().equals(transactionDetails[3]) && dateFile.isAfter(ChronoLocalDate.from(monthAgo)) && dateFile.isBefore(ChronoLocalDate.from(now)) || dateFile.isEqual(ChronoLocalDate.from(now))) {
                    count += 1;
                }

            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return count;
    }

    public void salespersonListMenu(Salesperson salesperson) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("--- List Menu ---");
            System.out.println("1. List the number of cars in a day");
            System.out.println("2. List the number of cars in a week");
            System.out.println("3. List the number of cars in a month");
            System.out.println("4. Back");
            System.out.print("Enter your choice: ");
            int mainChoice = scanner.nextInt();

            Calculate cal = new Calculate();

//            scanner.nextLine();
            switch (mainChoice) {
                case 1 -> {
                    System.out.println("-------------------------");
                    System.out.printf("Number of car sold in a day: %d\n", salespersonCarSoldInDay(salesperson));
                    System.out.println("-------------------------");
                }
                case 2 -> {
                    System.out.println("-------------------------");
                    System.out.printf("Number of car sold in a week: %d\n", salespersonCarSoldInWeek(salesperson));
                    System.out.println("-------------------------");
                }
                case 3 -> {
                    System.out.println("-------------------------");
                    System.out.printf("Number of car sold in a month: %d\n", salespersonCarSoldInMonth(salesperson));
                    System.out.println("-------------------------");
                }
                case 4 -> {
                    System.out.println("-------------------------");
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
