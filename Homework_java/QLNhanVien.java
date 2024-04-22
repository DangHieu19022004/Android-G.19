import java.util.ArrayList;
import java.util.List;

class Employee{
    private int id;
    private String name;
    private int age;
    private String department;
    private String code;
    private double salaryRate;

    public Employee(int id, String name, int age, String department, String code, double salaryRate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.department = department;
        this.code = code;
        this.salaryRate = salaryRate;
    }

    public void getter(){
        System.out.println("ID: " + id + "\nName: " + name + "\nAge: " + age + "\nDepartment: " + department + "\nCode: " + code + "\nSalary Rate: " + salaryRate);
    }

    public void setter(int id, String name, int age, String department, String code, double salaryRate){
        this.id = id;
        this.name = name;
        this.age = age;
        this.department = department;
        this.code = code;
        this.salaryRate = salaryRate;
    }

    public int getid(){
        return id;
    }
}

class ManageEmployee{
    private List<Employee> listEmployees;

    public ManageEmployee() {
        listEmployees = new ArrayList<Employee>();
    }

    // khoi tao 10 nhan vien
    public void initListEmployee(){
        for(int i = 0; i<10; i++){
            Employee employee = new Employee(i, "Name " + i, 20 + i, "Department " + i, "Code " + i, 1000 + i);
            listEmployees.add(employee);
        }
    }

    // hien thi danh sach nhan vien
    public void ShowListEmployee(){
        for (Employee employee : listEmployees) {
            employee.getter();
        }
    }

    // them nhan vien
    public void addEmployee(Employee employee){
        listEmployees.add(employee);
    }

    // xoa nhan vien
    public void deleteEmployee(int id){
        listEmployees.removeIf(employee -> employee.getid() == id);
    }
}

public class QLNhanVien {
    public static void main(String[] args) {
        ManageEmployee manageEmployee = new ManageEmployee();
        manageEmployee.initListEmployee();
        System.out.println("Danh sach nhan vien: ");
        manageEmployee.ShowListEmployee();
        System.out.println("=====================================");

        System.out.println("Danh sach nhan vien sau thêm: ");
        manageEmployee.addEmployee(new Employee(10, "Hieu", 19, "Department new", "123", 2000));
        manageEmployee.ShowListEmployee();
        System.out.println("=====================================");

        System.out.println("Danh sach nhan vien sau xóa: ");
        manageEmployee.deleteEmployee(5);
        manageEmployee.ShowListEmployee();

    }
}
