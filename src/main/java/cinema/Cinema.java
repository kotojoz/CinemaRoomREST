package cinema;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Cinema {

    @JsonIgnore
    @Value("${cinema.password}")
    private String PASSWORD;

    private final int total_rows;

    private final int total_columns;

    public final List<Ticket> available_seats = new ArrayList<>();

    @JsonIgnore
    private int current_income = 0;

    @JsonIgnore
    private int number_of_available_seats;

    @JsonIgnore
    private int number_of_purchased_tickets = 0;


    public Cinema(int total_rows, int total_columns) {
        this.total_rows = total_rows;
        this.total_columns = total_columns;
        this.number_of_available_seats = total_columns * total_rows;
        setSeats();
    }

    public int getTotal_rows() {
        return total_rows;
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public List<Ticket> getAvailable_seats() {
        return available_seats.stream().filter(Ticket::isAvailable).collect(Collectors.toList());
    }

    public Ticket getTicket(Ticket ticket) {
        return available_seats.stream().filter(x -> x.equals(ticket)).findFirst().orElse(null);
    }

    public Ticket getTicket(String uuid) {
        return available_seats.stream().
                filter(ticket -> ticket.getToken().toString().equals(uuid)).findFirst().orElse(null);
    }

    public Map<String, Object> purchaseTicket(Ticket ticket) {
        Ticket responseTicket = getTicket(ticket);
        if (!responseTicket.isAvailable()) {
            throw new SoldOutException();
        } else {
            responseTicket.setAvailable(false);
            current_income += responseTicket.getPrice();
            number_of_purchased_tickets++;
            number_of_available_seats--;
            return Map.of("token", responseTicket.getToken(), "ticket", responseTicket);
        }
    }

    public Map<String, Object> returnTicket(Ticket ticket) {
        Ticket responseTicket = getTicket(ticket);
        responseTicket.setAvailable(true);
        current_income -= responseTicket.getPrice();
        number_of_purchased_tickets--;
        number_of_available_seats++;
        return Map.of("returned_ticket", responseTicket);
    }

    private void setSeats() {
        for (int i = 1; i <= total_rows; i++) {
            for (int j = 1; j <= total_columns; j++) {
                available_seats.add(new Ticket(i, j));
            }
        }
    }

    public Map<String, Integer> getStats(String password) {
        if (PASSWORD.equals(password)) {
            return Map.of("current_income", current_income,
                    "number_of_available_seats", number_of_available_seats,
                    "number_of_purchased_tickets", number_of_purchased_tickets);
        } else {
            throw new BadPasswordException();
        }
    }

}
