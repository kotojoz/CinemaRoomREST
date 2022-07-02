package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CinemaController {
    private final Cinema cinema;

    public CinemaController(Cinema cinema) {
        this.cinema = cinema;
    }

    @GetMapping("/seats")
    public Cinema showAvailableTickets() {
        return cinema;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> buyTicket(@RequestBody Ticket ticket) {
        if (ticket.getRow() > cinema.getTotal_rows() || ticket.getRow() <= 0 ||
                ticket.getColumn() > cinema.getTotal_columns() || ticket.getColumn() <= 0) {
            throw new OutOfBoundsException();
        }
        if (cinema.getTicket(ticket) != null) {
            return new ResponseEntity<>(cinema.purchaseTicket(ticket), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnTicket(@RequestBody Map<String, String> token) {
        Ticket ticket = cinema.getTicket(token.get("token"));
        if (ticket == null) {
            throw new BadTokenException();
        } else {
            return new ResponseEntity<>(cinema.returnTicket(ticket), HttpStatus.OK);
        }
    }

    @PostMapping("/stats")
    public ResponseEntity<?> getStats(@RequestParam(required = false) String password) {
            return new ResponseEntity<>(cinema.getStats(password), HttpStatus.OK);
    }
}
