package naga.fx.sun.scene.input;

import naga.fx.geometry.Point3D;
import naga.fx.scene.input.PickResult;
import naga.fx.scene.input.TransferMode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Utility class for helper methods needed by input events.
 */
public class InputEventUtils {

    /**
     * Recomputes event coordinates for a different node.
     * @param result Coordinates to recompute
     * @param newSource Node to whose coordinate system to recompute
     * @return the recomputed coordinates
     */
    public static Point3D recomputeCoordinates(PickResult result, Object newSource) {

        Point3D coordinates = result.getIntersectedPoint();
        if (coordinates == null)
            return new Point3D(Double.NaN, Double.NaN, Double.NaN);

        System.out.println("Warning: InputEventUtils.recomputeCoordinates() not yet implemented");

/*
        Node oldSourceNode = result.getIntersectedNode();
        Node newSourceNode = (newSource instanceof Node) ? (Node) newSource : null;

        final SubScene oldSubScene =
                (oldSourceNode == null ? null : NodeHelper.getSubScene(oldSourceNode));
        final SubScene newSubScene =
                (newSourceNode == null ? null : NodeHelper.getSubScene(newSourceNode));
        final boolean subScenesDiffer = (oldSubScene != newSubScene);

        if (oldSourceNode != null) {
            // transform to scene/nearest-subScene coordinates
            coordinates = oldSourceNode.localToScene(coordinates);
            if (subScenesDiffer && oldSubScene != null) {
                // transform to scene coordiantes
                coordinates = SceneUtils.subSceneToScene(oldSubScene, coordinates);
            }
        }

        if (newSourceNode != null) {
            if (subScenesDiffer && newSubScene != null) {
                // flatten the coords to flat mouse coordinates - project
                // by scene's camera
                Point2D planeCoords = CameraHelper.project(
                        SceneHelper.getEffectiveCamera(newSourceNode.getScene()),
                        coordinates);
                // convert the point to subScene coordinates
                planeCoords = SceneUtils.sceneToSubScenePlane(newSubScene, planeCoords);
                // compute inner intersection with the subScene's camera
                // projection plane
                if (planeCoords == null) {
                    coordinates = null;
                } else {
                    coordinates = CameraHelper.pickProjectPlane(
                            SubSceneHelper.getEffectiveCamera(newSubScene),
                            planeCoords.getX(), planeCoords.getY());
                }
            }
            // transform the point to source's local coordinates
            if (coordinates != null) {
                coordinates = newSourceNode.sceneToLocal(coordinates);
            }
            if (coordinates == null) {
                coordinates = new Point3D(Double.NaN, Double.NaN, Double.NaN);
            }
        }
*/

        return coordinates;
    }

    private static final List<TransferMode> TM_ANY =
            Collections.unmodifiableList(Arrays.asList(
                    TransferMode.COPY,
                    TransferMode.MOVE,
                    TransferMode.LINK
            ));

    private static final List<TransferMode> TM_COPY_OR_MOVE =
            Collections.unmodifiableList(Arrays.asList(
                    TransferMode.COPY,
                    TransferMode.MOVE
            ));

    /**
     * Makes sure changes to the static arrays specified in TransferMode
     * don't have any effect on the transfer modes used.
     * @param modes Modes passed in by user
     * @return list containing the passed modes. If one of the static arrays
     *         is passed, the expected modes are returned regardless of the
     *         values in those arrays.
     */
    public static List<TransferMode> safeTransferModes(TransferMode[] modes) {
        if (modes == TransferMode.ANY) {
            return TM_ANY;
        } else if (modes == TransferMode.COPY_OR_MOVE) {
            return TM_COPY_OR_MOVE;
        } else {
            return Arrays.asList(modes);
        }
    }
}