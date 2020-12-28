package ru.job4j.nonblock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 28.12.2020
 */
public final class ConcurrentCache implements Cache<Base> {
    private final Map<Integer, Base> map = new ConcurrentHashMap<>();

    @Override
    public final void add(Base base) {
        Base val = map.putIfAbsent(base.getId(), Base.of(base));
        if (val != null) {
            update(base);
        }
    }

    @Override
    public final void delete(Base base) {
        if (base.getVersion() != map.get(base.getId()).getVersion()) {
            throw new OptimisticException("Concurrent version modification");
        }
        map.remove(base.getId());
    }

    @Override
    public final void update(Base base) {
        map.computeIfPresent(base.getId(), (k, v) -> {
            if (base.getVersion() != v.getVersion()) {
                throw new OptimisticException("Concurrent version modification");
            }
            return base.updVersion();
        });
    }

    public Base get(int id) {
        return map.get(id);
    }
}
