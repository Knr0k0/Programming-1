package main;

import model.user.*;
import service.CarService;
import service.PartService;
import service.ServiceService;
import service.TransactionService;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        CarService carService = new CarService();
        PartService partService = new PartService();
        ServiceService serviceService = new ServiceService();
        TransactionService transactionService = new TransactionService();

        while (running) {
            System.out.print("COSC2081 GROUP ASSIGNMENT\n" +
                    "AUTO168 CAR DEALERSHIP MANAGEMENT SYSTEM\n" +
                    "Instructor: Mr. Minh Vu & Mr. Dung Nguyen\n" +
                    "Group: Group 7\n" +
                    "s3979077, Nguyen Dung Tri\n" +
                    "s3975161, Do Thanh Luan\n" +
                    "s3975935, Nguyen Ngoc Huy\n" +
                    "s3893964, Le Phuc Thinh\n" +
                    "-------------------------\n");
            System.out.println("1. Manager Login");
            System.out.println("2. Employee Login");
            System.out.println("3. Client Login");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> {
                        User manager = new Manager();
                        if (manager.login()) {
                            manager.setInfo();
                           manager.displayMenu(scanner, carService, partService, serviceService, transactionService);
                        }
                    }
                    case 2 -> {
                        boolean running1 = true;
                        while (running1) {
                            System.out.println("-------------------------");
                            System.out.println("1. Salesperson Login");
                            System.out.println("2. Mechanic Login");
                            System.out.println("3. Exit");
                            System.out.print("Enter your choice: ");

                            try {
                                int choice1 = scanner.nextInt();
                                switch (choice1) {
                                    case 1 -> {
                                        User salesperson = new Salesperson();
                                        if (salesperson.login()) {
                                            salesperson.setInfo();
                                            salesperson.employeeMenu(scanner);
                                        }
                                    } case 2 -> {
                                        User mechanic = new Mechanic();
                                        if (mechanic.login()) {
                                            mechanic.setInfo();
                                            mechanic.employeeMenu(scanner);
                                        }
                                    }
                                    case 3 -> {
                                        running1 = false;
                                        System.out.println("Exit...");
                                        System.out.println("-------------------------");
                                    }
                                    default -> System.out.print("Invalid choice. Please try again.\n***************************\n");
                                }
                            } catch (Exception e) {
                                System.out.print("Invalid input. Please enter a number.\n***************************\n");
                                scanner.nextLine();
                            }
                        }
                    }
                    case 3 -> {
                        User client = new Client();
                        if (client.login()) {
                            client.setInfo();
                            client.clientMenu(scanner);
                        }
                    }
                    case 4 -> {
                        running = false;
                        System.out.println("Exiting system.");
                    }
                    default -> System.out.print("Invalid choice. Please try again.\n***************************\n");
                }
            } catch (Exception e) {
                System.out.print("Invalid input. Please enter a number.\n***************************\n");
                scanner.nextLine();
            }
        }
    }

    //Prompt
    public static String promptForNonEmptyString(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.println(prompt);
            input = scanner.nextLine();
            if (input.isEmpty()) {
                System.out.println("This field cannot be empty. Please try again.");
            }
        } while (input.isEmpty());
        return input;
    }

    public static String promptForOptionalString(Scanner scanner, String prompt, String defaultValue) {
        System.out.println(prompt);
        String input = scanner.nextLine();
        return input.isEmpty() ? defaultValue : input;
    }

    public static int promptForYear(Scanner scanner) {
        int year = 0;
        while (year < 1886 || year > 2024) {
            try {
                System.out.println("Enter Year: ");
                year = Integer.parseInt(scanner.nextLine());
                if (year < 1886 || year > 2024) {
                    System.out.println("Invalid year. Please enter a year between 1886 and 2024.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid year.");
            }
        }
        return year;
    }

    public static int promptForOptionalYear(Scanner scanner, int defaultYear) {
        int year = defaultYear;
        try {
            System.out.println("Enter Year (" + defaultYear + "): ");
            String yearInput = scanner.nextLine();
            if (!yearInput.isEmpty()) {
                year = Integer.parseInt(yearInput);
                if (year < 1886 || year > 2024) {
                    System.out.println("Invalid year. Reverting to default year: " + defaultYear);
                    year = defaultYear;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Reverting to default year: " + defaultYear);
        }
        return year;
    }

    public static int promptForPositiveInteger(Scanner scanner, String promptMessage) {
        int value = -1;
        while (value <= 0) {
            try {
                System.out.println(promptMessage);
                value = Integer.parseInt(scanner.nextLine());
                if (value <= 0) {
                    System.out.println("Value must be a positive integer. Please enter a valid value.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return value;
    }

    public static int promptForOptionalInteger(Scanner scanner, String prompt, int defaultValue) {
        int value = defaultValue;
        try {
            System.out.println(prompt);
            String input = scanner.nextLine();
            if (!input.isEmpty()) {
                value = Integer.parseInt(input);
                if (value < 0) {
                    System.out.println("Invalid input. Reverting to default value: " + defaultValue);
                    value = defaultValue;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Reverting to default value: " + defaultValue);
        }
        return value;
    }

    public static double promptForPositiveDouble(Scanner scanner, String promptMessage) {
        double value = -1;
        while (value < 0) {
            try {
                System.out.println(promptMessage);
                value = Double.parseDouble(scanner.nextLine());
                if (value < 0) {
                    System.out.println("Value cannot be negative. Please enter a valid value.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return value;
    }

    public static double promptForOptionalDouble(Scanner scanner, String prompt, double defaultValue) {
        double value = defaultValue;
        try {
            System.out.println(prompt);
            String input = scanner.nextLine();
            if (!input.isEmpty()) {
                value = Double.parseDouble(input);
                if (value < 0) {
                    System.out.println("Invalid input. Reverting to default value: " + defaultValue);
                    value = defaultValue;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Reverting to default value: " + defaultValue);
        }
        return value;
    }

    public static String promptForCarStatus(Scanner scanner) {
        String status;
        do {
            System.out.println("Enter Status (available/sold): ");
            status = scanner.nextLine().toLowerCase();
            if (!status.equals("available") && !status.equals("sold")) {
                System.out.println("Invalid status. Please enter either 'available' or 'sold'.");
            }
        } while (!status.equals("available") && !status.equals("sold"));
        return status;
    }

    public static String promptForOptionalCarStatus(Scanner scanner, String defaultStatus) {
        String status;
        do {
            System.out.println("Enter Status (" + defaultStatus + "): ");
            status = scanner.nextLine().toLowerCase();
            if (status.isEmpty()) {
                status = defaultStatus;
            }
            if (!status.equals("available") && !status.equals("sold")) {
                System.out.println("Invalid status. Please enter either 'available' or 'sold'.");
            }
        } while (!status.equals("available") && !status.equals("sold"));
        return status;
    }

    public static List<String> promptForReplacedParts(Scanner scanner) {
        List<String> replacedParts = new ArrayList<>();
        while (true) {
            String part = promptForNonEmptyString(scanner, "Enter replaced part (or 'done' to finish): ");
            if (part.equalsIgnoreCase("done")) {
                break;
            }
            replacedParts.add(part);
        }
        return replacedParts;
    }

    public static List<String> promptForOptionalReplacedParts(Scanner scanner, List<String> currentParts) {
        List<String> replacedParts = new ArrayList<>(currentParts);
        while (true) {
            String part = promptForOptionalString(scanner, "Enter replaced part (or 'done' to finish, leave blank to keep current): ", "");
            if (part.equalsIgnoreCase("done")) {
                break;
            }
            if (!part.isEmpty()) {
                replacedParts.add(part);
            }
        }
        return replacedParts;
    }

    public static List<String> promptForPurchasedItems(Scanner scanner) {
        List<String> purchasedItems = new ArrayList<>();
        System.out.println("Enter purchased items (type 'done' to finish):");
        while (true) {
            String item = scanner.nextLine();
            if (item.equalsIgnoreCase("done")) {
                break;
            }
            purchasedItems.add(item);
        }
        return purchasedItems;
    }
}
