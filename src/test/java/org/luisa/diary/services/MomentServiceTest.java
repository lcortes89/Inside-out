package org.luisa.diary.services;

import java.time.LocalDate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.luisa.diary.models.Emotion;
import org.luisa.diary.models.Moment;
import org.luisa.diary.repositories.MomentRepository;

/**
 * Unit tests for {@link MomentService}.
 */
class MomentServiceTest {

    private MomentRepository momentRepository;
    private MomentService momentService;

    @BeforeEach
    void setUp() {
        this.momentRepository = mock(MomentRepository.class);
        this.momentService = new MomentService(this.momentRepository);
    }

    @Test
    void addMomentSavesAValidMoment() {
        LocalDate momentDate = LocalDate.of(2024, 5, 20);
        Moment expected = new Moment("Title", "Description", Emotion.ALEGRIA, momentDate);
        when(this.momentRepository.save(any(Moment.class))).thenReturn(expected);

        Moment result = this.momentService.addMoment("Title", "Description", Emotion.ALEGRIA, momentDate);

        assertThat(result, is(equalTo(expected)));
        verify(this.momentRepository).save(any(Moment.class));
    }

    @Test
    void addMomentThrowsWhenTitleIsBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> this.momentService.addMoment("   ", "Description", Emotion.ALEGRIA, LocalDate.now()));
    }

    @Test
    void addMomentThrowsWhenDescriptionIsBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> this.momentService.addMoment("Title", "", Emotion.ALEGRIA, LocalDate.now()));
    }

    @Test
    void getAllMomentsDelegatesToRepository() {
        List<Moment> moments = List.of(new Moment("Title", "Description", Emotion.ALEGRIA, LocalDate.now()));
        when(this.momentRepository.findAll()).thenReturn(moments);

        assertThat(this.momentService.getAllMoments(), is(equalTo(moments)));
    }

    @Test
    void deleteMomentDelegatesToRepository() {
        when(this.momentRepository.deleteById(1)).thenReturn(true);

        assertThat(this.momentService.deleteMoment(1), is(true));
    }

    @Test
    void filterByEmotionDelegatesToRepository() {
        List<Moment> moments = List.of(new Moment("Title", "Description", Emotion.TRISTEZA, LocalDate.now()));
        when(this.momentRepository.findByEmotion(Emotion.TRISTEZA)).thenReturn(moments);

        assertThat(this.momentService.filterByEmotion(Emotion.TRISTEZA), is(equalTo(moments)));
    }

    @Test
    void filterByDateDelegatesToRepository() {
        LocalDate date = LocalDate.of(2024, 5, 1);
        List<Moment> moments = List.of(new Moment("Title", "Description", Emotion.ALEGRIA, date));
        when(this.momentRepository.findByDate(date)).thenReturn(moments);

        assertThat(this.momentService.filterByDate(date), is(equalTo(moments)));
    }

}
