import com.raijin.serializer.Serializer;
import com.raijin.serializer.model.Link;

import com.raijin.serializer.model.TestClass;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {

        testSerialization(new Link(null, null, null));
        testSerialization(new Link("someFullUrl", "asfdaf1", new Link.ExpiryDate(1, 1)));
        testSerialization(new Link("someFullUrl", "asfdaf2", new Link.ExpiryDate(10, 2)));
        testSerialization(new Link("someFullUrl", null, new Link.ExpiryDate(10, 3)));
        testSerialization(new Link(null, "asfdaf4", new Link.ExpiryDate(10, 4)));
        testSerialization(new Link("someFullUrl", "asfdaf5", null));
        testSerialization(new TestClass("DIMA", 111, Math.PI, new TestClass.Cutee(15415, "QUERY")));
        System.out.println("All tests is OK!");

    }

    private static void testSerialization(final Object o) throws InvocationTargetException, IllegalAccessException {
        final Map<String, Object> json = Serializer.serialize(o);
        System.out.println(json);
    }
}
