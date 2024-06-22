package ru.ylab.database;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.ylab.model.Place;
import ru.ylab.model.User;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class Repository {

    @Getter(lazy = true)
    private static final Repository instance = new Repository();

    private final AtomicLong counterUser = new AtomicLong(0L);
    private final AtomicLong counterPlace = new AtomicLong(0L);
    @Getter
    private final Map<Long, User> users = new ConcurrentHashMap<>();
    @Getter
    private final Map<Long, Place> places = new ConcurrentHashMap<>();

    public boolean create(User newUser) {
        boolean isAlreadyExists = users.values().stream().anyMatch(u -> u.getLogin().equalsIgnoreCase(newUser.getLogin()));
        if (isAlreadyExists) {
            return false;
        }

        newUser.setId(counterUser.incrementAndGet());
        users.put(newUser.getId(), newUser);
        log.info("New user={} was created!", newUser);
        return true;
    }

    public void delete(Long id) {
        User remove = users.remove(id);
        if (remove == null) {
            log.info("User with ID={} not exist in DB.", id);
        } else {
            log.info("User with ID={} removed from DB.", id);
        }
    }

    public void update(User user) {
        users.put(user.getId(), user);
        log.info("User with ID={} updated in DB.", user.getId());
    }

    public User findUser(long id) {
        return users.get(id);
    }

    public User findUser(String login, String password) {
        return users.values().stream()
                .filter(u -> u.getLogin().equalsIgnoreCase(login) && Objects.equals(u.getPassword(), password))
                .findAny()
                .orElse(null);
    }

    public void create(Place place) {
        place.setId(counterPlace.incrementAndGet());
        places.put(place.getId(), place);
        log.info("New place = {} was created!", place);
    }
}
