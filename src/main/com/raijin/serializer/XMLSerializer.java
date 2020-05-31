package com.raijin.serializer;


import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

public class XMLSerializer extends Serializer {

    private String OPEN = "<";
    private String OPEN_CLOSE = ">";
    private String CLOSE_OPEN = "</";
    private String CLOSE_CLOSE = "/>";

    private Map<String, Object> C;

    public XMLSerializer(Object object) throws InvocationTargetException, IllegalAccessException {
        super();
        this.C = serialize(object);
    }


    public String build() {
        return buildElement(C);
    }


    private String buildElement(Map<String, Object> map) {

        StringBuilder builder = new StringBuilder();
        for (String key: map.keySet()) {
            if (map.get(key) instanceof Map) {
                builder.append(openElement(key)).append(buildElement((Map<String, Object>) map.get(key))).append(closeElement(key));
            } else {
                builder.append(openElement(key)).append(map.get(key)).append(closeElement(key));
            }
        }
        return builder.toString();
    }

    private String openElement(String s) {
        return OPEN + s + OPEN_CLOSE;
    }

    private String singleElement(String s) {
        return OPEN + s + CLOSE_CLOSE;
    }

    private String closeElement(String s) {
        return CLOSE_OPEN + s + OPEN_CLOSE;
    }
}
