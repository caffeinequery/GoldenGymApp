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
        ResultSet rs; // Stores the result set returned by the query execution.
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
        return false;
    }

    @Override
    public boolean addClient(GymClient client) {
        return false;
    }

    @Override
    public boolean modifyClient(GymClient client) {
        return false;
    }

    @Override
    public boolean deleteClient(GymClient client) {
        return false;
    }

    public static void main(String[] args) {
        // List clients
        System.out.println("*** Listing clients ***");
        IDAOClient daoClient = new DAOClient();
        var clients = daoClient.listClients();
        clients.forEach(System.out::println);
    }
}
