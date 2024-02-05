package gui.mvp.basicquiz.game;

import gui.mvp.basicquiz.main.MainPresenter;
import gui.mvp.basicquiz.model.Model;
import gui.mvp.basicquiz.model.Question;
import javafx.scene.layout.VBox;

public class QuizPresenter
{
    private QuizView quizGui;

    private Model game;

    private MainPresenter mainPresenter;

    private int qestionsCount;

    public QuizPresenter(QuizView quizView, Model game)
    {
        this.quizGui = quizView;
        this.game = game;
        this.qestionsCount = 0;
    }

    private Question getQuestionFromGame()
    {

        return game.getNextQuestion();
    }

    public void setViewQuestion()
    {

        System.out.println(qestionsCount + " " + game.getSize());
        if (qestionsCount >= game.getSize())
        {
            quizGui.setQuestion("Ende des Quiz");
            quizGui.setAnswers(new String[]
            { "" });
            quizGui.deActivateEnterButton(true);
            qestionsCount = 0;
            return;
        }
        qestionsCount++;

        Question q = getQuestionFromGame();
        quizGui.deActivateEnterButton(false);
        quizGui.setQuestion(q.getQuestion());
        quizGui.setAnswers(q.getPossibleAnswers());
    }

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
        return this.quizGui.getGui();
    }

    private int getSelectedAnswer()
    {
        return quizGui.getSelectedToggle();
    }

    public void checkAnswer()
    {
        game.checkQuestion(getSelectedAnswer());
        mainPresenter.addQuestionToList();
        setViewQuestion();

    }

    public void reseCounter()
    {
        qestionsCount = 0;
        game.resetCounter();
        this.setViewQuestion();
    }

    public void resetGame()
    {
        this.game.resetGame();
        this.setViewQuestion();
    }

}
