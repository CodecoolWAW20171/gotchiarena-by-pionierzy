package com.codecool.pionierzy.gotchiarena.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "gotchi")
public class Gotchi {

    private static Map<AttackType, AttackType> strongAgainst;
    private static Map<AttackType, AttackType> weakAgainst;

    private static final double STRONG_MODIFIER = 1.25;
    private static final double WEAK_MODIFIER = 0.75;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "userId", updatable = false, unique = true, nullable = false)
    private Long userId;

    @Column(name = "name", updatable = false, unique = true, nullable = false)
    @NotEmpty(message = "Please provide name")
    private String name;

    @Column(name = "speed", updatable = false, unique = true, nullable = false)
    private int speed;

    @Column(name = "defence", updatable = false, unique = true, nullable = false)
    private int defence;

    @Column(name = "attack", updatable = false, unique = true, nullable = false)
    private int attack;

    @Column(name = "health", unique = true, nullable = false)
    private int health;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", updatable = false, unique = true, nullable = false)
    private AttackType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "secondaryAttack", updatable = false, unique = true, nullable = false)
    private AttackType secondaryAttack;



    static {
        strongAgainst = new HashMap<>();
        weakAgainst = new HashMap<>();

        strongAgainst.put(AttackType.LIGHTNING, AttackType.PLANT);
        strongAgainst.put(AttackType.PLANT, AttackType.EARTH);
        strongAgainst.put(AttackType.EARTH, AttackType.LIGHTNING);
        strongAgainst.put(AttackType.FIRE, AttackType.ICE);
        strongAgainst.put(AttackType.ICE, AttackType.WATER);
        strongAgainst.put(AttackType.WATER, AttackType.FIRE);
        for (Map.Entry<AttackType, AttackType> entry : strongAgainst.entrySet()) {
            AttackType strong = entry.getKey();
            AttackType weak = entry.getValue();
            weakAgainst.put(weak, strong);
        }
    }

    @Transient

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Gotchi)) return false;
        Gotchi other = (Gotchi) obj;
        if (!other.name.equals(this.name)) return false;
        return other.id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }



    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public AttackType getType() {
        return type;
    }

    public void setType(AttackType type) {
        this.type = type;
    }

    public AttackType getSecondaryAttack() {
        return secondaryAttack;
    }

    public void setSecondaryAttack(AttackType secondaryAttack) {
        this.secondaryAttack = secondaryAttack;
    }

    public void attack(Gotchi gotchi) {
        AttackType opponentType = gotchi.getType();
        if (opponentType == strongAgainst.get(this.type)) {
            gotchi.setHealth(gotchi.getHealth() - attack);
        }
    }
}
