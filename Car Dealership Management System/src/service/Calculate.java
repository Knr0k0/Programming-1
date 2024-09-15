package service;

import model.user.Mechanic;
import model.user.Salesperson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Calculate {
    public int numberOfCars() {
        String filePath = "src/database/Transactions.txt";

        int countCars = 0;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime monthAgo = now.minusDays(30);

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] transactionDetails = line.split(",");
                if (transactionDetails[4].contains(";")) {
                    String[] items = transactionDetails[4].split(";");
                    for (String item: items) {
                        if (item.contains("c")) {
                            countCars++;
                        }
                    }
                } else if (transactionDetails[4].contains("c")) {
                    countCars++;
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return countCars;
    }

    public int revenueInDay() {
        String filePath = "src/database/Transactions.txt";

        int revenue = 0;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fmd = now.format(dateTimeFormatter);

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] transactionDetails = line.split(",");
                if (fmd.equals(transactionDetails[1])) {
                    revenue += Integer.parseInt(transactionDetails[6]);
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return revenue;
    }

    public int revenueInWeek() {
        String filePath = "src/database/Transactions.txt";

        int revenue = 0;

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
                    revenue += Integer.parseInt(transactionDetails[6]);
                }

            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return revenue;
    }

    public int revenueInMonth() {
        String filePath = "src/database/Transactions.txt";

        int revenue = 0;

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
                    revenue += Integer.parseInt(transactionDetails[6]);
                }

            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return revenue;
    }

    public List<String> listMechanicID() {
        String filePath = "src/database/Mechanics.txt";

        List<String> mechanicsID = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] mechanicDetails = line.split(",");
                mechanicsID.add(mechanicDetails[0]);
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }

        return mechanicsID;
    }

    public int mechanicRevenue(String mechanicID) {
        String filePath = "src/database/Services.txt";

        int revenue = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] serviceDetails = line.split(",");
                if (serviceDetails[3].equals(mechanicID)) {
                    revenue += Integer.parseInt(serviceDetails[6]);
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }

        return revenue;
    }

    public List<String> listSalespersonID() {
        String filePath = "src/database/Salespersons.txt";

        List<String> SalespersonsID = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] salespersonDetails = line.split(",");
                SalespersonsID.add(salespersonDetails[0]);
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }

        return SalespersonsID;
    }

    public List<String> listSoldCars(String salespersonID) {
        String filePath = "src/database/Transactions.txt";

        List<String> soldCars = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] transactionDetails = line.split(",");
                if (transactionDetails[3].equals(salespersonID)) {
                    String[] cars = transactionDetails[4].split(";");
                    for (String car: cars) {
                        if (car.contains("c")) {
                            soldCars.add(car);
                        }
                    }
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }

        return soldCars;
    }

    public int salespersonRevenue(List<String> listSoldCars) {
        String filePath = "src/database/Cars.txt";

        int revenue = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] carsDetails = line.split(",");
                for (int i = 0; i < listSoldCars.size(); i++) {
                    if (carsDetails[0].equals(listSoldCars.get(i))) {
                        revenue += Integer.parseInt(carsDetails[7]);
                    }
                }

            }
        } catch (IOException e) {
//            e.printStackTrace();
        }

        return revenue;
    }

    public void calculateMenu() {
        while (true) {
            System.out.println("--- Calculate Menu ---");
            System.out.println("1. Number of cars sold in a month");
            System.out.println("2. Revenue in a day");
            System.out.println("3. Revenue in a week");
            System.out.println("4. Revenue in a month");
            System.out.println("5. Revenue of the services done of all mechanics");
            System.out.println("6. revenue of the cars sold of all salespersons");
            System.out.println("7. Back");
            System.out.print("Enter your choice: ");

            Calculate cal = new Calculate();
            Scanner scanner = new Scanner(System.in);

            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
            int revenueInDay = cal.revenueInDay();
            String formattedRevenueInDay = numberFormat.format(revenueInDay);
            int revenueInWeek = cal.revenueInWeek();
            String formattedRevenueInWeek = numberFormat.format(revenueInWeek);
            int revenueInMonth = cal.revenueInMonth();
            String formattedRevenueInMonth = numberFormat.format(revenueInMonth);

            int mainChoice = scanner.nextInt();
            scanner.nextLine();
            switch (mainChoice) {
                case 1 -> {
                    System.out.printf("There are %d sold cars in a month\n", cal.numberOfCars());
                }
                case 2 -> {
                    System.out.printf("Revenue in a day: %s VND\n", formattedRevenueInDay);
                }
                case 3 -> {
                    System.out.printf("Revenue in a week: %s VND\n", formattedRevenueInWeek);
                }
                case 4 -> {
                    System.out.printf("Revenue in a month: %s VND\n", formattedRevenueInMonth);
                }
                case 5 -> {
                    for (int i = 0; i < cal.listMechanicID().size(); i++) {
                        int mechanicRevenue = cal.mechanicRevenue(listMechanicID().get(i));
                        String formattedMechanicRevenue = numberFormat.format(mechanicRevenue);
                        System.out.printf("Revenue of %s is %s VND\n", listMechanicID().get(i), formattedMechanicRevenue);
                    }
                }
                case 6 -> {
                    for (int i = 0; i < cal.listSalespersonID().size(); i++) {
                        int salespersonRevenue = cal.salespersonRevenue(cal.listSoldCars(cal.listSalespersonID().get(i)));
                        String formattedSalespersonRevenue = numberFormat.format(salespersonRevenue);
                        System.out.printf("Revenue of %s is %s VND\n", listSalespersonID().get(i), formattedSalespersonRevenue);
                    }
                }
                case 7 -> {
                    System.out.println("-------------------------");
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }
    //--------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------
    public int mechanicRevenueInDay(Mechanic mechanic) {
        String filePath = "src/database/Services.txt";

        int revenue = 0;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fmd = now.format(dateTimeFormatter);

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] serviceDetails = line.split(",");
                if (mechanic.getUserID().equals(serviceDetails[3]) && fmd.equals(serviceDetails[1])) {
                    revenue += Integer.parseInt(serviceDetails[6]);
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return revenue;
    }

    public int mechanicRevenueInWeek(Mechanic mechanic) {
        String filePath = "src/database/Services.txt";

        int revenue = 0;

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
                    revenue += Integer.parseInt(serviceDetails[6]);
                }

            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return revenue;
    }

    public int mechanicRevenueInMonth(Mechanic mechanic) {
        String filePath = "src/database/Services.txt";

        int revenue = 0;

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
                    revenue += Integer.parseInt(serviceDetails[6]);
                }

            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return revenue;
    }

    public void mechanicCalculateMenu(Mechanic mechanic) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("--- Calculate Menu ---");
            System.out.println("1. Revenue in a day");
            System.out.println("2. Revenue in a week");
            System.out.println("3. Revenue in a month");
            System.out.println("4. Back");
            System.out.print("Enter your choice: ");
            int mainChoice = scanner.nextInt();

            Calculate cal = new Calculate();

            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
            int revenueInDay = cal.mechanicRevenueInDay(mechanic);
            String formattedRevenueInDay = numberFormat.format(revenueInDay);

            int revenueInWeek = cal.mechanicRevenueInWeek(mechanic);
            String formattedRevenueInWeek = numberFormat.format(revenueInWeek);

            int revenueInMonth = cal.mechanicRevenueInMonth(mechanic);
            String formattedRevenueInMonth = numberFormat.format(revenueInMonth);


//            scanner.nextLine();
            switch (mainChoice) {
                case 1 -> {
                    System.out.println("-------------------------");
                    System.out.printf("Revenue in a day: %s VND\n", formattedRevenueInDay);
                    System.out.println("-------------------------");
                }
                case 2 -> {
                    System.out.println("-------------------------");
                    System.out.printf("Revenue in a week: %s VND\n", formattedRevenueInWeek);
                    System.out.println("-------------------------");
                }
                case 3 -> {
                    System.out.println("-------------------------");
                    System.out.printf("Revenue in a month: %s VND\n", formattedRevenueInMonth);
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
    //--------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------
    public int salespersonRevenueInDay(Salesperson salesperson) {
        String filePath = "src/database/Transactions.txt";

        int revenue = 0;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fmd = now.format(dateTimeFormatter);

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] transactionDetails = line.split(",");
                if (salesperson.getUserID().equals(transactionDetails[3]) && fmd.equals(transactionDetails[1])) {
                    revenue += Integer.parseInt(transactionDetails[6]);
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return revenue;
    }

    public int salespersonRevenueInWeek(Salesperson salesperson) {
        String filePath = "src/database/Transactions.txt";

        int revenue = 0;

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
                    revenue += Integer.parseInt(transactionDetails[6]);
                }

            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return revenue;
    }

    public int salespersonRevenueInMonth(Salesperson salesperson) {
        String filePath = "src/database/Transactions.txt";

        int revenue = 0;

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
                    revenue += Integer.parseInt(transactionDetails[6]);
                }

            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return revenue;
    }

    public void salespersonCalculateMenu(Salesperson salesperson) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("--- Calculate Menu ---");
            System.out.println("1. Revenue in a day");
            System.out.println("2. Revenue in a week");
            System.out.println("3. Revenue in a month");
            System.out.println("4. Back");
            System.out.print("Enter your choice: ");
            int mainChoice = scanner.nextInt();

            Calculate cal = new Calculate();

            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
            int revenueInDay = cal.salespersonRevenueInDay(salesperson);
            String formattedRevenueInDay = numberFormat.format(revenueInDay);

            int revenueInWeek = cal.salespersonRevenueInWeek(salesperson);
            String formattedRevenueInWeek = numberFormat.format(revenueInWeek);

            int revenueInMonth = cal.salespersonRevenueInMonth(salesperson);
            String formattedRevenueInMonth = numberFormat.format(revenueInMonth);


//            scanner.nextLine();
            switch (mainChoice) {
                case 1 -> {
                    System.out.println("-------------------------");
                    System.out.printf("Revenue in a day: %s VND\n", formattedRevenueInDay);
                    System.out.println("-------------------------");
                }
                case 2 -> {
                    System.out.println("-------------------------");
                    System.out.printf("Revenue in a week: %s VND\n", formattedRevenueInWeek);
                    System.out.println("-------------------------");
                }
                case 3 -> {
                    System.out.println("-------------------------");
                    System.out.printf("Revenue in a month: %s VND\n", formattedRevenueInMonth);
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
