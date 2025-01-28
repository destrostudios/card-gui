package com.destrostudios.cardgui.test;

import com.destrostudios.cardgui.BoardObjectVisualizer;
import com.destrostudios.cardgui.BoardSettings;
import com.destrostudios.cardgui.Card;
import com.destrostudios.cardgui.CardZone;
import com.destrostudios.cardgui.samples.tools.deckbuilder.DeckBuilderAppState;
import com.destrostudios.cardgui.samples.tools.deckbuilder.DeckBuilderSettings;
import com.destrostudios.cardgui.samples.visualization.DebugZoneVisualizer;
import com.destrostudios.cardgui.test.files.FileAssets;
import com.destrostudios.cardgui.zones.SimpleIntervalZone;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.system.AppSettings;

import java.util.Comparator;
import java.util.Map;

public abstract class DeckBuilderTestApplication<DBAS extends DeckBuilderAppState<MyCardModel>> extends SimpleApplication implements ActionListener {

    protected DeckBuilderTestApplication() {
        AppSettings settings = new AppSettings(true);
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setTitle("CardGui - TestApp (DeckBuilder)");
        settings.setGammaCorrection(false);
        setSettings(settings);
        setShowSettings(false);
    }
    protected DBAS deckBuilderAppState;

    @Override
    public void simpleInitApp() {
        assetManager.registerLocator(FileAssets.ROOT, FileLocator.class);
        viewPort.setBackgroundColor(ColorRGBA.White);

        initCamera();
        initLight();
        initListeners();
        initDeckBuilder();
    }

    private void initCamera() {
        flyCam.setMoveSpeed(100);
        flyCam.setEnabled(false);
        cam.setLocation(new Vector3f(0, 15, 0));
        // Look straight down
        cam.lookAtDirection(new Vector3f(0, -1, 0).normalizeLocal(), Vector3f.UNIT_Z.mult(-1));
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
        inputManager.addMapping("5", new KeyTrigger(KeyInput.KEY_5));
        inputManager.addMapping("6", new KeyTrigger(KeyInput.KEY_6));
        inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addListener(this, "space", "1", "2", "3", "4", "5", "6", "left", "right");
    }

    private void initDeckBuilder() {
        deckBuilderAppState = createDeckBuilder(getDeckBuilderSettings());
        stateManager.attach(deckBuilderAppState);
    }

    protected abstract DBAS createDeckBuilder(DeckBuilderSettings<MyCardModel> deckBuilderSettings);

    private DeckBuilderSettings<MyCardModel> getDeckBuilderSettings() {
        CardZone deckZone = new SimpleIntervalZone(new Vector3f(8.25f, 0, -5), new Vector3f(1, 1, 0.57f));
        BoardObjectVisualizer<CardZone> deckZoneVisualizer = new DebugZoneVisualizer() {

            @Override
            protected Geometry createVisualizationObject(AssetManager assetManager) {
                Geometry visualizationObject = super.createVisualizationObject(assetManager);
                visualizationObject.move(0,0, 5);
                return visualizationObject;
            }

            @Override
            protected Vector2f getSize(CardZone zone) {
                return new Vector2f(4, 10);
            }
        };
        Comparator<MyCardModel> deckCardOrder = Comparator.comparing(MyCardModel::getName);
        return DeckBuilderSettings.<MyCardModel>builder()
                .deckZone(deckZone)
                .deckZoneVisualizer(deckZoneVisualizer)
                .deckCardVisualizer(new MyDeckBuilderDeckCardVisualizer())
                .deckCardOrder(deckCardOrder)
                .deckCardsMaximumTotal(30)
                .cardNotAddableCallback(cardModel -> onCallback("cardNotAddable", cardModel))
                .cardAddedCallback(cardModel -> onCallback("cardAdded", cardModel))
                .cardAddedInterceptor(cardModel -> onInterceptor("cardAdded", cardModel))
                .cardRemovedCallback(cardModel -> onCallback("cardRemoved", cardModel))
                .cardRemovedInterceptor(cardModel -> onInterceptor("cardRemoved", cardModel))
                .cardClearedCallback(cardModel -> onCallback("cardCleared", cardModel))
                .cardClearedInterceptor(cardModel -> onInterceptor("cardCleared", cardModel))
                .deckSizeChangedCallback(deckSize -> onCallback("deckSizeChanged", deckSize))
                .boardSettings(BoardSettings.builder()
                        .hoverInspectionDelay(1f)
                        .isInspectable(transformedBoardObject -> {
                            if (transformedBoardObject instanceof Card) {
                                Card card = (Card) transformedBoardObject;
                                return (card.getZonePosition().getZone() != deckZone);
                            }
                            return false;
                        })
                        .build())
                .build();
    }

    protected boolean onInterceptor(String key, MyCardModel cardModel) {
        onCallback(key + "Interceptor", cardModel);
        return !cardModel.getName().equals("Ookazi");
    }

    protected void onCallback(String key, MyCardModel cardModel) {
        onCallback(key, cardModel.getColor().name() + " " + cardModel.getName());
    }

    private void onCallback(String key, int value) {
        onCallback(key, "" + value);
    }

    private void onCallback(String key, String text) {
        System.out.println("Callback '" + key + "': " + text);
    }

    @Override
    public void onAction(String name, boolean isPressed, float lastTimePerFrame) {
        if ("space".equals(name) && isPressed) {
            inputManager.setCursorVisible(flyCam.isEnabled());
            flyCam.setEnabled(!flyCam.isEnabled());
        } else if ("1".equals(name) && isPressed) {
            System.out.println("---Deck---");
            for (Map.Entry<MyCardModel, Integer> entry : deckBuilderAppState.getDeck().entrySet()) {
                MyCardModel cardModel = entry.getKey();
                int amount = entry.getValue();
                System.out.println(amount + "x " + cardModel.getColor().name() + " " + cardModel.getName());
            }
            System.out.println("----------");
        } else if ("2".equals(name) && isPressed) {
            deckBuilderAppState.clearDeck();
        }
    }
}
