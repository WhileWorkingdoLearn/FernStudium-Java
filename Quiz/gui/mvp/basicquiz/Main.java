package gui.mvp.basicquiz;

import java.util.List;

import gui.mvp.basicquiz.game.QuizPresenter;
import gui.mvp.basicquiz.game.QuizView;
import gui.mvp.basicquiz.main.MainPresenter;
import gui.mvp.basicquiz.main.MainView;
import gui.mvp.basicquiz.model.Model;
import gui.mvp.basicquiz.overview.OverviewPresenter;
import gui.mvp.basicquiz.overview.OverviewView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        List<String> path = getParameters().getRaw();
        Model m = ModelInitializer.initModel(path.get(0));
        initApplication(primaryStage, m);
    }

    private void initApplication(Stage primaryStage, Model game)
    {

        MainView mv = new MainView();
        MainPresenter mp = new MainPresenter(mv);
        mv.setMainPresenter(mp);

        QuizView qv = new QuizView();
        QuizPresenter qp = new QuizPresenter(qv, game);
        qv.setQuizPresenter(qp);
        qp.setMainPresenter(mp);
        qp.setViewQuestion();

        OverviewView ov = new OverviewView();
        OverviewPresenter op = new OverviewPresenter(ov, game);
        ov.setOverviewPresenter(op);
        op.setMainPresenter(mp);

        mp.setQuizPresenter(qp);
        mp.setOverviewPresenter(op);
        mp.changeQuestionSection(false);

        Scene sc = new Scene(mp.getGui());
        primaryStage.setScene(sc);
        primaryStage.setTitle("Quiz");
        primaryStage.show();
    };

    public static void main(String[] args)
    {
        launch(args);
    }
}
