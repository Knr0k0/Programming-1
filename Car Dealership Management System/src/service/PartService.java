package service;

import model.Part;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PartService {
    private final List<Part> parts;
    private final String filePath = "src/database/Parts.txt"; // Path to the file

    public PartService() {
        this.parts = new ArrayList<>();
        loadPartsFromFile();
    }

    public void addPart(Part part) {
        parts.add(part);
        savePartsToFile();
    }

    public Part getPartById(String partId) {
        return parts.stream().filter(part -> part.getPartId().equals(partId)).findFirst().orElse(null);
    }

    public void updatePart(Part part) {
        Part existingPart = getPartById(part.getPartId());
        if (existingPart != null) {
            parts.remove(existingPart);
            parts.add(part);
            savePartsToFile();
        }
    }

    public void deletePart(String partId) {
        parts.removeIf(part -> part.getPartId().equals(partId));
        savePartsToFile();
    }

    public List<Part> getAllParts() {
        return new ArrayList<>(parts);
    }

    private void loadPartsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] partsData = line.split(",");
                if (partsData.length == 8) {
                    Part part = new Part(
                            partsData[0],
                            partsData[1],
                            partsData[2],
                            partsData[3],
                            partsData[4],
                            partsData[5],
                            Integer.parseInt(partsData[6]),
                            partsData[7]
                    );
                    parts.add(part);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void savePartsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Part part : parts) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s,%.0f,%s",
                        part.getPartId(),
                        part.getPartName(),
                        part.getManufacturer(),
                        part.getPartNumber(),
                        part.getCondition(),
                        part.getWarranty(),
                        part.getCost(),
                        part.getNotes()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
