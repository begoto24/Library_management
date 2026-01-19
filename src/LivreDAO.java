import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreDAO {

    private Connection conn;

    public LivreDAO(Connection conn) {
        this.conn = conn;
    }

    // Ajouter un livre dans la base de données
    public void ajouterLivre(Livre livre) throws SQLException {
        String query = "INSERT INTO livres (titre, auteur, categorie, nombreExemplaires) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, livre.getTitre());
            stmt.setString(2, livre.getAuteur());
            stmt.setString(3, livre.getCategorie());
            stmt.setInt(4, livre.getNombreExemplaires());
            stmt.executeUpdate();
        }
    }

    // Rechercher un livre par titre
    public Livre rechercherLivreParTitre(String titre) throws SQLException {
        String query = "SELECT * FROM livres WHERE titre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, titre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Livre(
                    rs.getInt("id"),
                    rs.getString("titre"),
                    rs.getString("auteur"),
                    rs.getString("categorie"),
                    rs.getInt("nombreExemplaires")
                );
            }
        }
        return null;  
        // Aucun livre trouvé
    }

    // Rechercher tous les livres disponibles
    public List<Livre> afficherLivres() throws SQLException {
        List<Livre> livres = new ArrayList<>();
        String query = "SELECT * FROM livres";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                livres.add(new Livre(
                    rs.getInt("id"),
                    rs.getString("titre"),
                    rs.getString("auteur"),
                    rs.getString("categorie"),
                    rs.getInt("nombreExemplaires")
                ));
            }
        }
        return livres;
    }
}

