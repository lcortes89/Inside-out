package org.luisa.diary.controllers;

import java.time.LocalDate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.luisa.diary.contracts.MomentServiceContract;
import org.luisa.diary.models.Emotion;
import org.luisa.diary.models.Moment;

/**
 * Unit tests for {@link MomentController}.
 */
class MomentControllerTest {

    private MomentServiceContract momentService;
    private MomentController momentController;

    @BeforeEach
    void setUp() {
        this.momentService = mock(MomentServiceContract.class);
        this.momentController = new MomentController(this.momentService);
    }

    @Test
    void addMomentDelegatesToService() {
        LocalDate momentDate = LocalDate.of(2024, 5, 20);
        Moment expected = new Moment("Title", "Description", Emotion.ALEGRIA, momentDate);
        when(this.momentService.addMoment("Title", "Description", Emotion.ALEGRIA, momentDate))
                .thenReturn(expected);

        Moment result = this.momentController.addMoment("Title", "Description", momentDate, Emotion.ALEGRIA);

        assertThat(result, is(equalTo(expected)));
    }

    @Test
    void getAllMomentsDelegatesToService() {
        List<Moment> moments = List.of(new Moment("Title", "Description", Emotion.ALEGRIA, LocalDate.now()));
        when(this.momentService.getAllMoments()).thenReturn(moments);

        assertThat(this.momentController.getAllMoments(), is(equalTo(moments)));
    }

    @Test
    void deleteMomentParsesIdAndDelegates() {
        when(this.momentService.deleteMoment(5)).thenReturn(true);

        assertThat(this.momentController.deleteMoment("5"), is(true));
    }

    @Test
    void deleteMomentThrowsWhenIdIsNotANumber() {
        assertThrows(IllegalArgumentException.class, () -> this.momentController.deleteMoment("abc"));
    }

    @Test
    void filterByEmotionParsesOptionAndDelegates() {
        List<Moment> moments = List.of(new Moment("Title", "Description", Emotion.TRISTEZA, LocalDate.now()));
        when(this.momentService.filterByEmotion(Emotion.TRISTEZA)).thenReturn(moments);

        assertThat(this.momentController.filterByEmotion("2"), is(equalTo(moments)));
    }

    @Test
    void filterByEmotionThrowsWhenOptionIsOutOfRange() {
        assertThrows(IllegalArgumentException.class, () -> this.momentController.filterByEmotion("11"));
    }

    @Test
    void filterByEmotionThrowsWhenOptionIsNotANumber() {
        assertThrows(IllegalArgumentException.class, () -> this.momentController.filterByEmotion("abc"));
    }

    @Test
    void filterByDateParsesRawDateAndDelegates() {
        LocalDate date = LocalDate.of(2024, 5, 1);
        List<Moment> moments = List.of(new Moment("Title", "Description", Emotion.ALEGRIA, date));
        when(this.momentService.filterByDate(date)).thenReturn(moments);

        assertThat(this.momentController.filterByDate("01/05/2024"), is(equalTo(moments)));
    }

    @Test
    void filterByDateThrowsWhenFormatIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> this.momentController.filterByDate("2024-05-01"));
    }

    @Test
    void parseDateReturnsTheParsedDate() {
        LocalDate result = this.momentController.parseDate("15/03/2024");

        assertThat(result, is(equalTo(LocalDate.of(2024, 3, 15))));
    }

    @Test
    void parseDateThrowsWhenFormatIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> this.momentController.parseDate("2024-03-15"));
    }

    @Test
    void parseEmotionOptionReturnsTheSelectedEmotion() {
        Emotion result = this.momentController.parseEmotionOption("1");

        assertThat(result, is(equalTo(Emotion.ALEGRIA)));
    }

}
