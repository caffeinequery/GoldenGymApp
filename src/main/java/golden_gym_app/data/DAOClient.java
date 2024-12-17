package golden_gym_app.data;

import golden_gym_app.domain.GymClient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static golden_gym_app.connection.Connect.getConnection;

public class DAOClient implements IDAOClient {

    @Override
    public List<GymClient> listClients() {
        List<GymClient> clients = new ArrayList<>();
        PreparedStatement ps; // Prepares the SQL query to be executed on the database with parameters (if any).
        ResultSet rs; // Stores the result set returned by the query execution. Used when you retrieve info.
        Connection con = getConnection();

        var sql = "SELECT * FROM client ORDER BY client_id";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                var client = new GymClient();
                client.setId(rs.getInt("client_id"));
                client.setFirstName(rs.getString("client_first_name"));
                client.setSurname(rs.getString("client_surname"));
                client.setMembership(rs.getInt("client_membership"));
                clients.add(client);
            }
        } catch(Exception e) {
            System.out.println("An error has occurred while listing the clients: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("An error has occurred while closing the connection to the db: " + e.getMessage());
            }
        }

        return clients;
    }

    @Override
    public boolean searchClientById(GymClient client) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConnection();
        String sql = "SELECT * FROM client WHERE client_id = ?"; // The "?" works as a placeholder.
        // The "?" placeholder will be replaced by the value set with ps.setInt(1, client.getId()).

        try {

            ps = con.prepareStatement(sql);
            // ps.setInt(index, value): assigns the specified value to the placeholder at the given index in the SQL query.
            ps.setInt(1, client.getId()); // In this case, it replaces the first "?" placeholder in the SQL query (sql) with the value of client.getId().
            rs = ps.executeQuery();

            if(rs.next()) {
                client.setFirstName(rs.getString("client_first_name"));
                client.setSurname(rs.getString("client_surname"));
                client.setMembership(rs.getInt("client_membership"));

                // Returns true since a register with the specified ID was found.
                return true;
            }
        } catch (Exception e) {
            System.out.println("An error has occurred while searching client by ID: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch(Exception e) {
                System.out.println("An error has occurred while closing the connection to the db: " + e.getMessage());
            }
        }

        // In case no register was found by the entered ID, it returns false.
        return false;
    }

    @Override
    public boolean addClient(GymClient client) {
        PreparedStatement ps;
        Connection con = getConnection();
        String sql = "INSERT INTO client(client_first_name, client_surname, client_membership) "
                + "VALUES(?, ?, ?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getSurname());
            ps.setInt(3, client.getMembership());

            ps.execute();

            return true;
        } catch(Exception e) {
            System.out.println("An error has occurred while adding the client: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("An error has occurred while closing the connection to the db: " + e.getMessage());
            }
        }

        return false;
    }

    @Override
    public boolean modifyClient(GymClient client) {
        PreparedStatement ps;
        Connection con = getConnection();
        var sql = "UPDATE client SET client_first_name=?, client_surname=?, client_membership=? " +
                "WHERE client_id=?";

        try {
            ps = con.prepareStatement(sql);

            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getSurname());
            ps.setInt(3, client.getMembership());
            ps.setInt(4, client.getId());

            ps.execute();

            return true;
        } catch (Exception e) {
            System.out.println("An error has occurred while modifying the client: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("An error has occurred while closing the connection to the db: " + e.getMessage());
            }
        }

        return false;
    }

    @Override
    public boolean deleteClient(GymClient client) {
        return false;
    }

    public static void main(String[] args) {
        // Creates an instance of the DAOClient class in order to test its methods.
        IDAOClient daoClient = new DAOClient();

        // List clients
        // System.out.println("*** Listing clients ***");
        // var clients = daoClient.listClients();
        // clients.forEach(System.out::println);

        // Search client by ID
        // var client1 = new GymClient(3);
        // System.out.println("Client before search: " + client1 + "\n");
        // var found = daoClient.searchClientById(client1);
        // if(found) {
        //     System.out.println("Client was found: " + client1);
        // } else {
        //     System.out.println("Didn't found client with ID: " + client1.getId());
        // }

        // Add client
        // var newClient = new GymClient("Daniel", "Ortiz", 400);
        // var added = daoClient.addClient(newClient);
        // if(added) {
        //     System.out.println("\nClient successfully added: " + newClient);
        // } else {
        //     System.out.println("\nClient couldn't be added: " + newClient);
        // }

        // Modify client
        var modifiedClient = new GymClient(6, "Carlos Daniel", "Ortiz", 400);
        var modified = daoClient.modifyClient(modifiedClient);
        if(modified) {
            System.out.println("\nClient successfully modified: " + modifiedClient);
        } else {
            System.out.println("\nCouldn't modify client: " + modifiedClient);
        }

        System.out.println("\n*** Listing clients ***");
        var clients = daoClient.listClients();
        clients.forEach(System.out::println);
    }
}
