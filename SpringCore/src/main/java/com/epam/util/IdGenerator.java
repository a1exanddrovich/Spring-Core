package com.epam.util;

import com.epam.entity.EventImpl;
import com.epam.entity.Identifiable;
import com.epam.entity.TicketImpl;
import com.epam.entity.UserImpl;

public class IdGenerator {

    private static long USER_COUNTER = 1;
    private static long TICKET_COUNTER = 1;
    private static long EVENT_COUNTER = 1;

    public long generateId(Class<? extends Identifiable> clazz) {
        if (clazz.equals(UserImpl.class)) {
            return USER_COUNTER++;
        }
        if (clazz.equals(TicketImpl.class)) {
            return TICKET_COUNTER++;
        }
        if (clazz.equals(EventImpl.class)) {
            return EVENT_COUNTER++;
        }

        return 0;
    }

}
