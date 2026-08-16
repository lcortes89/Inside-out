package org.luisa.diary;

import java.util.Scanner;

import org.luisa.diary.controllers.MomentController;
import org.luisa.diary.repositories.MomentRepository;
import org.luisa.diary.services.MomentService;
import org.luisa.diary.view.ConsoleView;

/**
 * Entry point of the Mi Diario application. Wires together the
 * repository, service, controller and view, then starts the console loop.
 */
public final class App {

    private App() {
    }

    /**
     * Starts the application.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        MomentRepository momentRepository = new MomentRepository();
        MomentService momentService = new MomentService(momentRepository);
        MomentController momentController = new MomentController(momentService);
        try (Scanner scanner = new Scanner(System.in)) {
            ConsoleView consoleView = new ConsoleView(momentController, scanner);
            consoleView.run();
        }
    }

}