package ru.job4j.exam.model;

import net.jcip.annotations.Immutable;

import java.util.Objects;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 16.01.2021
 */
@Immutable
public final class Root {
    private final int id;
    private final String sourceDataUrl;
    private final String tokenDataUrl;

    public Root(int id, String sourceDataUrl, String tokenDataUrl) {
        this.id = id;
        this.sourceDataUrl = sourceDataUrl;
        this.tokenDataUrl = tokenDataUrl;
    }

    public int getId() {
        return id;
    }

    public String getSourceDataUrl() {
        return sourceDataUrl;
    }

    public String getTokenDataUrl() {
        return tokenDataUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Root root = (Root) o;
        return id == root.id &&
                Objects.equals(sourceDataUrl, root.sourceDataUrl) &&
                Objects.equals(tokenDataUrl, root.tokenDataUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sourceDataUrl, tokenDataUrl);
    }

    @Override
    public String toString() {
        return "Root{" +
                "id=" + id +
                ", sourceDataUrl='" + sourceDataUrl + '\'' +
                ", tokenDataUrl='" + tokenDataUrl + '\'' +
                '}';
    }
}
