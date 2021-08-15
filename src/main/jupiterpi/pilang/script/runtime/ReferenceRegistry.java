package jupiterpi.pilang.script.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ReferenceRegistry {
    /* functions registry */

    private Map<String, Function> functions = new HashMap<>();

    public String registerFunction(Function function) {
        String id = UUID.randomUUID().toString();
        functions.put(id, function);

        return id;
    }

    public Function findFunction(String id) {
        return functions.get(id);
    }
}
