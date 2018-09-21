package com.codecool.pionierzy.gotchiarena.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @JoinColumn(name = "owner", nullable = false)
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    private User owner;

    @JoinColumn(name = "opponent")
    @OneToOne(fetch = FetchType.EAGER)
    private User opponent;

    @JoinColumn(name = "ownerGotchi")
    @OneToOne(fetch = FetchType.EAGER)
    private Gotchi ownerGotchi;

    @JoinColumn(name = "opponentGotchi")
    @OneToOne(fetch = FetchType.EAGER)
    private Gotchi opponentGotchi;



    public Room() {}

    public Room(User owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public Gotchi getOwnerGotchi() {
        return ownerGotchi;
    }

    public Gotchi getOpponentGotchi() {
        return opponentGotchi;
    }

    public void setOwnerGotchi(Gotchi ownerGotchi) {
        this.ownerGotchi = ownerGotchi;
    }

    public void setOpponentGotchi(Gotchi opponentGotchi) {
        this.opponentGotchi = opponentGotchi;
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

    public boolean setOpponent(User opponent) {
        if (opponent == null || (this.opponent == null && !owner.equals(opponent))) {
            this.opponent = opponent;
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Room: %d (%d/2). Owner: %s", id, getCount(), owner.getUsername());
    }

    public User getOwner() {
        return owner;
    }

    public User getOpponent() {
        return opponent;
    }
}
