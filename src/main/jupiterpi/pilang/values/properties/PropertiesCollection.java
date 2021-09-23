package jupiterpi.pilang.values.properties;

import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;
import jupiterpi.pilang.values.properties.collections.BoolProperties;
import jupiterpi.pilang.values.properties.collections.CharProperties;
import jupiterpi.pilang.values.properties.collections.FloatProperties;
import jupiterpi.pilang.values.properties.collections.IntProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public abstract class PropertiesCollection {
    private List<Property> properties = new ArrayList<>();

    protected void addProperty(String propertyName, DataType requiredType, DataType returnType, BiFunction<Value, Scope, Value> definition) {
        properties.add(new Property(propertyName, requiredType, definition, returnType));
    }

    private DataType defaultRequiredType;
    protected void setDefaultRequiredType(DataType defaultRequiredType) {
        this.defaultRequiredType = defaultRequiredType;
    }
    protected void addProperty(String propertyName, DataType returnType, BiFunction<Value, Scope, Value> definition) {
        addProperty(propertyName, defaultRequiredType, returnType, definition);
    }

    public List<Property> getProperties() {
        return properties;
    }

    /* collections */

    private static List<PropertiesCollection> collections;

    static {
        collections = new ArrayList<>();
        collections.add(new IntProperties());
        collections.add(new FloatProperties());
        collections.add(new BoolProperties());
        collections.add(new CharProperties());
    }

    public static Property getProperty(String propertyName, DataType sourceType) {
        for (PropertiesCollection collection : collections) {
            for (Property property : collection.getProperties()) {
                if (property.getPropertyName().equals(propertyName)) {
                    if (property.suitsType(sourceType)) {
                        return property;
                    }
                }
            }
        }
        new Exception(String.format("cannot find property %s for values of type %s", propertyName, sourceType)).printStackTrace();
        return null;
    }
}
