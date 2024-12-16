package golden_gym_app.domain;

import java.util.Objects;

public class Client {
    // ATTRIBUTES ------------------------------------------
    private int clientId;
    private String clientFirstName;
    private String clientSurname;
    private int clientMembership;

    // METHODS ---------------------------------------------
    // Constructor methods +++++++++++++++++++++++++++++++++
    public Client() {}

    public Client(int clientId) {
        this.clientId = clientId;
    }

    public Client(String clientFirstName, String clientSurname, int clientMembership) {
        this.setFirst_name(clientFirstName);
        this.setSurname(clientSurname);
        this.setMembership(clientMembership);
    }

    public Client(int clientId, String clientFirstName, String clientSurname, int clientMembership) {
        this(clientFirstName, clientSurname, clientMembership); // This is a call to the constructor where it's just needed the first name, surname, and membership number of the client as arguments.
        this.setId(clientId);
    }

    // Setter methods ++++++++++++++++++++++++++++++++++++++
    public void setId(int clientId) {
        if(clientId == 0) {
            throw new IllegalArgumentException("The entered id is not valid: " + clientId);
        }
        this.clientId = clientId;
    }

    public void setFirst_name(String clientFirstName) {
        if(clientFirstName.isEmpty()) {
            throw new IllegalArgumentException("Client first name can't be empty: " + clientFirstName);
        }
        this.clientFirstName = clientFirstName;
    }

    public void setSurname(String clientSurname) {
        if(clientSurname.isEmpty()) {
            throw new IllegalArgumentException("Client surname can't be empty: " + clientSurname);
        }
        this.clientSurname = clientSurname;
    }

    public void setMembership(int clientMembership) {
        if(clientMembership < 0 || clientMembership == 0) {
            throw new IllegalArgumentException("The entered membership number is not valid: " + clientMembership);
        }
        this.clientMembership = clientMembership;
    }

    // Getter methods ++++++++++++++++++++++++++++++++++++++

    public int getId() {
        return clientId;
    }

    public String getFirst_name() {
        return clientFirstName;
    }

    public String getSurname() {
        return clientSurname;
    }

    public int getMembership() {
        return clientMembership;
    }

    // toString method +++++++++++++++++++++++++++++++++++++
    @Override
    public String toString() {
        return "Client{" +
                "id=" + clientId +
                ", first_name='" + clientFirstName + '\'' +
                ", surname='" + clientSurname + '\'' +
                ", membership=" + clientMembership +
                '}';
    }

    // equals and hashCode methods +++++++++++++++++++++++++
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return clientId == client.clientId && clientMembership == client.clientMembership && Objects.equals(clientFirstName, client.clientFirstName) && Objects.equals(clientSurname, client.clientSurname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, clientFirstName, clientSurname, clientMembership);
    }
}
