public class Membre {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String adhesionDate;

    // Constructeur
    public Membre(int id, String nom, String prenom, String email, String adhesionDate) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.adhesionDate = adhesionDate;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdhesionDate() {
        return adhesionDate;
    }

    public void setAdhesionDate(String adhesionDate) {
        this.adhesionDate = adhesionDate;
    }

    // Méthode pour afficher les détails du membre
    public void afficherDetails() {
        System.out.println("Nom: " + nom);
        System.out.println("Prénom: " + prenom);
        System.out.println("Email: " + email);
        System.out.println("Date d'adhésion: " + adhesionDate);
    }
}

