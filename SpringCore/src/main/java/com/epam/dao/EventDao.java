package com.epam.dao;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.epam.entity.EventImpl;
import com.epam.model.Event;
import com.epam.util.IdGenerator;
import com.epam.util.Paginator;

@Slf4j
@Setter
public class EventDao {

    private final Map<Long, Event> events = new HashMap<>();
    private Paginator<Event> paginator;
    private IdGenerator generator;

    public Optional<Event> findById(long eventId) {
        LOG.info("Retrieving an event by {} id...", eventId);
        return Optional.ofNullable(events.get(eventId));
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNumber) {
        LOG.debug("Retrieving events by {}... Passed page size - {}, page number - {}", title, pageSize, pageNumber);
        return filter(event -> event.getTitle().contains(title), pageSize, pageNumber);
    }

    public List<Event> getEventsByDay(Date day, int pageSize, int pageNumber) {
        LOG.info("Retrieving events by {}... Passed page size - {}, page number - {}", day, pageSize, pageNumber);
        return filter(event -> event.getDate().equals(day), pageSize, pageNumber);
    }

    public Event create(Event event) {
        event.setId(generator.generateId(EventImpl.class));
        LOG.info("Adding a new event: title - {}, date - {}...", event.getTitle(), event.getDate());
        events.put(event.getId(), event);
        LOG.info("The event was added successfully");
        return events.get(event.getId());
    }

    public Optional<Event> update(Event event) {
        if (events.containsKey(event.getId())) {
            LOG.info("Updating an event by {} id with the following data: title - {}, date - {}...", event.getId(), event.getTitle(), event.getDate());
            events.put(event.getId(), event);
            LOG.info("The event was updated successfully");
            return Optional.of(events.get(event.getId()));
        }
        LOG.warn("Such event was not found while updating");
        return Optional.empty();
    }

    public boolean deleteById(long eventId) {
        LOG.info("Deleting an event by {} id...", eventId);
        if (!events.containsKey(eventId)) {
            LOG.warn("No event was found with such id");
            return false;
        }
        events.remove(eventId);
        LOG.info("The event was deleted successfully");
        return true;
    }

    private List<Event> filter(Predicate<Event> predicate, int pageSize, int pageNumber) {
        return paginator.paginate(events
                .values()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList()), pageSize, pageNumber);
    }

}
