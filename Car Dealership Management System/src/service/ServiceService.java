package service;

import java.util.ArrayList;

import model.Service;
import model.SalesTransaction;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceService {
    private final List<Service> services;
    private static final String filePath = "src/database/Services.txt"; // Đường dẫn đến file

    public ServiceService() {
        this.services = new ArrayList<>();
        loadServicesFromFile(); // Load dữ liệu từ file khi khởi tạo
    }

    public void addService(Service service) {
        services.add(service);
        saveServicesToFile(); // Lưu vào file sau khi thêm
    }

    public Service getServiceById(String serviceId) {
        return services.stream()
                .filter(service -> service.getServiceId().equals(serviceId))
                .findFirst()
                .orElse(null);
    }

    public void updateService(Service newService) {
        Service existingService = getServiceById(newService.getServiceId());
        if (existingService != null) {
            services.remove(existingService);
            services.add(newService);
            saveServicesToFile(); // Lưu vào file sau khi cập nhật
        } else {
            System.out.println("No existing service found with ID: " + newService.getServiceId());
        }
    }

    public void deleteService(String serviceId) {
        services.removeIf(service -> service.getServiceId().equals(serviceId));
        saveServicesToFile(); // Lưu vào file sau khi xóa
    }

    public List<Service> getAllServices() {
        return new ArrayList<>(services);
    }

    // Đọc dữ liệu từ file
    private void loadServicesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 8) { // Đảm bảo đủ các trường dữ liệu
                    Service service = new Service(
                            parts[0], // serviceId
                            parts[1], // serviceDate
                            parts[2], // clientId
                            parts[3], // mechanicId
                            parts[4], // serviceType
                            Arrays.asList(parts[5].split(";")), // replacedParts, ngăn cách bằng dấu chấm phẩy
                            Float.parseFloat(parts[6]), // serviceCost
                            parts[7]  // notes
                    );
                    services.add(service);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Lưu dữ liệu vào file
    private void saveServicesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Service service : services) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s,%.2f,%s%n",
                        service.getServiceId(),
                        service.getServiceDate(),
                        service.getClientId(),
                        service.getMechanicId(),
                        service.getServiceType(),
                        String.join(";", service.getReplacedParts()), // Ghi replacedParts bằng dấu chấm phẩy
                        service.getServiceCost(),
                        service.getNotes()
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

