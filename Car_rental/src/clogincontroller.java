import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class clogincontroller {

    @FXML
    private TextField cid;

    @FXML
    private PasswordField pwd;

    @FXML
    private Label warn1;

     @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    Connection con;
    PreparedStatement pst;
    ResultSet rst;

    @FXML
    void lin(ActionEvent event)throws IOException {
        String acc=cid.getText();
        String pass=pwd.getText();
        if (acc.isEmpty() || pass.isEmpty()) {
            warn1.setText("Please enter correct details");
        } else {
            try {
                con=DriverManager.getConnection("jdbc:mysql://localhost:3306/car_rentals", "root", "@N4cnd9k2ay");
                pst=con.prepareStatement("SELECT * FROM users WHERE UR_ID = ? AND PASSWORD = ?");
                pst.setString(1, acc);
                pst.setString(2, pass);
                rst=pst.executeQuery();
                
                if (rst.next()) {
                    boolean adm=rst.getBoolean("ADMIN");
                    if(adm==false)
                    {
                        FXMLLoader loader=new FXMLLoader(getClass().getResource("home.fxml"));
                        root=loader.load();
                        homecontroller hc=loader.getController();
                        hc.getusr(acc);
                        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
                        scene=new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }
                    else{

                    }
                }
                else{
                    warn1.setText("Please enter correct details");
                }
            }catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }   


        }
    }
}