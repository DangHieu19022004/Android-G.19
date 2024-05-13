import java.util.ArrayList;
import java.util.Scanner;

class Employee {
    private int id;
    private String name;
    private int age;
    private String department;
    private String code;
    private double salaryRate;

    // Set
    public Employee(int id, String name, int age, String department, String code, double salaryRate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.department = department;
        this.code = code;
        this.salaryRate = salaryRate;
    }

    int getID() {
        return id;
    }

    public void display() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Department: " + department);
        System.out.println("Code: " + code);
        System.out.println("Salary Rate: " + salaryRate);
    }
}

public class QLNV {
    private static ArrayList<Employee> employeeList = new ArrayList<>();
    private static int nextId = 1;

    public static void main(String[] arg) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Number: ");
        int Number = scanner.nextInt();

        initializeEmployees(Number, scanner);
        displayEmployeeList();
        removeEmployee(scanner);
        displayEmployeeList();
        scanner.close();
    }

    private static void initializeEmployees(int Number, Scanner scanner) {
        for (int i = 0; i < Number; i++) {
            System.out.println("\nEnter details for employee #" + (i + 1) + ":");
            addEmployee(scanner);
        }
    }

    private static void addEmployee(Scanner scanner) {
        scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Department: ");
        String department = scanner.nextLine();

        System.out.print("Code: ");
        String code = scanner.nextLine();

        System.out.print("Salary Rate: ");
        double salaryrate = scanner.nextDouble();
        

        Employee employee = new Employee(nextId++, name, age, department, code, salaryrate);
        employeeList.add(employee);
    }

    private static void displayEmployeeList() {
        System.out.println("\nEmployee List:");
        for (int i=0; i<employeeList.size(); i++) {
            Employee employee = employeeList.get(i);
            employee.display();
            System.out.println();
        }
    }

    private static void removeEmployee(Scanner scanner) {
        scanner.nextLine();
        System.out.println("Enter ID of the employee to remove, or press Enter to finish: ");
        String input = scanner.nextLine();
        if (!input.isEmpty()) {
            int IDRemove = Integer.parseInt(input);
            boolean removed = false;
            for (int i = 0; i < employeeList.size(); i++) {
                if (employeeList.get(i).getID() == IDRemove) {
                    employeeList.remove(i);
                    removed = true;
                    System.out.println("Employee with ID " + IDRemove + " removed successfully!");
                    break;
                }
            }
            if (!removed) {
                System.out.println("Employee with ID " + IDRemove + " not found!");
            }
        }
    }
}

