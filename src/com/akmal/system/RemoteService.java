package com.akmal.system;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.WritableImage;
import javafx.scene.robot.Robot;
import javafx.stage.Screen;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class RemoteService extends Service<Void> {

    private final BooleanProperty isRunning = new SimpleBooleanProperty(false);

    private final DoubleProperty mouseX = new SimpleDoubleProperty();
    private final DoubleProperty mouseY = new SimpleDoubleProperty();


    public void startService() {
        isRunning.set(true);
        start();
    }

    public void stopService() {
        isRunning.set(false);
    }

    public DoubleProperty getMouseX() {
        return mouseX;
    }

    public DoubleProperty getMouseY() {
        return mouseY;
    }

    @Override
    protected Task<Void> createTask() {
        return new UpdateTask();
    }


    private class UpdateTask extends Task<Void> {


        @Override
        protected Void call() throws Exception {
            System.out.println("running " + isRunning.get());

            while (isRunning.get()) {

                Platform.runLater(() -> {
                    Robot robot = new Robot();

                    mouseX.set(robot.getMouseX());
                    mouseY.set(robot.getMouseY());

//                    WritableImage roi = new WritableImage(500, 500);
//                    Rectangle2D rect = Screen.getPrimary().getVisualBounds();
//
//                    WritableImage image = robot.getScreenCapture(roi, rect);
//
//                    try {
//                        ImageIO.write(
//                                SwingFXUtils.fromFXImage(image, null),
//                                "png",
//                                new File("screenshot.png")
//                        );
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                });

                Thread.sleep(100);
            }

            return null;
        }
    }
}
