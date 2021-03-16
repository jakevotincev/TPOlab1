package model;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class RadarTest {
    Radar radar;
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    ArrayList<SpaceBody> foundBodies;
    SpaceBody sun;
    SpaceBody earth;
    SpaceBody moon;

    @BeforeEach
    public void setUp() {
        sun = new SpaceBody("Sun", SpaceBodyType.STAR, Color.YELLOW, 10000);
        earth = new SpaceBody("Earth", SpaceBodyType.PlANET, Color.BLUE, 5000);
        moon = new SpaceBody("Moon", SpaceBodyType.SATELLITE, Color.GRAY, 500);
        radar = new Radar(RadarSize.HUGE);
        System.setOut(new PrintStream(outContent));
        foundBodies = radar.getFoundBodies();
    }

    @AfterEach
    public void tearDown() {
        System.setOut(System.out);
    }

    @Test
    public void testCalculateCapacity() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        for (RadarSize size : RadarSize.values()) {
            Radar radar = new Radar(size);
            Field field = radar.getClass().getDeclaredField("screenCapacity");
            field.setAccessible(true);
            Method method = radar.getClass().getDeclaredMethod("calculateCapacity");
            method.setAccessible(true);
            method.invoke(radar);
            int actual = (int) field.get(radar);
            switch (size) {
                case VERY_SMALL -> Assertions.assertEquals(3000, actual, "Ошибка в методе calculateCapacity");
                case SMALL -> Assertions.assertEquals(6000, actual, "Ошибка в методе calculateCapacity");
                case MIDDLE -> Assertions.assertEquals(9000, actual, "Ошибка в методе calculateCapacity");
                case BIG -> Assertions.assertEquals(12000, actual, "Ошибка в методе calculateCapacity");
                case HUGE -> Assertions.assertEquals(15000, actual, "Ошибка в методе calculateCapacity");
            }

        }

    }

    @Test
    public void testZoom() {
        int actualScale = radar.zoom(1);
        Assertions.assertEquals(2, actualScale, "Ошибка в методе zoom");
        actualScale = radar.zoom(-1);
        Assertions.assertEquals(3, actualScale, "Ошибка в методе zoom");
        actualScale = radar.zoom(-10);
        Assertions.assertEquals(5, actualScale, "Ошибка в методе zoom");
        actualScale = radar.zoom(10);
        Assertions.assertEquals(1, actualScale, "Ошибка в методе zoom");
    }

    @Test
    public void testShow() throws NoSuchFieldException, IllegalAccessException {
        radar.show();
        Assertions.assertTrue(outContent.toString().contains("Объекты не найдены"), "Ошибка в методе show (no objects)");
        Field field = radar.getClass().getDeclaredField("foundBodies");
        field.setAccessible(true);
        ArrayList<SpaceBody> foundBodies = new ArrayList<>(Arrays.asList(new SpaceBody("Sun", SpaceBodyType.STAR, Color.YELLOW, 1000),
                new SpaceBody("Earth", SpaceBodyType.PlANET, Color.BLUE, 200)));
        field.set(radar, foundBodies);
        outContent.reset();
        radar.show();
        Assertions.assertTrue(outContent.toString().toLowerCase().contains("sun"), "Ошибка в методе show ");
        Assertions.assertTrue(outContent.toString().toLowerCase().contains("earth"), "Ошибка в методе show ");
        Assertions.assertTrue(outContent.toString().toLowerCase().contains("star"), "Ошибка в методе show ");
        Assertions.assertTrue(outContent.toString().toLowerCase().toLowerCase().contains("planet"), "Ошибка в методе show ");
        Assertions.assertTrue(outContent.toString().contains("1000"), "Ошибка в методе show ");
        Assertions.assertTrue(outContent.toString().contains("200"), "Ошибка в методе show ");
    }

    @Test
    public void testScan() {
        Galaxy testGalaxy = new Galaxy("Млечный путь", sun, earth, moon);
        radar.scan(null);
        Assertions.assertTrue(outContent.toString().toLowerCase().contains("объект galaxy null"), "Ошибка в методе scan (null)");
        radar.scan(testGalaxy);
        Assertions.assertEquals(2, foundBodies.size(), "Ошибка в методе scan (size)");
        Assertions.assertTrue(foundBodies.contains(sun), "Ошибка в методе scan ");
        Assertions.assertTrue(foundBodies.contains(earth), "Ошибка в методе scan ");
        SpaceBody bigSun = new SpaceBody("Sun", SpaceBodyType.STAR, Color.YELLOW, 20000);
        Galaxy testGalaxy1 = new Galaxy("Млечный путь", bigSun, moon);
        radar.scan(testGalaxy1);
        Assertions.assertEquals(1, foundBodies.size(), "Ошибка в методе scan (size)");
        Assertions.assertTrue(foundBodies.contains(bigSun), "Ошибка в методе scan ");
        radar.zoom(-10);
        radar.scan(testGalaxy1);
        Assertions.assertEquals(2, foundBodies.size(), "Ошибка в методе scan (size)");
    }

    @Test
    public void testScroll() {
        Assertions.assertEquals(0, radar.scroll(3), "Ошибка в методе scroll (не вызван метод scan)");
        Galaxy testGalaxy = new Galaxy("Млечный путь", sun, earth, moon);
        radar.scan(testGalaxy);
        radar.scroll(1);
        Assertions.assertEquals(2, foundBodies.size(), "Ошибка в методе scroll (size)");
        Assertions.assertTrue(foundBodies.contains(earth), "Ошибка в методе scroll ");
        Assertions.assertTrue(foundBodies.contains(moon), "Ошибка в методе scroll ");
        radar.scroll(-10);
        Assertions.assertEquals(2, foundBodies.size(), "Ошибка в методе scroll (size)");
        Assertions.assertTrue(foundBodies.contains(earth), "Ошибка в методе scroll ");
        Assertions.assertTrue(foundBodies.contains(sun), "Ошибка в методе scroll ");
        radar.scroll(10);
        Assertions.assertEquals(1, foundBodies.size(), "Ошибка в методе scroll (size)");
        Assertions.assertTrue(foundBodies.contains(moon), "Ошибка в методе scroll ");
    }
}
