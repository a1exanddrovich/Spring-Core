package com.epam.util;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.epam.entity.UserImpl;

class PaginatorTest {

    private final Paginator<UserImpl> sut = new Paginator<>();

    @Test
    void shouldPaginateEntities() {
        //given
        List<UserImpl> users = Arrays.asList(new UserImpl("Alex1", "Alex322@mail.ru"),
                new UserImpl("Alex2", "Alex322@mail.ru"),
                new UserImpl("Alex3", "Alex322@mail.ru"),
                new UserImpl("Alex4", "Alex322@mail.ru"),
                new UserImpl("Alex5", "Alex322@mail.ru"),
                new UserImpl("Alex6", "Alex322@mail.ru"),
                new UserImpl("Alex7", "Alex322@mail.ru"),
                new UserImpl("Alex8", "Alex322@mail.ru"),
                new UserImpl("Alex9", "Alex322@mail.ru"),
                new UserImpl("Alex10", "Alex322@mail.ru"));

        List<UserImpl> expected1 = Arrays.asList(new UserImpl("Alex4", "Alex322@mail.ru"),
                new UserImpl("Alex5", "Alex322@mail.ru"),
                new UserImpl("Alex6", "Alex322@mail.ru"));

        List<UserImpl> expected2 = Arrays.asList(new UserImpl("Alex10", "Alex322@mail.ru"));

        List<UserImpl> expected3 = Arrays.asList(new UserImpl("Alex7", "Alex322@mail.ru"),
                new UserImpl("Alex8", "Alex322@mail.ru"),
                new UserImpl("Alex9", "Alex322@mail.ru"),
                new UserImpl("Alex10", "Alex322@mail.ru"));


        //when
        List<UserImpl> actual1 = sut.paginate(users, 3, 2);
        List<UserImpl> actual2 = sut.paginate(users, 3, 4);
        List<UserImpl> actual3 = sut.paginate(users, 6, 2);
        List<UserImpl> actual4 = sut.paginate(users, 6, 3);

        //then
        assertThat(actual1, is(expected1));
        assertThat(actual2, is(expected2));
        assertThat(actual3, is(expected3));
        assertThat(actual4, is(Collections.emptyList()));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenInvalidPageSizePassed() {
        assertThrows(IllegalArgumentException.class, () -> sut.paginate(Collections.emptyList(), 0, 10));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenInvalidPageNumberPassed() {
        assertThrows(IllegalArgumentException.class, () -> sut.paginate(Collections.emptyList(), 2, -2));
    }



}
