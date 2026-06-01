import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // --- 1. PROJECT IDENTITY ---
        System.out.println("===============================================");
        System.out.println("   ENCODERS LOGISTICS & DISTRIBUTION SYSTEM");
        System.out.println("   'Fastest delivery in the heart of Kayseri'");
        System.out.println("===============================================\n");

        // --- 2. INITIALIZE DATA STRUCTURES ---
        MasterRegistrySLL masterLog = new MasterRegistrySLL();
        IntakeBufferDLL intakeBuffer = new IntakeBufferDLL();
        StandardDeliveryQueue deliveryQueue = new StandardDeliveryQueue();
        TruckLoadingStack truckStack = new TruckLoadingStack();
        AddressDirectoryAVL addressDirectory = new AddressDirectoryAVL();
        CityGraph cityMap = new CityGraph();

        // --- 3. LOAD DATA FROM EXTERNAL FILES ---
        
        // A. Loading City Map Data (CityGraph)
        try (Scanner mapScanner = new Scanner(new File("mapData.txt"))) {
            while (mapScanner.hasNextLine()) {
                String line = mapScanner.nextLine();
                if (line.startsWith("#") || line.trim().isEmpty()) continue;
                String[] parts = line.split(" ");
                cityMap.addEdge(parts[0], parts[1], Integer.parseInt(parts[2]));
            }
            System.out.println("--> City map network loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("Error: mapData.txt not found!");
        }

        // B. Loading Daily Package Data (Warehouse Operations)
try (Scanner pkgScanner = new Scanner(new File("packageData.txt"))) {
    int count = 0;
    while (pkgScanner.hasNextLine()) {
        String line = pkgScanner.nextLine().trim();
        
        // Skip comments, empty lines, or lines that don't contain a space (like "Meydan.")
        if (line.startsWith("#") || line.isEmpty() || !line.contains(" ")) {
            continue;
        }
        
        String[] parts = line.split("\\s+");
        
        // Ensure we have exactly two parts: ID and Destination
        if (parts.length >= 2) {
            Package newPkg = new Package(parts[0], parts[1]);
            
            masterLog.addRecord(newPkg);     
            intakeBuffer.insertAtTail(newPkg); 
            addressDirectory.insert(newPkg.destination, "ID_" + parts[0]);
            count++;
        }
    }
    System.out.println("--> " + count + " packages registered and moved to intake buffer.");
} catch (FileNotFoundException e) {
    System.out.println("Error: packageData.txt not found!");
}

        // --- 4. WAREHOUSE WORKFLOW (Queue & Stack) ---
        System.out.println("\n--> Transferring packages from Buffer (DLL) to Delivery Queue (FIFO)...");
        Package p;
        while ((p = intakeBuffer.removeFromHead()) != null) {
            deliveryQueue.enqueue(p); 
        }

        System.out.println("--> Loading Truck (LIFO - Stack simulation starting)...");
        while ((p = deliveryQueue.dequeue()) != null) {
            truckStack.push(p); 
        }

        // --- 5. AUDIT & OPTIMIZATION OUTPUTS ---
        System.out.println("\n--> Daily Audit Log (Master Registry):");
        masterLog.displayLog();

        System.out.println("\n--> City Routing Optimization:");
        cityMap.calculateShortestPath("Meydan", "Mimsin"); 
        cityMap.calculateMST(); 
        
        System.out.println("\nAddress Directory Lookup (AVL Test): Packages for Talas -> " + addressDirectory.search("Talas"));
        
        System.out.println("\n===============================================");
        System.out.println("   DAILY LOGISTICS OPERATION COMPLETED");
        System.out.println("===============================================");
    }
}