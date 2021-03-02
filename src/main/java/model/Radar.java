package model;

import java.util.ArrayList;

public class Radar {
    private final RadarSize size;
    private int screenCapacity;
    private int scale = 3;
    private final int MIN_SCALE = 1;
    private final int MAX_SCALE = 5;
    private int position = 0;
    private Galaxy currentGalaxy;
    private ArrayList<SpaceBody> foundBodies = new ArrayList<>();


    public RadarSize getSize() {
        return size;
    }

    public int getMIN_SCALE() {
        return MIN_SCALE;
    }

    public int getMAX_SCALE() {
        return MAX_SCALE;
    }

    public Radar(RadarSize size) {
        this.size = size;
        calculateCapacity();

    }

    public int getPosition() {
        return position;
    }

    public void scan(Galaxy galaxy) {
        this.currentGalaxy = galaxy;
        ArrayList<SpaceBody> spaceBodies = galaxy.getSpaceBodies();
        foundBodies.clear();
        int capacity = screenCapacity;
        if (position < spaceBodies.size())
            for (int i = position; i < spaceBodies.size(); i++) {
                SpaceBody spaceBody = spaceBodies.get(i);
                if (spaceBody.getSize() < capacity) foundBodies.add(spaceBody);
                else {
                    foundBodies.add(spaceBody);
                    break;
                }
                capacity -= spaceBody.getSize();
            }
        show();
    }

    public void show() {
        if (foundBodies.size() != 0) foundBodies.forEach(System.out::println);
        else System.out.println("Объекты не найдены");
    }

    public int scroll(int value) {
        if (currentGalaxy != null) {
            int maxPosition = currentGalaxy.getSpaceBodies().size() - 1;
            position += value;
            if (position > maxPosition) position = maxPosition;
            if (position < 0) position = 0;
            scan(currentGalaxy);
        }
        return position;
    }

    public int zoom(int scale) {
        this.scale += -scale;
        if (this.scale > MAX_SCALE) this.scale = MAX_SCALE;
        if (this.scale < MIN_SCALE) this.scale = MIN_SCALE;
        calculateCapacity();
        return this.scale;
    }

    private void calculateCapacity() {
        switch (size) {
            case VERY_SMALL -> screenCapacity = scale * 1000;
            case SMALL -> screenCapacity = scale * 2000;
            case MIDDLE -> screenCapacity = scale * 3000;
            case BIG -> screenCapacity = scale * 4000;
            case HUGE -> screenCapacity = scale * 5000;
        }
    }
}

