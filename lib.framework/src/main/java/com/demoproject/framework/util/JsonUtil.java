package com.demoproject.framework.util;

import com.google.gson.GsonBuilder;
import java.io.Reader;
import java.lang.reflect.Type;
import com.google.gson.Gson;

public class JsonUtil {

    private static final Gson gson;

    public static <T> String serialize(final T src) {
        return JsonUtil.gson.toJson((Object) src);
    }

    public static <T> T deserialize(final String json, final Class<T> classOfT) {
        return (T) JsonUtil.gson.fromJson(json, (Class) classOfT);
    }

    public static <T> T deserialize(final String json, final Type typeOfT) {
        return (T) JsonUtil.gson.fromJson(json, typeOfT);
    }

    public static <T> T deserialize(final Reader json, final Type typeOfT) {
        return (T) JsonUtil.gson.fromJson(json, typeOfT);
    }

    static {
        gson = new GsonBuilder().serializeNulls().create();
    }
}
