package com.example.project_test;

import java.lang.*;
import javafx.application.*;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;

import javafx.scene.image.Image;

import java.io.*;
import java.util.ArrayList;

public class Game extends Application {
    static Scene mainMenu;
    static Scene pauseMenu;
    static Scene gameScreen;
    static Scene weaponScreen;
    static Scene gameOverscreen;
    static Scene reviveScreen;
    static Scene gameWinscreen;
    static Scene loadScreen;
    static Text score;
    static Text coinScore;
    static int row;
    static Hero hero;
    public static ArrayList<GameObject> list;
    static Stage stg;

    @Override
    public void start(Stage stage) throws IOException, Exception {
        makeMainMenu(stage);
        makePauseMenu(stage);
        makeWeaponInfoScreen(stage);
        makeGameOverScreen(stage);
        makeReviveScreen(stage);
        makeGameWinScreen(stage);
        makeLoadScreen(stage);
        stg = stage;
        stage.setTitle("Will Hero");
        stage.setScene(mainMenu);
        stage.show();
    }

    public void toMainMenu(Stage stage) {
        stage.setScene(mainMenu);
        stage.show();
    }

    public void toPauseMenu(Stage stage) {
        stage.setScene(pauseMenu);
        stage.show();
    }

    public void toGameScreen(Stage stage) throws Exception {
        makeGameScreen(stage);
        stage.setScene(gameScreen);
        stage.show();
    }

    public void resumeScreen(Stage stage) throws Exception {
        stage.setScene(gameScreen);
    }

    public void toWeaponScreen(Stage stage) {
        stage.setScene(weaponScreen);
        stage.show();
    }

    public static void toGameOverScreen() {
        stg.setScene(gameOverscreen);
        stg.show();
    }

    public static void toGameWinScreen() {
        AnchorPane curr = (AnchorPane) Game.gameWinscreen.getRoot();
        Text tc = new Text();
        tc.setText("You collected " + hero.coins + " coins.");
        tc.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        tc.setLayoutX(450);
        tc.setLayoutY(450);
        tc.setFill(Color.DARKCYAN);
        curr.getChildren().add(tc);
        stg.setScene(gameWinscreen);
        stg.show();
    }

    public static void toReviveScreen() throws InsufficientCoinsException {
        if (hero.coins < 10) throw new InsufficientCoinsException();
        else {
            AnchorPane curr = (AnchorPane) Game.reviveScreen.getRoot();
            Text tc = new Text();
            tc.setText("You have " + hero.coins + " coins.");
            tc.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
            tc.setFill(Color.WHITE);
            tc.setLayoutX(525);
            tc.setLayoutY(250);
            curr.getChildren().add(tc);
            stg.setScene(reviveScreen);
            stg.show();
        }
    }

    public static void toLoadScreen() {
        stg.setScene(loadScreen);
        stg.show();
    }

