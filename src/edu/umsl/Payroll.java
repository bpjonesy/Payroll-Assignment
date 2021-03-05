package edu.umsl;

import java.io.*;
import java.util.Scanner;

public class Payroll {

    private static final String EMPLOYEES_FILE_NAME = "employees.db";

    private Employee[] employees = new Employee[0];
    private boolean employeesLoaded = false;

    public static void main(String[] args) {
        new Payroll().menu();
    }

    private void menu() {
        int selection = 0;

        do {
            System.out.println(
                            "\nPlease make a selection:\n" +
                            "1) Populate Employees\n" +
                            "2) Select Employee\n" +
                            "3) Save Employees\n" +
                            "4) Load Employees\n" +
                            "5) Launch GUI\n" +
                            "6) Exit\n");

            selection = new Scanner(System.in).nextInt();
            switch (selection) {
                case 1:
                    if (employeesLoaded) {
                        System.out.println("You have already loaded employees from file");
                    } else {
                        populateEmployees();
                    }
                    break;
                case 2:
                    selectEmployee();
                    break;
                case 3:
                    saveEmployees();
                    break;
                case 4:
                    loadEmployees();
                    break;
                case 5:
                    this.loadEmployees();
                    new PayrollGUI().launch(this);
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Please enter a valid selection\n");
                    break;
            }
        } while (selection !=6);

    }

    private void populateEmployees() {

        String enterMore = "N";

        do {
            System.out.println("\nPlease enter details for employee " + (employees.length + 1) + ":");

            Employee employee = new Employee();
            Scanner inputScanner = new Scanner(System.in);

            System.out.println("-->First Name?");
            employee.setFirstName(inputScanner.nextLine());

            System.out.println("-->Last Name?");
            employee.setLastName(inputScanner.nextLine());

            System.out.println("-->Hours Worked?");
            employee.setHours(inputScanner.nextInt());

            System.out.println("-->Pay Rate?");
            employee.setPayRate(inputScanner.nextFloat());

            addEmployee(employee);

            inputScanner = new Scanner(System.in);
            System.out.println("Do you wish to enter another (Y/N)?");
            enterMore = inputScanner.nextLine();

        } while (enterMore.equalsIgnoreCase("Y"));


    }

    private void selectEmployee() {
        System.out.println("\nPlease enter the employee name (First Last):");

        Scanner inputScanner = new Scanner(System.in);

        String searchFirstName = inputScanner.next();
        String searchLastName = inputScanner.next();

        Employee foundEmployee = null;
        for (int i =0; i < employees.length; i++) {
            if ((employees[i].getFirstName().equalsIgnoreCase(searchFirstName)) &&
                (employees[i].getLastName().equalsIgnoreCase(searchLastName))) {
                foundEmployee = employees[i];
                break;
            }
        }

        if (foundEmployee != null) {
            foundEmployee.menu();
        } else {
            System.out.println("Employee not found");
        }

    }

    private void saveEmployees() {

        try
        {
            FileOutputStream file = new FileOutputStream(EMPLOYEES_FILE_NAME);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(employees);

            out.close();
            file.close();

            System.out.println("Employees successfully saved!");

        }

        catch(IOException ex)
        {
            System.out.println("Error saving employees");
        }

        saveEmployeeReports();

    }

    private void loadEmployees() {
        try
        {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(EMPLOYEES_FILE_NAME);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            employees = (Employee[])in.readObject();

            in.close();
            file.close();

            employeesLoaded = true;

            System.out.println("Employees successfully loaded!");

        }

        catch(IOException e)
        {
            e.printStackTrace();
        }

        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private void addEmployee(Employee employee) {
        Employee[] newEmployees = new Employee[employees.length + 1];
        for (int i = 0; i < employees.length; i++) {
            newEmployees[i] = employees[i];
        }
        newEmployees[newEmployees.length -1] = employee;
        employees = newEmployees;
    }

    private void saveEmployeeReports() {
        for (int i =0; i < employees.length; i++) {
            employees[i].saveEmployeeReport();
        }
    }

    public Employee[] getEmployees() {
        return this.employees;
    }

}
