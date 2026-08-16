package org.luisa.diary.view;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.luisa.diary.controllers.MomentController;
import org.luisa.diary.models.Emotion;
import org.luisa.diary.models.Moment;

/**
 * Unit tests for {@link ConsoleView}. Feeds simulated keyboard input through
 * a {@link Scanner} and captures everything printed to {@link System#out}.
 */
class ConsoleViewTest {

    private MomentController momentController;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        this.momentController = mock(MomentController.class);
        this.outputStream = new ByteArrayOutputStream();
        this.originalOut = System.out;
        System.setOut(new PrintStream(this.outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(this.originalOut);
    }

    private ConsoleView newView(String simulatedInput) {
        return new ConsoleView(this.momentController, new Scanner(simulatedInput));
    }

    @Test
    void exitingImmediatelyPrintsFarewellMessage() {
        ConsoleView view = newView("5\n");

        view.run();

        assertThat(this.outputStream.toString(), containsString("Hasta la próxima!!!"));
    }

    @Test
    void addingAMomentWithValidDataCallsTheController() {
        LocalDate momentDate = LocalDate.of(2024, 3, 15);
        when(this.momentController.parseDate("15/03/2024")).thenReturn(momentDate);
        when(this.momentController.parseEmotionOption("1")).thenReturn(Emotion.ALEGRIA);
        ConsoleView view = newView("1\nTítulo\nDescripción\n15/03/2024\n1\n5\n");

        view.run();

        verify(this.momentController).addMoment("Título", "Descripción", momentDate, Emotion.ALEGRIA);
        assertThat(this.outputStream.toString(), containsString("Momento vivído añadido correctamente."));
    }

    @Test
    void listingMomentsWhenThereAreNonePrintsAMessage() {
        when(this.momentController.getAllMoments()).thenReturn(List.of());
        ConsoleView view = newView("2\n5\n");

        view.run();

        assertThat(this.outputStream.toString(), containsString("Todavía no hay momentos registrados."));
    }

    @Test
    void listingMomentsPrintsEachOne() {
        Moment moment = new Moment("Título", "Descripción", Emotion.ALEGRIA, LocalDate.of(2024, 3, 15));
        moment.setId(1);
        when(this.momentController.getAllMoments()).thenReturn(List.of(moment));
        ConsoleView view = newView("2\n5\n");

        view.run();

        assertThat(this.outputStream.toString(), containsString("Título: Título"));
    }

    @Test
    void deletingAnExistingMomentPrintsSuccess() {
        when(this.momentController.deleteMoment("1")).thenReturn(true);
        ConsoleView view = newView("3\n1\n5\n");

        view.run();

        assertThat(this.outputStream.toString(), containsString("Momento vivído eliminado correctamente."));
    }

    @Test
    void deletingANonExistentMomentPrintsAnError() {
        when(this.momentController.deleteMoment("99")).thenReturn(false);
        ConsoleView view = newView("3\n99\n5\n");

        view.run();

        assertThat(this.outputStream.toString(), containsString("No existe ningún momento con ese identificador."));
    }

    @Test
    void filteringByEmotionPrintsMatchingMoments() {
        Moment moment = new Moment("Título", "Descripción", Emotion.ALEGRIA, LocalDate.of(2024, 3, 15));
        moment.setId(1);
        when(this.momentController.filterByEmotion("1")).thenReturn(List.of(moment));
        ConsoleView view = newView("4\n1\n1\n5\n");

        view.run();

        assertThat(this.outputStream.toString(), containsString("Título: Título"));
    }

    @Test
    void filteringByMonthWithNoResultsPrintsAMessage() {
        when(this.momentController.filterByMonth("05/2024")).thenReturn(List.of());
        ConsoleView view = newView("4\n2\n05/2024\n5\n");

        view.run();

        assertThat(this.outputStream.toString(), containsString("No hay momentos que coincidan con ese filtro."));
    }

    @Test
    void invalidMainMenuOptionPrintsAnError() {
        ConsoleView view = newView("9\n5\n");

        view.run();

        assertThat(this.outputStream.toString(), containsString("Opción no válida."));
    }

}