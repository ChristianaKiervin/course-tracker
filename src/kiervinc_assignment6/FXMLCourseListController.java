package kiervinc_assignment6;

import Models.Course;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import static java.lang.Double.parseDouble;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Christiana Kiervin
 * 
 * Student ID: 991622137
 * 
 * Assignment 6
 * 
 * This class controls the logic of an application which allows users to update course information in a file.
 */
public class FXMLCourseListController implements Initializable {

    @FXML
    private ComboBox<Course> ddCourses;
    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtGrade;
    @FXML
    private TextField txtMaxGrade;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnExit;
    @FXML
    private Label lblNotif;
    
    ObservableList<Course> courseList;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Initialize course list
        courseList = FXCollections.observableArrayList();
        Scanner in = null;
        
        try {
            in = new Scanner(new File("CourseList.txt"));
        } catch (FileNotFoundException ex) {
            System.out.println("File not found.");
        }
        
        while (in.hasNextLine()) {
            String s = in.nextLine();
            String[] fields = s.split(",");
            String courseCode = fields[0];
            double grade = parseDouble(fields[1]);
            double maxGrade = parseDouble(fields[2]);
            courseList.add(new Course(courseCode, grade, maxGrade));   
        }
        
        in.close();
        ddCourses.setItems(courseList);
        
        
        //add event listener for when a user selects an item
        ddCourses.getSelectionModel().selectedItemProperty().addListener(new InvalidationListener() 
        {
            
            /**
             * Any time the selected item changes, update the text boxes to show the corresponding field
             */
            @Override
            public void invalidated(Observable observable) {
                lblNotif.setText(""); //Clear any previously invalid entries
                
                int index = ddCourses.getSelectionModel().getSelectedIndex();
                Course selectedCourse = courseList.get(index);

                txtCode.setText(selectedCourse.getCode());
                txtGrade.setText(String.format("%.2f", selectedCourse.getGrade()));
                txtMaxGrade.setText(String.format("%.2f", selectedCourse.getMaxGrade()));
            }   
        });
    }    

    /**
     * The edit method retrieves user input from the textFields to update 
     * the Course object that corresponds to the selected list item. It checks whether the user is entering
     * valid data, and if so, it updates the Course object then updates the txt file containing the list of
     * courses this program initially reads from.
     * 
     * @param event represents the clicking of the Edit button
     * @throws FileNotFoundException if the print writer tries to write to CourseList.txt but it doesn't exist.
     */
    @FXML
    private void edit(ActionEvent event) throws FileNotFoundException {
        
        boolean valid = true;
        String newCode = "";
        double newGrade = 0;
        double newMax = 0;
        
        //Retrieve information from the textfields if it's valid and store it in variables.
        try {
            newCode = txtCode.getText();
            newGrade = parseDouble(txtGrade.getText());
            newMax = parseDouble(txtMaxGrade.getText());
            valid = true;

        } catch (Exception ex) {
            lblNotif.setText("Invalid Data");  //If user enters invalid data, this message will be displayed on the gui.
            valid = false;
        }
        
        
        //If user input was valid, the currently selected Course object will be updated in the courseList
        if (valid) {
            lblNotif.setText("");
            int index = ddCourses.getSelectionModel().getSelectedIndex();
            courseList.get(index).setCode(newCode);
            courseList.get(index).setGrade(newGrade);
            courseList.get(index).setMaxGrade(newMax);
            
            //update the combobox with the new courseList
            ddCourses.setItems(courseList);
            
            
            //Print the new course list to the original CourseList file to update it.
            PrintWriter fileOut = new PrintWriter("CourseList.txt");
            
            for (Course course : courseList) {
                fileOut.println(course.getCode() + "," + course.getGrade() + "," + course.getMaxGrade());
            }
            fileOut.close();
 
        }  
    }

    /**
     * A simple exit button.
     * 
     * @param event represents the clicking of the exit button.
     */
    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }
    
}
