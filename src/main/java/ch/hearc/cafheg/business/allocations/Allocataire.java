package ch.hearc.cafheg.business.allocations;

public class Allocataire {

  private NoAVS noAVS;
  private String nom;
  private String prenom;

  public Allocataire(){
  }

  public Allocataire(NoAVS noAVS, String nom, String prenom) {
    this.noAVS = noAVS;
    this.nom = nom;
    this.prenom = prenom;
  }

  public String getNom() {
    return nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public NoAVS getNoAVS() {
    return noAVS;
  }

}
