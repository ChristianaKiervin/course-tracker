package kiervinc_assignment6;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Christiana Kiervin
 * 
 * Student ID: 991622137
 * 
 * Assignment 6
 */
public class KiervinC_Assignment6 extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
                launch(args);
    }
 
    /**
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLCourseList.fxml"));
        stage.setTitle("Course List");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
    

