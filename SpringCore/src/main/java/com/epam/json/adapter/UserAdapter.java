package com.epam.json.adapter;

import java.lang.reflect.Type;
import java.util.Objects;

import com.epam.entity.UserImpl;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class UserAdapter implements JsonDeserializer<UserImpl> {

    private static final String NAME = "name";
    private static final String EMAIL = "email";

    @Override
    public UserImpl deserialize(JsonElement jsonElement,
                                Type type,
                                JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        return new UserImpl(object.get(NAME).getAsString(),
                            object.get(EMAIL).getAsString());
    }

}
