package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 24.12.2020
 */
public class Wget implements Runnable {
    private final String url;
    private final long speed;
    private static final String OUTPUT_FILE_NAME = "./pom_temp.xml";
    private BufferedInputStream input;
    private FileOutputStream output;
    private byte[] buffer = new byte[1024];
    private int bytesRead = 0;
    private int bufferNumber = 0;

    public Wget(String url, long speed) {
        this.url = url;
        this.speed = speed;
        init();
    }

    private boolean readBuffer() {
        boolean result = true;
        try {
            bytesRead = input.read(buffer, 0, buffer.length);
            if (bytesRead == -1) {
                result = false;
            }
            System.out.printf("Buffer #%d loaded\n", ++bufferNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void writeBuffer() {
        try {
            output.write(buffer, 0, bytesRead);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean readAndWriteBuffer() {
        boolean result = false;
        if (readBuffer()) {
            result = true;
            writeBuffer();
        }
        return result;
    }

    private void init() {
        try {
            input = new BufferedInputStream(new URL(url).openStream());
            output = new FileOutputStream(OUTPUT_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                long startTime = System.currentTimeMillis();
                if (!readAndWriteBuffer()) {
                    Thread.currentThread().interrupt();
                }
                long finishTime = System.currentTimeMillis();
                long duration = finishTime - startTime;
                if (duration < speed) {
                    long delta = speed - duration;
                    Thread.sleep(delta);
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        String url = args[0];
        long speed = Long.parseLong(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        try {
            wget.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
