package ch.hearc.cafheg.business.allocations;

import javax.xml.crypto.dsig.CanonicalizationMethod;

public class FamilyComposition {
    private Canton enfantResidence;
    private Canton parent1Residence;
    private Canton parent2Residence;
    private boolean parent1ActiviteLucrative;
    private boolean parent2ActiviteLucrative;
    private boolean parentsEnsemble;
    private int parent1Salaire;
    private int parent2Salaire;
    private boolean parent1AutoriteParentale;
    private boolean parent2AutoriteParentale;
    private Canton parent1Workplace;
    private Canton parent2Workplace;
    private boolean isParent1Independant;
    private boolean isParent2Independant;

    public FamilyComposition(Canton enfantResidence, Canton parent1Residence, boolean parent1ActiviteLucrative, boolean parentsEnsemble, int parent1Salaire, boolean parent1AutoriteParentale, Canton parent1Workplace, boolean isParent1Independant) {
        this.enfantResidence = enfantResidence;
        this.parent1Residence = parent1Residence;
        this.parent1ActiviteLucrative = parent1ActiviteLucrative;
        this.parentsEnsemble = parentsEnsemble;
        this.parent1Salaire = parent1Salaire;
        this.parent1AutoriteParentale = parent1AutoriteParentale;
        this.parent1Workplace = parent1Workplace;
        this.isParent1Independant = isParent1Independant;
    }

    public FamilyComposition(Canton enfantResidence, Canton parent1Residence, Canton parent2Residence, boolean parent1ActiviteLucrative, boolean parent2ActiviteLucrative, boolean parentsEnsemble, int parent1Salaire, int parent2Salaire, boolean parent1AutoriteParentale, boolean parent2AutoriteParentale, Canton parent1Workplace, Canton parent2Workplace, boolean isParent1Independant, boolean isParent2Independant) {
        this.enfantResidence = enfantResidence;
        this.parent1Residence = parent1Residence;
        this.parent2Residence = parent2Residence;
        this.parent1ActiviteLucrative = parent1ActiviteLucrative;
        this.parent2ActiviteLucrative = parent2ActiviteLucrative;
        this.parentsEnsemble = parentsEnsemble;
        this.parent1Salaire = parent1Salaire;
        this.parent2Salaire = parent2Salaire;
        this.parent1AutoriteParentale = parent1AutoriteParentale;
        this.parent2AutoriteParentale = parent2AutoriteParentale;
        this.parent1Workplace = parent1Workplace;
        this.parent2Workplace = parent2Workplace;
        this.isParent1Independant = isParent1Independant;
        this.isParent2Independant = isParent2Independant;
    }

    public Canton getEnfantResidence() {
        return enfantResidence;
    }

    public void setEnfantResidence(Canton enfantResidence) {
        this.enfantResidence = enfantResidence;
    }

    public Canton getParent1Residence() {
        return parent1Residence;
    }

    public void setParent1Residence(Canton parent1Residence) {
        this.parent1Residence = parent1Residence;
    }

    public Canton getParent2Residence() {
        return parent2Residence;
    }

    public void setParent2Residence(Canton parent2Residence) {
        this.parent2Residence = parent2Residence;
    }

    public boolean isParent1ActiviteLucrative() {
        return parent1ActiviteLucrative;
    }

    public void setParent1ActiviteLucrative(boolean parent1ActiviteLucrative) {
        this.parent1ActiviteLucrative = parent1ActiviteLucrative;
    }

    public boolean isParent2ActiviteLucrative() {
        return parent2ActiviteLucrative;
    }

    public void setParent2ActiviteLucrative(boolean parent2ActiviteLucrative) {
        this.parent2ActiviteLucrative = parent2ActiviteLucrative;
    }

    public boolean isParentsEnsemble() {
        return parentsEnsemble;
    }

    public void setParentsEnsemble(boolean parentsEnsemble) {
        this.parentsEnsemble = parentsEnsemble;
    }

    public int getParent1Salaire() {
        return parent1Salaire;
    }

    public void setParent1Salaire(int parent1Salaire) {
        this.parent1Salaire = parent1Salaire;
    }

    public int getParent2Salaire() {
        return parent2Salaire;
    }

    public void setParent2Salaire(int parent2Salaire) {
        this.parent2Salaire = parent2Salaire;
    }

    public boolean isParent1AutoriteParentale() {
        return parent1AutoriteParentale;
    }

    public void setParent1AutoriteParentale(boolean parent1AutoriteParentale) {
        this.parent1AutoriteParentale = parent1AutoriteParentale;
    }

    public boolean isParent2AutoriteParentale() {
        return parent2AutoriteParentale;
    }

    public void setParent2AutoriteParentale(boolean parent2AutoriteParentale) {
        this.parent2AutoriteParentale = parent2AutoriteParentale;
    }

    public Canton getParent1Workplace() {
        return parent1Workplace;
    }

    public void setParent1Workplace(Canton parent1Workplace) {
        this.parent1Workplace = parent1Workplace;
    }

    public Canton getParent2Workplace() {
        return parent2Workplace;
    }

    public void setParent2Workplace(Canton parent2Workplace) {
        this.parent2Workplace = parent2Workplace;
    }

    public boolean isParent1Independant() {
        return isParent1Independant;
    }

    public void setParent1Independant(boolean parent1Independant) {
        isParent1Independant = parent1Independant;
    }

    public boolean isParent2Independant() {
        return isParent2Independant;
    }

    public void setParent2Independant(boolean parent2Independant) {
        isParent2Independant = parent2Independant;
    }
}
