package ru.job4j.io;

import java.io.*;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 26.12.2020
 */
public class ParseFile {
    private volatile File file;

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
        int ch;
        StringBuffer buffer = new StringBuffer();
        while ((ch = reader.read()) >= 0) {
            if (ch < 0x80) {
                buffer.append((char) ch);
            }
        }
        return buffer.toString();
    }

    public synchronized void saveContent(String content) throws IOException {
        PrintStream printer = new PrintStream(file);
        printer.print(content);
    }
}
