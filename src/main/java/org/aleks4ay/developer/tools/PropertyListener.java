package org.aleks4ay.developer.tools;

import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;

import java.io.File;

public class PropertyListener extends Thread{
    private static final PropertyListener instance = new PropertyListener();

    private static final File file1 = new File(Constants.FILE_CHANGES);
    private final LongProperty orderTimeProperty = new SimpleLongProperty(new File(Constants.FILE_CHANGES).lastModified());
    private final Thread mainThread = Thread.currentThread();


    private PropertyListener() {
    }

    public static LongProperty getOrderTimeProperty() {
        if(!instance.isAlive()) {
            instance.start();
            instance.setName("Listener thread");
        }
        return instance.orderTimeProperty;
    }

    @Override
    public void run() {
        while (mainThread.isAlive()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (orderTimeProperty.get() != file1.lastModified()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater( () -> orderTimeProperty.setValue(file1.lastModified()));
            }
        }
    }
}
