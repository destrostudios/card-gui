package com.destrostudios.cardgui.samples.boardobjects.connectionmarker;

import com.destrostudios.cardgui.BoardObject;

/**
 *
 * @author Carl
 */
public class ConnectionMarker extends BoardObject<ConnectionMarkerModel> {

    public ConnectionMarker() {
        super(new ConnectionMarkerModel());
    }

    @Override
    public boolean needsVisualizationUpdate() {
        return true;
    }
}
