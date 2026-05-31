public class Main {
    public static void main(String[] args) {
        
        // --- PART A & B OMITTED FOR BREVITY ---
        // You can paste the previous Part A and B logic here
        
        // --- PART C: City Routing (Graph Algorithms) ---
        System.out.println("\n--> Initializing City Graph (Kayseri Map)...");
        CityGraph cityMap = new CityGraph();

        // Building the map network based on mapData.txt
        cityMap.addEdge("Meydan", "Alpaslan", 4);
        cityMap.addEdge("Meydan", "Talas", 8);
        cityMap.addEdge("Meydan", "Erkilet", 10);
        cityMap.addEdge("Meydan", "Belsin", 12);
        cityMap.addEdge("Alpaslan", "Talas", 5);
        cityMap.addEdge("Alpaslan", "Erkilet", 9);
        cityMap.addEdge("Alpaslan", "Ildem", 12);
        cityMap.addEdge("Talas", "Mimsin", 11);
        cityMap.addEdge("Belsin", "Anbar", 3);
        cityMap.addEdge("Belsin", "Erkilet", 14);
        cityMap.addEdge("Ildem", "Mimsin", 6);

        System.out.println("\n--> Calculating Shortest Delivery Routes (Dijkstra's)...");
        // Example: Finding the fastest route from warehouse hub (Meydan) to Mimsin
        cityMap.calculateShortestPath("Meydan", "Mimsin");
        
        // Example: Finding the fastest route from Belsin to Ildem
        cityMap.calculateShortestPath("Belsin", "Ildem");

        System.out.println("\n--> Calculating Infrastructure Network (Prim's MST)...");
        // Finding the most efficient paths connecting all neighborhoods
        cityMap.calculateMST();
    }
}