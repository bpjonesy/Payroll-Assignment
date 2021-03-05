package edu.umsl;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class PayrollGUI {

    private Payroll payroll;
    private EmployeeTableModel employeeTableModel;

    private JTable employeesTable;

    private JLabel firstNameLabel = new JLabel("First Name: ");
    private JLabel lastNameLabel = new JLabel("Last Name: ");
    private JLabel hoursLabel = new JLabel("Hours Worked: ");
    private JLabel payRateLabel = new JLabel("Pay Rate: ");
    private JLabel grossPayLabel = new JLabel("Gross Pay: ");
    private JLabel taxLabel = new JLabel("Tax: ");
    private JLabel netPayLabel = new JLabel("Net Pay: ");
    private JLabel netPercentLabel = new JLabel("Net Percent: ");

    private JTextField firstNameText = new JTextField(10);
    private JTextField lastNameText = new JTextField(10);
    private JTextField hoursText = new JTextField(5);
    private JTextField payRateText = new JTextField(5);

    private JLabel grossPayValueLabel = new JLabel("", SwingConstants.RIGHT);
    private JLabel taxValueLabel = new JLabel("", SwingConstants.RIGHT);
    private JLabel netPayValueLabel = new JLabel("", SwingConstants.RIGHT);
    private JLabel netPercentValueLabel = new JLabel("", SwingConstants.RIGHT);

    private JButton newButton = new JButton("New");
    private JButton saveButton = new JButton("Save");
    private JButton cancelButton = new JButton("Cancel");

    private JButton saveEmployeesButton = new JButton("Save Employees");

    public void launch(Payroll payroll) {
        this.payroll = payroll;
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private void createAndShowGUI() {

        //setLookAndFeel();
        //UIManager.getLookAndFeelDefaults()
                //.put("defaultFont", new Font("Ubuntu Regular", Font.PLAIN, 11));

        //setFonts();
        JFrame frame = new JFrame("Payroll");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addEmployeeDetailPanel(frame);
        addEmployeesTable(frame, payroll.getEmployees());
        addSavePanel(frame);

        frame.setPreferredSize(new Dimension(800, 600));
        SwingUtilities.updateComponentTreeUI(frame);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void setFonts() {
        FontUIResource f = new javax.swing.plaf.FontUIResource("Ubuntu",Font.PLAIN,14);
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
        }
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private void addSavePanel(JFrame frame) {
        JPanel savePanel = new JPanel();
        savePanel.add(saveEmployeesButton, BorderLayout.CENTER);
        frame.add(savePanel, BorderLayout.SOUTH);
    }

    private void addEmployeeDetailPanel(JFrame frame) {
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        // add components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        detailPanel.add(firstNameLabel, constraints);

        constraints.gridx = 1;
        detailPanel.add(firstNameText, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        detailPanel.add(lastNameLabel, constraints);

        constraints.gridx = 1;
        detailPanel.add(lastNameText, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        detailPanel.add(payRateLabel, constraints);

        constraints.gridx = 1;
        detailPanel.add(payRateText, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        detailPanel.add(hoursLabel, constraints);

        constraints.gridx = 1;
        detailPanel.add(hoursText, constraints);


        constraints.gridx = 2;
        constraints.gridy = 0;
        detailPanel.add(grossPayLabel, constraints);

        constraints.gridx = 3;
        detailPanel.add(grossPayValueLabel, constraints);

        constraints.gridx = 2;
        constraints.gridy = 1;
        detailPanel.add(taxLabel, constraints);

        constraints.gridx = 3;
        detailPanel.add(taxValueLabel, constraints);

        constraints.gridx = 2;
        constraints.gridy = 2;
        detailPanel.add(netPayLabel, constraints);

        constraints.gridx = 3;
        detailPanel.add(netPayValueLabel, constraints);

        constraints.gridx = 2;
        constraints.gridy = 3;
        detailPanel.add(netPercentLabel, constraints);

        constraints.gridx = 3;
        detailPanel.add(netPercentValueLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        //constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        detailPanel.add(newButton, constraints);

        constraints.gridx = 1;
        //constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        detailPanel.add(saveButton, constraints);

        constraints.gridx = 2;
        //constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        detailPanel.add(cancelButton, constraints);

        // set border for the panel
        detailPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Employee Detail"));

        frame.add(detailPanel, BorderLayout.NORTH);

        firstNameText.setEditable(false);
        lastNameText.setEditable(false);

        payRateText.setEditable(false);
        payRateText.setInputVerifier(new InputVerifier(){
            @Override
            public boolean verify(JComponent input) {
                JTextField textField = ((JTextField) input);
                String text = textField.getText();
                try {
                    float value = Float.parseFloat(text);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        });

        hoursText.setEditable(false);
        hoursText.setInputVerifier(new InputVerifier(){
            @Override
            public boolean verify(JComponent input) {
                JTextField textField = ((JTextField) input);
                String text = textField.getText();
                try {
                    int value = Integer.parseInt(text);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        });

        Dimension labelDimensions = new Dimension(70,20);
        grossPayValueLabel.setPreferredSize(labelDimensions);
        taxValueLabel.setPreferredSize(labelDimensions);
        netPayValueLabel.setPreferredSize(labelDimensions);
        netPercentValueLabel.setPreferredSize(labelDimensions);

        saveButton.setEnabled(false);
        cancelButton.setEnabled(false);

        newButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                clearEmployeeFields();
                firstNameText.setEditable(true);
                lastNameText.setEditable(true);
                payRateText.setEditable(true);
                hoursText.setEditable(true);
                saveButton.setEnabled(true);
                cancelButton.setEnabled(true);
                newButton.setEnabled(false);
            }
        });

        saveButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmployee();
                lockEmployeeFields();
            }
        });

        cancelButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                lockEmployeeFields();
            }
        });

    }

    private void addEmployee() {
        Employee employee = new Employee();
        employee.setFirstName(firstNameText.getText());
        employee.setLastName(lastNameText.getText());
        employee.setPayRate(Float.parseFloat(payRateText.getText()));
        employee.setHours(Integer.parseInt(payRateText.getText()));
        employeeTableModel.addEmployee(employee);
        employeeTableModel.fireTableDataChanged();
        int selectionRow = employeeTableModel.getRowCount() -1;
        employeesTable.getSelectionModel().setSelectionInterval(selectionRow, selectionRow);
    }

    private void lockEmployeeFields() {
        firstNameText.setEditable(false);
        lastNameText.setEditable(false);
        payRateText.setEditable(false);
        hoursText.setEditable(false);
        saveButton.setEnabled(false);
        cancelButton.setEnabled(false);
        newButton.setEnabled(true);
    }

    private void clearEmployeeFields() {
        firstNameText.setText("");
        lastNameText.setText("");
        payRateText.setText("");
        hoursText.setText("");
        grossPayValueLabel.setText("");
        taxValueLabel.setText("");
        netPayValueLabel.setText("");
        netPercentValueLabel.setText("");
    }

    private void addEmployeesTable(JFrame frame, Employee[] employees) {

        this.employeeTableModel = new EmployeeTableModel(employees);

        employeesTable = new JTable(employeeTableModel);

        employeesTable.setAutoCreateRowSorter(true);

        employeesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                int selectedRow = employeesTable.getSelectedRow();
                if (selectedRow > -1) {
                    loadDetails((Employee) employeesTable.getValueAt(selectedRow, -1));
                }
            }
        });
        frame.add(new JScrollPane(employeesTable), BorderLayout.CENTER);

    }

    private void loadDetails(Employee employee) {
        employee.computeAll();
        firstNameText.setText(employee.getFirstName());
        lastNameText.setText(employee.getLastName());
        payRateText.setText(Float.toString(employee.getPayRate()));
        hoursText.setText(Integer.toString(employee.getHours()));

        PayrollResults payrollResults = employee.computePayrollResults();

        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        grossPayValueLabel.setText(formatter.format(payrollResults.getGross()));
        taxValueLabel.setText(formatter.format(payrollResults.getTax()));
        netPayValueLabel.setText(formatter.format(payrollResults.getNet()));
        netPercentValueLabel.setText(String.format("%,.2f%%", (payrollResults.getNetPercent() * 100)));

        firstNameText.setEditable(false);
        lastNameText.setEditable(false);
        payRateText.setEditable(false);
        hoursText.setEditable(false);

    }

}
