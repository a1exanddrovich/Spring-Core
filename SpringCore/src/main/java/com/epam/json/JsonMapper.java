package com.epam.json;

import com.epam.entity.Identifiable;
import com.epam.json.adapter.EventAdapter;
import com.epam.json.adapter.TicketAdapter;
import com.epam.json.adapter.UserAdapter;
import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonMapper {

    private final Gson delegate;

    private static JsonMapper withTypeAdapters() {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .disableHtmlEscaping()
                .registerTypeAdapter(User.class, new UserAdapter())
                .registerTypeAdapter(Event.class, new EventAdapter())
                .registerTypeAdapter(Ticket.class, new TicketAdapter());

        return new JsonMapper(gsonBuilder.create());
    }

    public JsonMapper(Gson delegate) {
        this.delegate = delegate;
    }

    public JsonMapper() {
        this.delegate = withTypeAdapters().delegate;
    }

    public <T extends Identifiable> T fromJson(String json, Class<T> clazz) {
        return delegate.fromJson(json, clazz);
    }

}
