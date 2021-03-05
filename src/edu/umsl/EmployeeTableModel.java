package edu.umsl;

import javax.swing.table.AbstractTableModel;

public class EmployeeTableModel extends AbstractTableModel {

    private String[] columnNames = {"First Name", "Last Name", "Hours Worked", "Pay Rate"};
    private Employee[] employees;

    public EmployeeTableModel(Employee[] employees) {
        this.employees = employees;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return this.employees.length;
    }

    public Object getValueAt(int row, int col) {
        Object value = null;
        if (col == 0) {
            value = employees[row].getFirstName();
        }
        else if (col == 1) {
            value = employees[row].getLastName();
        }
        else if (col == 2) {
            value = employees[row].getHours();
        }
        else if (col == 3) {
            value = employees[row].getPayRate();
        }
        else if (col == -1) {
            value = employees[row];
        }
        return value;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Class getColumnClass(int col) {
        if (col == 2) {
            return Integer.class;
        }
        else if (col == 3) {
            return Float.class;
        }
        else {
            return String.class;
        }
    }

    public void addEmployee(Employee employee) {
        Employee[] newEmployees = new Employee[employees.length + 1];
        for (int i = 0; i < employees.length; i++) {
            newEmployees[i] = employees[i];
        }
        newEmployees[newEmployees.length -1] = employee;
        employees = newEmployees;
    }

}
