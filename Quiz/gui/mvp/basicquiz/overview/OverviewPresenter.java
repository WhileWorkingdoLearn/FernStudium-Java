package gui.mvp.basicquiz.overview;

import java.util.List;

import gui.mvp.basicquiz.main.MainPresenter;
import gui.mvp.basicquiz.model.Model;
import gui.mvp.basicquiz.model.Question;
import javafx.scene.layout.VBox;

public class OverviewPresenter
{
    private OverviewView overviewGui;

    private MainPresenter mainPresenter;

    private Model game;

    public OverviewPresenter(OverviewView overviewGui, Model game)
    {
        this.overviewGui = overviewGui;
        this.game = game;
        setAnswersToList();
    }

    public void setAnswersToList()
    {
        List<Question> quetions = this.game.getAllQuestion();

        overviewGui.setOverViewList(quetions);
    }

    // public void addCurrentAnswerToList()
    // {
    // Question question = game.getCurrentQuestion();
    // String questionStr = "";
    // questionStr += question.getQuestion();
    // questionStr += "( Antworten: " + question.getAnswered() + " davon
    // richtig: " + question.getAnsweredRight() + ")";
    // overviewGui.addToOverviewList(questionStr);
    // }

    public void setMainPresenter(MainPresenter mainPresenter)
    {
        this.mainPresenter = mainPresenter;
    }

    public MainPresenter getMainPresenter()
    {
        return mainPresenter;
    }

    public VBox getGui()
    {
        return this.overviewGui.getGui();
    }

    // public void deleteList()
    // {
    // game.deleteFinishedQuestions();
    // }

    public void resetAnswers()
    {
        // TODO Auto-generated method stub
        game.resetGame();
        setAnswersToList();
    }

}
