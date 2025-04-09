package main;

import java.util.Scanner;
import entity.User;
import entity.Product;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import util.DBPropertyUtil;
import util.DBConnUtil;

public class MainModule {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nOrder Management System");
            System.out.println("1. Create User");
            System.out.println("2. Create Product");
            System.out.println("3. Create Order");
            System.out.println("4. Get All Products");
            System.out.println("5. Get Orders by User");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createUser(scanner);
                    break;
                case 2:
                    createProduct(scanner);
                    break;
                case 3:
                    createOrder(scanner);
                    break;
                case 4:
                    getAllProducts();
                    break;
                case 5:
                    getOrdersByUser(scanner);
                    break;
                case 6:
                    running = false;
                    System.out.println("Thank you for using Order Management System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void createUser(Scanner scanner) {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Role (Admin/User): ");
        String role = scanner.nextLine();

        User user = new User(userId, username, password, role);
        // TODO: Add user creation logic
        System.out.println("User created successfully!");
    }

    private static void createProduct(Scanner scanner) {
        System.out.print("Enter Product ID: ");
        int productId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Product Name: ");
        String productName = scanner.nextLine();
        System.out.print("Enter Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Quantity in Stock: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Product Type (Electronics/Clothing): ");
        String type = scanner.nextLine();

        try {
            // Get database connection
            String connectionString = DBPropertyUtil.getConnectionString("src/db.properties");
            Connection conn = DBConnUtil.getConnection(connectionString);
            
            if (conn != null) {
                // Prepare the insert query
                String query = "INSERT INTO products (product_id, product_name, description, price, quantity_in_stock, type) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(query);
                
                // Set the values
                pstmt.setInt(1, productId);
                pstmt.setString(2, productName);
                pstmt.setString(3, description);
                pstmt.setDouble(4, price);
                pstmt.setInt(5, quantity);
                pstmt.setString(6, type);
                
                // Execute the insert
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("Product created successfully!");
                } else {
                    System.out.println("Failed to create product.");
                }
                
                // Close resources
                pstmt.close();
                conn.close();
            }
        } catch (Exception e) {
            System.out.println("Error creating product: " + e.getMessage());
        }
    }

    private static void createOrder(Scanner scanner) {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Show all available products from database
        System.out.println("\nAvailable Products in Inventory:");
        System.out.printf("%-5s %-25s %-15s %-15s %-10s\n", "ID", "Name", "Price", "Type", "Quantity");
        
        try {
            // Get database connection
            String connectionString = DBPropertyUtil.getConnectionString("src/db.properties");
            Connection conn = DBConnUtil.getConnection(connectionString);
            
            if (conn != null) {
                // Query to get all products
                String query = "SELECT * FROM products WHERE quantity_in_stock > 0";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                
                // Display products
                while (rs.next()) {
                    System.out.printf("%-5d %-25s %-15.2f %-15s %-10d\n",
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getDouble("price"),
                        rs.getString("type"),
                        rs.getInt("quantity_in_stock"));
                }
                
                // Close resources
                rs.close();
                stmt.close();
                conn.close();
            }
        } catch (Exception e) {
            System.out.println("Error displaying products: " + e.getMessage());
            return;
        }

        // Ask for product selection
        System.out.print("\nEnter Product ID you want to order: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Ask for quantity
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // TODO: Add code to create order in database
        System.out.println("Order created successfully!");
    }

    private static void getAllProducts() {
        try {
            String connectionString = DBPropertyUtil.getConnectionString("src/db.properties");
            Connection conn = DBConnUtil.getConnection(connectionString);
            
            if (conn != null) {
                System.out.println("\nAll Products in Inventory:");               
                String query = "SELECT * FROM products";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                
                boolean hasProducts = false;
                
                while (rs.next()) {
                    hasProducts = true;
                    System.out.println("ID: " + rs.getInt("product_id"));
                    System.out.println("Name: " + rs.getString("product_name"));
                    System.out.println("Price: " + rs.getDouble("price"));
                    System.out.println("Type: " + rs.getString("type"));
                    System.out.println("Quantity: " + rs.getInt("quantity_in_stock"));
                }
                
                if (!hasProducts) {
                    System.out.println("No products found in inventory.");
                }
                
                rs.close();
                stmt.close();
                conn.close();
            } else {
                System.out.println("Failed to connect to database.");
            }
        } catch (Exception e) {
            System.out.println("Error displaying products: " + e.getMessage());
        }
    }

    private static void getOrdersByUser(Scanner scanner) {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        try {
            String connectionString = DBPropertyUtil.getConnectionString("src/db.properties");
            Connection conn = DBConnUtil.getConnection(connectionString);
            
            if (conn != null) {
                String userQuery = "SELECT username FROM users WHERE user_id = ?";
                PreparedStatement userStmt = conn.prepareStatement(userQuery);
                userStmt.setInt(1, userId);
                ResultSet userRs = userStmt.executeQuery();
                
                if (!userRs.next()) {
                    System.out.println("User not found!");
                    return;
                }
                
                String username = userRs.getString("username");
                System.out.println("\nOrders for User: " + username);
                
                String query = "SELECT o.order_id, p.product_name, p.price, p.type, o.order_date " +
                             "FROM orders o " +
                             "JOIN products p ON o.product_id = p.product_id " +
                             "WHERE o.user_id = ? " +
                             "ORDER BY o.order_date DESC";
                
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, userId);
                ResultSet rs = pstmt.executeQuery();
                
                boolean hasOrders = false;
                
                while (rs.next()) {
                    hasOrders = true;
                    System.out.println("Order ID: " + rs.getInt("order_id"));
                    System.out.println("Product: " + rs.getString("product_name"));
                    System.out.println("Price: " + rs.getDouble("price"));
                    System.out.println("Type: " + rs.getString("type"));
                    System.out.println("Date: " + rs.getDate("order_date"));
                }
                
                if (!hasOrders) {
                    System.out.println("No orders found for this user.");
                }
                
                rs.close();
                pstmt.close();
                userRs.close();
                userStmt.close();
                conn.close();
            } else {
                System.out.println("Failed to connect to database.");
            }
        } catch (Exception e) {
            System.out.println("Error displaying orders: " + e.getMessage());
        }
    }
} 