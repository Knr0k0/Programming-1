package model;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import service.*;
import static main.Main.*;

public class Car {
    private String carId; // formatted as c-number
    private String make;
    private String model;
    private int year;
    private int mileage;
    private String color;
    private String status; // available/sold
    private float price;
    private String notes;
    private List<String> serviceHistory;

    // Constructor
    public Car(String carId, String make, String model, int year, int mileage, String color, String status, float price, String notes, List<String> serviceHistory) {
        if (!carId.matches("c-\\d+")) {
            throw new IllegalArgumentException("Car ID must be in the format p-number");
        }
        this.carId = carId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.color = color;
        this.status = status;
        this.price = price;
        this.notes = notes;
        this.serviceHistory = serviceHistory;
    }

    // Getters and Setters
    public String getCarId() { return carId; }
    public void setCarId(String carId) { this.carId = carId; }

    public String getMaker() { return make; }
    public void setMaker(String maker) { this.make = maker; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public int getMileage() { return mileage; }
    public void setMileage(int mileage) { this.mileage = mileage; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public List<String> getServiceHistory() { return serviceHistory; }
    public void setServiceHistory(List<String> serviceHistory) { this.serviceHistory = serviceHistory; }

    @Override
    public String toString() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        String formattedPrice = numberFormat.format(price);

        return "Car ID: " + carId + "\n" +
                "Car Maker: " + make + "\n" +
                "Car Model: " + model + "\n" +
                "Car Year: " + year + "\n" +
                "Car Mileage: " + mileage + "\n" +
                "Car Color: " + color + "\n" +
                "Car Status: " + status + "\n" +
                "Car Cost: " + formattedPrice + " VND" + "\n" +
                "Notes: " + notes + "\n" +
                "Service History: " + (serviceHistory.isEmpty() ? "None" : String.join(", ", serviceHistory)) + "\n";
    }

    // Cars Menu
    public static void carMenu(Scanner scanner, CarService carService) {
        while (true) {
            System.out.println("--- Cars Menu ---");
            System.out.println("1. Add Car");
            System.out.println("2. Update Car");
            System.out.println("3. Delete Car");
            System.out.println("4. View All Cars");
            System.out.println("5. View Sold Cars");
            System.out.println("6. Back");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addCar(scanner, carService);
                case 2 -> updateCar(scanner, carService);
                case 3 -> deleteCar(scanner, carService);
                case 4 -> viewAllCars(carService);
                case 5 -> viewSoldCars(carService);
                case 6 -> {
                    System.out.println("-------------------------");
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    // Method to add cars
    private static void addCar(Scanner scanner, CarService carService) {
        String carId = promptForCarId(scanner);
        String make = promptForNonEmptyString(scanner, "Enter Make: ");
        String model = promptForNonEmptyString(scanner, "Enter Model: ");
        int year = promptForYear(scanner);
        int mileage = promptForPositiveInteger(scanner, "Enter Mileage: ");
        String color = promptForNonEmptyString(scanner, "Enter Color: ");
        String status = promptForCarStatus(scanner);
        int price = promptForPositiveInteger(scanner, "Enter Price: ");
        String notes = promptForNonEmptyString(scanner, "Enter Notes: ");
        List<String> serviceHistory = new ArrayList<>();
        Car newCar = new Car(carId, make, model, year, mileage, color, status, price, notes, serviceHistory);
        carService.addCar(newCar);
        System.out.println("Car added successfully!");
    }

    // Method to prompt for cars ID
    private static String promptForCarId(Scanner scanner) {
        String carId;
        while (true) {
            System.out.println("Enter Car ID (format c-number): ");
            carId = scanner.nextLine();
            if (carId.matches("c-\\d+")) {
                break;
            } else {
                System.out.println("Invalid Car ID format. Please enter in the format c-number.");
            }
        }
        return carId;
    }

    // Method to update cars
    private static void updateCar(Scanner scanner, CarService carService) {
        String carId = promptForNonEmptyString(scanner, "Enter Car ID to update: ");
        Car existingCar = carService.getCarById(carId);

        if (existingCar != null) {
            String make = promptForOptionalString(scanner, "Enter Make (" + existingCar.getMaker() + "): ", existingCar.getMaker());
            String model = promptForOptionalString(scanner, "Enter Model (" + existingCar.getModel() + "): ", existingCar.getModel());
            int year = promptForOptionalYear(scanner, existingCar.getYear());
            int mileage = promptForOptionalInteger(scanner, "Enter Mileage (" + existingCar.getMileage() + "): ", existingCar.getMileage());
            String color = promptForOptionalString(scanner, "Enter Color (" + existingCar.getColor() + "): ", existingCar.getColor());
            String status = promptForOptionalCarStatus(scanner, existingCar.getStatus());
            double price = promptForOptionalDouble(scanner, "Enter Price (" + existingCar.getPrice() + "): ", existingCar.getPrice());
            String notes = promptForOptionalString(scanner, "Enter Notes (" + existingCar.getNotes() + "): ", existingCar.getNotes());

            existingCar.setMaker(make);
            existingCar.setModel(model);
            existingCar.setYear(year);
            existingCar.setMileage(mileage);
            existingCar.setColor(color);
            existingCar.setStatus(status);
            existingCar.setPrice((int) price);
            existingCar.setNotes(notes);

            carService.updateCar(existingCar);
            System.out.println("Car updated successfully!");
        } else {
            System.out.println("Car not found!");
        }
    }

    // Method to delete cars
    private static void deleteCar(Scanner scanner, CarService carService) {
        String carId = promptForNonEmptyString(scanner, "Enter Car ID to delete: ");
        carService.deleteCar(carId);
        System.out.println("Car deleted successfully!");
    }

    // Method to view all cars
    private static void viewAllCars(CarService carService) {
        List<Car> cars = carService.getAllCars();
        if (cars.isEmpty()) {
            System.out.println("No cars available.");
        } else {
            for (Car car : cars) {
                System.out.println("-------------------------");
                System.out.print(car);
            }
        }
    }

    // Method to view sold cars
    private static void viewSoldCars(CarService carService) {
        List<Car> soldCars = carService.getSoldCars();
        if (soldCars.isEmpty()) {
            System.out.println("No sold cars available.");
        } else {
            for (Car car : soldCars) {
                System.out.println("-------------------------");
                System.out.print(car);
            }
        }
    }
}
