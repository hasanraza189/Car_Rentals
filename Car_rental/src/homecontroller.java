import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.x.protobuf.MysqlxCrud.DataModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class homecontroller {

    private Stage stage;
    private Scene scene;
    private Parent root;
    Connection con;
    PreparedStatement pst;
    ResultSet rst;
    public static String dl;
     public void getusr(String acc) {
        dl=acc;
    }


    @FXML
    private TableColumn<DataModel, Boolean> avl;

    @FXML
    private TableColumn<DataModel, String> lid;

    @FXML
    private TableColumn<DataModel, String> mn;

    @FXML
    private TableColumn<DataModel, String> reg;

    @FXML
    private TableView<DataModel> view;

    @FXML
    void acar(ActionEvent event) throws SQLException {
            mn.setCellValueFactory(new PropertyValueFactory<DataModel,String>("field1"));
            reg.setCellValueFactory(new PropertyValueFactory<DataModel,String>("field2"));
            lid.setCellValueFactory(new PropertyValueFactory<DataModel,String>("field3"));
            avl.setCellValueFactory(new PropertyValueFactory<DataModel,Boolean>("field4"));
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/car_rentals", "root", "@N4cnd9k2ay");
            pst=con.prepareStatement("SELECT * from car WHERE AVAILABILITY_FLAG = 1");
            rst=pst.executeQuery();
    
            while (rst.next()) 
            {
                String model=rst.getString("MODEL_NAME");
                String rgno=rst.getString("REGISTRATION_NUMBER");
                String loc=rst.getString("LOC_ID");
                boolean avlf=rst.getBoolean("AVAILABILITY_FLAG");
                DataModel data= new DataModel(model,rgno,loc,avlf); 
                view.getItems().add(data);       
            }
            try {
                view.setRowFactory(tv -> {
                TableRow<DataModel> row = new TableRow<>();
                row.setOnMouseClicked(ActionEventevent -> {
                    if (!row.isEmpty()) {
                        DataModel rowData = row.getItem();
                        String rg=rowData.getField2();
                        FXMLLoader loader=new FXMLLoader(getClass().getResource("rentcar.fxml"));
                        try {
                            root=loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        rentcarcontroller uf=loader.getController();
                        try {
                            uf.getusr(rg);
                        } catch (SQLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
                        scene=new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                }
            });
            return row;
        });  
            } catch (Exception e) {
                // TODO: handle exception
            }

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
    void ocar(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("mycar.fxml"));
        root=loader.load();
        mycarcontroller my=loader.getController();
        my.getsr(dl);
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
        public class DataModel {

        private String field1;
        private String field2;
        private String field3;
        private Boolean field4;
    
    
        public DataModel(String field1, String field2, String field3, Boolean field4) {
            this.field1 = field1;
            this.field2 = field2;
            this.field3 = field3;
            this.field4 = field4;
        }

        public String getField1() {
            return field1;
        }
    
        public String getField2() {
            return field2;
        }

        public String getField3() {
            return field3;
        }
    
        public Boolean getField4() {
            return field4;
        }
    }
       

}
