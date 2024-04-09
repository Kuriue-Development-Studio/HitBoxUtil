### PolygonDetectorBuilder
```java
public PlolygonCollisionDetector AttackPolygon(Player p) {
    Location loc = p.getLocation();
    PolygonDetectorBuilder builder;
    builder = new PolygonDetectorBuilder();
    builder.addPoint(loc.toVector(5, 0, 5))
            .addPoint(5, 0, -5)
            .addPoint(-5, 0, -5)
            .addPoint(-5, 0, 5);
    return builder.build();
}
```

### HitBoxBuilder
```java
public void createHitBox(Player p) {
    HitBoxBuilder builder;
    builder = new HitBoxBuilder();
    builder.setOwner(p);
    builder.setTick(20);
    builder.addPolygonDetector(AttackPolygon(p));
    builder.setLocation(p.getLocation());
    builder.build(HitBox.class);
}
```