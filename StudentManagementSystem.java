import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class StudentManagementSystem extends JFrame {
    private ArrayList<Student> students = new ArrayList<>();
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> studentList = new JList<>(listModel);

    public StudentManagementSystem() {
        setTitle("Student Management System");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        panel.add(new JScrollPane(studentList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
        JButton editButton = new JButton("Edit");
        JButton searchButton = new JButton("Search");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(editButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);

        addButton.addActionListener(e -> addStudent());
        removeButton.addActionListener(e -> removeStudent());
        editButton.addActionListener(e -> editStudent());
        searchButton.addActionListener(e -> searchStudent());
        saveButton.addActionListener(e -> saveToFile());
        loadButton.addActionListener(e -> loadFromFile());
    }

    private void addStudent() {
        String name = JOptionPane.showInputDialog(this, "Enter name:");
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty.");
            return;
        }

        String roll = JOptionPane.showInputDialog(this, "Enter roll number:");
        if (roll == null || roll.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Roll number cannot be empty.");
            return;
        }

        String grade = JOptionPane.showInputDialog(this, "Enter grade:");
        if (grade == null || grade.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Grade cannot be empty.");
            return;
        }

        Student s = new Student(name.trim(), roll.trim(), grade.trim());
        students.add(s);
        listModel.addElement(s.toString());
    }

    private void removeStudent() {
        int index = studentList.getSelectedIndex();
        if (index != -1) {
            students.remove(index);
            listModel.remove(index);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student to remove.");
        }
    }

    private void editStudent() {
        int index = studentList.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to edit.");
            return;
        }

        Student s = students.get(index);

        String newName = JOptionPane.showInputDialog(this, "Enter new name:", s.getName());
        if (newName == null || newName.trim().isEmpty()) return;

        String newGrade = JOptionPane.showInputDialog(this, "Enter new grade:", s.getGrade());
        if (newGrade == null || newGrade.trim().isEmpty()) return;

        s.setName(newName.trim());
        s.setGrade(newGrade.trim());
        listModel.set(index, s.toString());
    }

    private void searchStudent() {
        String roll = JOptionPane.showInputDialog(this, "Enter roll number to search:");
        if (roll == null || roll.trim().isEmpty()) return;

        for (Student s : students) {
            if (s.getRollNumber().equalsIgnoreCase(roll.trim())) {
                JOptionPane.showMessageDialog(this, "Found: " + s.toString());
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Student not found.");
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter("students.csv")) {
            for (Student s : students) {
                writer.println(s.toCSV());
            }
            JOptionPane.showMessageDialog(this, "Data saved successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("students.csv"))) {
            students.clear();
            listModel.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                Student s = Student.fromCSV(line);
                students.add(s);
                listModel.addElement(s.toString());
            }
            JOptionPane.showMessageDialog(this, "Data loaded successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentManagementSystem().setVisible(true));
    }
}

class Student {
    private String name;
    private String rollNumber;
    private String grade;

    public Student(String name, String rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() { return name; }
    public String getRollNumber() { return rollNumber; }
    public String getGrade() { return grade; }

    public void setName(String name) { this.name = name; }
    public void setGrade(String grade) { this.grade = grade; }

    public String toString() {
        return rollNumber + " - " + name + " (" + grade + ")";
    }

    public String toCSV() {
        return rollNumber + "," + name + "," + grade;
    }

    public static Student fromCSV(String csv) {
        String[] parts = csv.split(",");
        return new Student(parts[1], parts[0], parts[2]);
    }
}
