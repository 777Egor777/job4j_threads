package ru.job4j.exam.parse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import ru.job4j.exam.model.Camera;
import ru.job4j.exam.model.Root;
import ru.job4j.exam.model.Source;
import ru.job4j.exam.model.Token;
import ru.job4j.exam.store.Store;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 16.01.2021
 */
public final class Parser {
    private Parser() {
    }

    private final static class Holder {
        private final static Parser INSTANCE = new Parser();
    }

    public static Parser instOf() {
        return Holder.INSTANCE;
    }

    public Token parseToken(String link) {
        String content = "";
        try {
            content = getContent(link);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject obj = (JSONObject) JSONValue.parse(content);
        return new Token(obj.get("value").toString(), Integer.parseInt(obj.get("ttl").toString()));
    }

    public Source parseSource(String link) {
        String content = "";
        try {
            content = getContent(link);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject obj = (JSONObject) JSONValue.parse(content);
        return new Source(obj.get("urlType").toString(), obj.get("videoUrl").toString());
    }

    private Root convertJsonToRoot(Object json) {
        JSONObject obj = (JSONObject) json;
        return new Root(
                Integer.parseInt(obj.get("id").toString()),
                obj.get("sourceDataUrl").toString(),
                obj.get("tokenDataUrl").toString()
        );
    }

    public List<Root> parseRoots(String link) {
        List<Root> result = new ArrayList<>();
        String content = "";
        try {
            content = getContent(link);
        } catch (IOException e) {
            e.printStackTrace();
        }
        content = "{\"arr\":" + content + "}";
        JSONObject obj = (JSONObject) JSONValue.parse(content);
        JSONArray arr =  (JSONArray) obj.get("arr");
        arr.forEach(json -> result.add(convertJsonToRoot(json)));
        return result;
    }

    private String getContent(String link) throws IOException {
        String content;
        try (BufferedReader input = new BufferedReader(new InputStreamReader(
                new URL(link).openStream()
        ))) {
            content = input.lines().collect(Collectors.joining(System.lineSeparator()));
        }
        return content;
    }

    private Camera convertRootToCamera(Root root) {
        Token token = parseToken(root.getTokenDataUrl());
        Source source = parseSource(root.getSourceDataUrl());
        return new Camera(
                root.getId(),
                source.getUrlType(),
                source.getVideoUrl(),
                token.getValue(),
                token.getTtl()
        );
    }

    private class ParseTask extends RecursiveTask<Boolean> {
        private final List<Root> roots;
        private final int begin;
        private final int end;
        private final static int THRESHOLD = 5;

        private void linearParse() {
            for (int index = begin; index <= end; ++index) {
                Store.instOf().add(convertRootToCamera(roots.get(index)).toString());
            }
        }

        public ParseTask(List<Root> roots, int begin, int end) {
            this.roots = roots;
            this.begin = begin;
            this.end = end;
        }

        @Override
        protected Boolean compute() {
            if (end - begin <= THRESHOLD) {
                linearParse();
            } else {
                int mid = (begin + end) / 2;
                ParseTask leftTask = new ParseTask(roots, begin, mid);
                ParseTask rightTask = new ParseTask(roots, mid + 1, end);
                leftTask.fork();
                rightTask.fork();
                leftTask.join();
                rightTask.join();
            }
            return true;
        }
    }

    public boolean parseAndAggregate(String link) {
        List<Root> roots = parseRoots(link);
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(new ParseTask(roots, 0, roots.size() - 1));
    }
}
