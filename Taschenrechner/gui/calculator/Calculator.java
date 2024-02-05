package gui.calculator;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Calculator extends Application
{
    private static Pane root;

    private static TextField txtField;

    private static String lastResult;

    @FXML
    public synchronized void handleEvent(ActionEvent e)
    {
        Button btn = (Button) e.getSource();
        String btnText = btn.getText().toLowerCase();
        switch (btnText)
        {
            case "clear":
                txtField.clear();
                lastResult = null;
                break;
            case "delete":

                String str = txtField.getText().equals("") ? "" : txtField.getText().substring(0, txtField.getText().length() - 1);
                txtField.setText(str);
                lastResult = null;
                break;
            case "=":
                txtField.appendText(calculate(btnText));
                break;
            default:
                if (lastResult != null)
                {
                    txtField.clear();
                    lastResult = null;
                }
                txtField.appendText(btnText);
                break;
        }
    };

    private String calculate(String calc)
    {
        String result = "";
        try
        {
            result += Computation.evaluate(txtField.getText());
        }
        catch (IllegalArgumentException exception)
        {
            result = ">FEHLER";
        }
        lastResult = calc + result;
        return calc + result;
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        try
        {
            root = (Pane) FXMLLoader.load(getClass().getResource("Taschenrechner.fxml"));
            txtField = (TextField) root.lookup("#textfield");
            txtField.setId("display");
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Zähler");
            primaryStage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public static void main(String[] args)
    {
        launch(args);

    }

}
