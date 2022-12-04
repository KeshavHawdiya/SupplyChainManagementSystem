package com.example.SupplyChainSystem;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class SupplyChain extends Application {

    public static final int width= 700, height= 600, upperLine= 50;

    Pane bodyPane = new Pane();

    public Login login = new Login();                              // create object (instance of)

    ProductDetails productDetails = new ProductDetails();

    Boolean loggedIn = false;

    Label loginLabel;

    Button orderButton;

    private GridPane headerBar(){                                  // create header
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(width, upperLine-5);
        gridPane.setAlignment(Pos.CENTER);                         // to have it at center
        gridPane.setHgap(5);                                       // to prevent from smashed it

        TextField searchText = new TextField();
        searchText.setMinWidth(250);
        searchText.setPromptText("Please search here");

        loginLabel = new Label("Please Login !");
        Button loginButton = new Button("Login");                // this one - button at header, not for page(loginPage)
        loginButton.setOnAction(new EventHandler<ActionEvent>() {   // provide functionality/action to login button
            @Override
            public void handle(ActionEvent actionEvent) {
                if(loggedIn == false){
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(loginPage());
//                    loggedIn = true;                                // on click-on loginButton (header portion),,, login page will display, but might possible if person not log--in here so remove
                    //loginButton.setText("Logout");                // show logout when person logged-In, & again show login when person log-out
                }
                //if(loggedIn == true){
                //
                //    loginButton.setText("Logout");                // show logout when person logged-In, & again show login when person log-out
                //}
            }
        });

        Button searchButton = new Button("Search");
        gridPane.add(searchText, 0, 0);
        gridPane.add(searchButton, 1, 0 );
        gridPane.add(loginLabel, 2, 0);                         // also add loginLabel after searchButton
        gridPane.add(loginButton, 3, 0);
//        gridPane.getChildren().remove(3, 0);
        searchButton.setOnAction(new EventHandler<ActionEvent>() {   // provide functionality to give productDetails(data) on clicking
            @Override
            public void handle(ActionEvent actionEvent) {
//                productDetails.getAllProducts();
                bodyPane.getChildren().clear();
//                bodyPane.getChildren().add(productDetails.getAllProducts());
                String searcH = searchText.getText();
                bodyPane.getChildren().add(productDetails.getProductsByName(searcH));
            }
        });

        return gridPane;
    }


    private GridPane footerBar(){
        GridPane gridPane = new GridPane();
        orderButton = new Button("Buy Now");
        orderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                productDetails.getSelectedProduct();
                if(loggedIn == false){                                      // if person not logged-in, then firstly we display login-page to him
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(loginPage());
                }
                else{
                    Product product = productDetails.getSelectedProduct();  // if person logged-in
                    if(product != null){
                        String email = loginLabel.getText();
                        email = email.substring(10);
                        System.out.println(email);
                        if(Order.placeSingleOrder(product, email)){
                            System.out.println("Order Placed");
                        }
                        else{
                            System.out.println("Order Failed");
                        }
                    }
                    else{                                                   // logged-in but not selecting product
                        System.out.println("Please select a product");
                    }
                }
            }
        });
        gridPane.add(orderButton,0, 0);
        gridPane.setMinWidth(width);
        gridPane.setTranslateY(height+35);
        return gridPane;
    }


    private GridPane loginPage(){
        Label emailLabel = new Label("Email");
        Label passwordLabel = new Label("Password");
        Label messageLabel = new Label("I am message");

        TextField emailTextField = new TextField();
        emailTextField.setPromptText("Please enter email");         // blur-blur sa already likha rhega textField pr
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Please enter password");

        Button loginButton = new Button("Login");                // this one - button for page (loginPage)
        loginButton.setOnAction(new EventHandler<ActionEvent>() {   // provide action/functionality to login-button
            @Override
            public void handle(ActionEvent actionEvent) {
                String email = emailTextField.getText();
                String password = passwordField.getText();
                if(login.customerLogin(email, password)){
                    loginLabel.setText("Welcome : " + email);        // we can try to add NAME of person instead of email
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(productDetails.getAllProducts());  // after successful login, it will ri-direct to product page
                    loggedIn = true;
                    messageLabel.setText("Login Successful");
                }
                else{
                    messageLabel.setText("Invalid User");
                }
//                messageLabel.setText("email - " + email + " && password - " + password);
            }
        });

        GridPane gridPane = new GridPane();                         // divides whole into blocks/grid... it holds our controllers- buttons, labels, etc
        gridPane.setMinSize(bodyPane.getMinWidth(), bodyPane.getMinHeight());

        gridPane.setVgap(10);                                       // provide gap so everything not getting smashed
        gridPane.setHgap(10);

        gridPane.setAlignment(Pos.CENTER);
//        gridPane.setStyle("-fx-background-color: #C0C0C0;");        // to set color for Pane


        gridPane.add(emailLabel, 0, 0);
        gridPane.add(emailTextField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 0, 2);
        gridPane.add(messageLabel, 1, 2);

        return gridPane;
    }

    Pane createContent(){
        Pane root = new Pane();
        root.setPrefSize(width, height+upperLine+15);
        bodyPane.setTranslateY(upperLine+10);
        bodyPane.setMinSize(width, height);
//        bodyPane.setBorder(new Border(Color.BLACK));
//        bodyPane.setStyle("-fx-background-color: #C0C9C0;");

//        bodyPane.getChildren().add(loginPage());                     // add login to bodyPane && then bodyPane will be added to MAIN-ROOT-PANE
        bodyPane.getChildren().add(productDetails.getAllProducts());   // add products and login portion is at header section


        // we create different/separate platform for those controllers and then add that platform(gridPane) as a part of our original pane(ROOT)... it will be helpfull for Further additions
        root.getChildren().addAll(bodyPane, headerBar(), footerBar());
        return root;
    }

    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("Supply Chain System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}