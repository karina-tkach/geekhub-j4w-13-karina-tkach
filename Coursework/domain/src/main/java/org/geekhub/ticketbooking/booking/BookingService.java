package org.geekhub.ticketbooking.booking;

import jakarta.mail.MessagingException;
import org.geekhub.ticketbooking.exception.BookingValidationException;
import org.geekhub.ticketbooking.mail.MailSenderService;
import org.geekhub.ticketbooking.show.Show;
import org.geekhub.ticketbooking.show.ShowService;
import org.geekhub.ticketbooking.show_seat.ShowSeat;
import org.geekhub.ticketbooking.show_seat.ShowSeatService;
import org.geekhub.ticketbooking.user.User;
import org.geekhub.ticketbooking.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

@Service
@SuppressWarnings("java:S1192")
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingValidator bookingValidator;
    private final UserService userService;
    private final ShowSeatService showSeatService;
    private final ShowService showService;
    private final MailSenderService mailSenderService;
    private final Logger logger = LoggerFactory.getLogger(BookingService.class);

    public BookingService(BookingRepository bookingRepository, BookingValidator bookingValidator,
                          UserService userService, ShowSeatService showSeatService,
                          ShowService showService, MailSenderService mailSenderService) {
        this.bookingRepository = bookingRepository;
        this.bookingValidator = bookingValidator;
        this.userService = userService;
        this.showSeatService = showSeatService;
        this.showService = showService;
        this.mailSenderService = mailSenderService;
    }

    public Booking getBookingById(int bookingId) {
        try {
            logger.info("Try to get booking by id");
            Booking booking = bookingRepository.getBookingById(bookingId);
            logger.info("Booking was fetched successfully");
            return booking;
        } catch (DataAccessException exception) {
            logger.warn("Booking wasn't fetched\n{}", exception.getMessage());
            return null;
        }
    }

    public List<Booking> getBookingsWithPagination(int pageNumber, int limit) {
        try {
            if (pageNumber < 0 || limit < 0) {
                throw new IllegalArgumentException("Page number and limit must be greater than 0");
            }
            logger.info("Try to get bookings with pagination");
            List<Booking> bookings = bookingRepository.getBookingsWithPagination(pageNumber, limit);
            logger.info("Bookings were fetched with pagination successfully");
            return bookings;
        } catch (IllegalArgumentException | DataAccessException exception) {
            logger.warn("Bookings weren't fetched with pagination\n{}", exception.getMessage());
            return Collections.emptyList();
        }
    }

    public int getBookingsRowsCount() {
        try {
            logger.info("Try to get bookings rows count");
            int count = bookingRepository.getBookingsRowsCount();
            logger.info("Bookings rows count were fetched successfully");
            return count;
        } catch (DataAccessException | NullPointerException exception) {
            logger.warn("Bookings rows count weren't fetched\n{}", exception.getMessage());
            return -1;
        }
    }

    public Booking addBooking(Booking booking) {
        try {
            logger.info("Try to add booking");
            validateBooking(booking);

            int id = bookingRepository.addBooking(booking);
            if (id == -1) {
                throw new BookingValidationException("Unable to retrieve the generated key");
            }

            ShowSeat seat = showSeatService.getSeatById(booking.getSeatId());
            seat.setBooked(true);
            showSeatService.updateSeatById(seat, seat.getId(), seat.getHallId(), seat.getShowId());

            String emailContent = createEmailContent(booking);
            mailSenderService.sendEmail(booking.getUser().getEmail(), "Cinema Ticket", emailContent);

            logger.info("Booking was added:\n{}", booking);
            return getBookingById(id);
        } catch (BookingValidationException | DataAccessException exception) {
            logger.warn("Booking wasn't added: {}\n{}", booking, exception.getMessage());
            return null;
        } catch (MessagingException | UnsupportedEncodingException e) {
            return null;
        }
    }

    public void validateBooking(Booking booking) {
        bookingValidator.validate(booking);
        User user = userService.getUserById(booking.getUser().getId());
        Show show = showService.getShowById(booking.getShowId());
        ShowSeat seat = showSeatService.getSeatById(booking.getSeatId());

        if (user == null || show == null || seat == null || seat.isBooked()) {
            throw new BookingValidationException("User/show/seat of booking is incorrect");
        }
    }

    public boolean deleteBookingById(int bookingId) {
        Booking bookingToDel = getBookingById(bookingId);
        try {
            logger.info("Try to delete booking");
            if (bookingToDel == null) {
                throw new BookingValidationException("Booking with id '" + bookingId + "' not found");
            }

            bookingRepository.deleteBookingById(bookingId);

            ShowSeat seat = showSeatService.getSeatById(bookingToDel.getSeatId());
            seat.setBooked(false);

            showSeatService.updateSeatById(seat, seat.getId(), seat.getHallId(), seat.getShowId());
            logger.info("Booking was deleted:\n{}", bookingToDel);
            return true;
        } catch (BookingValidationException | DataAccessException exception) {
            logger.warn("Booking wasn't deleted: {}\n{}", bookingToDel, exception.getMessage());
            return false;
        }
    }

    private String createEmailContent(Booking booking) {
        User user = booking.getUser();
        return "<h1 style=\"text-align: center; color: black; margin-top: 20px;\">Booking Details</h1>" +
            "<table style=\"border-collapse: collapse; border: 2px solid black; margin: 0 auto; width: 400px;\">" +
            "<tr>" +
            "<th style=\"background-color: grey; padding: 10px; border-bottom: 1px solid black; border-right: 1px solid black; border-left: 1px solid black; width: 120px; text-align: left;\">Field</th>" +
            "<th style=\"padding: 10px; border-bottom: 1px solid black; border-right: 1px solid black; border-left: 1px solid black; width: 280px; text-align: left;\">Value</th>" +
            "</tr>" +
            "<tr>" +
            "<td style=\"padding: 10px; border-bottom: 1px solid black; border-right: 1px solid black; border-left: 1px solid black;\">First Name</td>" +
            "<td style=\"padding: 10px; border-bottom: 1px solid black; border-right: 1px solid black;\">" + user.getFirstName() + "</td>" +
            "</tr>" +
            "<tr>" +
            "<td style=\"padding: 10px; border-bottom: 1px solid black; border-right: 1px solid black; border-left: 1px solid black;\">Last Name</td>" +
            "<td style=\"padding: 10px; border-bottom: 1px solid black; border-right: 1px solid black;\">" + user.getLastName() + "</td>" +
            "</tr>" +
            "<tr>" +
            "<td style=\"padding: 10px; border-bottom: 1px solid black; border-right: 1px solid black; border-left: 1px solid black;\">Email</td>" +
            "<td style=\"padding: 10px; border-bottom: 1px solid black; border-right: 1px solid black;\">" + user.getEmail() + "</td>" +
            "</tr>" +
            "<tr>" +
            "<td style=\"padding: 10px; border-bottom: 1px solid black; border-right: 1px solid black; border-left: 1px solid black;\">Booking Identifier</td>" +
            "<td style=\"padding: 10px; border-bottom: 1px solid black; border-right: 1px solid black;\">" + booking.getUuid().toString() + "</td>" +
            "</tr>" +
            "<tr>" +
            "<td style=\"padding: 10px; border-bottom: 1px solid black; border-right: 1px solid black; border-left: 1px solid black;\">Details</td>" +
            "<td style=\"padding: 10px; border-bottom: 1px solid black; border-right: 1px solid black;\">" + booking.getShowDetails() + "</td>" +
            "</tr>" +
            "</table>";
    }
}
