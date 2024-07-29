package com.destrostudios.cardgui.test;

import com.destrostudios.cardgui.BoardObjectVisualizer;
import com.destrostudios.cardgui.CardZone;
import com.destrostudios.cardgui.samples.tools.scrollselector.ScrollSelectorAppState;
import com.destrostudios.cardgui.samples.tools.scrollselector.ScrollSelectorSettings;
import com.destrostudios.cardgui.samples.visualization.DebugZoneVisualizer;
import com.destrostudios.cardgui.test.files.FileAssets;
import com.destrostudios.cardgui.zones.SimpleIntervalZone;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.system.AppSettings;

import java.util.LinkedList;
import java.util.List;

public class ScrollSelectorTestApplication extends SimpleApplication implements ActionListener {

    public static void main(String[] args) {
        FileAssets.readRootFile();

        ScrollSelectorTestApplication app = new ScrollSelectorTestApplication();
        app.setShowSettings(false);
        AppSettings settings = new AppSettings(true);
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setTitle("CardGui - TestApp (ScrollSelector)");
        settings.setGammaCorrection(false);
        app.setSettings(settings);
        app.start();
    }

    private List<MyCardModel> cards;

    @Override
    public void simpleInitApp() {
        assetManager.registerLocator(FileAssets.ROOT, FileLocator.class);
        viewPort.setBackgroundColor(ColorRGBA.White);

        initCamera();
        initLight();
        initListeners();
        initCards();
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
        inputManager.addMapping("delete", new KeyTrigger(KeyInput.KEY_DELETE));
        inputManager.addMapping("1", new KeyTrigger(KeyInput.KEY_1));
        inputManager.addMapping("2", new KeyTrigger(KeyInput.KEY_2));
        inputManager.addMapping("3", new KeyTrigger(KeyInput.KEY_3));
        inputManager.addMapping("4", new KeyTrigger(KeyInput.KEY_4));
        inputManager.addMapping("5", new KeyTrigger(KeyInput.KEY_5));
        inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addListener(this, "space", "return", "delete", "1", "2", "3", "4", "5", "left", "right");
    }

    private void initCards() {
        cards = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            cards.add(TestCards.getRandomCardModel());
        }
        CardZone cardZone = new SimpleIntervalZone(new Vector3f(), new Vector3f(0.5f, 0.01f, 0));
        BoardObjectVisualizer<CardZone> cardZoneVisualizer = new DebugZoneVisualizer() {

            @Override
            protected Vector2f getSize(CardZone zone) {
                return new Vector2f(20, 4);
            }
        };
        ScrollSelectorSettings<MyCardModel> settings = ScrollSelectorSettings.<MyCardModel>builder()
            .cards(cards)
            .cardZone(cardZone)
            .cardZoneVisualizer(cardZoneVisualizer)
            .cardVisualizer(new MyCardVisualizer(false))
            .cardSelectionChangeCallback((card, selected) -> card.setGlowColor(selected ? ColorRGBA.Green : null))
            .maximumSelectedCards(3)
            .build();
        stateManager.attach(new ScrollSelectorAppState<>(rootNode, settings));
    }

    @Override
    public void onAction(String name, boolean isPressed, float lastTimePerFrame) {
        ScrollSelectorAppState<MyCardModel> scrollSelectorAppState = stateManager.getState(ScrollSelectorAppState.class);
        if ("space".equals(name) && isPressed) {
            inputManager.setCursorVisible(flyCam.isEnabled());
            flyCam.setEnabled(!flyCam.isEnabled());
        } else if ("return".equals(name) && isPressed) {
            scrollSelectorAppState.selectAllCards();
        } else if ("delete".equals(name)) {
            scrollSelectorAppState.unselectAllCards();
        } else if (("1".equals(name) || "2".equals(name) || "3".equals(name) || "4".equals(name) || "5".equals(name)) && isPressed) {
            int cardIndex = (Integer.parseInt(name) - 1);
            MyCardModel cardModel = cards.get(cardIndex);
            scrollSelectorAppState.setCardSelected(cardModel, !scrollSelectorAppState.isCardSelected(cardModel));
        } else if ("left".equals(name) && isPressed) {
            scrollSelectorAppState.focusPreviousCard();
        } else if ("right".equals(name) && isPressed) {
            scrollSelectorAppState.focusNextCard();
        }
    }
}
