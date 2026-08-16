package org.luisa.diary.services;

import java.time.LocalDate;
import java.util.List;

import org.luisa.diary.contracts.MomentServiceContract;
import org.luisa.diary.models.Emotion;
import org.luisa.diary.models.Moment;
import org.luisa.diary.repositories.MomentRepository;

/**
 * Implements the business rules for managing lived moments,
 * delegating storage to a {@link MomentRepository}.
 */
public class MomentService implements MomentServiceContract {

    private static final int MIN_MONTH = 1;
    private static final int MAX_MONTH = 12;

    private final MomentRepository momentRepository;

    /**
     * Creates a new service backed by the given repository.
     *
     * @param momentRepository the repository used to persist moments
     */
    public MomentService(MomentRepository momentRepository) {
        this.momentRepository = momentRepository;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if the title or description is empty
     */
    @Override
    public Moment addMoment(String title, String description, Emotion emotion, LocalDate momentDate) {
        if (title == null || title.isBlank() || description == null || description.isBlank()) {
            throw new IllegalArgumentException("El título y la descripción son obligatorios.");
        }
        Moment moment = new Moment(title, description, emotion, momentDate);
        return this.momentRepository.save(moment);
    }

    @Override
    public List<Moment> getAllMoments() {
        return this.momentRepository.findAll();
    }

    @Override
    public boolean deleteMoment(int id) {
        return this.momentRepository.deleteById(id);
    }

    @Override
    public List<Moment> filterByEmotion(Emotion emotion) {
        return this.momentRepository.findByEmotion(emotion);
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if the month is outside 1-12
     */
    @Override
    public List<Moment> filterByMonth(int month, int year) {
        if (month < MIN_MONTH || month > MAX_MONTH) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12.");
        }
        return this.momentRepository.findByMonth(month, year);
    }
}