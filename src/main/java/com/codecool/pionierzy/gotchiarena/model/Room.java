package com.codecool.pionierzy.gotchiarena.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    @NotEmpty(message = "Please provide a room name")
    private String name;

    @JoinColumn(name = "owner", nullable = false)
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    private User owner;

    @JoinColumn(name = "opponent")
    @OneToOne(fetch = FetchType.EAGER)
    private User opponent;

    public Room() {}

    public Room(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOwnerName() {
        return owner.getUsername();
    }

    public String getOpponentName() {
        return opponent != null ? opponent.getUsername() : null;
    }

    public int getCount() {
        return opponent != null ? 2 : 1;
    }

    public boolean joinRoom(User user) {
        if (opponent == null && !owner.equals(user)) {
            opponent = user;
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id) &&
                Objects.equals(name, room.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return String.format("Room: %s (%d/2). Owner: %s", name, getCount(), owner.getUsername());
    }
}
