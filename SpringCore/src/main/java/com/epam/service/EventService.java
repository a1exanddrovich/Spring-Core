package com.epam.service;

import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.epam.dao.EventDao;
import com.epam.model.Event;

@AllArgsConstructor
public class EventService {

    private EventDao dao;

    public Optional<Event> getById(long eventId) {
        return dao.findById(eventId);
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNumber) {
        return dao.getEventsByTitle(title, pageSize, pageNumber);
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNumber) {
        return dao.getEventsByDay(day, pageSize, pageNumber);
    }

    public Event create(Event event) {
        return dao.create(event);
    }

    public Optional<Event> update(Event event) {
        return dao.update(event);
    }

    public boolean delete(long eventId) {
        return dao.deleteById(eventId);
    }

}
