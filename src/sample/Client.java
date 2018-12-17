package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.WindowEvent;
import sample.config.Const;
import javafx.application.Application;
import javafx.stage.Stage;
import sample.model.Model;
import sample.objects.User;

import java.io.*;
import java.net.*;

public class Client extends Application {

    private static Socket clientSocket;
    private static ObjectOutputStream outputStream;
    private static ObjectInputStream inputStream;
    public static Stage mainStage;

    @Override
    public void start(Stage primaryStage){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Const.PATH_MAIN_VIEW));

            Parent fxmlMain = loader.load();
            mainStage = primaryStage;
            primaryStage.setTitle("Главная");

            Scene scene = new Scene(fxmlMain);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            eventClose(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            clientSocket = new Socket(Const.HOST, Const.PORT);
            System.out.println("Connection accepted " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        launch(args);
    }

    public static ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public static ObjectInputStream getInputStream() {
        return inputStream;
    }

    public static void close() {
        try {
            User user = new User();
            user.setType(Model.LOGOUT);

            Model modelObj = new Model();
            modelObj.setTypeObject(Model.USER);
            modelObj.setUserObject(user);

            outputStream.writeObject(modelObj);

            if (outputStream != null) outputStream.close();
            if (inputStream != null) inputStream.close();
            if (clientSocket != null) clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void eventClose(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                Client.close();
            }
        });
    }
}




