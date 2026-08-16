package org.luisa.diary.contracts;

import java.time.LocalDate;
import java.util.List;
import org.luisa.diary.models.Emotion;
import org.luisa.diary.models.Moment;

/**
 * Defines the business operations available for lived moments.
 */
public interface MomentServiceContract {

    /**
     * Adds a new moment after validating its required fields.
     *
     * @param title the moment's title
     * @param description the moment's description
     * @param emotion the emotion associated with the moment
     * @param momentDate the date the moment happened
     * @return the saved moment, with its id already assigned
     */
    Moment addMoment(String title, String description, Emotion emotion, LocalDate momentDate);

    /**
     * Returns every registered moment.
     *
     * @return the list of all moments
     */
    List<Moment> getAllMoments();

    /**
     * Deletes the moment with the given id.
     *
     * @param id the id of the moment to delete
     * @return true if a moment was found and deleted, false otherwise
     */
    boolean deleteMoment(int id);

    /**
     * Returns the moments that match the given emotion.
     *
     * @param emotion the emotion to filter by
     * @return the list of moments with that emotion
     */
    List<Moment> filterByEmotion(Emotion emotion);

    /**
     * Returns the moments that happened on the given date.
     *
     * @param date the date to filter by
     * @return the list of moments that happened on that date
     */
    List<Moment> filterByDate(LocalDate date);
}
