package com.destrostudios.cardgui.test;

import com.destroflyer.jme3.effekseer.model.ParticleEffectSettings;
import com.destrostudios.cardgui.*;
import com.destrostudios.cardgui.boardobjects.TargetArrow;
import com.destrostudios.cardgui.events.*;
import com.destrostudios.cardgui.interactivities.*;
import com.destrostudios.cardgui.samples.animations.*;
import com.destrostudios.cardgui.samples.boardobjects.connectionmarker.*;
import com.destrostudios.cardgui.samples.boardobjects.targetarrow.*;
import com.destrostudios.cardgui.samples.visualization.*;
import com.destrostudios.cardgui.TargetSnapMode;
import com.destrostudios.cardgui.test.game.*;
import com.destrostudios.cardgui.transformations.*;
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
    private Board board;
    private MyGame game;
    private PlayerZones[] playerZones;
    private HashMap<MyCard, Card<MyCardModel>> visualCards = new HashMap<>();
    private HashMap<Card<MyCardModel>, MyCard> gameCards = new HashMap<>();
    private LinkedList<TransformedBoardObject> removeAfterReachingTargetObjects = new LinkedList<>();

    @Override
    public void simpleInitApp() {
        assetManager.registerLocator(FileAssets.ROOT, FileLocator.class);
        viewPort.setBackgroundColor(ColorRGBA.Black);

        initCamera();
        initLight();
        initListeners();
        initGame();
        initBoardGui();

        game.start();
        updateBoard();
    }

    private void initCamera() {
        flyCam.setMoveSpeed(100);
        flyCam.setEnabled(false);
        cam.setLocation(new Vector3f(2.584369f, 14.878008f, 0.86850137f));
        cam.setRotation(new Quaternion(-0.001344382f, 0.72532254f, -0.6884064f, -0.0014168395f));
    }

    private void initLight() {
        rootNode.addLight(new AmbientLight(ColorRGBA.White.mult(1)));
        Vector3f lightDirection = new Vector3f(1, -5, -1).normalizeLocal();
        DirectionalLight directionalLight = new DirectionalLight(lightDirection, ColorRGBA.White.mult(0.5f));
        rootNode.addLight(directionalLight);
    }

    private void initListeners() {
        inputManager.addMapping("space", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("1", new KeyTrigger(KeyInput.KEY_1));
        inputManager.addMapping("2", new KeyTrigger(KeyInput.KEY_2));
        inputManager.addMapping("3", new KeyTrigger(KeyInput.KEY_3));
        inputManager.addMapping("4", new KeyTrigger(KeyInput.KEY_4));
        inputManager.addListener(this, "space", "1", "2", "3", "4");
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
        board = new Board();
        board.registerVisualizer_Class(CardZone.class, new DebugZoneVisualizer() {

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
        });
        board.registerVisualizer_ZonePosition(zonePosition -> {
            for (PlayerZones playerZone : playerZones) {
                if (zonePosition.getZone() == playerZone.getBoardZone()) {
                    return true;
                }
            }
            return false;
        }, new MyCardVisualizer(true));
        board.registerVisualizer_Class(Card.class, new MyCardVisualizer(false));
        board.registerVisualizer_Class(TargetArrow.class, new SimpleTargetArrowVisualizer(new SimpleTargetArrowSettings()));
        board.registerVisualizer_Class(ConnectionMarker.class, new ConnectionMarkerVisualizer(
                SimpleTargetArrowSettings.builder()
                        .resolution(20)
                        .width(0.25f)
                        .build()
        ));
        board.registerVisualizer_Class(MyColoredSphere.class, new MyColoredSphereVisualizer());

        int playersCount = game.getPlayers().length;
        playerZones = new PlayerZones[playersCount];
        Vector3f offset = new Vector3f(0, 0, 2);
        for (int i = 0; i < playersCount; i++) {
            if (i == 1) {
                offset.addLocal(0, 0, -6);
            }
            SimpleIntervalZone deckZone = new SimpleIntervalZone(offset.add(10, 0, 0), new Vector3f(0, 0.04f, 0));
            SimpleIntervalZone handZone = new SimpleIntervalZone(offset.add(0, 0, 2.5f), new Quaternion().fromAngleAxis(FastMath.QUARTER_PI, Vector3f.UNIT_X), new Vector3f(1, 1, 1));
            SimpleIntervalZone boardZone = new SimpleScalingIntervalZone(offset.add(0, 0, 0), new Vector3f(1, 1, 1)) {

                @Override
                protected float getScale() {
                    long cardsCount = board.getCardsInZone(this).size();
                    return ((cardsCount < 3) ? 1 : (3f / cardsCount));
                }
            };
            board.addZone(deckZone);
            board.addZone(handZone);
            board.addZone(boardZone);
            playerZones[i] = new PlayerZones(deckZone, handZone, boardZone);
        }

        stateManager.attach(new BoardAppState(board, rootNode, BoardSettings.builder()
                .hoverInspectionDelay(1f)
                .build()));
    }

    private void updateBoard() {
        for (int i=0;i<game.getPlayers().length;i++) {
            final int opponentPlayerIndex = ((i + 1) % 2);
            MyPlayer player = game.getPlayers()[i];
            updateZone(player.getDeck(), playerZones[i].getDeckZone(), new Vector3f(0, 1, 0), new ClickInteractivity() {

                @Override
                public void trigger(BoardObject source, BoardObject target) {
                    game.getCurrentPlayer().draw();
                    updateBoard();
                }
            });
            updateZone(player.getHand(), playerZones[i].getHandZone(), new Vector3f(1, 0, 0), new DragToPlayInteractivity() {

                @Override
                public void trigger(BoardObject source, BoardObject target) {
                    MyCard myCard = gameCards.get(source);
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
                public void trigger(BoardObject source, BoardObject target) {
                    MyCard myCard = gameCards.get(source);
                    if (myCard.isDamaged()) {
                        ConnectionMarker connectionMarker = new ConnectionMarker();
                        connectionMarker.getModel().setSourceBoardObject((TransformedBoardObject) source);
                        connectionMarker.getModel().setTargetBoardObject((TransformedBoardObject) target);
                        board.register(connectionMarker);
                    } else {
                        Card targetCard = (Card) target;
                        MyCard targetMyCard = gameCards.get(targetCard);
                        myCard.setDamaged(true);
                        targetMyCard.setDamaged(true);
                        shootSphere((TransformedBoardObject) source, (TransformedBoardObject) target);
                        updateBoard();
                    }
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
            if ((card.getZonePosition().getZone() != null) && (cardZone != card.getZonePosition().getZone())) {
                if (cardModel.getColor() == MyCard.Color.NEUTRAL) {
                    board.playAnimation(new ShootInEntryAnimation(card, 0.75f, this));
                } else if (cardModel.getColor() == MyCard.Color.RED) {
                    board.playAnimation(new SlamEntryAnimation(card));
                } else if (cardModel.getColor() == MyCard.Color.BLUE) {
                    board.playAnimation(new WhirlpoolEntryAnimation(card, 5, FastMath.TWO_PI, 0.75f));
                } else if (cardModel.getColor() == MyCard.Color.GREEN) {
                    board.playAnimation(new FlipEntryAnimation(card, 2, 1, 0.75f));
                }
            }
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

    private void shootSphere(TransformedBoardObject source, TransformedBoardObject target) {
        MyColoredSphere sphere = new MyColoredSphere();
        sphere.getModel().setColorRGBA(ColorRGBA.randomColor());
        sphere.position().setTransformation(new ConstantButTargetedTransformation<>(source.position().getCurrentValue()));
        board.register(sphere);
        board.playAnimation(new TargetedArcAnimation(sphere, target, 5, 0.75f));
        removeAfterReachingTargetObjects.add(sphere);
    }

    @Override
    public void simpleUpdate(float tpf) {
        super.simpleUpdate(tpf);
        checkObjectsToBeRemoved();
    }

    private void checkObjectsToBeRemoved() {
        List<TransformedBoardObject> objectsToRemove = removeAfterReachingTargetObjects.stream()
                .filter(TransformedBoardObject::hasReachedTargetTransform)
                .collect(Collectors.toList());
        for (TransformedBoardObject transformedBoardObject : objectsToRemove) {
            board.unregister(transformedBoardObject);
        }
        removeAfterReachingTargetObjects.removeAll(objectsToRemove);
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
        } else if ("4".equals(name) && isPressed) {
            board.playAnimation(new EffekseerAnimation(
                    rootNode,
                    FileAssets.ROOT,
                    FileAssets.ROOT + "./effekseer/Pierre02/Benediction.efkproj",
                    ParticleEffectSettings.builder()
                            .loop(false)
                            .build(),
                    assetManager
            ));
        }
    }
}
