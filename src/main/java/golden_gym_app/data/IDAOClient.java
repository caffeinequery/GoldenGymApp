package golden_gym_app.data;

import golden_gym_app.domain.GymClient;
import java.util.List;

public interface IDAOClient {
    List<GymClient> listClients(); // Returns a list of the current clients on the db.

    boolean searchClientById(GymClient client); // Searches for a client by ID and returns a boolean indicating whether the client exists in the db.

    boolean addClient(GymClient client); // Adds client to the db and returns a boolean indicating whether the client was successfully added.

    boolean modifyClient(GymClient client); // Receives a GymClient object with the new data to update an existing client in the db.

    boolean deleteClient(GymClient client); // Deletes the specified client from the db.
}
