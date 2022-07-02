package cinema;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CinemaRoomRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaRoomRestApplication.class, args);
    }

    @Bean
    public Cinema cinema(
            @Value("${cinema.rows}") int rows,
            @Value("${cinema.columns}") int columns) {
        return new Cinema(rows, columns);
    }
}
