package ru.job4j.exam.model;

import net.jcip.annotations.Immutable;

import java.util.Objects;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 16.01.2021
 */
@Immutable
public final class Token {
    private final String value;
    private final int ttl;

    public Token(String value, int ttl) {
        this.value = value;
        this.ttl = ttl;
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
        Token token = (Token) o;
        return ttl == token.ttl &&
                Objects.equals(value, token.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, ttl);
    }

    @Override
    public String toString() {
        return "Token{" +
                "value='" + value + '\'' +
                ", ttl=" + ttl +
                '}';
    }
}
