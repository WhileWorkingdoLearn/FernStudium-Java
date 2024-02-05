package gui.mvp.basicquiz.game;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class QuizView
{

    private Label question;

    private VBox frame;

    private VBox questionSection;

    private ToggleGroup tg;

    private QuizPresenter quizPresenter;

    private HBox enterArea;

    private Button enterBtn;

    public QuizView()
    {
        // TODO Auto-generated constructor stub
        initGui();
    }

    private void initGui()
    {
        frame = new VBox();
        questionSection = new VBox();
        question = new Label();
        question.setId("question");
        tg = new ToggleGroup();
        frame.getChildren().add(question);
        frame.getChildren().add(questionSection);
        enterArea = new HBox();
        enterBtn = new Button("=>");
        enterBtn.setOnAction(e -> pressEnter());
        enterArea.getChildren().add(enterBtn);
        frame.getChildren().add(enterArea);
    }

    public VBox getGui()
    {
        return this.frame;
    }

    public void deActivateEnterButton(boolean state)
    {
        enterBtn.setDisable(state);
    }

    public void setQuestion(String question)
    {
        this.question.setText(question);
    }

    public void setAnswers(String[] answers)
    {
        questionSection.getChildren().clear();
        tg.getToggles().clear();
        int index = 0;
        if (answers[0].equals(""))
        {
            return;
        }
        for (String string : answers)
        {
            RadioButton radioBtn = new RadioButton(string);
            radioBtn.setToggleGroup(tg);
            radioBtn.setUserData(index);
            questionSection.getChildren().add(radioBtn);
            index++;
        }
    }

    public void setQuizPresenter(QuizPresenter quizPresenter)
    {
        this.quizPresenter = quizPresenter;
    }

    public QuizPresenter getQuizPresenter()
    {
        return quizPresenter;
    }

    public int getSelectedToggle()
    {
        return (int) this.tg.getSelectedToggle().getUserData();
    }

    public synchronized void pressEnter()
    {
        if (tg.getSelectedToggle() != null)
        {
            quizPresenter.checkAnswer();
        }
        else
        {
            quizPresenter.setViewQuestion();
        }
    }

}
