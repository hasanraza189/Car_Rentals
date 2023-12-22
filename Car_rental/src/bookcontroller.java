import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class bookcontroller {

    public String rgn;
    public int cpd;

    @FXML
    private TextField dln;

    @FXML
    private TextField dloc;

    @FXML
    private DatePicker fd;

    @FXML
    private TextField pl;

    @FXML
    private DatePicker rd;

    private Stage stage;
    private Scene scene;
    private Parent root;
    Connection con;
    PreparedStatement pst,pst1;
    int rst,rst1;
     public void getusr(String rg,int cd) {
        this.rgn=rg;
        this.cpd=cd;
    }

    @FXML
    void book(ActionEvent event) throws SQLException, IOException {
        LocalDate frd=fd.getValue();
        LocalDate rnd=rd.getValue();
        long df=ChronoUnit.DAYS.between(frd, rnd);
        int amt=(int)df*cpd;
        String pkl=pl.getText();
        String dpl=dloc.getText();
        String dl=dln.getText();
        try{
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/car_rentals", "root", "@N4cnd9k2ay");
            pst=con.prepareStatement("INSERT INTO booking_details(FROM_DT_TIME,RET_DT_TIME,AMOUNT,PICKUP_LOC,DROP_LOC,REG_NUM,DL_NUM) VALUES (?, ?, ?, ?, ?, ?, ?)");
            pst.setDate(1, java.sql.Date.valueOf(frd));
            pst.setDate(2, java.sql.Date.valueOf(rnd));
            pst.setInt(3, amt);
            pst.setString(4, pkl);
            pst.setString(5, dpl);
            System.out.println(this.rgn);
            pst.setString(6, this.rgn);
            pst.setString(7, dl);
            rst=pst.executeUpdate();
                
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Booked");
            alert.showAndWait();
                
            
        }catch (MysqlDataTruncation e) {
            // Handle the truncation exception
            System.err.println("Data truncation error: " + e.getMessage());
            // Optionally, log the error or show a user-friendly message
        }
        FXMLLoader loader=new FXMLLoader(getClass().getResource("home.fxml"));
        root=loader.load();
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    

}
