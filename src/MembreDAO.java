import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembreDAO {

    private Connection conn;

    public MembreDAO(Connection conn) {
        this.conn = conn;
    }

    // Ajouter un membre dans la base de données
    public void ajouterMembre(Membre membre) throws SQLException {
        String query = "INSERT INTO membres (nom, prenom, email, adhesionDate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, membre.getNom());
            stmt.setString(2, membre.getPrenom());
            stmt.setString(3, membre.getEmail());

            // Conversion de la chaîne en java.sql.Date
            Date adhesionDate = Date.valueOf(membre.getAdhesionDate());  
            // Conversion automatique de String à Date
            stmt.setDate(4, adhesionDate);
             // Insertion dans la base de données

            stmt.executeUpdate();
            System.out.println("Membre ajouté avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du membre");
            e.printStackTrace();
        }
    }

    // Supprimer un membre par son ID
    public void supprimerMembre(int id) throws SQLException {
        String query = "DELETE FROM membres WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Membre supprimé avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du membre");
            e.printStackTrace();
        }
    }

    // Rechercher un membre par son nom
    public Membre rechercherMembreParNom(String nom) throws SQLException {
        String query = "SELECT * FROM membres WHERE nom = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nom);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Membre(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("adhesionDate")
                );
            }
        }
        return null;  
        // Aucun membre trouvé
    }

    // Afficher tous les membres
    public List<Membre> afficherMembres() throws SQLException {
        List<Membre> membres = new ArrayList<>();
        String query = "SELECT * FROM membres";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                membres.add(new Membre(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("adhesionDate")
                ));
            }
        }
        return membres;
    }
}
