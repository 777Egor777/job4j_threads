package ru.job4j.io;

import java.io.*;
import java.util.stream.Collectors;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 26.12.2020
 */
public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized ParseFile setFile(File f) {
        return new ParseFile(f);
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() {
        String content = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            content = reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        return content;
    }

    public synchronized String getContentWithoutUnicode() {
        String content = "";
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file))) {
            int ch;
            StringBuffer buffer = new StringBuffer();
            while ((ch = reader.read()) >= 0) {
                if (ch < 0x80) {
                    buffer.append((char) ch);
                }
            }
            content = buffer.toString();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        return content;
    }

    public synchronized void saveContent(String content) {
        try (PrintStream printer = new PrintStream(file)) {
            printer.print(content);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }
}
