package org.luisa.diary.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Moment}.
 */
class MomentTest {

    @Test
    void constructorSetsAllFields() {
        LocalDate momentDate = LocalDate.of(2024, 5, 20);
        Moment moment = new Moment("Title", "Description", Emotion.ALEGRIA, momentDate);

        assertThat(moment.getTitle(), is(equalTo("Title")));
        assertThat(moment.getDescription(), is(equalTo("Description")));
        assertThat(moment.getEmotion(), is(equalTo(Emotion.ALEGRIA)));
        assertThat(moment.getMomentDate(), is(equalTo(momentDate)));
    }

    @Test
    void creationAndModificationDatesAreSetToToday() {
        Moment moment = new Moment("Title", "Description", Emotion.TRISTEZA, LocalDate.now());

        assertThat(moment.getCreationDate(), is(equalTo(LocalDate.now())));
        assertThat(moment.getModificationDate(), is(equalTo(LocalDate.now())));
    }

    @Test
    void setIdAssignsTheId() {
        Moment moment = new Moment("Title", "Description", Emotion.IRA, LocalDate.now());

        moment.setId(7);

        assertThat(moment.getId(), is(equalTo(7)));
    }

}