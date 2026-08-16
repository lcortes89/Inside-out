package org.luisa.diary.repositories;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.luisa.diary.models.Emotion;
import org.luisa.diary.models.Moment;

/**
 * Stores moments in memory using a {@link Map}, keyed by their id.
 */
public class MomentRepository {

    private final Map<Integer, Moment> moments = new HashMap<>();
    private int nextId = 1;

    /**
     * Saves a new moment, assigning it the next available id.
     *
     * @param moment the moment to save
     * @return the saved moment, with its id already assigned
     */
    public Moment save(Moment moment) {
        moment.setId(this.nextId);
        this.moments.put(this.nextId, moment);
        this.nextId++;
        return moment;
    }

    /**
     * Returns all the stored moments.
     *
     * @return a list with every moment currently stored
     */
    public List<Moment> findAll() {
        return new ArrayList<>(this.moments.values());
    }

    /**
     * Deletes the moment with the given id.
     *
     * @param id the id of the moment to delete
     * @return true if a moment was found and deleted, false otherwise
     */
    public boolean deleteById(int id) {
        return this.moments.remove(id) != null;
    }

    /**
     * Returns the moments that match the given emotion.
     *
     * @param emotion the emotion to filter by
     * @return the list of moments with that emotion
     */
    public List<Moment> findByEmotion(Emotion emotion) {
        List<Moment> result = new ArrayList<>();
        for (Moment moment : this.moments.values()) {
            if (moment.getEmotion() == emotion) {
                result.add(moment);
            }
        }
        return result;
    }

    /**
     * Returns the moments that happened on the given date.
     *
     * @param date the date to filter by
     * @return the list of moments that happened on that date
     */
    public List<Moment> findByDate(LocalDate date) {
        List<Moment> result = new ArrayList<>();
        for (Moment moment : this.moments.values()) {
            if (moment.getMomentDate().equals(date)) {
                result.add(moment);
            }
        }
        return result;
    }

}
