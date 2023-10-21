import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main implements Serializable {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of cities: ");
        int numberOfCities = scanner.nextInt();
        Customer[] customers = new Customer[numberOfCities];
        for (int i = 0;i<numberOfCities;i++){
            int cNumber = i+1;
            System.out.print("Enter the " + cNumber + " City Name: ");
            String cityNameInput = scanner.next();
            String cityName = cityNameInput.toUpperCase();
            System.out.print("Enter Customer ID for City " + cNumber + ": ");
            int customerId = scanner.nextInt();
            System.out.print("Enter Order ID for City " + cNumber + ": ");
            int orderId = scanner.nextInt();
            System.out.print("Enter Customer Name for City " + cNumber + ": ");
            String customerName = scanner.next();
            System.out.print("Enter lat for City " + cNumber + ": ");
            float cityLt = Float.parseFloat(scanner.next());
            System.out.print("Enter lon for City " + cNumber + ": "); // Corrected 'lat' to 'lon'
            float cityLn = Float.parseFloat(scanner.next());
            customers[i] = new Customer(cityName,customerId, orderId, customerName, cityLt, cityLn);
        }
        int[][] cityDistanceGraph = new int[numberOfCities][numberOfCities];

        for (int i=0;i<numberOfCities;i++){
            for (int j = 0;j<numberOfCities;j++){
                if(i==j){
                    cityDistanceGraph[i][j]=0;
                }else{
                    String city1Name = customers[i].getCityName();
                    String city2Name = customers[j].getCityName();
                    System.out.print("Enter the distance from City " + city1Name + " to City " + city2Name + ": ");
                    cityDistanceGraph[i][j] = scanner.nextInt();
                }
            }
        }

        Order[] orders = new Order[numberOfCities];
        for (int i = 0; i < numberOfCities; i++) {
            orders[i] = new Order(i, customers[i]);
        }

        int[] tour = tspNearestRoute(numberOfCities, cityDistanceGraph, orders);
        // Include an extra element for returning to City 0
        // Set the last city to City 0
        int[] modifiedTour = new int[numberOfCities + 1];
        for (int i = 0; i < numberOfCities; i++) {
            modifiedTour[i] = tour[i];
        }
        modifiedTour[numberOfCities] = tour[0];

        // Create a list to store the delivery route
        List<Customer> deliveryRoute = new ArrayList<>();
        for (int i = 0; i < modifiedTour.length; i++) {
            int cityIndex = modifiedTour[i];
            Customer customer = customers[cityIndex];
            deliveryRoute.add(customer);
        }

        // Print the delivery route including customer information
        System.out.println("Delivery Route:");
        for (Customer customer : deliveryRoute) {
            System.out.println("City "+customer.getCityName()+" Customer ID=" + customer.getCustomerId()
                    + ", Order ID=" + customer.getOrderId() + ", Name=" + customer.getCustomerName() + ", lat= " + customer.getCityLt() + ", Lon=" + customer.getCityLn());
        }

        int totalCost = calculateTourCost(modifiedTour, cityDistanceGraph);
        System.out.println("Total Distances: " + totalCost);

        scanner.close();



        try {
            FileOutputStream fos = new FileOutputStream("main.ser");
            ObjectOutputStream obs = new ObjectOutputStream(fos);
            obs.writeObject(new Main());
            obs.close();
            fos.close();
            System.out.println("Main object has been serialized.");
        } catch (IOException e) {
            e.printStackTrace();
        }







    }
    private static int[] tspNearestRoute(int numberOfCities, int[][] cityDistanceGraph, Order[] orders) {
        int[] tour = new int[numberOfCities];
        boolean[] visited = new boolean[numberOfCities];
        tour[0] = 0;
        visited[0] = true;

        for (int i = 1; i < numberOfCities; i++) {
            int nearestCity = findNearestCity(tour[i - 1], cityDistanceGraph, visited);
            tour[i] = nearestCity;
            visited[nearestCity] = true;
        }

        return tour;
    }

    private static int findNearestCity(int currentCity, int[][] cityDistanceGraph, boolean[] visited) {
        int nearestCity = -1;
        int minDistance = Integer.MAX_VALUE;

        for (int i = 0; i < cityDistanceGraph.length; i++) {
            if (!visited[i] && cityDistanceGraph[currentCity][i] < minDistance) {
                minDistance = cityDistanceGraph[currentCity][i];
                nearestCity = i;
            }
        }

        return nearestCity;
    }

    private static int calculateTourCost(int[] tour, int[][] cityDistanceGraph) {
        int totalCost = 0;
        for (int i = 0; i < tour.length - 1; i++) {
            totalCost += cityDistanceGraph[tour[i]][tour[i + 1]];
        }
        return totalCost;
    }


}