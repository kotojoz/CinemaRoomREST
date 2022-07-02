package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public class Ticket {

    @JsonIgnore
    private final UUID token;

    private final int row;

    private final int column;

    private int price;

    @JsonIgnore
    private boolean available;


    public Ticket(int row, int column) {
        this.row = row;
        this.column = column;
        this.available = true;
        this.token = UUID.randomUUID();
        setPrice();
    }

    public int getRow() {
        return row;
    }

    public UUID getToken() {
        return token;
    }

    public int getColumn() {
        return column;
    }

    public int getPrice() {
        return price;
    }

    private void setPrice() {
        this.price = row <= 4 ? 10 : 8;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (row != ticket.row) return false;
        return column == ticket.column;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        return result;
    }
}
