package com.epam.facade;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;
import com.epam.service.EventService;
import com.epam.service.TicketService;
import com.epam.service.UserService;

@Slf4j
@AllArgsConstructor
public class BookingFacadeImpl implements BookingFacade {

    private final EventService eventService;
    private final TicketService ticketService;
    private final UserService userService;

    @Override
    public Event getEventById(long eventId) {
        return eventService.getById(eventId).orElse(null);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNumber) {
        return eventService.getEventsByTitle(title, pageSize, pageNumber);
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNumber) {
        return eventService.getEventsForDay(day, pageSize, pageNumber);
    }

    @Override
    public Event createEvent(Event event) {
        return eventService.create(event);
    }

    @Override
    public Event updateEvent(Event event) {
        return eventService.update(event).orElse(null);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        return eventService.delete(eventId);
    }

    @Override
    public User getUserById(long userId) {
        return userService.getById(userId).orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        return userService.getByEmail(email).orElse(null);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNumber) {
        return userService.getByName(name, pageSize, pageNumber);
    }

    @Override
    public User createUser(User user) {
        return userService.create(user);
    }

    @Override
    public User updateUser(User user) {
        return userService.update(user).orElse(null);
    }

    @Override
    public boolean deleteUser(long userId) {
        return userService.delete(userId);
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        return ticketService.book(userId, eventId, place, category).orElse(null);
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNumber) {
        return ticketService.getBookedTicketsByUser(user, pageSize, pageNumber);
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNumber) {
        return ticketService.getBookedTicketsByEvent(event, pageSize, pageNumber);
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        return ticketService.cancel(ticketId);
    }

}
