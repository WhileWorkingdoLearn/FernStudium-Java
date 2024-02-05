package gui.pizza;

import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Pizza extends Application
{
    private VBox root;

    private TextArea txtf;

    private TilePane toppingBox;

    private ToggleGroup tg;

    private HBox sizeBox;

    private Map<String, Integer> prices;

    @Override
    public void start(Stage primaryStage)
    {
        // Input params
        Map<String, String> params = getParameters().getNamed();
        Configuration cfg = ParameterConverter.createConfiguration(params);

        // Erstelle eine Item Preisliste
        prices = new HashMap<String, Integer>();
        for (int i = 0; i < cfg.getToppingNames().length; i++)
        {
            prices.put(cfg.getToppingNames()[i], cfg.getToppingPrices()[i]);
        }
        for (int i = 0; i < cfg.getSizeNames().length; i++)
        {
            prices.put(cfg.getSizeNames()[i], cfg.getSizePrices()[i]);
        }

        // Hauptbox
        root = new VBox(5);

        // Zutaten
        toppingBox = new TilePane();
        Label l = new Label("Zutaten: ");
        for (int i = 0; i < cfg.getToppingNames().length; i++)
        {
            CheckBox cb = new CheckBox(cfg.getToppingNames()[i]);
            if (i < cfg.getNumberOfDefaultToppings())
            {
                cb.setSelected(true);
                cb.setDisable(true);
            }
            toppingBox.getChildren().add(cb);
        }

        // Größe
        sizeBox = new HBox(5);
        Label l2 = new Label("Größe: ");
        tg = new ToggleGroup();
        for (String s : cfg.getSizeNames())
        {
            RadioButton rb = new RadioButton(s);
            rb.setToggleGroup(tg);

            sizeBox.getChildren().add(rb);
        }
        int index = tg.getToggles().size();
        tg.selectToggle(tg.getToggles().get(index % 2));
        toppingBox.getChildren().add(sizeBox);

        // Button
        Button btn = new Button("Bestellen!");
        btn.setOnAction(e -> selectPizza());

        // Rückgabetext;
        txtf = new TextArea();
        txtf.setId("bestelltext");
        txtf.setEditable(false);

        // Zusammenführen
        root.getChildren().add(l);
        root.getChildren().add(toppingBox);
        root.getChildren().add(l2);
        root.getChildren().add(sizeBox);
        root.getChildren().add(btn);
        root.getChildren().add(txtf);

        // Build GUI
        Scene newScene = new Scene(root);
        primaryStage.setScene(newScene);
        primaryStage.setTitle("Pizza Bestellen");
        primaryStage.show();

    }

    private synchronized void selectPizza()
    {
        String subString = "";
        int price = 0;
        for (Node cbItem : toppingBox.getChildren())
        {

            if (cbItem instanceof CheckBox)
            {

                CheckBox cb = (CheckBox) cbItem;
                if (cb.selectedProperty().getValue())
                {
                    if (subString.length() > 1)
                    {
                        subString += " ,";
                    }
                    subString += cb.getText();
                    price += prices.get(cb.getText());
                }
            }
        }
        RadioButton rd = (RadioButton) tg.getSelectedToggle();
        price += prices.get(rd.getText());
        double amount = (double) price / 100;

        String order = "Sie haben eine Pizza Bestellt. ";
        String ingredients = "Zutaten: " + subString + " ";
        String diameter = "Die Größe ist: " + rd.getText() + " ";
        String endPrice = "Der Preis beträgt: " + amount + " €. ";
        String thanks = "Vielen Dank.";
        String str = (order + " \n" + ingredients + " \n" + diameter + " \n" + endPrice + " \n" + thanks);
        this.txtf.setText(str);

    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
