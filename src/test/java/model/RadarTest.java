package model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
    Field field;
    ArrayList<SpaceBody> foundBodies;
    SpaceBody sun;
    SpaceBody earth;
    SpaceBody moon;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        sun = new SpaceBody("Sun", SpaceBodyType.STAR, Color.YELLOW, 10000);
        earth = new SpaceBody("Earth", SpaceBodyType.PlANET, Color.BLUE, 5000);
        moon = new SpaceBody("Moon", SpaceBodyType.SATELLITE, Color.GRAY, 500);
        radar = new Radar(RadarSize.HUGE);
        System.setOut(new PrintStream(outContent));
        field = radar.getClass().getDeclaredField("foundBodies");
        field.setAccessible(true);
        foundBodies = (ArrayList<SpaceBody>) field.get(radar);
    }

    @After
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
                case VERY_SMALL -> Assert.assertEquals("Ошибка в методе calculateCapacity", 3000, actual);
                case SMALL -> Assert.assertEquals("Ошибка в методе calculateCapacity", 6000, actual);
                case MIDDLE -> Assert.assertEquals("Ошибка в методе calculateCapacity", 9000, actual);
                case BIG -> Assert.assertEquals("Ошибка в методе calculateCapacity", 12000, actual);
                case HUGE -> Assert.assertEquals("Ошибка в методе calculateCapacity", 15000, actual);
            }

        }

    }

    @Test
    public void testZoom() {
        int actualScale = radar.zoom(1);
        Assert.assertEquals("Ошибка в методе zoom", 2, actualScale);
        actualScale = radar.zoom(-1);
        Assert.assertEquals("Ошибка в методе zoom", 3, actualScale);
        actualScale = radar.zoom(-10);
        Assert.assertEquals("Ошибка в методе zoom", 5, actualScale);
        actualScale = radar.zoom(10);
        Assert.assertEquals("Ошибка в методе zoom", 1, actualScale);
    }

    @Test
    public void testShow() throws NoSuchFieldException, IllegalAccessException {
        radar.show();
        Assert.assertTrue("Ошибка в методе show (no objects)", outContent.toString().contains("Объекты не найдены"));
        Field field = radar.getClass().getDeclaredField("foundBodies");
        field.setAccessible(true);
        ArrayList<SpaceBody> foundBodies = new ArrayList<>(Arrays.asList(new SpaceBody("Sun", SpaceBodyType.STAR, Color.YELLOW, 1000),
                new SpaceBody("Earth", SpaceBodyType.PlANET, Color.BLUE, 200)));
        field.set(radar, foundBodies);
        outContent.reset();
        radar.show();
        Assert.assertTrue("Ошибка в методе show ", outContent.toString().toLowerCase().contains("sun"));
        Assert.assertTrue("Ошибка в методе show ", outContent.toString().toLowerCase().contains("earth"));
        Assert.assertTrue("Ошибка в методе show ", outContent.toString().toLowerCase().contains("star"));
        Assert.assertTrue("Ошибка в методе show ", outContent.toString().toLowerCase().toLowerCase().contains("planet"));
        Assert.assertTrue("Ошибка в методе show ", outContent.toString().contains("1000"));
        Assert.assertTrue("Ошибка в методе show ", outContent.toString().contains("200"));
    }

    @Test
    public void testScan() {
        Galaxy testGalaxy = new Galaxy("Млечный путь", sun, earth, moon);
        radar.scan(null);
        Assert.assertTrue("Ошибка в методе scan (null)", outContent.toString().toLowerCase().contains("объект galaxy null"));
        radar.scan(testGalaxy);
        Assert.assertEquals("Ошибка в методе scan (size)", 2, foundBodies.size());
        Assert.assertTrue("Ошибка в методе scan ", foundBodies.contains(sun));
        Assert.assertTrue("Ошибка в методе scan ", foundBodies.contains(earth));
        SpaceBody bigSun = new SpaceBody("Sun", SpaceBodyType.STAR, Color.YELLOW, 20000);
        Galaxy testGalaxy1 = new Galaxy("Млечный путь", bigSun, moon);
        radar.scan(testGalaxy1);
        Assert.assertEquals("Ошибка в методе scan (size)", 1, foundBodies.size());
        Assert.assertTrue("Ошибка в методе scan ", foundBodies.contains(bigSun));
        radar.zoom(-10);
        radar.scan(testGalaxy1);
        Assert.assertEquals("Ошибка в методе scan (size)", 2, foundBodies.size());
    }

    @Test
    public void testScroll() {
        Assert.assertEquals("Ошибка в методе scroll (не вызван метод scan)", 0, radar.scroll(3));
        Galaxy testGalaxy = new Galaxy("Млечный путь", sun, earth, moon);
        radar.scan(testGalaxy);
        radar.scroll(1);
        Assert.assertEquals("Ошибка в методе scroll (size)", 2, foundBodies.size());
        Assert.assertTrue("Ошибка в методе scroll ", foundBodies.contains(earth));
        Assert.assertTrue("Ошибка в методе scroll ", foundBodies.contains(moon));
        radar.scroll(-10);
        Assert.assertEquals("Ошибка в методе scroll (size)", 2, foundBodies.size());
        Assert.assertTrue("Ошибка в методе scroll ", foundBodies.contains(earth));
        Assert.assertTrue("Ошибка в методе scroll ", foundBodies.contains(sun));
        radar.scroll(10);
        Assert.assertEquals("Ошибка в методе scroll (size)", 1, foundBodies.size());
        Assert.assertTrue("Ошибка в методе scroll ", foundBodies.contains(moon));
    }
}
