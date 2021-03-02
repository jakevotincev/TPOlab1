package model;

import java.util.ArrayList;
import java.util.Arrays;

public class Galaxy {
    private final String name;
    private  final ArrayList<SpaceBody> spaceBodies = new ArrayList<>();


    public Galaxy(String name, SpaceBody... spaceBodies) {
        this.name = name;
        this.spaceBodies.addAll(Arrays.asList(spaceBodies));
    }

    public String getName() {
        return name;
    }

    public ArrayList<SpaceBody> getSpaceBodies() {
        return spaceBodies;
    }
}
