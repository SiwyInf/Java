package zad3;

public record Point2D(double x, double y) {

    public double distanceTo(Point2D otherPoint){
        double deltaX = otherPoint.x() - this.x();
        double deltaY = otherPoint.y() - this.y();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}
