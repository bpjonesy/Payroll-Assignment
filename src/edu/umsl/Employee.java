package edu.umsl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Scanner;

public class Employee implements Serializable {

    private static final long serialVersionUID = 1462259511474889744L;

    private static final int REGULAR_HOURS = 40;

    /*********************
     Attributes
     *********************/
    String firstName;
    String lastName;
    float payRate = 30.0f;
    float taxRate = 0.2f;
    int hours = 45;
    float gross = 0.0f;
    float tax = 0.0f;
    float net = 0.0f;
    float netPercent = 0.0f;
    //End Attributes

    /********************
     Constructors
     ********************/
    public Employee()
    {

    }

    /********************
     Properties
     ********************/
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public float getPayRate() {
        return payRate;
    }

    public void setPayRate(float payRate) {
        this.payRate = payRate;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    /********************
     Methods
     ********************/
    public void menu() {

        displayFullName(System.out);

        int selection = 0;

        do {
            System.out.println(
                            "\nPlease make a selection:\n" +
                            "1) Calculate Gross Pay\n" +
                            "2) Calculate Tax\n" +
                            "3) Calculate Net Pay\n" +
                            "4) Calculate Net Percent\n" +
                            "5) Calculate All\n" +
                            "6) Display Employee\n" +
                            "7) Go Back\n");

            selection = new Scanner(System.in).nextInt();
            switch (selection) {
                case 1:
                    computeGross();
                    displayGross(System.out);
                    break;
                case 2:
                    computeTax();
                    displayTax(System.out);
                    break;
                case 3:
                    computeNet();
                    displayNet(System.out);
                    break;
                case 4:
                    computeNetPercent();
                    displayNetPercent(System.out);
                    break;
                case 5:
                    computeAll();
                    displayPayrollCalculations(System.out);
                    break;
                case 6:
                    computeAll();
                    displayEmployee(System.out);
                    break;
                case 7:
                    break;
                default:
                    System.out.println("Please enter a valid selection\n");
                    break;
            }
        } while (selection !=7);

    }

    public void computeGross() {
        int overtimeHours = this.getHours() - REGULAR_HOURS;

        if (overtimeHours > 0) {
            this.gross = (float)((REGULAR_HOURS * this.getPayRate()) + (overtimeHours * (this.getPayRate() * 1.5)));
        } else {
            this.gross = this.getHours() * this.getPayRate();
        }
    }

    protected void computeTax() {
        computeGross();
        this.tax = this.gross * this.taxRate;
    }

    protected void computeNet(){
        computeTax();
        this.net = this.gross - this.tax;
    }

    protected void computeNetPercent() {
        computeNet();
        this.netPercent = this.net / this.gross;
    }

    protected void computeAll() {
        computeNetPercent();
    }

    protected PayrollResults computePayrollResults() {
        computeAll();
        return new PayrollResults(this.gross,this.tax,this.net,this.netPercent);
    }

    private void displayNetPercent(PrintStream printStream) {
        printStream.println("Net Percent: " + this.netPercent);
    }

    private void displayNet(PrintStream printStream) {
        printStream.println("Net: " + this.net);
    }

    private void displayTax(PrintStream printStream) {
        printStream.println("Tax: " + this.tax);
    }

    private void displayGross(PrintStream printStream) {
        printStream.println("Gross: " + this.gross);
    }

    public void displayFullName(PrintStream printStream) {
        printStream.println(
                "Employee: " + this.getFirstName() + " " + this.getLastName());
    }

    public void displayPayrollCalculations(PrintStream printStream) {
        displayGross(printStream);
        displayTax(printStream);
        displayNet(printStream);
        displayNetPercent(printStream);
    }

    public void displayPayrollReferenceData(PrintStream printStream) {
        printStream.println(
                "Hours Worked: " + this.getHours() +
                "\nPay Rate: " + this.getPayRate() +
                "\nTax Rate: " + this.taxRate);
    }

    protected void displayEmployee(PrintStream printStream){
        printStream.println();
        printStream.println("***** Employee Info *****");
        displayFullName(printStream);
        displayPayrollReferenceData(printStream);
        printStream.println();
        printStream.println("***** Payroll Info  *****");
        displayPayrollCalculations(printStream);
        printStream.println();
    }

    public void saveEmployeeReport() {

        computeAll();

        File file=new File(getFirstName() + "-" + getLastName() + ".txt");
        FileOutputStream fileOutputStream=null;
        PrintStream printStream=null;
        try {
            fileOutputStream=new FileOutputStream(file);
            printStream=new PrintStream(fileOutputStream);
            displayEmployee(printStream);

        } catch (Exception e) {
            System.out.println("Error saving file '" + file.getName() + "'");
        } finally {
            try {
                if(fileOutputStream!=null){
                    fileOutputStream.close();
                }
                if(printStream!=null){
                    printStream.close();
                }
            } catch (Exception e) {
                System.out.println("Error saving file '" + file.getName() + "'");
            }
        }
    }

}
