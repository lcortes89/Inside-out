package org.luisa.diary.repositories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.luisa.diary.models.Emotion;
import org.luisa.diary.models.Moment;

/**
 * Unit tests for {@link MomentRepository}.
 */
class MomentRepositoryTest {

    private MomentRepository momentRepository;

    @BeforeEach
    void setUp() {
        this.momentRepository = new MomentRepository();
    }

    @Test
    void saveAssignsIncrementingIds() {
        Moment first = this.momentRepository.save(newMoment(Emotion.ALEGRIA, LocalDate.of(2024, 1, 10)));
        Moment second = this.momentRepository.save(newMoment(Emotion.TRISTEZA, LocalDate.of(2024, 2, 10)));

        assertThat(first.getId(), is(equalTo(1)));
        assertThat(second.getId(), is(equalTo(2)));
    }

    @Test
    void findAllReturnsEveryStoredMoment() {
        this.momentRepository.save(newMoment(Emotion.ALEGRIA, LocalDate.of(2024, 1, 10)));
        this.momentRepository.save(newMoment(Emotion.TRISTEZA, LocalDate.of(2024, 2, 10)));

        assertThat(this.momentRepository.findAll(), hasSize(2));
    }

    @Test
    void findAllReturnsEmptyListWhenNoMoments() {
        assertThat(this.momentRepository.findAll(), is(empty()));
    }

    @Test
    void deleteByIdRemovesAnExistingMoment() {
        Moment saved = this.momentRepository.save(newMoment(Emotion.ALEGRIA, LocalDate.of(2024, 1, 10)));

        boolean deleted = this.momentRepository.deleteById(saved.getId());

        assertThat(deleted, is(true));
        assertThat(this.momentRepository.findAll(), is(empty()));
    }

    @Test
    void deleteByIdReturnsFalseWhenIdDoesNotExist() {
        boolean deleted = this.momentRepository.deleteById(99);

        assertThat(deleted, is(false));
    }

    @Test
    void findByEmotionReturnsOnlyMatchingMoments() {
        Moment alegria = this.momentRepository.save(newMoment(Emotion.ALEGRIA, LocalDate.of(2024, 1, 10)));
        this.momentRepository.save(newMoment(Emotion.TRISTEZA, LocalDate.of(2024, 2, 10)));

        List<Moment> result = this.momentRepository.findByEmotion(Emotion.ALEGRIA);

        assertThat(result, contains(alegria));
    }

    @Test
    void findByMonthReturnsOnlyMomentsInThatMonthAndYear() {
        Moment mayMoment = this.momentRepository.save(newMoment(Emotion.ALEGRIA, LocalDate.of(2024, 5, 15)));
        this.momentRepository.save(newMoment(Emotion.TRISTEZA, LocalDate.of(2024, 6, 15)));
        this.momentRepository.save(newMoment(Emotion.IRA, LocalDate.of(2023, 5, 15)));

        List<Moment> result = this.momentRepository.findByMonth(5, 2024);

        assertThat(result, contains(mayMoment));
    }

    private Moment newMoment(Emotion emotion, LocalDate momentDate) {
        return new Moment("Title", "Description", emotion, momentDate);
    }

}