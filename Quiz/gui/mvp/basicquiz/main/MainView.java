package gui.mvp.basicquiz.main;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainView
{
    private VBox mainGui;

    private HBox buttonsArea;

    private VBox questionArea;

    private Button btn1;

    private Button btn2;

    private Button btn3;

    private MainPresenter mainPresenter;

    public MainView()
    {
        initGui();
    }

    private void initGui()
    {
        mainGui = new VBox();
        btn1 = new Button("Quiz starten!");
        btn2 = new Button("Quiz fortsetzen!");
        btn2.setDisable(true);
        btn3 = new Button("Überblick!");
        btn1.setOnAction(e -> startButton());
        btn2.setOnAction(e -> switchPanels(true));
        btn3.setOnAction(e -> switchPanels(false));
        btn3.setId("overview");
        buttonsArea = new HBox();
        buttonsArea.getChildren().add(btn1);
        buttonsArea.getChildren().add(btn2);
        buttonsArea.getChildren().add(btn3);
        mainGui.getChildren().add(buttonsArea);
        questionArea = new VBox(6);
        mainGui.getChildren().add(questionArea);
    }

    public VBox getGui()
    {
        return mainGui;
    }

    public void setMainPresenter(MainPresenter mainPresenter)
    {
        this.mainPresenter = mainPresenter;
    }

    public void setQuestionSection(VBox frame)
    {
        questionArea.getChildren().setAll(frame);

    }

    public void setBttnActive(boolean state, int index)
    {
        buttonsArea.getChildren().get(index).setDisable(state);
    }

    public synchronized void switchPanels(boolean state)
    {
        this.btn2.setDisable(state);
        this.btn3.setDisable(!state);
        mainPresenter.changeQuestionSection(!state);
    }

    public synchronized void startButton()
    {
        this.btn2.setDisable(true);
        this.btn3.setDisable(false);
        mainPresenter.statGame();
    }

}
