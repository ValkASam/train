package pattern.bridge;

/**
 * Created by Valk on 07.01.16.
 */
public class BridgeTest {
    public static void main (String [] args){
        Shape [] shapes = {
                new Circle(5,10,10, new LargeCircleDrawer()),
                new Circle(20,30,100, new SmallCircleDrawer())};

        for (Shape next : shapes){
            next.draw();
        }
    }
}

interface Drawer {
    void drawCircle(int x, int y, int radius);

}

class SmallCircleDrawer implements Drawer{
    public static final double radiusMultiplier = 0.25;

    @Override
    public void drawCircle(int x, int y, int radius) {
        System.out.println("Small circle center = " + x + "," + y + " radius = " + radius*radiusMultiplier);
    }
}

class LargeCircleDrawer implements Drawer{
    public static final int radiusMultiplier = 10;

    @Override
    public void drawCircle(int x, int y, int radius) {
        System.out.println("Large circle center = " + x + "," + y + " radius = " + radius*radiusMultiplier);
    }
}

abstract class Shape {
    protected Drawer drawer;

    protected Shape(Drawer drawer){
        this.drawer = drawer;
    }

    public abstract void draw();
}

class Circle extends Shape{
    private int x;
    private int y;
    private int radius;

    public Circle(int x, int y, int radius, Drawer drawer) {
        super(drawer);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void draw() {
        drawer.drawCircle(x, y, radius);
    }
}

