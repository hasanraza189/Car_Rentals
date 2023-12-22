import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class mycarcontroller {

    private Stage stage;
    private Scene scene;
    private Parent root;
    Connection con;
    PreparedStatement pst;
    ResultSet rst;
    public static String dln;
    
    public void getsr(String dl) {
        dln=dl;
    }

    @FXML
    private TableColumn<DataModel, Integer> bid;

    @FXML
    private TableColumn<DataModel, Date> fd;

    @FXML
    private TableColumn<DataModel, String> reg;

    @FXML
    private TableColumn<DataModel, Date> td;

    @FXML
    private TableView<DataModel> view;

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
    void ocar(ActionEvent event) throws SQLException {
        bid.setCellValueFactory(new PropertyValueFactory<DataModel,Integer>("field1"));
        reg.setCellValueFactory(new PropertyValueFactory<DataModel,String>("field2"));
        fd.setCellValueFactory(new PropertyValueFactory<DataModel,Date>("field3"));
        td.setCellValueFactory(new PropertyValueFactory<DataModel,Date>("field4"));
        view.getItems().clear();
        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/car_rentals", "root", "@N4cnd9k2ay");
        pst=con.prepareStatement("{CALL GetBookingDetailsByCustomer(?)}");
        pst.setString(1, dln);
        rst=pst.executeQuery();
        while (rst.next()) {
            int model=rst.getInt("BOOKING_ID");
            String rgno=rst.getString("REG_NUM");
            Date picdate=rst.getDate("FROM_DT_TIME");
            LocalDate pcd=picdate.toLocalDate();
            Date runtdate=rst.getDate("RET_DT_TIME");
            LocalDate rdte=runtdate.toLocalDate();
            DataModel data= new DataModel(model,rgno,pcd,rdte); 
            view.getItems().add(data);       
        }
         try {
                view.setRowFactory(tv -> {
                TableRow<DataModel> row = new TableRow<>();
                row.setOnMouseClicked(ActionEventevent -> {
                    if (!row.isEmpty()) {
                        DataModel rowData = row.getItem();
                        try {
                            pst=con.prepareStatement("DELETE FROM booking_details WHERE BOOKING_ID = ?");
                            pst.setInt(1, rowData.getField1());
                            pst.executeUpdate();
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Success");
                            alert.setHeaderText(null);
                            alert.setContentText("Car Returned");
                            alert.showAndWait();
                        }
                        catch (SQLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                       
                       
                    }
                });
                return row;
            });  
            } catch (Exception e) {
                // TODO: handle exception
            }
    }

    public class DataModel {

        private int field1;
        private String field2;
        private LocalDate field3;
        private LocalDate field4;
      
    
        public DataModel(int field1, String field2, LocalDate field3, LocalDate field4) {
            this.field1 = field1;
            this.field2 = field2;
            this.field3 = field3;
            this.field4 = field4;
        }

        public int getField1() {
            return field1;
        }
    
        public String getField2() {
            return field2;
        }

        public LocalDate getField3() {
            return field3;
        }
    
        public LocalDate getField4() {
            return field4;
        }

    }


}