    public void makeMainMenu(Stage stage) throws Exception {
        Button mb1 = new Button("START GAME");
        Button mb2 = new Button("LOAD GAME");
        Button mb3 = new Button("EXIT");

        Font font = Font.font("Courier New", FontWeight.BOLD, 36);
        mb1.setFont(font);
        mb1.setStyle("-fx-background-color: #4169e1 ; -fx-text-fill: #FFFFFF; -fx-border-color: #000000; -fx-border-width: 1.5px; -fx-border-radius: 20;-fx-background-radius: 20;");
        Font font1 = Font.font("Courier New", FontWeight.BOLD, 25);
        mb2.setFont(font1);
        mb2.setStyle("-fx-background-color: #FF00FF; -fx-border-color: #000000; -fx-border-width: 1.5px;-fx-border-radius: 20;-fx-background-radius: 20;");
        mb3.setFont(font1);
        mb3.setStyle("-fx-background-color: #FF0000; -fx-border-color: #000000; -fx-border-width: 1.5px;-fx-border-radius: 20;-fx-background-radius: 20;");

        mb1.setPrefSize(300, 75);
        mb2.setPrefSize(200, 50);
        mb3.setPrefSize(200, 50);

        mb1.setLayoutX(490);
        mb1.setLayoutY(500);
        mb2.setLayoutX(1005);
        mb2.setLayoutY(560);
        mb3.setLayoutX(75);
        mb3.setLayoutY(560);

        mb1.setOnMouseClicked(e -> {
            try {
                hero = new Hero(175, 480);
                makeGameScreen(stage);
                toGameScreen(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        mb3.setOnMouseClicked(e -> {
            stage.hide();
        });
        mb2.setOnMouseClicked(e -> {
            try {
                makeLoadScreen(stage);
                load(stage);
                toLoadScreen();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        AnchorPane mr1 = new AnchorPane();

        FileInputStream titlei = new FileInputStream("images\\titlescreen.jpg");
        Image titleimage = new Image(titlei);
        BackgroundImage titlebkgimg = new BackgroundImage(titleimage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1280, 720, false, false, true, false));
        Background titlebkg = new Background(titlebkgimg);
        mr1.setBackground(titlebkg);

        FileInputStream hm = new FileInputStream("images\\helmet_img.jpg");
        Image helmet_img = new Image(hm);
        ImageView imageView = new ImageView(helmet_img);
        imageView.setPreserveRatio(true);
        Button mb4 = new Button();
        mb4.setTranslateX(100);
        mb4.setTranslateY(40);
        imageView.setFitHeight(250);
        mb4.setPrefSize(20, 20);
        mb4.setGraphic(imageView);
        mb4.setStyle("-fx-background-color: #00FF00; -fx-border-color: #000000; -fx-border-width: 1.5px;-fx-border-radius: 15;-fx-background-radius: 15;");
        mb4.setOnAction(e -> {
            toWeaponScreen(stage);
        });
        mr1.getChildren().addAll(mb1, mb2, mb3, mb4);
        mainMenu = new Scene(mr1, 1280, 720);
    }

    public void makePauseMenu(Stage stage) throws Exception {
        Text t=new Text("PAUSE MENU");
        t.setLayoutX(510);
        t.setLayoutY(75);
        t.setFont(Font.font("Helvetica", FontWeight.BOLD, 40));
        Button pb1 = new Button("RESUME GAME");
        Button pb2 = new Button("RESTART GAME");
        Button pb3 = new Button("SAVE GAME");
        Button pb4 = new Button("EXIT");
        Button pb5 = new Button("LOAD GAME");
        Font font1 = Font.font("Courier New", FontWeight.BOLD, 25);
        pb1.setFont(font1);
        pb2.setFont(font1);
        pb3.setFont(font1);
        pb4.setFont(font1);
        pb5.setFont(font1);
        pb1.setStyle("-fx-background-color: #00FF00 ; -fx-border-color: #000000; -fx-border-width: 1.5px;-fx-border-radius: 20;-fx-background-radius: 20;");
        pb2.setStyle("-fx-background-color: #008000 ; -fx-border-color: #000000; -fx-border-width: 1.5px;-fx-border-radius: 20;-fx-background-radius: 20;");
        pb3.setStyle("-fx-background-color: #FF00FF; -fx-border-color: #000000; -fx-border-width: 1.5px;-fx-border-radius: 20;-fx-background-radius: 20;");
        pb4.setStyle("-fx-background-color: #FF0000; -fx-border-color: #000000; -fx-border-width: 1.5px;-fx-border-radius: 20;-fx-background-radius: 20;");
        pb5.setStyle("-fx-background-color: #9370DB; -fx-border-color: #000000; -fx-border-width: 1.5px;-fx-border-radius: 20;-fx-background-radius: 20;");
        pb1.setPrefSize(250, 50);
        pb2.setPrefSize(250, 50);
        pb3.setPrefSize(250, 50);
        pb4.setPrefSize(250, 50);
        pb5.setPrefSize(250, 50);

        pb1.setLayoutX(515);
        pb1.setLayoutY(130);
        pb2.setLayoutX(515);
        pb2.setLayoutY(250);
        pb3.setLayoutX(515);
        pb3.setLayoutY(370);
        pb5.setLayoutX(515);
        pb5.setLayoutY(490);
        pb4.setLayoutX(515);
        pb4.setLayoutY(610);

        pb1.setOnMouseClicked(e -> {
            try {
                resumeScreen(stage);
                for (GameObject g : list) {
                    g.movement();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        pb2.setOnMouseClicked(e -> {
            try {
                hero = new Hero(175, 480);
                makeGameScreen(stage);
                toGameScreen(stage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        pb4.setOnMouseClicked(e ->
        {
            try {
                makeMainMenu(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            toMainMenu(stage);
        });

        pb3.setOnMouseClicked(e -> {
            try {
                save(hero, stage);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        pb5.setOnMouseClicked(e -> {
            try {
                makeLoadScreen(stage);
                load(stage);
                toLoadScreen();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        AnchorPane mr2 = new AnchorPane();
        mr2.getChildren().addAll(pb1, pb2, pb3, pb4,pb5,t);
        mr2.setStyle("-fx-background-color: #87CEFA");
        pauseMenu = new Scene(mr2, 1280, 720);
        pauseMenu.setFill(Color.BLUE);
        pauseMenu.setOnKeyPressed(e -> {
            try {
                save(hero, stage);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void makeGameScreen(Stage stage) throws Exception {
        list = new ArrayList<GameObject>();
        AnchorPane curr = new AnchorPane();
        score = new Text();
        score.setText("Distance moved: " + hero.distance);
        score.setLayoutX(1100);
        score.setLayoutY(20);
        score.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        coinScore = new Text();
        coinScore.setText("Coins Collected: " + hero.coins);
        coinScore.setLayoutX(1100);
        coinScore.setLayoutY(40);
        coinScore.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        list.add(hero);
        GameLayout gl = GameLayout.getInstance();
        gl.GameScreenLayout(curr);
        curr.getChildren().addAll(score, coinScore);
        gameScreen = new Scene(curr, 1280, 720);

        gameScreen.setOnKeyPressed(e -> {
            for (GameObject g : list) {
                g.stopMovement();
            }
            toPauseMenu(stage);
        });
        curr.getChildren().add(hero);

        for (GameObject gameObject : list) {
            gameObject.movement();
        }

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                hero.rightmover.playFromStart();
                try {
                    hero.fireWeapon();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        curr.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        curr.setPrefSize(1000000000, 720);
        curr.setStyle("-fx-background-color: #87CEFA");
        gameScreen.setFill(Color.LIGHTSKYBLUE);
    }

    public void makeWeaponInfoScreen(Stage stage) throws FileNotFoundException {
        FileInputStream s1 = new FileInputStream("images\\Throwing_knives.jpg");
        Image img_w1 = new Image(s1);
        ImageView iv1 = new ImageView(img_w1);
        iv1.setFitHeight(250);
        iv1.setPreserveRatio(true);
        Button w1 = new Button();
        w1.setTranslateX(360);
        w1.setTranslateY(250);
        w1.setPrefSize(200, 250);
        w1.setGraphic(iv1);
        w1.setStyle("-fx-background-color: #00BFFF; -fx-border-color: #000000; -fx-border-width: 1.5px;-fx-border-radius: 15;-fx-background-radius: 15;");
        Text t1 = new Text();
        t1.setText("THROWING KNIVES");
        t1.setLayoutX(380);
        t1.setLayoutY(550);
        t1.setFont(Font.font(null, FontWeight.BOLD, 20));

        FileInputStream s2 = new FileInputStream("images\\Throwing_spears.jpg");
        Image img_w2 = new Image(s2);
        ImageView iv2 = new ImageView(img_w2);
        iv2.setFitHeight(250);
        iv2.setPreserveRatio(true);
        Button w2 = new Button();
        w2.setTranslateX(720);
        w2.setTranslateY(250);
        w2.setPrefSize(200, 250);
        w2.setGraphic(iv2);
        w2.setStyle("-fx-background-color: #00BFFF; -fx-border-color: #000000; -fx-border-width: 1.5px;-fx-border-radius: 15;-fx-background-radius: 15;");
        Text t2 = new Text();
        t2.setText("THROWING SPEARS");
        t2.setLayoutX(740);
        t2.setLayoutY(550);
        t2.setFont(Font.font(null, FontWeight.BOLD, 20));
        Font font1 = Font.font("Courier New", FontWeight.BOLD, 25);
        Button back = new Button("<|- BACK");
        back.setFont(font1);
        back.setStyle("-fx-background-color: #BA55D3 ; -fx-border-color: #000000; -fx-border-width: 1.5px;-fx-border-radius: 20;-fx-background-radius: 20;");
        back.setOnMouseClicked(e -> {
            toMainMenu(stage);
        });
        Text t = new Text();
        t.setText("AVAILABLE WEAPONS");
        t.setFont(Font.font("Helvetica", FontWeight.BOLD, 40));
        t.setFill(Color.PURPLE);
        t.setLayoutX(450);
        t.setLayoutY(150);
        AnchorPane mr4 = new AnchorPane();
        mr4.getChildren().addAll(w1, w2, t1, t2, back, t);
        mr4.setStyle("-fx-background-color: #D8BFD8");
        weaponScreen = new Scene(mr4, 1280, 720);
    }

    public void makeGameOverScreen(Stage stage) throws Exception {
        Font font1 = Font.font("Courier New", FontWeight.BOLD, 25);
        Button home = new Button("<|- Back to Main Menu");
        home.setFont(font1);
        home.setStyle("-fx-background-color: #006400 ; -fx-border-width: 1.5px;-fx-text-fill: #FFFFFF;");
        home.setOnMouseClicked(e -> {
            toMainMenu(stage);
        });
        AnchorPane mr5 = new AnchorPane();
        FileInputStream f = new FileInputStream("images\\gameover.png");
        Image go_img = new Image(f);
        BackgroundImage bkgimg = new BackgroundImage(go_img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1280, 720, false, false, true, false));
        Background gobkg = new Background(bkgimg);
        mr5.setBackground(gobkg);
        mr5.getChildren().add(home);
        gameOverscreen = new Scene(mr5, 1280, 720);
    }

    public void makeReviveScreen(Stage stage) throws Exception {
        AnchorPane revivePane = new AnchorPane();
        Button yes = new Button("YES");
        Button no = new Button("NO");
        Font font1 = Font.font("Courier New", FontWeight.BOLD, 25);
        yes.setFont(font1);
        no.setFont(font1);
        yes.setStyle("-fx-background-color: #FFFFFF ; -fx-border-color: #000000; -fx-border-width: 1.5px;");
        no.setStyle("-fx-background-color: #FFFFFF ; -fx-border-color: #000000; -fx-border-width: 1.5px;");
        yes.setLayoutY(400);
        yes.setLayoutX(400);
        no.setLayoutY(400);
        no.setLayoutX(800);
        yes.setOnMouseClicked(e -> {
            hero.respawn();
            try {
                resumeScreen(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        no.setOnMouseClicked(e -> {
            try {
                toGameOverScreen();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Text t = new Text();
        t.setText("REVIVE FOR 10 HERO COINS?");
        t.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        t.setFill(Color.WHITE);
        t.setLayoutX(350);
        t.setLayoutY(200);

        FileInputStream f = new FileInputStream("images\\respawn.png");
        Image rsp_img = new Image(f);
        BackgroundImage rspbkgimg = new BackgroundImage(rsp_img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1280, 720, false, false, true, false));
        Background rsp = new Background(rspbkgimg);
        revivePane.setBackground(rsp);
        revivePane.getChildren().addAll(t, yes, no);
        reviveScreen = new Scene(revivePane, 1280, 720);
    }

    public void makeGameWinScreen(Stage stage) throws Exception {
        Font font1 = Font.font("Courier New", FontWeight.BOLD, 25);
        Button home = new Button("<|- Back to Main Menu");
        home.setFont(font1);
        home.setStyle("-fx-background-color: #006400 ; -fx-border-width: 1.5px;-fx-text-fill: #FFFFFF;");
        home.setOnMouseClicked(e -> {
            toMainMenu(stage);
        });
        AnchorPane pane = new AnchorPane();
        FileInputStream f = new FileInputStream("images\\gamewin.png");
        Image imp = new Image(f);
        BackgroundImage bgimg = new BackgroundImage(imp, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1280, 720, false, false, true, false));
        Background win = new Background(bgimg);
        pane.setBackground(win);
        Text t = new Text();
        t.setText("YOU WON!!!!");
        t.setFont(Font.font("Helvetica", FontWeight.BOLD, 80));
        t.setFill(Color.WHITE);
        t.setLayoutX(400);
        t.setLayoutY(350);
        t.setFill(Color.YELLOW);
        pane.getChildren().addAll(home, t);
        gameWinscreen = new Scene(pane, 1280, 720);
    }

    public void makeLoadScreen(Stage stage) throws Exception {
        AnchorPane loadpane = new AnchorPane();
        loadpane.setStyle("-fx-background-color: #D8BFD8");
        Text t = new Text();
        t.setText("YOUR SAVED GAMES");
        t.setFont(Font.font("Helvetica", FontWeight.BOLD, 40));
        t.setFill(Color.DARKMAGENTA);
        t.setLayoutX(450);
        t.setLayoutY(75);
        Label l = new Label("Type the game number you want to resume:");
        TextField tf = new TextField();
        l.setLayoutX(525);
        l.setLayoutY(120);
        tf.setLayoutX(575);
        tf.setLayoutY(150);
        Font font1 = Font.font("Courier New", FontWeight.BOLD, 25);
        Button submit = new Button("Submit.");
        submit.setLayoutX(700);
        submit.setLayoutY(150);
        submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int fileNumber = Integer.parseInt(tf.getText());
                    File file = new File("savefiles");
                    int n = file.listFiles().length;
                    if (fileNumber <= 0 || fileNumber > n) {
                        toMainMenu(stage);
                    } else {
                        try {
                            loadNewHero(fileNumber);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    toMainMenu(stage);
                } catch (Exception e) {
                }
            }
        });
        Button back = new Button("<|- BACK");
        back.setFont(font1);
        back.setStyle("-fx-background-color: #BA55D3 ; -fx-border-color: #000000; -fx-border-width: 1.5px;-fx-border-radius: 20;-fx-background-radius: 20;");
        back.setOnMouseClicked(e -> {
            toMainMenu(stage);
        });
        loadpane.getChildren().addAll(t, tf, back, submit, l);
        row = 0;
        loadScreen = new Scene(loadpane, 1280, 720);

    }

    public void save(Hero h, Stage stage) throws IOException {
        for (GameObject g : list) {
            g.stopMovement();
        }
        File savefolder = new File("savefiles");
        File[] savelist = savefolder.listFiles();
        String newname = "savefiles\\file" + String.valueOf(savelist.length + 1) + ".txt";
        File newfile = new File(newname);
        try {
            newfile.createNewFile();
        } catch (IOException e) {
            System.out.println("File creation failed");
        }
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(newname));
            out.writeObject(hero);
            toMainMenu(stage);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
        } finally {
            if (out != null) out.close();
        }
    }

    public void load(Stage stage) throws Exception {
        File savefiles = new File("savefiles");
        File[] savelist = savefiles.listFiles();
        ObjectInputStream in = null;
        try {
            AnchorPane curr = (AnchorPane) loadScreen.getRoot();
            for (int i = 0; i < savelist.length; i++) {
                Hero h = null;
                try {
                    String filename = "savefiles\\" + savelist[i].getName();
                    in = new ObjectInputStream(new FileInputStream(filename));
                    if (in == null) {
                        System.out.println("in was null");
                    }
                    h = (Hero) in.readObject();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                int pos = i % 4;
                Rectangle r = new Rectangle();
                r.setLayoutX(65 + 300 * pos);
                r.setLayoutY(200 + 230 * row);
                r.setWidth(250);
                r.setHeight(150);
                r.setFill(Color.MEDIUMPURPLE);
                Text t1 = new Text();
                t1.setText(String.valueOf(i + 1));
                t1.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
                t1.setLayoutX(80 + 300 * pos);
                t1.setLayoutY(275 + 230 * row);
                Text t2 = new Text();
                t2.setText("Coins: " + h.coins);
                t2.setLayoutX(150 + 300 * pos);
                t2.setLayoutY(250 + 220 * row);
                t2.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
                Text t3 = new Text();
                t3.setText("Distance: " + h.distance / 100);
                t3.setLayoutX(150 + 300 * pos);
                t3.setLayoutY(310 + 220 * row);
                t3.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
                curr.getChildren().addAll(r, t1, t2, t3);
                if (pos == 3) {
                    row++;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
    }

    public void loadNewHero(int n) throws Exception {
        ObjectInputStream in = null;
        Hero temp = null;
        File savefile = new File("savefiles");
        File[] savelist = savefile.listFiles();
        try {
            String filename = "savefiles\\" + savelist[n - 1].getName();
            in = new ObjectInputStream(new FileInputStream(filename));
            temp = (Hero) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            toMainMenu(stg);
        } catch (Exception e) {
        }

        hero = new Hero(temp.getHitbox().getX(), temp.getHitbox().getY());
        hero.h = temp.h;
        hero.currweapon = temp.currweapon;
        hero.coins = temp.coins;
        hero.distance = temp.distance;
        hero.revived=temp.revived;
        makeGameScreen(stg);
        list.add(hero);
        coinScore.setLayoutX(hero.getHitbox().getX() + 925);
        score.setLayoutX(hero.getHitbox().getX() + 925);
        score.setText("Distance moved: " + hero.distance / 100);
        coinScore.setText("Coins Collected: " + hero.coins);
        AnchorPane curr = (AnchorPane) gameScreen.getRoot();
        curr.setLayoutX(-hero.hitbox.getX() + 175);
        for (GameObject g : list) {
            g.movement();
        }
        resumeScreen(stg);
    }

    public static void main(String[] args) {
        launch();
    }
}