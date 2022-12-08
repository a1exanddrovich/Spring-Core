package com.epam.dao;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.epam.entity.TicketImpl;
import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;
import com.epam.util.IdGenerator;
import com.epam.util.Paginator;

@Slf4j
@Setter
public class TicketDao {

    private final Map<Long, Ticket> tickets = new HashMap<>();
    private Paginator<Ticket> paginator;
    private IdGenerator generator;

    public Ticket create(Ticket ticket) {
        ticket.setId(generator.generateId(TicketImpl.class));
        LOG.info("Adding a new ticket: userId - {}, eventId - {}, place - {}, category - {}...", ticket.getUserId(), ticket.getEventId(), ticket.getPlace(), ticket.getCategory());
        tickets.put(ticket.getId(), ticket);
        LOG.debug("The event was added successfully");
        return tickets.get(ticket.getId());
    }

    public List<Ticket> getByUser(User user, int pageSize, int pageNumber) {
        LOG.info("Retrieving tickets by user id {}... Passed page size - {}, page number - {}", user.getId(), pageSize, pageNumber);
        return filter(ticket -> ticket.getUserId() == user.getId(), pageSize, pageNumber);
    }

    public List<Ticket> getByEvent(Event event, int pageSize, int pageNumber) {
        LOG.info("Retrieving tickets by event id {}... Passed page size - {}, page number - {}", event.getId(), pageSize, pageNumber);
        return filter(ticket -> ticket.getEventId() == event.getId(), pageSize, pageNumber);
    }

    public boolean doesEventExistByUserIdAndEventId(long userId, long eventId) {
        LOG.info("Retrieving tickets by user id {} and event id {}...", userId, eventId);
        return this.tickets
                .values()
                .stream()
                .anyMatch(ticket -> ticket.getUserId() == userId && ticket.getEventId() == eventId);
    }

    public boolean deleteById(long ticketId) {
        LOG.info("Deleting a ticket by {} id...", ticketId);
        if (!tickets.containsKey(ticketId)) {
            LOG.warn("No ticket was found with such id");
            return false;
        }
        tickets.remove(ticketId);
        LOG.info("The ticket was deleted successfully");
        return true;
    }

    private List<Ticket> filter(Predicate<Ticket> predicate, int pageSize, int pageNumber) {
        return paginator.paginate(this.tickets
                .values()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList()), pageSize, pageNumber);
    }

}
