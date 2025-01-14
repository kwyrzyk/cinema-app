package com.example.database;

import com.example.database.db_classes.Reservation;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationRepositoryTest {

    private static Connection connection;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @BeforeAll
    static void setupDatabase() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;MODE=Oracle;DB_CLOSE_DELAY=-1", "sa", "");
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                CREATE TABLE screening_room (
                    id_room INT PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    num_rows INT NOT NULL,
                    seats_per_row INT NOT NULL
                )
            """);
            

            statement.execute("""
                CREATE TABLE accounts (
                id_account NUMBER, 
                login VARCHAR2(255) NOT NULL,
                password VARCHAR2(255) NOT NULL,
                email VARCHAR2(255) NOT NULL,
                phone_number VARCHAR2(20),
                loyalty_points NUMBER(10, 2) DEFAULT 0,
                balance NUMBER(10, 2) DEFAULT 0
                )
                """
            );


            statement.execute("""
                CREATE TABLE showing (
                id_showing INT PRIMARY KEY,
                id_film INT NOT NULL,              -- Foreign key to the films table
                id_room INT NOT NULL,              -- Foreign key to the screening_room table
                show_time DATETIME NOT NULL,           -- Time of the showing
                end_time DATETIME
                )
            """);


            statement.execute("CREATE SEQUENCE reservation_seq START WITH 1 INCREMENT BY 1");

            statement.execute("""
                CREATE TABLE reservations (
                    reservation_id INT DEFAULT reservation_seq.NEXTVAL PRIMARY KEY,
                    id_room INT NOT NULL,
                    id_account INT NOT NULL,
                    start_time TIMESTAMP NOT NULL,
                    end_time TIMESTAMP NOT NULL,
                    FOREIGN KEY (id_room) REFERENCES screening_room (id_room)
                )
            """);


        }
    }

    @BeforeEach
    void setupTestData() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM reservations");
            statement.execute("DELETE FROM screening_room");

            statement.execute("""
                INSERT INTO screening_room (id_room, name, num_rows, seats_per_row)
                VALUES (1, 'Room A', 10, 20), (2, 'Room B', 8, 25)
            """);
            statement.execute("""
                INSERT INTO reservations (id_room, id_account, start_time, end_time)
                VALUES (1, 1, '2025-01-15 10:00:00', '2025-01-15 12:00:00'),
                       (2, 2, '2025-01-16 14:00:00', '2025-01-16 16:00:00')
            """);

            statement.execute("""
                INSERT INTO accounts (id_account, login, password, email, phone_number) 
            VALUES (1, 'behq', 'behq', 'behq1@example.com', '123456789')
            """);
            
        }
    }

    @Test
        void testReserveIfPossible_NoConflict() {
        LocalDateTime startTime = LocalDateTime.of(2025, 1, 15, 12, 30, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 1, 15, 13, 30, 0);

        boolean success = ReservationRepository.reserve_if_possible(
                1, 1, startTime.format(FORMATTER), endTime.format(FORMATTER), connection);

        assertTrue(success);

        List<Reservation> reservations = ReservationRepository.getAllReservationsByAccountId(1, connection);
        assertEquals(2, reservations.size());

        Reservation reservation = reservations.get(1);
        assertEquals(startTime, reservation.getStarTime());
        assertEquals(endTime, reservation.getEndTime());
    }

    @Test
    void testReserveIfPossible_WithConflict() {
        LocalDateTime startTime = LocalDateTime.of(2025, 1, 15, 11, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 1, 15, 11, 30, 0);

        boolean success = ReservationRepository.reserve_if_possible(
                1, 3, startTime.format(FORMATTER), endTime.format(FORMATTER), connection);

        assertFalse(success);

        List<Reservation> reservations = ReservationRepository.getAllReservationsByAccountId(3, connection);
        assertTrue(reservations.isEmpty());
    }

    @AfterAll
    static void tearDownDatabase() throws SQLException {
        connection.close();
    }
}
