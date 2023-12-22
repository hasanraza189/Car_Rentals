import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class rentcarcontroller {

    @FXML
    private Label cat;

    @FXML
    private Label cpd;

    @FXML
    private Label lfc;

    @FXML
    private Label mil;

    @FXML
    private Label mod;

    @FXML
    private Label mody;

    @FXML
    private Label np;

    private Stage stage;
    private Scene scene;
    private Parent root;
    Connection con;
    PreparedStatement pst;
    ResultSet rst;
    String rg;
    int cd;

    public void getusr(String acc) throws SQLException {
        this.rg=acc;
        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/car_rentals", "root", "@N4cnd9k2ay");
        pst=con.prepareStatement("SELECT * from car_details WHERE RG_NUM = ?");
        pst.setString(1, rg);
        rst=pst.executeQuery();
    
        while (rst.next()) 
        {
            String model=rst.getString("MODEL_NAME");
            String ct=rst.getString("CATEGORY");
            int my=rst.getInt("MODEL_YEAR");
            int nop=rst.getInt("NO_OF_PERSON");
            int ml=rst.getInt("MILEAGE");
            this.cd=rst.getInt("COST_PER_DAY");
            int la=rst.getInt("LATE_FEE_PER_HOUR");
            mod.setText("MODEL : "+model);
            cat.setText("CATEGORY : "+ct);
            mody.setText("YEAR : "+my);
            np.setText("NO OF PERSONS : "+nop);
            mil.setText("MILEAGE : "+ml);
            cpd.setText("COST PER DAY : "+cd);
            lfc.setText("LATE FEE : "+la);
        }
        
    }

    @FXML
    void bn(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("book.fxml"));
        root=loader.load();
        bookcontroller bk=loader.getController();
        bk.getusr(rg,cd);
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void acar(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("home.fxml"));
        root=loader.load();
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void lo(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("clogin.fxml"));
        root=loader.load();
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void ocar(ActionEvent event) {

    }

}
