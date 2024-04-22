import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EmployeeManager manager = new EmployeeManager();
        Scanner scanner = new Scanner(System.in);

        // Hiển thị danh sách nhân viên ban đầu
        manager.displayEmployees();

        // Thêm một nhân viên mới bằng cách nhập từ bàn phím
        System.out.println("Nhap thong tin cho nhan vien moi:");
        System.out.print("ID: ");
        int newId = scanner.nextInt();
        scanner.nextLine(); // Đọc newline còn lại
        System.out.print("Name: ");
        String newName = scanner.nextLine();
        System.out.print("Age: ");
        int newAge = scanner.nextInt();
        scanner.nextLine(); // Đọc newline còn lại
        System.out.print("Deparment: ");
        String newDepartment = scanner.nextLine();
        System.out.print("Code: ");
        String newCode = scanner.nextLine();
        System.out.print("salaryRate: ");
        double newSalaryRate = scanner.nextDouble();

        Employee newEmployee = new Employee(newId, newName, newAge, newDepartment, newCode, newSalaryRate);
        manager.addEmployee(newEmployee);

        // Hiển thị lại danh sách sau khi thêm
        manager.displayEmployees();

        // Xóa một nhân viên bằng cách nhập từ bàn phím
        System.out.print("Nhap ID cua nhan vien muon xoa: ");
        int deleteId = scanner.nextInt();
        manager.deleteEmployee(deleteId);

        // Hiển thị lại danh sách sau khi xóa
        manager.displayEmployees();
    }
}
