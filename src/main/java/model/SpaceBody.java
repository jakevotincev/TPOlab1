package model;

import java.awt.*;

public class SpaceBody {
    private final String name;
    private final SpaceBodyType type;
    private final Color color;
    private final int size;

    public SpaceBody(String name, SpaceBodyType type, Color color, int size) {
        this.name = name;
        this.type = type;
        this.color = color;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "SpaceBody{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", color=" + color +
                ", size=" + size +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpaceBody spaceBody = (SpaceBody) o;

        if (size != spaceBody.size) return false;
        if (!name.equals(spaceBody.name)) return false;
        if (type != spaceBody.type) return false;
        return color.equals(spaceBody.color);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + color.hashCode();
        result = 31 * result + size;
        return result;
    }
}
