package org.luisa.diary.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.luisa.diary.controllers.MomentController;
import org.luisa.diary.models.Emotion;
import org.luisa.diary.models.Moment;

/**
 * Handles all console input and output: prints the menu, reads what the
 * user types, and shows the result of each operation.
 */
public class ConsoleView {

    private static final int OPTION_ADD = 1;
    private static final int OPTION_LIST = 2;
    private static final int OPTION_DELETE = 3;
    private static final int OPTION_FILTER = 4;
    private static final int OPTION_EXIT = 5;

    private static final int FILTER_BY_EMOTION = 1;
    private static final int FILTER_BY_DATE = 2;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final String ANSI_RED = "[31m";
    private static final String ANSI_RESET = "[0m";

    private final MomentController momentController;
    private final Scanner scanner;

    /**
     * Creates a new console view backed by the given controller.
     *
     * @param momentController the controller used to run each operation
     * @param scanner the scanner used to read user input
     */
    public ConsoleView(MomentController momentController, Scanner scanner) {
        this.momentController = momentController;
        this.scanner = scanner;
    }

    /**
     * Runs the main menu loop until the user chooses to exit.
     */
    public void run() {
        boolean exit = false;
        while (!exit) {
            printMenu();
            int option = readInt("");
            switch (option) {
                case OPTION_ADD -> addMoment();
                case OPTION_LIST -> listMoments();
                case OPTION_DELETE -> deleteMoment();
                case OPTION_FILTER -> filterMoments();
                case OPTION_EXIT -> exit = true;
                default -> printError("Opción no válida.");
            }
        }
        System.out.println("Hasta la próxima!!!");
    }

    private void printMenu() {
        System.out.println();
        System.out.println("+=========================================+");
        System.out.println("|          -` ́- MI DIARIO -` ́-            |");
        System.out.println("+=========================================+");
        System.out.println("|  1. Añadir momento                      |");
        System.out.println("|-----------------------------------------|");
        System.out.println("|  2. Ver todos los momentos disponibles  |");
        System.out.println("|-----------------------------------------|");
        System.out.println("|  3. Eliminar un momento                 |");
        System.out.println("|-----------------------------------------|");
        System.out.println("|  4. Filtrar los momentos                |");
        System.out.println("|-----------------------------------------|");
        System.out.println("|  5. Salir                               |");
        System.out.println("+=========================================+");
        System.out.print("Seleccione una opción: ");
    }

    private void printError(String message) {
        System.out.println(ANSI_RED + message + ANSI_RESET);
    }

    private void addMoment() {
        String title = readRequiredText("Ingrese el título: ", "El título no puede estar vacío.");
        String description = readRequiredText("Ingrese la descripción: ", "La descripción no puede estar vacía.");
        LocalDate momentDate = readValidDate();
        Emotion emotion = readValidEmotionOption();
        this.momentController.addMoment(title, description, momentDate, emotion);
        System.out.println("Momento vivído añadido correctamente.");
    }

    private String readRequiredText(String prompt, String errorMessage) {
        String value;
        do {
            System.out.print(prompt);
            value = this.scanner.nextLine();
            if (value.isBlank()) {
                printError(errorMessage);
            }
        } while (value.isBlank());
        return value;
    }

    private LocalDate readValidDate() {
        while (true) {
            System.out.print("Ingresa la fecha (dd/mm/year): ");
            String rawDate = this.scanner.nextLine();
            try {
                return this.momentController.parseDate(rawDate);
            } catch (IllegalArgumentException e) {
                printError(e.getMessage());
            }
        }
    }

    private Emotion readValidEmotionOption() {
        while (true) {
            String rawEmotion = readEmotionOption();
            try {
                return this.momentController.parseEmotionOption(rawEmotion);
            } catch (IllegalArgumentException e) {
                printError(e.getMessage());
            }
        }
    }

    private void listMoments() {
        List<Moment> moments = this.momentController.getAllMoments();
        if (moments.isEmpty()) {
            System.out.println("Todavía no hay momentos registrados.");
            return;
        }
        printMoments(moments);
    }

    private void deleteMoment() {
        System.out.print("Ingresa el identificador del momento: ");
        String rawId = this.scanner.nextLine();
        try {
            boolean deleted = this.momentController.deleteMoment(rawId);
            if (deleted) {
                System.out.println("Momento vivído eliminado correctamente.");
            } else {
                printError("No existe ningún momento con ese identificador.");
            }
        } catch (IllegalArgumentException e) {
            printError(e.getMessage());
        }
    }

    private void filterMoments() {
        System.out.println("Filtrar por ...:");
        System.out.println("1. Emoción");
        System.out.println("2. Fecha");
        System.out.print("Ingrese una opción: ");
        int option = readInt("");
        try {
            List<Moment> moments;
            if (option == FILTER_BY_EMOTION) {
                String rawEmotion = readEmotionOption();
                moments = this.momentController.filterByEmotion(rawEmotion);
            } else if (option == FILTER_BY_DATE) {
                System.out.print("Ingresa la fecha (dd/mm/year): ");
                String rawDate = this.scanner.nextLine();
                moments = this.momentController.filterByDate(rawDate);
            } else {
                printError("Opción no válida.");
                return;
            }
            if (moments.isEmpty()) {
                System.out.println("No hay momentos que coincidan con ese filtro.");
            } else {
                printMoments(moments);
            }
        } catch (IllegalArgumentException e) {
            printError(e.getMessage());
        }
    }

    private String readEmotionOption() {
        System.out.println("Selecciona una emoción:");
        Emotion[] emotions = Emotion.values();
        for (int i = 0; i < emotions.length; i++) {
            System.out.println((i + 1) + ". " + emotionDisplayName(emotions[i]));
        }
        System.out.print("Ingrese su opción: ");
        return this.scanner.nextLine();
    }

    private void printMoments(List<Moment> moments) {
        System.out.println("Lista de momentos vividos:");
        for (Moment moment : moments) {
            System.out.println(moment.getId() + ". Ocurrio el: " + moment.getMomentDate().format(DATE_FORMAT)
                    + ". Título: " + moment.getTitle()
                    + ". Descripción: " + moment.getDescription()
                    + ". Emoción: " + emotionDisplayName(moment.getEmotion()));
        }
    }

    private String emotionDisplayName(Emotion emotion) {
        return switch (emotion) {
            case ALEGRIA -> "Alegría";
            case TRISTEZA -> "Tristeza";
            case IRA -> "Ira";
            case ASCO -> "Asco";
            case MIEDO -> "Miedo";
            case ANSIEDAD -> "Ansiedad";
            case ENVIDIA -> "Envidia";
            case VERGUENZA -> "Vergüenza";
            case ABURRIMIENTO -> "Aburrimiento";
            case NOSTALGIA -> "Nostalgia";
        };
    }

    private int readInt(String prompt) {
        if (!prompt.isEmpty()) {
            System.out.print(prompt);
        }
        try {
            return Integer.parseInt(this.scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}