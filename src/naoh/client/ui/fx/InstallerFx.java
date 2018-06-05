package naoh.client.ui.fx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import naoh.client.Log;
import naoh.client.Predifined;
import naoh.installer.Uninstaller;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class InstallerFx extends Application {
//    public static void begin() {
//        launch();
//    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane bp = new BorderPane();
        GridPane pane = new GridPane();
        VBox windowBottom = new VBox();
        Text t = new Text("配置Sodium Hydroxide");
        HBox prog = new HBox();

        Button btnInstall = new Button("安装");
        Button btnDisable = new Button("禁用");
        Button btnUninstall = new Button("卸载");
        Button btnFetchLog = new Button("Fetch Log");

        ProgressBar progressBar = new ProgressBar();
        Text information = new Text();

        bp.setBottom(windowBottom);
        bp.setTop(pane);

        windowBottom.getChildren().addAll(information, progressBar);

        pane.setAlignment(Pos.TOP_CENTER);
        pane.setVgap(25);
        pane.setPadding(new Insets(25, 25, 25, 25));

//        pane.setGridLinesVisible(true);

        Scene sc = new Scene(bp, 300, 600);
        primaryStage.setScene(sc);

        t.setFont(Font.font(20));
        pane.add(t, 0, 0);

        VBox box = new VBox();
        box.setSpacing(10);
        box.setAlignment(Pos.TOP_CENTER);

        progressBar.setMaxWidth(Double.MAX_VALUE);

        prog.setAlignment(Pos.BOTTOM_CENTER);
//        prog.getChildren().addAll(progressBar);

        btnInstall.setMaxWidth(Double.MAX_VALUE);
        btnDisable.setMaxWidth(Double.MAX_VALUE);
        btnUninstall.setMaxWidth(Double.MAX_VALUE);
//        btnUninstall.setPrefWidth(Double.MAX_VALUE);
        btnFetchLog.setMaxWidth(Double.MAX_VALUE);

        btnDisable.setDisable(true);
        btnUninstall.setDisable(true);

        box.getChildren().addAll(btnInstall, btnDisable, btnUninstall, btnFetchLog);

        pane.add(box, 0, 1);

        primaryStage.setResizable(false);
        primaryStage.setTitle("配置Sodium Hydroxide");
        primaryStage.show();

        //Events
        btnInstall.setOnAction(ev -> {
            try {
                Runtime.getRuntime().exec(new File("/install.bat").getAbsolutePath());
            } catch (IOException e) {
                Log.printException(e);
            }
        });

        btnDisable.setOnAction(ev -> new Uninstaller());

        btnFetchLog.setOnAction(ev -> {
            try {
                Runtime.getRuntime().exec("explorer " + new File(Predifined.defaultLogPath.value).getParent());
            } catch (IOException e) {
                JOptionPane.showConfirmDialog(null, e.toString(), "Unable to find file", JOptionPane.OK_OPTION);
            }
        });

        primaryStage.setOnCloseRequest(ev -> naoh.client.Application.exit());
    }
}
