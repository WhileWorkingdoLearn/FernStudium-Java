package gui.mvp.basicquiz.main;

import gui.mvp.basicquiz.game.QuizPresenter;
import gui.mvp.basicquiz.overview.OverviewPresenter;
import javafx.scene.layout.VBox;

public class MainPresenter
{
    private MainView mainGui;

    private OverviewPresenter overviewPresenter;

    private QuizPresenter quizPresenter;

    public MainPresenter(MainView mainGui)
    {
        this.mainGui = mainGui;
    }

    public VBox getGui()
    {
        return this.mainGui.getGui();
    }

    public void setOverviewPresenter(OverviewPresenter overviewPresenter)
    {
        this.overviewPresenter = overviewPresenter;
    }

    public void setQuizPresenter(QuizPresenter quizPresenter)
    {
        this.quizPresenter = quizPresenter;
    }

    public void changeQuestionSection(boolean state)
    {
        VBox gui = quizPresenter.getGui();
        if (state)
        {
            // overviewPresenter.setAnswersToList();
            gui = overviewPresenter.getGui();

        }

        mainGui.setQuestionSection(gui);

    }

    public void statGame()
    {
        quizPresenter.reseCounter();
        changeQuestionSection(false);
    }

    public void addQuestionToList()
    {
        // TODO Auto-generated method stub
        // overviewPresenter.addCurrentAnswerToList();
        overviewPresenter.setAnswersToList();
    }

}
