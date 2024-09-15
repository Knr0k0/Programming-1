package model.user;

import service.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static main.Main.promptForNonEmptyString;
import static main.Main.promptForPositiveInteger;
import static model.Car.carMenu;
import static model.Part.partMenu;
import static model.SalesTransaction.transactionMenu;
import static model.Service.serviceMenu;

public class Mechanic extends User{
    public Mechanic(String userID, String username, String password, String fullName, String dateOfBirth, String address, int phoneNumber, String email, String userType, String status) {
        super(userID, username, password, fullName, dateOfBirth, address, phoneNumber, email, "Mechanic", status);
    }

    public Mechanic() {
        super();
    }

    @Override
    public boolean login() {
        //User file path
        String filePath = "src/database/Mechanics.txt";
        List<User> mechanics = new ArrayList<>();
        User mechanic;

        // Read data from file and add in list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails[8].contains("mechanic")) {
                    mechanic = new Mechanic(
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
                    mechanics.add(mechanic);
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }

        AuthenticationService authSer = new AuthenticationService();
//        return authSer.login(managers);
        String[] usernamePassword = authSer.login(mechanics);
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

    @Override
    public void displayMenu(Scanner scanner, CarService carService, PartService partService, ServiceService serviceService, TransactionService transactionService) {}

    @Override
    public void employeeMenu(Scanner scanner) {
        while (true) {
            try {
                System.out.println("--- Mechanic Menu ---");
                System.out.println("1. Calculate");
                System.out.println("2. List");
                System.out.println("3. Information");
                System.out.println("4. Logout");
                System.out.print("Enter your choice: ");

                int mainChoice = scanner.nextInt();

                Calculate cal = new Calculate();
                ListOperation listOp = new ListOperation();

                switch (mainChoice) {
                    case 1 -> cal.mechanicCalculateMenu(this);
                    case 2 -> listOp.mechanicListMenu(this);
                    case 3 -> info(scanner, this);
                    case 4 -> {
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

    private void info(Scanner scanner,Mechanic mechanic) {
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
    public void clientMenu(Scanner scanner) {}

    @Override
    public void setInfo() {
        String filePath = "src/database/Mechanics.txt";

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
//        String filePath = "src/database/Mechanics.txt";
//
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            String line;
//
//            while ((line = br.readLine()) != null) {
//                String[] userDetails = line.split(",");
//                if (userDetails[1].equals(this.getUsername()) && userDetails[2].equals(this.getPassword())) {
//                    this.setUserID(userDetails[0]);
//                    this.setUsername(userDetails[1]);
//                    this.setPassword(userDetails[2]);
//                    this.setFullName(userDetails[3]);
//                    this.setDateOfBirth(userDetails[4]);
//                    this.setAddress(userDetails[5]);
//                    this.setPhoneNumber(Integer.parseInt(userDetails[6]));
//                    this.setEmail(userDetails[7]);
//                    this.setUserType(userDetails[8]);
//                    this.setStatus(userDetails[9]);
//                }
//            }
//        } catch (IOException e) {
////            e.printStackTrace();
//        }

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
