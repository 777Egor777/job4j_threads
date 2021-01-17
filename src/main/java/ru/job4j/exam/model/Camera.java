package ru.job4j.exam.model;

import net.jcip.annotations.Immutable;

import java.util.Objects;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 16.01.2021
 */
@Immutable
public final class Camera {
    private final int id;
    private final String urlType;
    private final String videoUrl;
    private final String value;
    private final int ttl;

    public Camera(int id, String urlType, String videoUrl, String value, int ttl) {
        this.id = id;
        this.urlType = urlType;
        this.videoUrl = videoUrl;
        this.value = value;
        this.ttl = ttl;
    }

    public int getId() {
        return id;
    }

    public String getUrlType() {
        return urlType;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getValue() {
        return value;
    }

    public int getTtl() {
        return ttl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Camera camera = (Camera) o;
        return id == camera.id &&
                ttl == camera.ttl &&
                Objects.equals(urlType, camera.urlType) &&
                Objects.equals(videoUrl, camera.videoUrl) &&
                Objects.equals(value, camera.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, urlType, videoUrl, value, ttl);
    }

    @Override
    public String toString() {
        String sep = System.lineSeparator();
        return String.format("{%s\"id\": %d,%s\"urlType\": \"%s\",%s\"videoUrl\": \"%s\",%s"
                           + "\"value\": \"%s\",%s\"ttl\": %d%s}",
                sep, id, sep, urlType, sep, videoUrl, sep, value, sep, ttl, sep
                );
    }
}
