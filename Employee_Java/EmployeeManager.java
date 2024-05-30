import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {
    private List<Employee> employees;

    public EmployeeManager() {
        this.employees = new ArrayList<>();
        initEmployees(); // Khởi tạo danh sách nhân viên ban đầu
    }

    private void initEmployees() {
        employees.add(new Employee(1, "John Doe", 30, "IT", "A001", 3000));
        employees.add(new Employee(2, "Jane Smith", 28, "HR", "B002", 3200));
        employees.add(new Employee(3, "Mike Johnson", 35, "Finance", "C003", 3500));
        employees.add(new Employee(4, "Emily Davis", 27, "Marketing", "D004", 3100));
        employees.add(new Employee(5, "Chris Brown", 32, "Operations", "E005", 3400));
        employees.add(new Employee(6, "David Wilson", 29, "IT", "F006", 2900));
        employees.add(new Employee(7, "Laura Garcia", 26, "HR", "G007", 3150));
        employees.add(new Employee(8, "Robert Martinez", 31, "Finance", "H008", 3350));
        employees.add(new Employee(9, "Mary Anderson", 33, "Marketing", "I009", 3050));
        employees.add(new Employee(10, "Linda White", 34, "Operations", "J010", 3300));
    }

    public void displayEmployees() {
        // Tiêu đề cột
        System.out.format("%-5s %-15s %-5s %-15s %-10s %-10s%n", 
                          "ID", "Name", "Age", "Department", "Code", "Salary Rate");
        System.out.println("--------------------------------------------------------------------");

        // Hiển thị danh sách nhân viên
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    public void addEmployee(Employee newEmployee) {
        employees.add(newEmployee);
        System.out.println("Da them nhan vien moi: " + newEmployee);
    }

    public boolean deleteEmployee(int employeeId) {
        boolean isDeleted = employees.removeIf(employee -> employee.getId() == employeeId);
        if (isDeleted) {
            System.out.println("Da xoa nhan vien co ID: " + employeeId);
        } else {
            System.out.println("Khong tim thay nhan vien co ID: " + employeeId);
        }
        return isDeleted;
    }
}
