package comp1140.ass2.Scenes;

import comp1140.ass2.*;
import comp1140.ass2.Game.*;
import comp1140.ass2.Players.EasyBot;
import comp1140.ass2.Players.Human;
import comp1140.ass2.Players.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Created by steveb on 12/08/2015.
 */
public class Game extends Scene {

    public int currentPlayer;
    public Player[] players;
    public PiecePreparerSprite piecePreparer;
    public Board board;
    public Panel[] panels;
    public boolean[] skip = {false, false, false, false};
    public Colour[] playerColours = {Colour.Blue, Colour.Yellow, Colour.Red, Colour.Green};

    // All players have to do everything through this interface
    public void makeMove(Player player, Piece piece) {
        board.placePiece(piece);
        //panels[currentPlayer].removePiece(piece);
        panels[currentPlayer].removePiece(piece.shape);

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(100),
                ae -> transitionMove()));
        timeline.play();
    }

    public void transitionMove() {
        piecePreparer.setActive(false);
        panels[currentPlayer].setActive(false);
        panels[currentPlayer].temporary = null;
        piecePreparer.removePiece();
        if(skip[0]&&skip[1]&&skip[2]&&skip[3]) {
            endGame();
            return;
        }

        currentPlayer = (currentPlayer+1) % players.length;
        skip[currentPlayer] = false;
        System.out.println("Player " + (currentPlayer+1)+"'s go!");
        if(players[currentPlayer].isHuman()) {
            piecePreparer.setActive(true);
            panels[currentPlayer].setActive(true);
        }
        // Eventually, the bots should get passed a clone of board instead of board itself, so they can't do anything to board
        // directly.
        // Also, think about not giving them access to parent
        //players[currentPlayer].think(board.clone());
        players[currentPlayer].think(board);
    }

    public void endGame() {
    }

    public Game(Group root, double width, double height, Blokus parent) {
        super(root, width, height, Color.WHITE);
        getStylesheets().add("comp1140/ass2/Assets/main.css");

        final ImageView imv1 = new ImageView();
        final Image image3 = new Image(Blokus.class.getResourceAsStream("Assets/blokusbg.png"));
        imv1.setImage(image3);
        imv1.setLayoutX(0); imv1.setLayoutY(0);
        imv1.setFitWidth(700);
        imv1.setPreserveRatio(true);
        root.getChildren().add(imv1);




        /**primaryStage.setOnCloseRequest(event -> {
         closingTests(didFinish);
         }); */

        int boardSize = 520;
        int gameSize = 640;

        int panelCell = 11;
        int boardCell = 23;




        // MENUBAR

        Button button0 = new Button("Pass");
        button0.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            }
        });
        button0.getStyleClass().add("pass");
        button0.setMinSize(80, 20);
        button0.setLayoutX(580);
        button0.setLayoutY(4);
        root.getChildren().add(button0);

        Button button1 = new Button("Menu");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            }
        });
        button1.getStyleClass().add("pass");
        button1.setMinSize(80, 20);
        button1.setLayoutX(470);
        button1.setLayoutY(4);
        root.getChildren().add(button1);

        /*
        Pane pane = new Pane();
        pane.setMinSize(640, 640);
        pane.setMaxSize(640, 640);
        pane.setLayoutX(30);
        pane.setLayoutY(50);
        pane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff;" +
                "-fx-background-insets: 0,10;");
                */

        int panelHeight = 200;
        int panelWidth = 140;

        Panel bluePanel = new Panel(20, 10, panelCell, Colour.Blue, this, true);
        Pane bluePane = new Pane();
        bluePane.setLayoutX(700-50*panelCell-80); bluePane.setLayoutY(700-80-50*panelCell);
        bluePane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff; -fx-background-insets: 0,10;");
        bluePane.setMinSize(panelCell*10+20, panelCell*20+20);
        bluePanel.setLayoutX(10); bluePanel.setLayoutY(10);
        bluePane.getChildren().add(bluePanel);

        Panel yellowPanel = new Panel(20, 10, panelCell, Colour.Yellow, this, true);
        Pane yellowPane = new Pane();
        yellowPane.setLayoutX(700-50*panelCell-80); yellowPane.setLayoutY(700-50-30*panelCell);
        yellowPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff; -fx-background-insets: 0,10;");
        yellowPane.setMinSize(panelCell*10+20, panelCell*20+20);
        yellowPanel.setLayoutX(10); yellowPanel.setLayoutY(10);
        yellowPane.getChildren().add(yellowPanel);

        Panel redPanel = new Panel(10, 20, panelCell, Colour.Red, this, false);
        Pane redPane = new Pane();
        redPane.setLayoutX(700-60-panelCell*40); redPane.setLayoutY(700-30-panelCell*10);
        redPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff; -fx-background-insets: 0,10;");
        redPane.setMinSize(panelCell*20+20, panelCell*10+20);
        redPanel.setLayoutX(10); redPanel.setLayoutY(10);
        redPane.getChildren().add(redPanel);

        Panel greenPanel = new Panel(10, 20, panelCell, Colour.Green, this, false);
        Pane greenPane = new Pane();
        greenPane.setLayoutX(700-30-panelCell*20); greenPane.setLayoutY(700-30-panelCell*10);
        greenPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff; -fx-background-insets: 0,10;");
        greenPane.setMinSize(panelCell * 20 + 20, panelCell*10+20);
        greenPanel.setLayoutX(10); greenPanel.setLayoutY(10);
        greenPane.getChildren().add(greenPanel);

        panels = new Panel[]{bluePanel, yellowPanel, redPanel, greenPanel};

        board = new Board(20, 20, boardCell, Colour.Empty, this);
        Pane boardPane = new Pane();
        boardPane.setLayoutX(700-30-boardCell*20); boardPane.setLayoutY(700-40-boardCell*20-20-panelCell*10);
        boardPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff; -fx-background-insets: 0,10;");
        boardPane.setMinSize(boardCell * 20 + 20, boardCell*20+20);
        board.setLayoutX(10); board.setLayoutY(10);
        boardPane.getChildren().add(board);

        int prepCell = 20;
        piecePreparer = new PiecePreparerSprite(5,5, prepCell, Colour.Empty, this);
        Pane prepPane = new Pane();
        prepPane.setLayoutX(700-80-panelCell*50); prepPane.setLayoutY(700-30-panelCell*10+10);
        prepPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff; -fx-background-insets: 0,10;");
        prepPane.setMinSize(prepCell * 5 + 20, prepCell*5+20);
        piecePreparer.setLayoutX(10); piecePreparer.setLayoutY(10);
        prepPane.getChildren().add(piecePreparer);

        root.getChildren().addAll(bluePane, yellowPane, redPane, greenPane, boardPane, prepPane);


        /*

        //Layout
        VBox vbox = new VBox();
        {
            HBox top = new HBox();
            {
                VBox topLeft = new VBox();
                {
                    topLeft.getChildren().addAll(bluePanel, yellowPanel);
                    //topLeft.setMargin(bluePanel, new Insets(5, 5, 5, 5));
                    //topLeft.setMargin(yellowPanel, new Insets(5, 5, 5, 5));
                }
                top.getChildren().addAll(topLeft, board);
                //top.setMargin(board, new Insets(5, 5, 5, 5));
                top.setMargin(board, new Insets(5, 50, 5, 5));
            }
            HBox bottom = new HBox();
            {
                bottom.getChildren().addAll(piecePreparer, redPanel, panelGreen);
                //bottom.setMargin(piecePreparer, new Insets(5, 5, 5, 5));
                //bottom.setMargin(redPanel, new Insets(5, 10, 5, 5));
                //bottom.setMargin(panelGreen, new Insets(5, 5, 5, 10));
            }
            vbox.getChildren().addAll(top, bottom);
            //vbox.setMargin(top, new Insets(5, 5, 0, 5));
            //vbox.setMargin(menuBar, new Insets(1, 1, 1, 1));
            //vbox.setMargin(bottom, new Insets(0, 5, 5, 5));
        }
        pane.getChildren().add(vbox);
        vbox.setLayoutX(10);
        vbox.setLayoutY(10);

        root.getChildren().add(pane);
        */

        final ImageView imv = new ImageView();
        final Image image2 = new Image(Blokus.class.getResourceAsStream("Assets/Blokus.png"));
        imv.setImage(image2);
        imv.setFitHeight(60);
        imv.preserveRatioProperty().setValue(true);
        imv.setLayoutX(30);
        imv.setLayoutY(1);
        root.getChildren().add(imv);

    }


    public void start() {

        Player player0 = new Human(0, this);
        Player player1 = new EasyBot(1, this);
        Player player2 = new EasyBot(2, this);
        Player player3 = new EasyBot(3, this);
        players = new Player[] {player0, player1, player2, player3};
        currentPlayer = players.length-1; // When we transition go, it will start with player 0

        board.setActive(true);

        // BEGIN!
        transitionMove();

    }




}
