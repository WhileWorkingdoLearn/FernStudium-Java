package gui.mvp.basicquiz.overview;

import java.util.List;

import gui.mvp.basicquiz.model.Question;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OverviewView
{
    private Label overView;

    private ListView<Question> listView;

    private VBox frame;

    private OverviewPresenter overviewPresenter;

    private HBox enterArea;

    private Button enterBtn;

    public OverviewView()
    {
        initGui();
    }

    private void initGui()
    {
        frame = new VBox();
        overView = new Label("Übersicht");
        listView = new ListView<Question>();
        listView.setId("overviewList");
        frame.getChildren().add(overView);
        frame.getChildren().add(listView);
        enterArea = new HBox();
        enterBtn = new Button("Ereignisse Löschen");
        enterBtn.setId("deleteHistory");
        enterBtn.setOnAction(e -> resetOverviewList());
        enterArea.getChildren().add(enterBtn);
        frame.getChildren().add(enterArea);
    }

    public VBox getGui()
    {
        return this.frame;
    }

    public void setOverviewPresenter(OverviewPresenter overviewPresenter)
    {
        this.overviewPresenter = overviewPresenter;
    }

    public OverviewPresenter getOverviewPresenter()
    {
        return overviewPresenter;
    }

    public void setOverViewList(List<Question> questions)
    {
        listView.getItems().clear();

        listView.getItems().setAll(questions);
        listView.setPrefHeight(questions.size() * 24 + 2);
    }

    public void resetOverviewList()
    {
        overviewPresenter.resetAnswers();
    }

}
