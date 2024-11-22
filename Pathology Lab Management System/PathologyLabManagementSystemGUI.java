import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Patient {
    String name;
    int age;
    String gender;
    ArrayList<Test> tests = new ArrayList<>();

    Patient(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    void addTest(Test test) {
        tests.add(test);
    }

    double calculateTotalBill() {
        double total = 0;
        for (Test test : tests) {
            total += test.cost;
        }
        return total;
    }

    @Override
    public String toString() {
        return name + " | Age: " + age + " | Gender: " + gender;
    }
}

class Test {
    String name;
    double cost;

    Test(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }
}

public class PathologyLabManagementSystemGUI {
    private JFrame frame;
    private DefaultListModel<Patient> patientListModel;
    private JList<Patient> patientList;

    public PathologyLabManagementSystemGUI() {
        frame = new JFrame("Pathology Lab Management System");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        patientListModel = new DefaultListModel<>();
        patientList = new JList<>(patientListModel);

        
        JButton addPatientButton = new JButton("Add Patient");
        JButton addTestButton = new JButton("Add Test");
        JButton viewDetailsButton = new JButton("View Details");
        JButton calculateBillButton = new JButton("Calculate Bill");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));
        buttonPanel.add(addPatientButton);
        buttonPanel.add(addTestButton);
        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(calculateBillButton);

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(patientList), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.EAST);

        
        addPatientButton.addActionListener(e -> addPatient());
        addTestButton.addActionListener(e -> addTest());
        viewDetailsButton.addActionListener(e -> viewDetails());
        calculateBillButton.addActionListener(e -> calculateBill());

        frame.setVisible(true);
    }

    private void addPatient() {
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JComboBox<String> genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});

        Object[] message = {
                "Name:", nameField,
                "Age:", ageField,
                "Gender:", genderBox
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Add Patient", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String gender = (String) genderBox.getSelectedItem();

            patientListModel.addElement(new Patient(name, age, gender));
            JOptionPane.showMessageDialog(frame, "Patient added successfully!");
        }
    }

    private void addTest() {
        Patient selectedPatient = patientList.getSelectedValue();
        if (selectedPatient == null) {
            JOptionPane.showMessageDialog(frame, "Please select a patient.");
            return;
        }

        JTextField testNameField = new JTextField();
        JTextField costField = new JTextField();

        Object[] message = {
                "Test Name:", testNameField,
                "Cost:", costField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Add Test", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String testName = testNameField.getText();
            double cost = Double.parseDouble(costField.getText());

            selectedPatient.addTest(new Test(testName, cost));
            JOptionPane.showMessageDialog(frame, "Test added successfully!");
        }
    }

    private void viewDetails() {
        Patient selectedPatient = patientList.getSelectedValue();
        if (selectedPatient == null) {
            JOptionPane.showMessageDialog(frame, "Please select a patient.");
            return;
        }

        StringBuilder details = new StringBuilder();
        details.append("Name: ").append(selectedPatient.name).append("\n");
        details.append("Age: ").append(selectedPatient.age).append("\n");
        details.append("Gender: ").append(selectedPatient.gender).append("\n");
        details.append("Tests:\n");

        for (Test test : selectedPatient.tests) {
            details.append("- ").append(test.name).append(": ₹").append(test.cost).append("\n");
        }

        JOptionPane.showMessageDialog(frame, details.toString(), "Patient Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void calculateBill() {
        Patient selectedPatient = patientList.getSelectedValue();
        if (selectedPatient == null) {
            JOptionPane.showMessageDialog(frame, "Please select a patient.");
            return;
        }

        double totalBill = selectedPatient.calculateTotalBill();
        JOptionPane.showMessageDialog(frame, "Total Bill: ₹" + totalBill, "Billing", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PathologyLabManagementSystemGUI::new);
    }
}