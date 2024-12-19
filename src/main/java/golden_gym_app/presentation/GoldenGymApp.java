package golden_gym_app.presentation;

import golden_gym_app.data.DAOClient;
import golden_gym_app.data.IDAOClient;
import golden_gym_app.domain.GymClient;

import java.util.List;
import java.util.Scanner;


public class GoldenGymApp {
    public static void main(String[] args) {
        goldenGymApp();
    }

    // goldenGymApp(): Launches the application.
    public static void goldenGymApp() {
        boolean exit = false;
        Scanner console = new Scanner(System.in); // An instance of Scanner is created statically to handle input.
        IDAOClient services = new DAOClient(); // DAOClient object that's going to supply all the services previously programmed.

        // Creates an AppContext instance to manage the application's state and services.
        AppContext context = new AppContext(console, services);

        System.out.println("\n-+-+-+-+-+-+-+-+-+-+-+-+- G O L D E N  G Y M -+-+-+-+-+-+-+-+-+-+-+-+-");
        System.out.println("Welcome back! This is the Golden Gym application for client management.");

        while(!exit) {
            try {
                int option = showMenu(console);
                exit = executeOption(option, context);
            } catch(Exception e) {
                System.out.println("An error has occurred during the execution of the application: " + e.getMessage());
            } finally {
                System.out.println();
            }
        }
    }

    private static int showMenu(Scanner console) {
        System.out.println("""
                \n          M E N U
                1. Show current client list.
                2. Search client by ID.
                3. Add a client.
                4. Modify a client.
                5. Delete a client.
                6. Exit.""");
        System.out.print("Please, enter your option: ");

        return Integer.parseInt(console.nextLine());
    }

    private static boolean executeOption(int option, AppContext context) {
        boolean exit = false;

        switch(option) {
            case 1 -> showClientList(context);
            case 2 -> searchClientById(context);
            case 3 -> addClientToList(context);
            case 4 -> modifyCurrentClient(context);
            case 5 -> deleteClientFromList(context);
            case 6 -> {
                System.out.println("\nCome back soon!");
                exit = true;
            }
            default -> System.out.println("Invalid option: " + option);
        }

        return exit;
    }

    private static void showClientList(AppContext context) {
        List<GymClient> currentClients = context.getServices().listClients();

        System.out.println("\n-+-+-+-+-+-+-+-+-+-+-+-+- Current clients -+-+-+-+-+-+-+-+-+-+-+-+-");
        currentClients.forEach(System.out::println);
    }

    private static void searchClientById(AppContext context) {
        int id;

        System.out.println("\n-+-+-+-+-+-+-+-+-+-+-+-+- Search client by ID -+-+-+-+-+-+-+-+-+-+-+-+-");
        System.out.print("Enter ID: ");
        id = Integer.parseInt(context.getConsole().nextLine());
        GymClient clientToSearch = new GymClient(id);

        System.out.println("\nSearching client with ID " + id + "...");
        var found = context.getServices().searchClientById(clientToSearch);
        if(found) {
            System.out.println("\n*** Client was found: " + clientToSearch);
        } else {
            System.out.println("\n*** Didn't found client with ID: " + id);
        }
    }

    private static void addClientToList(AppContext context) {
        // Variables needed for creating an instance of GymClient.
        String name;
        String surname;
        int membership;

        System.out.println("\n-+-+-+-+-+-+-+-+-+-+-+-+- Add client -+-+-+-+-+-+-+-+-+-+-+-+-");
        System.out.print("Enter the new client first name: ");
        name = context.getConsole().nextLine();
        System.out.print("Enter the new client surname: ");
        surname = context.getConsole().nextLine();
        System.out.print("Enter the client membership code: ");
        membership = Integer.parseInt(context.getConsole().nextLine());

        // Client that's going to be added to the db.
        GymClient newClient = new GymClient(name, surname, membership);

        // Add the client to the db.
        boolean added = context.getServices().addClient(newClient);

        if(added) {
            System.out.println("\n*** Client successfully added: " + newClient);
        } else {
            System.out.println("\n*** Couldn't add client: " + newClient);
        }
    }

    private static void modifyCurrentClient(AppContext context) {
        // Variables of the values that are going to be replaced on the existing client.
        int id;
        String name;
        String surname;
        int membership;
        GymClient modifiedClient;

        System.out.println("\n-+-+-+-+-+-+-+-+-+-+-+-+- Modify client -+-+-+-+-+-+-+-+-+-+-+-+-");
        showClientList(context);
        System.out.print("Enter the ID of the client you want to modify: ");

        id = Integer.parseInt(context.getConsole().nextLine());
        System.out.println("Modifying client...");
        System.out.print("Enter first name: ");
        name = context.getConsole().nextLine();
        System.out.print("Enter surname: ");
        surname = context.getConsole().nextLine();
        System.out.print("Enter membership: ");
        membership = Integer.parseInt(context.getConsole().nextLine());

        modifiedClient = new GymClient(id, name, surname, membership);
        var modified = context.getServices().modifyClient(modifiedClient);
        if(modified) {
            System.out.println("\nClient successfully modified: " + modifiedClient);
        } else {
            System.out.println("\nCouldn't modify client with ID: " + id);
        }
    }

    private static void deleteClientFromList(AppContext context) {
        int id;
        GymClient clientToDelete;
        boolean deleted;

        System.out.println("\n-+-+-+-+-+-+-+-+-+-+-+-+- Delete client -+-+-+-+-+-+-+-+-+-+-+-+-");
        showClientList(context);
        System.out.print("Enter the ID of the client you want to delete: ");
        id = Integer.parseInt(context.getConsole().nextLine());

        clientToDelete = new GymClient(id);
        deleted = context.getServices().deleteClient(clientToDelete);
        if(deleted) {
            System.out.println("\n*** Client successfully deleted: " + clientToDelete);
        } else {
            System.out.println("\n*** Couldn't delete client with ID: " + id);
        }
    }
}
