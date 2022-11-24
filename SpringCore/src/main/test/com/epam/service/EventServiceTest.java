package com.epam.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dao.EventDao;
import com.epam.entity.EventImpl;
import com.epam.model.Event;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventDao dao;
    @InjectMocks
    private EventService sut;

    @Test
    void shouldReturnEventWhenExistIdPassed() {
        //given
        Optional<Event> expected = Optional.of(new EventImpl("New Year", new Date()));
        when(dao.findById(1)).thenReturn(expected);

        //when
        Optional<Event> actual = sut.getById(1);

        //then
        assertThat(actual, is(expected));
    }

    @Test
    void shouldReturnEmptyWhenNotExistIdPassed() {
        //given
        when(dao.findById(anyLong())).thenReturn(Optional.empty());

        //when
        Optional<Event> actual = sut.getById(10);

        //then
        assertThat(actual, is(Optional.empty()));
    }

    @Test
    void shouldDeleteEventWhenExistIdPassed() {
        //given
        when(dao.deleteById(1)).thenReturn(true);

        //when
        boolean actual = sut.delete(1);

        //then
        assertTrue(actual);
    }

    @Test
    void shouldNotDeleteEventWhenNotExistIdPassed() {
        //given
        when(dao.deleteById(anyLong())).thenReturn(false);

        //when
        boolean actual = sut.delete(10);

        //then
        assertFalse(actual);
    }

    @Test
    void shouldUpdateEventWhenExistIdPassed() {
        //given
        Optional<Event> user = Optional.of(new EventImpl("New Year", new Date()));
        Optional<Event> expected = Optional.of(new EventImpl("New Year Updated", new Date()));
        when(dao.update(user.get())).thenReturn(expected);

        //when
        Optional<Event> actual = sut.update(user.get());

        //then
        assertThat(actual, is(expected));
    }

    @Test
    void shouldReturnEmptyEventWhenNotExistIdPassedWhileUpdating() {
        //given
        Optional<Event> user = Optional.of(new EventImpl("New Year", new Date()));
        when(dao.update(any(Event.class))).thenReturn(Optional.empty());

        //when
        Optional<Event> actual = sut.update(user.get());

        //then
        assertThat(actual, is(Optional.empty()));
    }

    @Test
    void shouldCreateEvent() {
        //given
        Optional<Event> event = Optional.of(new EventImpl("New Year", new Date()));;
        Event expected = new EventImpl("New Year", new Date());
        when(dao.create(event.get())).thenReturn(expected);

        //when
        Event actual = sut.create(event.get());

        //then
        assertThat(actual, is(expected));
    }

    @Test
    void shouldReturnNonEmptyEventsBtTitle() {
        //given
        int pageSize = 5;
        int pageNumber = 2;
        String title = "New Year";
        List<Event> expected = Arrays.asList(new EventImpl("New Year", new Date()),
                new EventImpl("New Year After-party", new Date()),
                new EventImpl("New Year Pre_party", new Date()));
        when(dao.getEventsByTitle(title, pageSize, pageNumber)).thenReturn(expected);

        //when
        List<Event> actual = sut.getEventsByTitle(title, pageSize, pageNumber);

        //then
        assertThat(actual, is(expected));
    }

    @Test
    void shouldReturnNonEmptyEventsByDay() {
        //given
        int pageSize = 5;
        int pageNumber = 2;
        Date date = new Date(123L);
        List<Event> expected = Arrays.asList(new EventImpl("New Year", new Date(123L)),
                new EventImpl("New Year After-party", new Date(123L)),
                new EventImpl("New Year Pre_party", new Date(123L)));
        when(dao.getEventsByDay(date, pageSize, pageNumber)).thenReturn(expected);

        //when
        List<Event> actual = sut.getEventsForDay(new Date(123L), pageSize, pageNumber);

        //then
        assertThat(actual, is(expected));
    }

}
