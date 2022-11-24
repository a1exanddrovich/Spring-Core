package com.epam;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.epam.entity.EventImpl;
import com.epam.entity.UserImpl;
import com.epam.facade.BookingFacadeImpl;
import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;

class MainTest {

    @Test
    void realLifeTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        BookingFacadeImpl bookingFacadeImpl = context.getBean("bookingFacade", BookingFacadeImpl.class);
        User user = new UserImpl("NewUser", "NewUser@mail.com");
        User createdUser = bookingFacadeImpl.createUser(user);

        assertThat(createdUser.getId(), is(5L));

        Event event = new EventImpl("NewEvent", new Date(333));

        Event createdEvent = bookingFacadeImpl.createEvent(event);

        assertThat(createdEvent.getId(), is(1L));

        Ticket ticket = bookingFacadeImpl.bookTicket(createdUser.getId(), createdEvent.getId(), 2, Ticket.Category.PREMIUM);

        assertThat(ticket.getId(), is(1L));
        assertThat(ticket.getUserId(), is(5L));
        assertThat(ticket.getEventId(), is(1L));
        assertThat(ticket.getCategory(), is(Ticket.Category.PREMIUM));
        assertThat(ticket.getPlace(), is(2));

        List<Ticket> bookedTickets = bookingFacadeImpl.getBookedTickets(createdUser, 10, 1);

        assertThat(bookedTickets, is(Collections.singletonList(ticket)));

        boolean isCanceled = bookingFacadeImpl.cancelTicket(ticket.getId());
        List<Ticket> bookedTicketsAfterCancellation = bookingFacadeImpl.getBookedTickets(createdUser, 10, 1);

        assertTrue(isCanceled);
        assertThat(bookedTicketsAfterCancellation, is(Collections.emptyList()));

    }

}
