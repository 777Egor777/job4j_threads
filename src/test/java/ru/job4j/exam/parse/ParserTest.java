package ru.job4j.exam.parse;

import org.junit.Test;
import ru.job4j.exam.model.Root;
import ru.job4j.exam.model.Token;
import ru.job4j.exam.store.Store;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void parseToken() {
        String link = "http://www.mocky.io/v2/5c51b5b6340000554e129f7b?mocky-delay=1s";
        Token result = Parser.instOf().parseToken(link);
        Token expected = new Token("fa4b588e-249b-11e9-ab14-d663bd873d93", 120);
        assertThat(result, is(expected));
    }

    @Test
    public void parseRoots() {
        String link = "http://www.mocky.io/v2/5c51b9dd3400003252129fb5";
        List<Root> roots = Parser.instOf().parseRoots(link);
        assertThat(roots.get(0), is(new Root(
                1,
                "http://www.mocky.io/v2/5c51b230340000094f129f5d",
                "http://www.mocky.io/v2/5c51b5b6340000554e129f7b?mocky-delay=1s"
        )));
    }

    @Test
    public void parseAndAggregate() {
        String link = "http://www.mocky.io/v2/5c51b9dd3400003252129fb5";
        Parser.instOf().parseAndAggregate(link);
        String result = Store.instOf().getReport();
        String expected = "[\r\n"
                + "{\r\n"
                + "\"id\": 1,\r\n"
                + "\"urlType\": \"LIVE\",\r\n"
                + "\"videoUrl\": \"rtsp://127.0.0.1/1\",\r\n"
                + "\"value\": \"fa4b588e-249b-11e9-ab14-d663bd873d93\",\r\n"
                + "\"ttl\": 120\r\n"
                + "},\r\n"
                + "{\r\n"
                + "\"id\": 20,\r\n"
                + "\"urlType\": \"ARCHIVE\",\r\n"
                + "\"videoUrl\": \"rtsp://127.0.0.1/2\",\r\n"
                + "\"value\": \"fa4b5b22-249b-11e9-ab14-d663bd873d93\",\r\n"
                + "\"ttl\": 60\r\n"
                + "},\r\n"
                + "{\r\n"
                + "\"id\": 3,\r\n"
                + "\"urlType\": \"ARCHIVE\",\r\n"
                + "\"videoUrl\": \"rtsp://127.0.0.1/3\",\r\n"
                + "\"value\": \"fa4b5d52-249b-11e9-ab14-d663bd873d93\",\r\n"
                + "\"ttl\": 120\r\n"
                + "},\r\n"
                + "{\r\n"
                + "\"id\": 2,\r\n"
                + "\"urlType\": \"LIVE\",\r\n"
                + "\"videoUrl\": \"rtsp://127.0.0.1/20\",\r\n"
                + "\"value\": \"fa4b5f64-249b-11e9-ab14-d663bd873d93\",\r\n"
                + "\"ttl\": 180\r\n"
                + "}\r\n"
                + "]";
        assertThat(result, is(expected));
    }
}