package ru.job4j.thread;

import java.io.*;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 24.12.2020
 */
public class Wget implements Runnable {
    private final String url;
    private final long speed;
    private static final String OUTPUT_FILE_NAME = "./pom_temp.xml";
    private final BufferedInputStream input;
    private final OutputStream output;
    private final byte[] buffer = new byte[1024];
    private final AtomicInteger bytesRead = new AtomicInteger();
    private final AtomicInteger bufferNumber = new AtomicInteger();

    public Wget(String url, long speed) {
        this.url = url;
        this.speed = speed;
        BufferedInputStream tempInput = null;
        input = getStableInput();
        output = getStableOutput();

    }

    private BufferedInputStream getStableInput() {
        byte[] bytes = new byte[0];
        BufferedInputStream input;
        try {
            input = new BufferedInputStream(new URL(url).openStream());
        } catch (Exception ex) {
            input = new BufferedInputStream(new ByteArrayInputStream(bytes));
            ex.printStackTrace();
        }
        return input;
    }

    private OutputStream getStableOutput() {
        int lengthOfBuffer = 10;
        OutputStream output;
        try {
            output = new FileOutputStream(OUTPUT_FILE_NAME);
        } catch (Exception ex) {
            output = new ByteArrayOutputStream(lengthOfBuffer);
            ex.printStackTrace();
        }
        return output;
    }

    private boolean readBuffer() {
        boolean result = true;
        try {
            bytesRead.set(input.read(buffer, 0, buffer.length));
            if (bytesRead.get() == -1) {
                result = false;
            }
            System.out.printf("Buffer #%d loaded\n", bufferNumber.incrementAndGet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void writeBuffer() {
        try {
            output.write(buffer, 0, bytesRead.get());
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
