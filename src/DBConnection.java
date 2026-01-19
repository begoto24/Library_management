import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
   // Utilisation des variables de classe pour l'URL, l'utilisateur, et le mot de passe
   private static final String URL = "jdbc:postgresql://localhost:5432/Library"; 
    // URL de la base de données
   private static final String USER = "postgres"; 
    // Nom d'utilisateur PostgreSQL
   private static final String PASSWORD = "root";  
   // Mot de passe de l'utilisateur PostgreSQL

   public DBConnection() {
      // Constructeur par défaut
   }

   public static Connection getConnection() throws SQLException {
      try {
         // Charger le driver PostgreSQL
         Class.forName("org.postgresql.Driver");
         
         // Utiliser les variables de classe pour obtenir la connexion
         return DriverManager.getConnection(URL, USER, PASSWORD);
      } catch (ClassNotFoundException e) {
         System.out.println("Erreur : Driver PostgreSQL non trouvé");
         throw new SQLException("Driver PostgreSQL non trouvé", e);
      }
   }
}
