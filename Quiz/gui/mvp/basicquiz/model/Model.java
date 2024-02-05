package gui.mvp.basicquiz.model;

import java.util.ArrayList;
import java.util.List;

public class Model
{
    private List<Question> questions;

    // private List<Question> finishedQuestions;

    private Question currentQuestion;

    private int index;

    public Model()
    {
        this.questions = new ArrayList<Question>();
        // this.finishedQuestions = new ArrayList<Question>();
        this.index = 0;
    }

    public int getSize()
    {
        return questions.size();
    }

    public void addQuestion(Question q)
    {
        this.questions.add(q);
    }

    public List<Question> getAllQuestion()
    {
        return questions;
    }

    // private void addFinishedQuestion(Question question)
    // {
    // if (!finishedQuestions.contains(question))
    // {
    // finishedQuestions.add(question);
    // }
    // };

    // public List<Question> getFinishedQuestions()
    // {
    // return finishedQuestions;
    // }

    // public void deleteFinishedQuestions()
    // {
    // finishedQuestions.clear();
    // }

    public synchronized boolean checkQuestion(int answerIndex)
    {
        if (answerIndex < 0 || answerIndex >= currentQuestion.getPossibleAnswers().length)
        {
            throw new IllegalArgumentException();
        }
        boolean state = false;

        if (currentQuestion.getIndexOfCorrectAnswer() == answerIndex)
        {
            state = true;
            currentQuestion.setAnsweredRight();
        }
        currentQuestion.setAswered();
        // addFinishedQuestion(currentQuestion);
        return state;
    }

    public void resetGame()
    {
        for (Question question : questions)
        {
            question.reseAnswerCount();
        }
        // finishedQuestions.clear();
        index = 0;
    }

    public void resetCounter()
    {
        index = 0;
    }

    public Question getCurrentQuestion()
    {
        return currentQuestion;
    }

    public Question getNextQuestion()
    {
        Question q = questions.get(index);
        this.index++;
        if (index >= questions.size())
        {
            index = 0;
        }
        currentQuestion = q;
        return q;
    }
}
