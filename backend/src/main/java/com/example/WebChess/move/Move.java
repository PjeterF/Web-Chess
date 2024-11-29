package com.example.WebChess.move;

import com.example.WebChess.game.Game;
import jakarta.persistence.*;

@Entity
@Table
public class Move {
    @Id
    @SequenceGenerator(
            name="move_sequence",
            sequenceName = "move_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "move_sequence"
    )
    private Long id;
    private String notation;
    private Integer moveNumber;
    @ManyToOne
    private Game game;

    public Move(String notation, Game game) {
        this.notation = notation;
        this.moveNumber = game.getMoves().size();
        this.game=game;
    }

    public Move() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotation() {
        return notation;
    }

    public void setNotation(String notation) {
        this.notation = notation;
    }

    public Integer getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(Integer moveNumber) {
        this.moveNumber = moveNumber;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "Move{" +
                "id=" + id +
                ", notation='" + notation + '\'' +
                ", order=" + moveNumber +
                '}';
    }
}
