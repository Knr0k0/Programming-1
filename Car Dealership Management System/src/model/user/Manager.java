package model.user;

//import service.AuthenticationService;
import service.*;
import static main.Main.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static model.Car.*;
import static model.Part.partMenu;
import static model.SalesTransaction.transactionMenu;
import static model.Service.serviceMenu;

public class Manager extends User{
    public Manager(String userID, String username, String password, String fullName, String dateOfBirth, String address, int phoneNumber, String email, String userType, String status) {
            super(userID, username, password, fullName, dateOfBirth, address, phoneNumber, email, "Manager", status);
    }

    public Manager() {
        super();
    }

    @Override
    public boolean login() {
        //User file path
        String filePath = "src/database/Managers.txt";
        List<User> managers = new ArrayList<>();
        User manager;

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails[8].contains("manager")) {
                    manager = new Manager(
                            userDetails[0], // ID
                            userDetails[1], // Username
                            userDetails[2], // Password
                            userDetails[3], // Name
                            userDetails[4], // Date of birth
                            userDetails[5], // Address
                            Integer.parseInt(userDetails[6]), // Phone number
                            userDetails[7], // Email
                            userDetails[8], // User type
                            userDetails[9]  // Status
                    );
                    managers.add(manager);
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }

        AuthenticationService authSer = new AuthenticationService();
//        return authSer.login(managers);
        String[] usernamePassword = authSer.login(managers);
        if (usernamePassword[0] != null) {
            this.setUsername(usernamePassword[0]);
            this.setPassword(usernamePassword[1]);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void viewActivityLog() {}

    public void displayMenu(Scanner scanner, CarService carService, PartService partService, ServiceService serviceService, TransactionService transactionService) {
        while (true) {
            try {
                System.out.println("--- Manager Menu ---");
                System.out.println("1. Cars");
                System.out.println("2. Car Parts");
                System.out.println("3. Services");
                System.out.println("4. Transactions");
                System.out.println("5. Calculate");
                System.out.println("6. Information");
                System.out.println("7. List");
                System.out.println("8. Logout");
                System.out.print("Enter your choice: ");

                int mainChoice = scanner.nextInt();

                Calculate cal = new Calculate();
                ListOperation listOp = new ListOperation();

                switch (mainChoice) {
                    case 1 -> carMenu(scanner, carService);
                    case 2 -> partMenu(scanner, partService);
                    case 3 -> serviceMenu(scanner, serviceService);
                    case 4 -> transactionMenu(scanner, transactionService);
                    case 5 -> cal.calculateMenu();
                    case 6 -> info(scanner, this);
                    case 7 -> listOp.listMenu();
                    case 8 -> {
                        System.out.println("Exiting system...");
                        System.out.println("-------------------------");
                        return;
                    }
                    default -> System.out.println("Invalid choice, please try again.");
                }
            } catch (Exception e) {
            System.out.print("Invalid input. Please enter a number.\n***************************\n");
            scanner.nextLine();
            }
        }
    }

    private void info(Scanner scanner,Manager manager) {
        while (true) {
            try {
                System.out.println("--- Information Menu ---");
                System.out.println("1. View information");
                System.out.println("2. Update information");
                System.out.println("3. Back");
                System.out.print("Enter your choice: ");

                int infoChoice = scanner.nextInt();

                switch (infoChoice) {
                    case 1 -> this.displayInfo();
                    case 2 -> this.updateInfo();
                    case 3 -> {
                        System.out.println("-------------------------");
                        return;
                    }
                    default -> System.out.println("Invalid choice, please try again.");
                }
            } catch (Exception e) {
                System.out.print("Invalid input. Please enter a number.\n***************************\n");
                scanner.nextLine();
            }
        }
    }

    @Override
    public void employeeMenu(Scanner scanner) {}

    @Override
    public void clientMenu(Scanner scanner) {}

    @Override
    public void setInfo() {
        String filePath = "src/database/Managers.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails[1].equals(this.getUsername()) && userDetails[2].equals(this.getPassword())) {
                    this.setUserID(userDetails[0]);
                    this.setUsername(userDetails[1]);
                    this.setPassword(userDetails[2]);
                    this.setFullName(userDetails[3]);
                    this.setDateOfBirth(userDetails[4]);
                    this.setAddress(userDetails[5]);
                    this.setPhoneNumber(Integer.parseInt(userDetails[6]));
                    this.setEmail(userDetails[7]);
                    this.setUserType(userDetails[8]);
                    this.setStatus(userDetails[9]);
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    @Override
    public void displayInfo() {
        System.out.println("User ID: " + this.getUserID() + "\n" +
                "Username: " + this.getUsername() + "\n" +
                "Password: " + this.getPassword() + "\n" +
                "FullName: " + this.getFullName() + "\n" +
                "Date of birth: " + this.getDateOfBirth() + "\n" +
                "Address: " + this.getAddress() + "\n" +
                "PhoneNumber: " + this.getPhoneNumber() + "\n" +
                "Email: " + this.getEmail() + "\n" +
                "user type: " + this.getUserType() + "\n" +
                "Status: " + this.getStatus());
    }

    @Override
    public void updateInfo() {
        Scanner sc = new Scanner(System.in);
        this.setUsername(promptForNonEmptyString(sc, "Enter username: "));
        this.setPassword(promptForNonEmptyString(sc, "Enter password: "));
        this.setFullName(promptForNonEmptyString(sc, "Enter full name: "));
        this.setDateOfBirth(promptForNonEmptyString(sc, "Enter date of birth (yyyy-mm-dd): "));
        this.setAddress(promptForNonEmptyString(sc, "Enter address: "));
        this.setPhoneNumber(promptForPositiveInteger(sc, "Enter phone number: "));
        this.setEmail(promptForNonEmptyString(sc, "Enter email: "));
    }

}
