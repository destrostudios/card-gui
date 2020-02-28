package com.destrostudios.cardgui;

import java.util.LinkedList;
import java.util.Objects;

public class BoardObjectModel {

    private BoardObjectModel parentModel;
    private LinkedList<BoardObjectModel> childModels = new LinkedList<>();
    private boolean wasChanged;

    public <T> void updateIfNotEquals(T oldValue, T newValue, PropertyUpdater propertyUpdater) {
        if (!Objects.equals(oldValue, newValue)) {
            propertyUpdater.updateProperty();
            if (newValue instanceof BoardObjectModel) {
                BoardObjectModel newSubModel = (BoardObjectModel) newValue;
                childModels.remove(oldValue);
                childModels.add(newSubModel);
                newSubModel.parentModel = this;
            }
            onChanged();
        }
    }

    private void onChanged() {
        wasChanged = true;
        if (parentModel != null) {
            parentModel.onChanged();
        }
    }

    public void onUpdate() {
        wasChanged = false;
        for (BoardObjectModel childModel : childModels) {
            childModel.onUpdate();
        }
    }

    public boolean wasChanged() {
        return wasChanged;
    }

    public interface PropertyUpdater {
        void updateProperty();
    }
}
