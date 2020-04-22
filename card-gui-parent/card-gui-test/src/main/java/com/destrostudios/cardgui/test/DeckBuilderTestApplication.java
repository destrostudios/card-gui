package com.destrostudios.cardgui.test;

import com.destrostudios.cardgui.*;
import com.destrostudios.cardgui.samples.tools.deckbuilder.DeckBuilderAppState;
import com.destrostudios.cardgui.samples.tools.deckbuilder.DeckBuilderDeckCardModel;
import com.destrostudios.cardgui.samples.tools.deckbuilder.DeckBuilderSettings;
import com.destrostudios.cardgui.samples.visualization.DebugZoneVisualizer;
import com.destrostudios.cardgui.test.files.FileAssets;
import com.destrostudios.cardgui.test.game.MyCard;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DeckBuilderTestApplication extends SimpleApplication implements ActionListener {

    public static void main(String[] args) {
        FileAssets.readRootFile();

        DeckBuilderTestApplication app = new DeckBuilderTestApplication();
        app.setShowSettings(false);
        AppSettings settings = new AppSettings(true);
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setTitle("CardGui - TestApp (DeckBuilder)");
        app.setSettings(settings);
        app.start();
    }

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
        inputManager.addMapping("return", new KeyTrigger(KeyInput.KEY_RETURN));
        inputManager.addListener(this, "space", "return");
    }

    private void initDeckBuilder() {
        List<MyCardModel> allCardModels = new LinkedList<>();
        for (int i = 0; i < 112; i++) {
            MyCardModel cardModel = new MyCardModel();
            cardModel.setColor(MyCard.Color.values()[(int) (Math.random() * MyCard.Color.values().length)]);
            cardModel.setName((Math.random() < 0.5) ? "Aether Adept" : "Shyvana");
            allCardModels.add(cardModel);
        }
        CardZone collectionZone = new SimpleIntervalZone(new Vector3f(-2, 0, 0), new Vector3f(1, 1, 1.4f));
        CardZone deckZone = new SimpleIntervalZone(new Vector3f(8.25f, 0, -4.715f), new Vector3f(1, 1, 0.57f));
        BoardObjectVisualizer<CardZone> collectionZoneVisualizer = new DebugZoneVisualizer() {

            @Override
            protected Vector2f getSize(CardZone zone) {
                return new Vector2f(16.5f, 10);
            }
        };
        BoardObjectVisualizer<CardZone> deckZoneVisualizer = new DebugZoneVisualizer() {

            @Override
            protected Geometry createVisualizationObject(AssetManager assetManager) {
                Geometry visualizationObject = super.createVisualizationObject(assetManager);
                visualizationObject.move(0,0, 4.715f);
                return visualizationObject;
            }

            @Override
            protected Vector2f getSize(CardZone zone) {
                return new Vector2f(4, 10);
            }
        };
        BoardObjectVisualizer<Card<MyCardModel>> collectionCardVisualizer = new MyCardVisualizer(false);
        BoardObjectVisualizer<Card<DeckBuilderDeckCardModel<MyCardModel>>> deckCardVisualizer = new MyDeckBuilderDeckCardVisualizer();
        Comparator<MyCardModel> deckCardOrder = Comparator.comparing(MyCardModel::getName);
        DeckBuilderSettings<MyCardModel> settings = DeckBuilderSettings.<MyCardModel>builder()
                .allCardModels(allCardModels)
                .collectionZone(collectionZone)
                .deckZone(deckZone)
                .collectionZoneVisualizer(collectionZoneVisualizer)
                .deckZoneVisualizer(deckZoneVisualizer)
                .collectionCardVisualizer(collectionCardVisualizer)
                .deckCardVisualizer(deckCardVisualizer)
                .deckCardOrder(deckCardOrder)
                .collectionCardsPerRow(16)
                .collectionRowsPerPage(7)
                .build();
        stateManager.attach(new DeckBuilderAppState<>(rootNode, settings));
    }

    @Override
    public void onAction(String name, boolean isPressed, float lastTimePerFrame) {
        if ("space".equals(name) && isPressed) {
            inputManager.setCursorVisible(flyCam.isEnabled());
            flyCam.setEnabled(!flyCam.isEnabled());
        } else if ("return".equals(name) && isPressed) {
            Map<MyCardModel, Integer> deck = stateManager.getState(DeckBuilderAppState.class).getDeck();
            System.out.println("---Deck---");
            for (Map.Entry<MyCardModel, Integer> entry : deck.entrySet()) {
                MyCardModel myCardModel = entry.getKey();
                int amount = entry.getValue();
                System.out.println(amount + "x " + myCardModel.getColor().name() + " " + myCardModel.getName());
            }
            System.out.println("----------");
        }
    }
}
