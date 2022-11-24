package com.epam.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.epam.dao.EventDao;
import com.epam.dao.TicketDao;
import com.epam.dao.UserDao;
import com.epam.entity.TicketImpl;
import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class TicketService {

    private TicketDao ticketDao;
    private UserDao userDao;
    private EventDao eventDao;

    public Optional<Ticket> book(long userId, long eventId, int place, Ticket.Category category) {
        if (userDao.findById(userId).isPresent() && eventDao.findById(eventId).isPresent()) {
            if (ticketDao.doesEventExistByUserIdAndEventId(userId, eventId)) {
                LOG.warn("There is already an event with passed ids(userId - {}, eventId - {}) added", userId, eventId);
                throw new IllegalStateException();
            }
            Ticket ticket = new TicketImpl(userId, eventId, category, place);
            return Optional.of(ticketDao.create(ticket));
        }
        LOG.warn("Either a user or an event does not exist with the passed ids: userId - {}, eventId - {}", userId, eventId);
        return Optional.empty();
    }

    public List<Ticket> getBookedTicketsByUser(User user, int pageSize, int pageNumber) {
        return ticketDao
                .getByUser(user, pageSize, pageNumber)
                .stream()
                .map(ticket -> ImmutablePair.of(eventDao.findById(ticket.getEventId()).get(), ticket))
                .sorted(Comparator.comparing(pair -> pair.getLeft().getDate(), Comparator.reverseOrder()))
                .map(Pair::getRight)
                .collect(Collectors.toList());
    }

    public List<Ticket> getBookedTicketsByEvent(Event event, int pageSize, int pageNumber) {
        return ticketDao
                .getByEvent(event, pageSize, pageNumber)
                .stream()
                .map(ticket -> ImmutablePair.of(userDao.findById(ticket.getUserId()).get(), ticket))
                .sorted(Comparator.comparing(pair -> pair.getLeft().getEmail()))
                .map(Pair::getRight)
                .collect(Collectors.toList());
    }

    public boolean cancel(long ticketId) {
        return ticketDao.deleteById(ticketId);
    }

}
