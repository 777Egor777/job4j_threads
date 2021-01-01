package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;
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

    public String filterLine(String line, Predicate<Integer> pred) {
        StringBuffer buffer = new StringBuffer();
        for (int index = 0; index < line.length(); ++index) {
            char c = line.charAt(index);
            if (pred.test((int) c)) {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }

    public synchronized String getContent(Predicate<Integer> pred) {
        String content = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            content = reader.lines()
                    .map(str -> filterLine(str, pred))
                    .collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        return content;
    }

    public String getContent() {
        return getContent(i -> true);
    }

    public String getContentWithoutUnicode() {
        return getContent(i -> i < 0x80);
    }

    public synchronized void saveContent(String content) {
        try (PrintStream printer = new PrintStream(file)) {
            printer.print(content);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }
}
