package com.epam.util;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import com.epam.entity.EventImpl;
import com.epam.entity.TicketImpl;
import com.epam.entity.UserImpl;

import static org.hamcrest.CoreMatchers.is;

class IdGeneratorTest {

    private final IdGenerator sut = new IdGenerator();

    @Test
    void shouldGenerateIdWhenDifferentEntitiesPassed() {
        //given
        Class<UserImpl> userClass = UserImpl.class;
        Class<TicketImpl> ticketClass = TicketImpl.class;
        Class<EventImpl> eventClass = EventImpl.class;

        //when
        long userId = sut.generateId(userClass);
        long eventId = sut.generateId(eventClass);
        long ticketId = sut.generateId(ticketClass);

        //then
        assertThat(userId, is(6L));
        assertThat(eventId, is(2L));
        assertThat(ticketId, is(2L));
    }

}
