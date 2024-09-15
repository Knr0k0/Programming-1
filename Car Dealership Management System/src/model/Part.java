package model;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import service.*;
import static main.Main.*;

public class Part {
    private String partId; // formatted as p-number
    private String partName;
    private String manufacturer;
    private String partNumber;
    private String condition; // new, used, refurbished
    private String warranty;
    private float cost;
    private String notes;

    // Constructor
    public Part(String partId, String partName, String manufacturer, String partNumber, String condition, String warranty, float cost, String notes) {
        if (!partId.matches("p-\\d+")) {
            throw new IllegalArgumentException("Part ID must be in the format p-number");
        }
        this.partId = partId;
        this.partName = partName;
        this.manufacturer = manufacturer;
        this.partNumber = partNumber;
        this.condition = condition;
        this.warranty = warranty;
        this.cost = cost;
        this.notes = notes;
    }

    // Getters and Setters
    public String getPartId() { return partId; }
    public void setPartId(String partId) { this.partId = partId; }

    public String getPartName() { return partName; }
    public void setPartName(String partName) { this.partName = partName; }

    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public String getPartNumber() { return partNumber; }
    public void setPartNumber(String partNumber) { this.partNumber = partNumber; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public CharSequence getWarranty() { return warranty; }
    public void setWarranty(String warranty) { this.warranty = warranty; }

    public double getCost() { return cost; }
    public void setCost(float cost) { this.cost = cost; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public String toString() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        String formattedCost = numberFormat.format(cost);

        return "Part ID: " + partId + "\n" +
                "Part Name: " + partName + "\n" +
                "Part Manufacturer: " + manufacturer + "\n" +
                "Part Number: " + partNumber + "\n" +
                "Part Condition: " + condition + "\n" +
                "Part Warranty: " + warranty + "\n" +
                "Part Cost: " + formattedCost + " VND" + "\n" +
                "Notes: " + notes + "\n";
    }

    // Car Parts Menu
    public static void partMenu(Scanner scanner, PartService partService) {
        while (true) {
            System.out.println("--- Car Parts Menu ---");
            System.out.println("1. Add Car Part");
            System.out.println("2. Update Car Part");
            System.out.println("3. Delete Car Part");
            System.out.println("4. View All Car Parts");
//            System.out.println("5. View Sold Car Parts");
            System.out.println("5. Back");
            System.out.println("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addPart(scanner, partService);
                case 2 -> updatePart(scanner, partService);
                case 3 -> deletePart(scanner, partService);
                case 4 -> viewAllParts(partService);
//                case 99 -> viewSoldParts(partService);
                case 5 -> {
                    return; // Go back to the main menu
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    // Method to add a new car parts
    private static void addPart(Scanner scanner, PartService partService) {
        String partId = promptForPartId(scanner);
        String partName = promptForNonEmptyString(scanner, "Enter Part Name: ");
        String manufacturer = promptForNonEmptyString(scanner, "Enter Manufacturer: ");
        String partNumber = promptForNonEmptyString(scanner, "Enter Part Number: ");
        String condition = promptForNonEmptyString(scanner, "Enter Condition: ");
        String warranty = promptForNonEmptyString(scanner, "Enter Warranty: ");
        int cost = promptForPositiveInteger(scanner, "Enter Price: ");
        String notes = promptForNonEmptyString(scanner, "Enter Notes: ");
        String status = promptForNonEmptyString(scanner, "Enter Status (sold/available): ");

        Part newPart = new Part(partId, partName, manufacturer, partNumber, condition, warranty, cost, notes);
        partService.addPart(newPart);
        System.out.println("Car part added successfully!");
    }

    // Method to prompt for car parts ID
    private static String promptForPartId(Scanner scanner) {
        String partId;
        while (true) {
            System.out.println("Enter Part ID (format p-number): ");
            partId = scanner.nextLine();
            if (partId.matches("p-\\d+")) {
                break;
            } else {
                System.out.println("Invalid Part ID format. Please enter in the format p-number.");
            }
        }
        return partId;
    }

    // Method to update an existing car parts
    private static void updatePart(Scanner scanner, PartService partService) {
        String partId = promptForNonEmptyString(scanner, "Enter Part ID to update: ");
        Part existingPart = partService.getPartById(partId);

        if (existingPart != null) {
            String partName = promptForOptionalString(scanner, "Enter Part Name (" + existingPart.getPartName() + "): ", existingPart.getPartName());
            String manufacturer = promptForOptionalString(scanner, "Enter Manufacturer (" + existingPart.getManufacturer() + "): ", existingPart.getManufacturer());
            String partNumber = promptForOptionalString(scanner, "Enter Part Number (" + existingPart.getPartNumber() + "): ", existingPart.getPartNumber());
            String condition = promptForOptionalString(scanner, "Enter Condition (" + existingPart.getCondition() + "): ", existingPart.getCondition());
            String warranty = promptForOptionalString(scanner, "Enter Warranty (" + existingPart.getWarranty() + " years): ", (String) existingPart.getWarranty());
            double cost = promptForOptionalDouble(scanner, "Enter Cost (" + existingPart.getCost() + "): ", existingPart.getCost());
            String notes = promptForOptionalString(scanner, "Enter Notes (" + existingPart.getNotes() + "): ", existingPart.getNotes());

            existingPart.setPartName(partName);
            existingPart.setManufacturer(manufacturer);
            existingPart.setPartNumber(partNumber);
            existingPart.setCondition(condition);
            existingPart.setWarranty(warranty);
            existingPart.setCost((int) cost);
            existingPart.setNotes(notes);

            partService.updatePart(existingPart);
            System.out.println("Car part updated successfully!");
        } else {
            System.out.println("Car part not found!");
        }
    }

    // Method to delete a car parts
    private static void deletePart(Scanner scanner, PartService partService) {
        String partId = promptForNonEmptyString(scanner, "Enter Part ID to delete: ");
        partService.deletePart(partId);
        System.out.println("Car part deleted successfully!");
    }

    // Method to view all car parts
    private static void viewAllParts(PartService partService) {
        List<Part> parts = partService.getAllParts();
        if (parts.isEmpty()) {
            System.out.println("No car parts available.");
        } else {
            for (Part part : parts) {
                System.out.println("-------------------------");
                System.out.print(part);
            }
        }
    }
}
