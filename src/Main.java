import java.sql.*;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection conn = null;

        try {
            // Connexion à la base de données
            conn = DBConnection.getConnection();
            System.out.println("Connexion à la base de données réussie !");

            // Création des objets DAO
            LivreDAO livreDAO = new LivreDAO(conn);
            MembreDAO membreDAO = new MembreDAO(conn);
            EmpruntDAO empruntDAO = new EmpruntDAO(conn);

            while (true) {
                // Affichage du menu
                System.out.println("\nMenu :");
                System.out.println("1. Ajouter un livre");
                System.out.println("2. Rechercher un livre");
                System.out.println("3. Inscrire un membre");
                System.out.println("4. Enregistrer un emprunt");
                System.out.println("5. Afficher les emprunts en retard");
                System.out.println("6. Retourner un livre");
                System.out.println("7. Quitter");

                // Demander à l'utilisateur de faire un choix
                System.out.print("Choisissez une option : ");
                int choix = scanner.nextInt();

                switch (choix) {
                    case 1:
                        // Ajouter un livre
                        System.out.print("Entrez le titre du livre : ");
                        scanner.nextLine(); 
                        // Consommer le retour à la ligne
                        String titre = scanner.nextLine();
                        System.out.print("Entrez l'auteur du livre : ");
                        String auteur = scanner.nextLine();
                        System.out.print("Entrez la catégorie du livre : ");
                        String categorie = scanner.nextLine();
                        System.out.print("Entrez le nombre d'exemplaires : ");
                        int nombreExemplaires = scanner.nextInt();

                        Livre livre = new Livre(0, titre, auteur, categorie, nombreExemplaires);
                        livreDAO.ajouterLivre(livre);
                        System.out.println("Livre ajouté avec succès.");
                        break;

                    case 2:
                        // Rechercher un livre
                        System.out.print("Entrez le titre du livre à rechercher : ");
                        scanner.nextLine(); 
                        // Consommer le retour à la ligne
                        String titreRecherche = scanner.nextLine();
                        Livre livreTrouve = livreDAO.rechercherLivreParTitre(titreRecherche);
                        if (livreTrouve != null) {
                            livreTrouve.afficherDetails();
                        } else {
                            System.out.println("Livre non trouvé.");
                        }
                        break;

                    case 3:
                        // Inscrire un membre
                        scanner.nextLine(); 
                        // Consommer le retour à la ligne
                        System.out.print("Entrez le nom du membre : ");
                        String nom = scanner.nextLine();
                        System.out.print("Entrez le prénom du membre : ");
                        String prenom = scanner.nextLine();
                        System.out.print("Entrez l'email du membre : ");
                        String email = scanner.nextLine();
                        System.out.print("Entrez la date d'adhésion (YYYY-MM-DD ou DD-MM-YYYY) : ");
                        String adhesionDate = scanner.nextLine();

                        // Conversion de la date d'adhésion
                        java.sql.Date adhesionDateConverted = convertDateFormat(adhesionDate);
                        if (adhesionDateConverted == null) {
                            System.out.println("Erreur : La date d'adhésion n'est pas valide.");
                            break;
                        }

                        Membre membre = new Membre(0, nom, prenom, email, adhesionDateConverted.toString());
                        membreDAO.ajouterMembre(membre);
                        System.out.println("Membre ajouté avec succès.");
                        break;

                    case 4:
                        // Enregistrer un emprunt
                        System.out.print("Entrez l'ID du membre : ");
                        int membreId = scanner.nextInt();
                        System.out.print("Entrez l'ID du livre : ");
                        int livreId = scanner.nextInt();
                        System.out.print("Entrez la date de retour prévue (YYYY-MM-DD ou DD-MM-YYYY) : ");
                        scanner.nextLine(); 
                        // Consommer le retour à la ligne
                        String dateRetourPrevueStr = scanner.nextLine();

                        // Conversion de la date de retour prévue
                        java.sql.Date dateRetourPrevue = convertDateFormat(dateRetourPrevueStr);
                        if (dateRetourPrevue == null) {
                            System.out.println("Erreur : La date de retour n'est pas valide.");
                            break;
                        }

                        Emprunt emprunt = new Emprunt(0, membreId, livreId,
                                new java.sql.Date(System.currentTimeMillis()), dateRetourPrevue, null);
                        empruntDAO.enregistrerEmprunt(emprunt);
                        System.out.println("Emprunt enregistré avec succès.");
                        break;

                    case 5:
                        // Afficher les emprunts en retard
                        System.out.println("Affichage des emprunts en retard :");
                        List<Emprunt> empruntsEnRetard = empruntDAO.afficherEmpruntsEnRetard();

                        // Vérifier si la liste est vide
                        if (empruntsEnRetard.isEmpty()) {
                            System.out.println("Aucun emprunt en retard.");
                        } else {
                            // Afficher chaque emprunt en retard
                            for (Emprunt empruntRetard : empruntsEnRetard) {
                                System.out.println("Emprunt ID: " + empruntRetard.getIdEmprunt());
                                System.out.println("Membre ID: " + empruntRetard.getMembreId());
                                System.out.println("Livre ID: " + empruntRetard.getLivreId());
                                System.out.println("Date retour prévue: " + empruntRetard.getDateRetourPrevue());
                                System.out.println("Date retour effective: " + empruntRetard.getDateRetourEffective());
                                System.out.println("-------------------------");
                            }
                        }
                        break;

                    case 6:
                        // Retourner un livre
                        System.out.print("Entrez l'ID de l'emprunt : ");
                        int empruntId = scanner.nextInt();
                        System.out.print("Entrez la date de retour effective (YYYY-MM-DD ou DD-MM-YYYY) : ");
                        scanner.nextLine(); 
                        // Consommer le retour à la ligne
                        String dateRetourStr = scanner.nextLine();

                        // Conversion de la date de retour
                        java.sql.Date dateRetour = convertDateFormat(dateRetourStr);
                        if (dateRetour == null) {
                            System.out.println("Erreur : La date de retour n'est pas valide.");
                            break;
                        }

                        empruntDAO.retournerLivre(empruntId, dateRetour);
                        break;

                    case 7:
                        // Quitter le programme
                        System.out.println("Au revoir !");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Choix invalide, veuillez réessayer.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur de connexion ou d'exécution SQL");
            e.printStackTrace();
        } finally {
            // Fermer la connexion
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Méthode pour convertir les dates dans le bon format (YYYY-MM-DD ou
    // DD-MM-YYYY)
    private static java.sql.Date convertDateFormat(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;

        // Essayer de convertir le format 'YYYY-MM-DD'
        try {
            date = sdf.parse(dateStr);
            return new java.sql.Date(date.getTime()); 
            // Conversion en Date SQL
        } catch (ParseException e1) {
            // Si le format est incorrect, essayer un autre format 'DD-MM-YYYY'
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
            try {
                date = sdf2.parse(dateStr);
                return new java.sql.Date(date.getTime()); 
                // Conversion en Date SQL
            } catch (ParseException e2) {
                System.out.println("Erreur : La date n'est pas au format valide (YYYY-MM-DD ou DD-MM-YYYY).");
                return null;
            }
        }
    }
}