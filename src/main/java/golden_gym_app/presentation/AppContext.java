package golden_gym_app.presentation;

import golden_gym_app.data.IDAOClient;
import golden_gym_app.domain.GymClient;

import java.util.List;
import java.util.Scanner;

public class AppContext {
    // Attributes
    private Scanner console;
    private IDAOClient services;

    // Methods
    // AppContext(): Constructor of the class.
    public AppContext(Scanner console, IDAOClient services) {
        this.console = console;
        this.services = services;
    }

    // Getters
    public Scanner getConsole() {
        return console;
    }

    public IDAOClient getServices() {
        return services;
    }
}
