package model.user;

import service.CarService;
import service.PartService;
import service.ServiceService;
import service.TransactionService;

import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class User {
    private String userID;
    private String username;
    private String password;
    private String fullName;
    private String dateOfBirth;
    private String address;
    private int phoneNumber;
    private String email;
    private String userType; // "Admin", "Manager", "Employee", "Client"
    private String status;   // "Active", "Inactive"

    public User() {};

    public User(String userID, String username, String password, String fullName, String dateOfBirth, String address, int phoneNumber, String email, String userType, String status) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userType = userType;
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public abstract boolean login() throws FileNotFoundException;

    public abstract void viewActivityLog();

    public abstract void displayMenu(Scanner scanner, CarService carService, PartService partService, ServiceService serviceService, TransactionService transactionService);

    public abstract void employeeMenu(Scanner scanner);

    public abstract void clientMenu(Scanner scanner);

    public abstract void setInfo();

    public abstract void displayInfo();

    public abstract void updateInfo();
}
