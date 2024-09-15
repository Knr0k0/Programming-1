package model;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import service.*;
import static main.Main.*;

public class Service {
    private String serviceId;
    private String serviceDate;
    private String clientId;
    private String mechanicId;
    private String serviceType;
    private List<String> replacedParts;
    private float serviceCost;
    private String notes;

    // Constructor
    public Service(String serviceId, String serviceDate, String clientId, String mechanicId,
                    String serviceType, List<String> replacedParts, float serviceCost, String notes) {
        if (!serviceId.matches("s-\\d+")) {
            throw new IllegalArgumentException("Service ID must be in the format s-number");
        }
        this.serviceId = serviceId;
        this.serviceDate = serviceDate;
        this.clientId = clientId;
        this.mechanicId = mechanicId;
        this.serviceType = serviceType;
        this.replacedParts = replacedParts;
        this.serviceCost = serviceCost;
        this.notes = notes;
    }

    // Getters and Setters
    public String getServiceId() { return serviceId; }
    public void setServiceId(String serviceId) { this.serviceId = serviceId; }

    public String getServiceDate() { return serviceDate; }
    public void setServiceDate(String serviceDate) { this.serviceDate = serviceDate; }

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getMechanicId() { return mechanicId; }
    public void setMechanicId(String mechanicId) { this.mechanicId = mechanicId; }

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    public List<String> getReplacedParts() { return replacedParts; }
    public void setReplacedParts(List<String> replacedParts) { this.replacedParts = replacedParts; }

    public double getServiceCost() { return serviceCost; }
    public void setServiceCost(float serviceCost) { this.serviceCost = serviceCost; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public String toString() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        String formattedServiceCost = numberFormat.format(serviceCost);

        return "Service ID: " + serviceId + "\n" +
                "Service Date: " + serviceDate + "\n" +
                "Client ID: " + clientId + "\n" +
                "Mechanic ID: " + mechanicId + "\n" +
                "Service Type: " + serviceType + "\n" +
                "Replaced Parts: " + (replacedParts.isEmpty() ? "None" : String.join(", ", replacedParts)) + "\n" +
                "Service Cost: " + formattedServiceCost + " VND" + "\n" +
                "Notes: " + notes + "\n";
    }

    // Services Menu
    public static void serviceMenu(Scanner scanner, ServiceService serviceService) {
        while (true) {
            System.out.println("--- Services Menu ---");
            System.out.println("1. Add Services");
            System.out.println("2. Update Services");
            System.out.println("3. Delete Services");
            System.out.println("4. View All Services");
            System.out.println("5. Back");
            System.out.println("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addServices(scanner, serviceService);
                case 2 -> updateServices(scanner, serviceService);
                case 3 -> deleteServices(scanner, serviceService);
                case 4 -> viewAllServices(serviceService);
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    // Method to add services
    private static void addServices(Scanner scanner, ServiceService serviceService) {
        String serviceId = promptForServicesId(scanner);
        String serviceDate = promptForNonEmptyString(scanner, "Enter Service Date (YYYY-MM-DD): ");
        String clientId = promptForNonEmptyString(scanner, "Enter Client ID: ");
        String mechanicId = promptForNonEmptyString(scanner, "Enter Mechanic ID: ");
        String serviceType = promptForNonEmptyString(scanner, "Enter Service Type: ");
        List<String> replacedParts = promptForReplacedParts(scanner);
        double serviceCost = promptForPositiveDouble(scanner, "Enter Service Cost: ");
        String notes = promptForNonEmptyString(scanner, "Enter Notes: ");

        Service newService = new Service(serviceId, serviceDate, clientId, mechanicId, serviceType, replacedParts, (float) serviceCost, notes);
        serviceService.addService(newService);
        System.out.println("Service added successfully!");
    }


    // Method to prompt for services ID
    private static String promptForServicesId(Scanner scanner) {
        String serviceId;
        while (true) {
            System.out.println("Enter Service ID (format s-number): ");
            serviceId = scanner.nextLine();
            if (serviceId.matches("s-\\d+")) {
                break;
            } else {
                System.out.println("Invalid Service ID format. Please enter in the format s-number.");
            }
        }
        return serviceId;
    }

    // Method to update services
    private static void updateServices(Scanner scanner, ServiceService serviceService) {
        String serviceId = promptForNonEmptyString(scanner, "Enter Service ID to update: ");
        Service existingService = serviceService.getServiceById(serviceId);

        if (existingService != null) {
            String serviceDate = promptForOptionalString(scanner, "Enter Service Date (" + existingService.getServiceDate() + "): ", existingService.getServiceDate());
            String clientId = promptForOptionalString(scanner, "Enter Client ID (" + existingService.getClientId() + "): ", existingService.getClientId());
            String mechanicId = promptForOptionalString(scanner, "Enter Mechanic ID (" + existingService.getMechanicId() + "): ", existingService.getMechanicId());
            String serviceType = promptForOptionalString(scanner, "Enter Service Type (" + existingService.getServiceType() + "): ", existingService.getServiceType());
            List<String> replacedParts = promptForOptionalReplacedParts(scanner, existingService.getReplacedParts());
            double serviceCost = promptForOptionalDouble(scanner, "Enter Service Cost (" + existingService.getServiceCost() + "): ", existingService.getServiceCost());
            String notes = promptForOptionalString(scanner, "Enter Notes (" + existingService.getNotes() + "): ", existingService.getNotes());

            existingService.setServiceDate(serviceDate);
            existingService.setClientId(clientId);
            existingService.setMechanicId(mechanicId);
            existingService.setServiceType(serviceType);
            existingService.setReplacedParts(replacedParts);
            existingService.setServiceCost((float) serviceCost);
            existingService.setNotes(notes);

            serviceService.updateService(existingService);
            System.out.println("Service updated successfully!");
        } else {
            System.out.println("Service not found!");
        }
    }

    // Method to delete services
    private static void deleteServices(Scanner scanner, ServiceService serviceService) {
        String serviceId = promptForNonEmptyString(scanner, "Enter Service ID to delete: ");
        serviceService.deleteService(serviceId);
        System.out.println("Service deleted successfully!");
    }

    // Method to view all services
    private static void viewAllServices(ServiceService serviceService) {
        List<Service> services = serviceService.getAllServices();
        if (services.isEmpty()) {
            System.out.println("No services available.");
        } else {
            for (Service service : services) {
                System.out.println("--------------------------------");
                System.out.print(service);
            }
        }
    }

//    // Method to view done services
//    private static void viewDoneServices(ServiceService serviceService) {
//        List<Service> doneServices = serviceService.getDoneServices();
//        if (doneServices.isEmpty()) {
//            System.out.println("No services marked as done.");
//        } else {
//            for (Service service : doneServices) {
//                System.out.println(service);
//                System.out.println("--------------------------------");
//            }
//        }
//    }
}
