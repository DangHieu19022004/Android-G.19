import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {
    private List<Employee> employeeList;

    public EmployeeManager() {
        employeeList = new ArrayList<>();
        initializeEmployees();
    }

    private void initializeEmployees() {
        employeeList.add(new Employee(1, "John Doe", 30, "Engineering", "ENG01", 5000));
        employeeList.add(new Employee(2, "Jane Smith", 28, "Marketing", "MKT01", 4500));
        employeeList.add(new Employee(3, "James Brown", 35, "Finance", "FIN01", 6000));
        employeeList.add(new Employee(4, "Emily Davis", 32, "Human Resources", "HR01", 4800));
        employeeList.add(new Employee(5, "Michael Johnson", 29, "Sales", "SLS01", 4700));
        employeeList.add(new Employee(6, "Sarah Wilson", 27, "Engineering", "ENG02", 5100));
        employeeList.add(new Employee(7, "David Miller", 33, "Operations", "OPS01", 5500));
        employeeList.add(new Employee(8, "Nancy Garcia", 31, "Customer Service", "CS01", 4300));
        employeeList.add(new Employee(9, "Steven Martinez", 34, "Legal", "LEG01", 6300));
        employeeList.add(new Employee(10, "Jessica Anderson", 26, "Information Technology", "IT01", 5200));
    }

    // Hiển thị danh sách nhân viên
    public void displayAllEmployees() {
        System.out.println("Danh sách nhân viên:");
        for (Employee employee : employeeList) {
            System.out.println(employee);
        }
    }

    // Thêm nhân viên mới
    public void addEmployee(Employee newEmployee) {
        employeeList.add(newEmployee);
        System.out.println("Nhân viên mới đã được thêm vào danh sách.");
    }

    // Xóa nhân viên theo ID
    public void deleteEmployee(int employeeId) {
        employeeList.removeIf(employee -> employee.getId() == employeeId);
        System.out.println("Nhân viên đã được xóa khỏi danh sách.");
    }
}
