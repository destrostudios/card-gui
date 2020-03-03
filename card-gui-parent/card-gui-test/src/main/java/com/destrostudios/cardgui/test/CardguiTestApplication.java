package com.destrostudios.cardgui.test;

import com.destrostudios.cardgui.*;
import com.destrostudios.cardgui.events.*;
import com.destrostudios.cardgui.interactivities.*;
import com.destrostudios.cardgui.samples.animations.*;
import com.destrostudios.cardgui.samples.visualisation.*;
import com.destrostudios.cardgui.targetarrow.TargetSnapMode;
import com.destrostudios.cardgui.test.game.MyCard;
import com.destrostudios.cardgui.test.game.MyCards;
import com.destrostudios.cardgui.test.game.MyGame;
import com.destrostudios.cardgui.test.game.MyPlayer;
import com.destrostudios.cardgui.zones.*;
import com.destrostudios.cardgui.test.files.FileAssets;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.*;
import com.jme3.system.AppSettings;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class CardguiTestApplication extends SimpleApplication implements ActionListener {

    public static void main(String[] args) {
        FileAssets.readRootFile();
        
        CardguiTestApplication app = new CardguiTestApplication();
        app.setShowSettings(false);
        AppSettings settings = new AppSettings(true);
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setTitle("CardGui - TestApp");
        app.setSettings(settings);
        app.start();
    }
    private Board<MyCardModel> board;
    private MyGame game;
    private PlayerZones[] playerZones;
    private HashMap<MyCard, Card<MyCardModel>> visualCards = new HashMap<>();
    private HashMap<Card<MyCardModel>, MyCard> gameCards = new HashMap<>();

    @Override
    public void simpleInitApp() {
        assetManager.registerLocator(FileAssets.ROOT, FileLocator.class);

        initCamera();
        initLight();
        initListeners();
        initGame();
        initBoardGui();

        game.start();
        updateBoard();
    }

    @Override
    public void simpleUpdate(float lastTimePerFrame) {
        // Nothing needs to be done here
    }
    
    private void initCamera() {
        flyCam.setMoveSpeed(100);
        flyCam.setEnabled(false);
        cam.setLocation(new Vector3f(2.584369f, 14.878008f, 0.86850137f));
        cam.setRotation(new Quaternion(-0.001344382f, 0.72532254f, -0.6884064f, -0.0014168395f));
    }

    private void initLight() {
        rootNode.addLight(new AmbientLight(ColorRGBA.White.mult(0.4f)));
        Vector3f lightDirection = new Vector3f(1, -5, -1).normalizeLocal();
        DirectionalLight directionalLight = new DirectionalLight(lightDirection, ColorRGBA.White.mult(1.1f));
        rootNode.addLight(directionalLight);
    }
    
    private void initListeners() {
        inputManager.addMapping("space", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("1", new KeyTrigger(KeyInput.KEY_1));
        inputManager.addMapping("2", new KeyTrigger(KeyInput.KEY_2));
        inputManager.addMapping("3", new KeyTrigger(KeyInput.KEY_3));
        inputManager.addListener(this, "space", "1", "2", "3");
    }
    
    private void initGame() {
        MyPlayer[] players = new MyPlayer[]{new MyPlayer(), new MyPlayer()};
        for (MyPlayer player : players) {
            for (int i = 0; i < 30; i++) {
                MyCard.Color color = MyCard.Color.values()[(int) (Math.random() * MyCard.Color.values().length)];
                String name = ((Math.random() < 0.5) ? "Aether Adept" : "Shyvana");
                player.getDeck().addCard(new MyCard(color, name));
            }
        }
        game = new MyGame(players);
    }
    
    private void initBoardGui() {
        board = new Board<>(new DebugZoneVisualizer() {

            @Override
            protected Vector2f getSize(CardZone zone) {
                for (int i=0;i<game.getPlayers().length;i++) {
                    if (zone == playerZones[i].getDeckZone()) {
                        return new Vector2f(1.2f, 1.2f);
                    } else if (zone == playerZones[i].getHandZone()) {
                        return new Vector2f(6, 2);
                    } else if (zone == playerZones[i].getBoardZone()) {
                        return new Vector2f(10, 3);
                    }
                }
                return super.getSize(zone);
            }
        }, new SimpleCardVisualizer<MyCardModel>() {

            @Override
            public PaintableImage paintCard(MyCardModel cardModel) {
                PaintableImage paintableImage = new PaintableImage(300, 400);
                paintableImage.setBackground_Alpha(0);
                BufferedImage imageCard = FileAssets.getImage("images/cards/" + cardModel.getName() + ".png");
                BufferedImage imageBackground = FileAssets.getImage("images/templates/mana_" + cardModel.getColor().ordinal() + ".png");
                paintableImage.paintImage(new PaintableImage(imageCard), 74, 43, 155, 155);
                paintableImage.paintImage(new PaintableImage(imageBackground), 0, 0, 300, 400);
                if (cardModel.isDamaged()) {
                    for (int x=0;x<paintableImage.getWidth();x++) {
                        for (int y=0;y<paintableImage.getHeight();y++) {
                            paintableImage.setPixel_Red(x, y, 255);
                        }
                    }
                }
                return paintableImage;
            }
        });

        int playersCount = game.getPlayers().length;
        playerZones = new PlayerZones[playersCount];
        Vector3f offset = new Vector3f(0, 0, 2);
        for (int i = 0; i < playersCount; i++) {
            if (i == 1) {
                offset.addLocal(0, 0, -6);
            }
            SimpleIntervalZone deckZone = new SimpleIntervalZone(offset.add(10, 0, 0), new Vector3f(0, 0.04f, 0));
            SimpleIntervalZone handZone = new SimpleIntervalZone(offset.add(0, 0, 2.5f), new Quaternion().fromAngleAxis(FastMath.QUARTER_PI, Vector3f.UNIT_X), new Vector3f(1, 1, 1));
            SimpleIntervalZone boardZone = new SimpleIntervalZone(offset.add(0, 0, 0), new Vector3f(1, 1, 1));
            board.addZone(deckZone);
            board.addZone(handZone);
            board.addZone(boardZone);
            playerZones[i] = new PlayerZones(deckZone, handZone, boardZone);
        }

        stateManager.attach(new BoardAppState(board, rootNode, new BoardSettings()));
    }
    
    private void updateBoard() {
        for (int i=0;i<game.getPlayers().length;i++) {
            final int opponentPlayerIndex = ((i + 1) % 2);
            MyPlayer player = game.getPlayers()[i];
            updateZone(player.getDeck(), playerZones[i].getDeckZone(), new Vector3f(0, 1, 0), new ClickInteractivity() {

                @Override
                public void trigger(BoardObject boardObject, BoardObject target) {
                    game.getCurrentPlayer().draw();
                    updateBoard();
                }
            });
            updateZone(player.getHand(), playerZones[i].getHandZone(), new Vector3f(1, 0, 0), new DragToPlayInteractivity() {

                @Override
                public void trigger(BoardObject boardObject, BoardObject target) {
                    MyCard myCard = gameCards.get(boardObject);
                    for (MyPlayer player : game.getPlayers()) {
                        if (player.getHand().contains(myCard)) {
                            player.getHand().removeCard(myCard);
                            player.getBoard().addCard(myCard);
                            updateBoard();
                            break;
                        }
                    }
                }
            });
            updateZone(player.getBoard(), playerZones[i].getBoardZone(), new Vector3f(1, 0, 0), new AimToTargetInteractivity(TargetSnapMode.VALID) {

                @Override
                public boolean isValid(BoardObject boardObject) {
                    if (boardObject instanceof Card) {
                        Card<MyCardModel> card = (Card<MyCardModel>) boardObject;
                        if (card.getZonePosition().getZone() == playerZones[opponentPlayerIndex].getBoardZone()) {
                            return !MyCard.Color.NEUTRAL.equals(card.getModel().getColor());
                        }
                    }
                    return false;
                }

                @Override
                public void trigger(BoardObject boardObject, BoardObject target) {
                    MyCard myCard = gameCards.get(boardObject);
                    Card targetCard = (Card) target;
                    MyCard targetMyCard = gameCards.get(targetCard);
                    myCard.setDamaged(true);
                    targetMyCard.setDamaged(true);
                    updateBoard();
                }
            });
        }
    }

    private void updateZone(MyCards myCards, CardZone cardZone, Vector3f interval, Interactivity interactivity) {
        int index = 0;
        Iterator<MyCard> cardIterator = myCards.iterator();
        while (cardIterator.hasNext()) {
            MyCard myCard = cardIterator.next();
            Card<MyCardModel> card = getOrCreateCard(myCard);
            MyCardModel cardModel = card.getModel();
            cardModel.setColor(myCard.getColor());
            cardModel.setName(myCard.getName());
            cardModel.setDamaged(myCard.isDamaged());
            board.triggerEvent(new ModelUpdatedEvent(card));
            board.triggerEvent(new MoveCardEvent(card, cardZone, interval.mult(index)));
            card.setInteractivity(interactivity);
            index++;
        }
    }
    
    private Card<MyCardModel> getOrCreateCard(MyCard myCard) {
        Card<MyCardModel> card = visualCards.get(myCard);
        if (card == null) {
            card = new Card<>(new MyCardModel());
            visualCards.put(myCard, card);
            gameCards.put(card, myCard);
        }
        return card;
    }

    @Override
    public void onAction(String name, boolean isPressed, float lastTimePerFrame) {
        if ("space".equals(name) && isPressed) {
            inputManager.setCursorVisible(flyCam.isEnabled());
            flyCam.setEnabled(!flyCam.isEnabled());
        } else if ("1".equals(name) && isPressed) {
            LinkedList<Card> cards = playerZones[0].getDeckZone().getCards();
            board.playAnimation(new ShuffleAnimation(cards, this));
        } else if ("2".equals(name) && isPressed) {
            board.playAnimation(new CameraShakeAnimation(cam,1, 0.01f));
        } else if ("3".equals(name) && isPressed) {
            board.playAnimation(new SnowAnimation(assetManager, cam, rootNode));
        }
    }
}