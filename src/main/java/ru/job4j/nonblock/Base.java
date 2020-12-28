package ru.job4j.nonblock;

import net.jcip.annotations.Immutable;

import java.util.Objects;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 28.12.2020
 */
@Immutable
public final class Base {
    private final int id;
    private final String name;
    private final int version;

    public Base(int id, String name) {
        this.id = id;
        this.name = name;
        this.version = 0;
    }

    private Base(int id, String name, int version) {
        this.id = id;
        this.name = name;
        this.version = version;
    }

    public static Base of(Base base) {
        return new Base(base.id, base.name, base.version);
    }

    public final Base setName(String name) {
        return new Base(id, name, version);
    }

    public final Base updVersion() {
        return new Base(id, name, version + 1);
    }

    public final int getId() {
        return id;
    }

    public final String getName() {
        return name;
    }

    public final int getVersion() {
        return version;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Base base = (Base) obj;
        return id == base.id
                && name.equals(base.name)
                && version == base.version;
    }

    @Override
    public final String toString() {
        return String.format("Base{id=%d, name=%s, version=%d}",
                id, name, version);
    }
}
