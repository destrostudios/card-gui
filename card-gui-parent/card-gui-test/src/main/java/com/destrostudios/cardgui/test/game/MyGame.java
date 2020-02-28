package com.destrostudios.cardgui.test.game;

/**
 *
 * @author Carl
 */
public class MyGame {

    public MyGame(MyPlayer[] players) {
        this.players = players;
    }
    private MyPlayer[] players;
    private int currentPlayerIndex;
    
    public void start() {
        for (MyPlayer player : players) {
            player.getDeck().shuffle();
            for (int i=0;i<3;i++) {
                player.draw();
            }
        }
        currentPlayerIndex = 0;
    }

    public MyPlayer getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    public MyPlayer[] getPlayers() {
        return players;
    }
}
