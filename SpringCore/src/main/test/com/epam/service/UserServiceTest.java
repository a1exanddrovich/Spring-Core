package com.epam.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dao.UserDao;
import com.epam.entity.UserImpl;
import com.epam.model.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDao dao;
    @InjectMocks
    private UserService sut;

    @Test
    void shouldReturnUserWhenExistIdPassed() {
        //given
        Optional<User> expected = Optional.of(new UserImpl("Alex", "Alex322@mail.com"));
        when(dao.findById(1)).thenReturn(expected);

        //when
        Optional<User> actual = sut.getById(1);

        //then
        assertThat(actual, is(expected));
    }

    @Test
    void shouldReturnEmptyWhenNotExistIdPassed() {
        //given
        when(dao.findById(anyLong())).thenReturn(Optional.empty());

        //when
        Optional<User> actual = sut.getById(10);

        //then
        assertThat(actual, is(Optional.empty()));
    }

    @Test
    void shouldReturnUserWhenExistEmailPassed() {
        //given
        Optional<User> expected = Optional.of(new UserImpl("Alex", "Alex322@mail.com"));
        when(dao.findByEmail("Alex322@mail.com")).thenReturn(expected);

        //when
        Optional<User> actual = sut.getByEmail("Alex322@mail.com");

        //then
        assertThat(actual, is(expected));
    }

    @Test
    void shouldReturnEmptyWhenNotExistEmailPassed() {
        //given
        when(dao.findByEmail(anyString())).thenReturn(Optional.empty());

        //when
        Optional<User> actual = sut.getByEmail("test");

        //then
        assertThat(actual, is(Optional.empty()));
    }

    @Test
    void shouldDeleteUserWhenExistIdPassed() {
        //given
        when(dao.deleteById(1)).thenReturn(true);

        //when
        boolean actual = sut.delete(1);

        //then
        assertTrue(actual);
    }

    @Test
    void shouldNotDeleteUserWhenNotExistIdPassed() {
        //given
        when(dao.deleteById(anyLong())).thenReturn(false);

        //when
        boolean actual = sut.delete(10);

        //then
        assertFalse(actual);
    }

    @Test
    void shouldUpdateUserWhenExistIdPassed() {
        //given
        Optional<User> user = Optional.of(new UserImpl("Alex", "Alex322@mail.com"));
        Optional<User> expected = Optional.of(new UserImpl("AlexUpdated", "Alex322@mail.com"));
        when(dao.update(user.get())).thenReturn(expected);

        //when
        Optional<User> actual = sut.update(user.get());

        //then
        assertThat(actual, is(expected));
    }

    @Test
    void shouldReturnEmptyUserWhenNotExistIdPassedWhileUpdating() {
        //given
        Optional<User> user = Optional.of(new UserImpl( "Alex", "Alex322@mail.com"));
        when(dao.update(any(User.class))).thenReturn(Optional.empty());

        //when
        Optional<User> actual = sut.update(user.get());

        //then
        assertThat(actual, is(Optional.empty()));
    }

    @Test
    void shouldCreateUser() {
        //given
        Optional<User> user = Optional.of(new UserImpl( "Alex", "Alex322@mail.com"));
        User expected = new UserImpl( "Alex", "Alex322@mail.com");
        when(dao.create(user.get())).thenReturn(expected);

        //when
        User actual = sut.create(user.get());

        //then
        assertThat(actual, is(expected));
    }

    @Test
    void shouldReturnNonEmptyUsers() {
        //given
        int pageSize = 5;
        int pageNumber = 2;
        String name = "Alex";
        List<User> expected = Arrays.asList(new UserImpl("Alex", "Alex322@mail.com"),
                new UserImpl("Alex21", "Alex@mail.ru"),
                new UserImpl( "Alex34", "Mimir@gmail.eu"));
        when(dao.getByName(name, pageSize, pageNumber)).thenReturn(expected);

        //when
        List<User> actual = sut.getByName(name, pageSize, pageNumber);

        //then
        assertThat(actual, is(expected));
    }

}
