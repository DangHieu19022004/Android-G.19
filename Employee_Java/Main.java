public class Main {
    public static void main(String[] args) {
        EmployeeManager employeeManager = new EmployeeManager();

        // Hiển thị danh sách nhân viên ban đầu
        employeeManager.displayAllEmployees();

        // Thêm nhân viên mới
        Employee newEmployee = new Employee(11, "William Robinson", 36, "Management", "MGT01", 7000);
        employeeManager.addEmployee(newEmployee);

        // Hiển thị lại danh sách nhân viên sau khi thêm nhân viên mới
        System.out.println("\nDanh sách sau khi thêm nhân viên:");
        employeeManager.displayAllEmployees();

        // Xóa một nhân viên
        employeeManager.deleteEmployee(3);

        // Hiển thị lại danh sách nhân viên sau khi xóa nhân viên
        System.out.println("\nDanh sách sau khi xóa nhân viên:");
        employeeManager.displayAllEmployees();
    }
}
