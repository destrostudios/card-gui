package com.destrostudios.cardgui;

import com.jme3.app.Application;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author Carl
 */
public class RayCasting {

    public RayCasting(Application application) {
        this.application = application;
    }
    private Application application;

    public CollisionResults getResults_Cursor(Spatial spatial) {
        return getResults_Screen(spatial, application.getInputManager().getCursorPosition());
    }
    
    public CollisionResults getResults_Screen(Spatial spatial, Vector2f screenLocation) {
        Vector3f cursorRayOrigin = application.getCamera().getWorldCoordinates(screenLocation, 0);
        Vector3f cursorRayDirection = application.getCamera().getWorldCoordinates(screenLocation, 1).subtractLocal(cursorRayOrigin).normalizeLocal();
        return getResults(spatial, new Ray(cursorRayOrigin, cursorRayDirection));
    }
    
    private CollisionResults getResults(Spatial spatial, Ray ray) {
        CollisionResults results = new CollisionResults();
        spatial.collideWith(ray, results);
        return results;
    }
}
