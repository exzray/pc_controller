package com.akmal.system;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.robot.Robot;

public class RemoteService extends Service<Void> {

    private final BooleanProperty isRunning = new SimpleBooleanProperty(false);

    private static RemoteService instance;

    private final DoubleProperty mouseX = new SimpleDoubleProperty();
    private final DoubleProperty mouseY = new SimpleDoubleProperty();


    public static RemoteService getInstance() {
        if (instance == null)
            instance = new RemoteService();

        return instance;
    }

    private RemoteService() {
    }

    public void startService() {
        isRunning.set(true);
        start();
    }

    public void stopService() {
        isRunning.set(false);
    }

    public BooleanProperty getIsRunning() {
        return isRunning;
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
//            final WritableImage roi = new WritableImage(500, 500);
//            final Rectangle2D rect = Screen.getPrimary().getVisualBounds();

            while (isRunning.get()) {

                Platform.runLater(() -> {
                    Robot robot = new Robot();

                    double x = Math.floor(robot.getMouseX());
                    double y = Math.floor(robot.getMouseY());

                    mouseX.set(x);
                    mouseY.set(y);

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
