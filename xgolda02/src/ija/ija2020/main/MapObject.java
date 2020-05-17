package ija.ija2020.main;
/**
 * Interface to unify all the drawable objects
 *
 * @author Vojtěch Golda
 */

import javafx.scene.shape.Shape;

import java.util.List;

public interface MapObject {
    List<Shape> getEl(float scale);
}
