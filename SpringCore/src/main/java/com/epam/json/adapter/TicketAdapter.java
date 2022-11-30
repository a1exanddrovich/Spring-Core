package com.epam.json.adapter;

import java.lang.reflect.Type;

import com.epam.entity.TicketImpl;
import com.epam.model.Ticket;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class TicketAdapter implements JsonDeserializer<Ticket> {

    public static final String USER_ID = "user_id";
    public static final String EVENT_ID = "event_id";
    public static final String CATEGORY = "category";
    public static final String PLACE = "place";

    @Override
    public Ticket deserialize(JsonElement jsonElement,
                              Type type,
                              JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        return new TicketImpl(object.get(USER_ID).getAsLong(),
                object.get(EVENT_ID).getAsLong(),
                Ticket.Category.valueOf(object.get(CATEGORY).getAsString()),
                object.get(PLACE).getAsInt());
    }

}
