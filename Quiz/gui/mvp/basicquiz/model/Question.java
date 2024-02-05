package gui.mvp.basicquiz.model;

public class Question
{
    private String question;

    private String[] possibleAnswers;

    private int indexOfCorrectAnswer;

    private int answered;

    private int answeredRight;

    // weitere Attribute nach Bedarf
    public Question(String question, String[] possibleAnswers, int indexOfCorrectAnswer)
    {
        if (indexOfCorrectAnswer < 0 || indexOfCorrectAnswer >= possibleAnswers.length)
        {
            throw new IllegalArgumentException("Index out of range");
        }
        this.question = question;
        this.possibleAnswers = possibleAnswers;
        this.indexOfCorrectAnswer = indexOfCorrectAnswer;
        this.answeredRight = 0;
        this.answered = 0;
    }

    public String getQuestion()
    {
        return this.question;
    }

    public String[] getPossibleAnswers()
    {
        return this.possibleAnswers;
    }

    public int getIndexOfCorrectAnswer()
    {
        return this.indexOfCorrectAnswer;
    }

    public void setAnsweredRight()
    {
        this.answeredRight++;
    }

    public void setAswered()
    {
        this.answered++;
    }

    public String toString()
    {
        return this.question + " (Antworten: " + this.answered + " davon richtig: " + this.answeredRight + ")";
    }

    public void reseAnswerCount()
    {
        this.answeredRight = 0;
        this.answered = 0;
    }
}
