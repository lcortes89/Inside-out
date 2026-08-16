package org.luisa.diary.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.luisa.diary.contracts.MomentServiceContract;
import org.luisa.diary.models.Emotion;
import org.luisa.diary.models.Moment;

/**
 * Converts raw console input into domain types and delegates the
 * actual work to a {@link MomentServiceContract}. Any invalid raw
 * input results in an {@link IllegalArgumentException} whose message
 * is meant to be shown to the user by the view.
 */
public class MomentController {

    private static final int MIN_EMOTION_OPTION = 1;
    private static final int MAX_EMOTION_OPTION = 10;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final MomentServiceContract momentService;

    /**
     * Creates a new controller backed by the given service.
     *
     * @param momentService the service used to run business operations
     */
    public MomentController(MomentServiceContract momentService) {
        this.momentService = momentService;
    }

    /**
     * Adds a new moment from already-validated data.
     *
     * @param title the moment's title
     * @param description the moment's description
     * @param momentDate the moment's date
     * @param emotion the emotion associated with the moment
     * @return the saved moment
     */
    public Moment addMoment(String title, String description, LocalDate momentDate, Emotion emotion) {
        return this.momentService.addMoment(title, description, emotion, momentDate);
    }

    /**
     * Returns every registered moment.
     *
     * @return the list of all moments
     */
    public List<Moment> getAllMoments() {
        return this.momentService.getAllMoments();
    }

    /**
     * Deletes the moment with the given raw id.
     *
     * @param rawId the id as typed by the user
     * @return true if a moment was found and deleted, false otherwise
     */
    public boolean deleteMoment(String rawId) {
        int id = parseId(rawId);
        return this.momentService.deleteMoment(id);
    }

    /**
     * Filters moments by the emotion selected from the raw menu option.
     *
     * @param rawEmotionOption the emotion menu option as typed by the user (1-10)
     * @return the list of moments with that emotion
     */
    public List<Moment> filterByEmotion(String rawEmotionOption) {
        Emotion emotion = parseEmotionOption(rawEmotionOption);
        return this.momentService.filterByEmotion(emotion);
    }

    /**
     * Filters moments by the raw date typed by the user.
     *
     * @param rawDate the date as typed by the user (dd/mm/yyyy)
     * @return the list of moments that happened on that date
     */
    public List<Moment> filterByDate(String rawDate) {
        LocalDate date = parseDate(rawDate);
        return this.momentService.filterByDate(date);
    }

    /**
     * Parses a raw date typed by the user.
     *
     * @param rawDate the date as typed by the user (dd/mm/yyyy)
     * @return the parsed date
     */
    public LocalDate parseDate(String rawDate) {
        try {
            return LocalDate.parse(rawDate, DATE_FORMAT);
        } catch (DateTimeParseException | NullPointerException e) {
            throw new IllegalArgumentException("La fecha debe tener el formato dd/mm/aaaa.");
        }
    }

    /**
     * Parses a raw emotion menu option typed by the user.
     *
     * @param rawEmotionOption the emotion menu option as typed by the user (1-10)
     * @return the selected emotion
     */
    public Emotion parseEmotionOption(String rawEmotionOption) {
        int option;
        try {
            option = Integer.parseInt(rawEmotionOption.trim());
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException("La opción de emoción debe ser un número entre 1 y 10.");
        }
        if (option < MIN_EMOTION_OPTION || option > MAX_EMOTION_OPTION) {
            throw new IllegalArgumentException("La opción de emoción debe estar entre 1 y 10.");
        }
        return Emotion.values()[option - 1];
    }

    private int parseId(String rawId) {
        try {
            return Integer.parseInt(rawId.trim());
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException("El identificador debe ser un número.");
        }
    }

}