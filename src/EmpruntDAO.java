import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpruntDAO {

    private Connection conn;

    public EmpruntDAO(Connection conn) {
        this.conn = conn;
    }

    // Enregistrer un emprunt
    public void enregistrerEmprunt(Emprunt emprunt) throws SQLException {
        String query = "INSERT INTO emprunts (membreId, livreId, dateEmprunt, dateRetourPrevue, dateRetourEffective) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, emprunt.getMembreId());
            stmt.setInt(2, emprunt.getLivreId());
            stmt.setDate(3, new java.sql.Date(emprunt.getDateEmprunt().getTime()));
            stmt.setDate(4, new java.sql.Date(emprunt.getDateRetourPrevue().getTime()));
            stmt.setDate(5, null); 
            // dateRetourEffective est null au départ
            stmt.executeUpdate();
        }
    }

    // Retourner un livre et mettre à jour la date de retour effective
    public void retournerLivre(int empruntId, Date dateRetourEffective) throws SQLException {
        String queryUpdateEmprunt = "UPDATE emprunts SET dateRetourEffective = ? WHERE idEmprunt = ?";
        try (PreparedStatement stmt = conn.prepareStatement(queryUpdateEmprunt)) {
            stmt.setDate(1, new java.sql.Date(dateRetourEffective.getTime()));
            stmt.setInt(2, empruntId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Retour du livre mis à jour avec succès.");
                // Récupérer l'emprunt et calculer la pénalité
                Emprunt emprunt = rechercherEmpruntParId(empruntId);
                if (emprunt != null) {
                    int penalite = calculerPenalite(emprunt);
                    System.out.println("Pénalité pour retard : " + penalite + " F CFA");
                }
            } else {
                System.out.println("Aucun emprunt trouvé avec cet ID.");
            }
        }
    }

    // Rechercher un emprunt par ID
    public Emprunt rechercherEmpruntParId(int empruntId) throws SQLException {
        String query = "SELECT * FROM emprunts WHERE idEmprunt = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, empruntId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Emprunt(
                        rs.getInt("idEmprunt"),
                        rs.getInt("membreId"),
                        rs.getInt("livreId"),
                        rs.getDate("dateEmprunt"),
                        rs.getDate("dateRetourPrevue"),
                        rs.getDate("dateRetourEffective"));
            }
        }
        return null;
    }

    // Afficher les emprunts en retard
   public List<Emprunt> afficherEmpruntsEnRetard() throws SQLException {
    List<Emprunt> empruntsEnRetard = new ArrayList<>();
    
    // Requête modifiée pour PostgreSQL : utilisation de CURRENT_DATE
    String query = "SELECT * FROM emprunts WHERE dateRetourEffective > dateRetourPrevue AND dateRetourPrevue < CURRENT_DATE";
    
    try (Statement stmt = conn.createStatement()) {
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Emprunt emprunt = new Emprunt(
                rs.getInt("idEmprunt"),
                rs.getInt("membreId"),
                rs.getInt("livreId"),
                rs.getDate("dateEmprunt"),
                rs.getDate("dateRetourPrevue"),
                rs.getDate("dateRetourEffective")
            );
            empruntsEnRetard.add(emprunt);
        }
    }
    System.out.println("Nombre d'emprunts en retard : " + empruntsEnRetard.size());  
    // Message de débogage
    return empruntsEnRetard;
}


    // Calculer la pénalité d'un emprunt (par exemple, 100 F CFA par jour de retard)
    public int calculerPenalite(Emprunt emprunt) {
        if (emprunt.getDateRetourEffective() == null) {
            return 0;
        }

        long millis = emprunt.getDateRetourEffective().getTime() - emprunt.getDateRetourPrevue().getTime();
        long joursDeRetard = millis / (1000 * 60 * 60 * 24);

        if (joursDeRetard > 0) {
            return (int) (joursDeRetard * 100); 
            // 100 F CFA par jour de retard
        }

        return 0; 
        // Pas de pénalité si pas de retard
    }
}
