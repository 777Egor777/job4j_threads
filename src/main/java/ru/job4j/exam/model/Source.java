package ru.job4j.exam.model;

import net.jcip.annotations.Immutable;

import java.util.Objects;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 16.01.2021
 */
@Immutable
public final class Source {
    private final String urlType;
    private final String videoUrl;

    public Source(String urlType, String videoUrl) {
        this.urlType = urlType;
        this.videoUrl = videoUrl;
    }

    public String getUrlType() {
        return urlType;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Source source = (Source) o;
        return Objects.equals(urlType, source.urlType) &&
                Objects.equals(videoUrl, source.videoUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urlType, videoUrl);
    }

    @Override
    public String toString() {
        return "Source{" +
                "urlType='" + urlType + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                '}';
    }
}
