package org.example;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import org.fxyz3d.geometry.Point3D;
import org.fxyz3d.scene.paint.Palette;
import org.fxyz3d.shapes.primitives.Surface3DMesh;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HeighMapMeshTest extends Application {


    private static final List<Color> COLOR_LIST = Arrays.asList(Color.web("#3b6eca"),
            Color.web("#d7d588"), Color.web("#60a318"), Color.web("#457517"), Color.web("#467610"),
            Color.web("#654f44"), Color.web("#56453d"), Color.web("#fdfefc"), Color.web("#ffffff"));

    private final Rotate rotateX = new Rotate(-10, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(5, Rotate.Y_AXIS);

    private double mousePosX;
    private double mousePosY;
    private double mouseOldX;
    private double mouseOldY;

    @Override
    public void start(Stage primaryStage) {
        Group sceneRoot = new Group();

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.getTransforms().addAll (rotateX, rotateY, new Translate(0, 0, -800));

        Scene scene = new Scene(sceneRoot, 1000, 600, true, SceneAntialiasing.BALANCED);
        scene.setCamera(camera);

        List<Point3D> data = processImage();

        Surface3DMesh heightMapMesh = new Surface3DMesh(data);
        heightMapMesh.setDrawMode(DrawMode.FILL);
        heightMapMesh.setTextureModeVertices3D(new Palette.ListColorPalette(COLOR_LIST), p -> -p.y);

        Surface3DMesh wireframe = new Surface3DMesh(data);
        wireframe.setTextureModeNone(Color.BLACK);

        Group mapGroup = new Group(heightMapMesh, wireframe);
        mapGroup.getTransforms().add(new Translate(-500, 100, 0));
        sceneRoot.getChildren().addAll(mapGroup, new AmbientLight());

        scene.setOnMousePressed(event -> {
            mousePosX = event.getSceneX();
            mousePosY = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            mousePosX = event.getSceneX();
            mousePosY = event.getSceneY();
            rotateX.setAngle(rotateX.getAngle() - (mousePosY - mouseOldY));
            rotateY.setAngle(rotateY.getAngle() + (mousePosX - mouseOldX));
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
        });

        primaryStage.setTitle("F(X)yz - HeightMapMesh");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private List<Point3D> processImage() {
        float[][] points = PerlounNoise.preSettedNoise();

        List<Point3D> data = new ArrayList<>();
        for (int i = 0; i < points.length; i++){
            for (int j = 0; j < points[0].length; j++){
                data.add(new Point3D( i, j, points[i][j]));
            }
        }
        return data;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
