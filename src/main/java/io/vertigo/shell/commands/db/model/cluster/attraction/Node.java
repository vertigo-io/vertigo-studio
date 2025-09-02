package io.vertigo.shell.commands.db.model.cluster.attraction;

import io.vertigo.core.lang.Assertion;

public class Node {
    private final String id;
    private double x, y; // Position
    private double vx, vy; // Velocity
    private double mass; // Represents table size or importance

    public Node(String id, double x, double y, double mass) {
        Assertion.check().isNotBlank(id);
        Assertion.check().isTrue(mass > 0, "Mass must be positive");
        this.id = id;
        this.x = x;
        this.y = y;
        this.mass = mass;
        this.vx = 0;
        this.vy = 0;
    }

    public String getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public double getMass() {
        return mass;
    }

    public void applyForce(double fx, double fy) {
        // F = ma => a = F/m
        // Update velocity based on acceleration
        this.vx += fx / mass;
        this.vy += fy / mass;
    }

    public void updatePosition() {
        this.x += this.vx;
        this.y += this.vy;
    }

    public void applyDamping(double dampingFactor) {
        this.vx *= dampingFactor;
        this.vy *= dampingFactor;
    }
}
