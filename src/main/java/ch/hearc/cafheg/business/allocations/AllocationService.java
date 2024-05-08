package ch.hearc.cafheg.business.allocations;

import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import ch.hearc.cafheg.infrastructure.persistance.AllocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AllocationService {

  private static final String PARENT_1 = "Parent1";
  private static final String PARENT_2 = "Parent2";
  private static final Logger logger = LoggerFactory.getLogger(AllocationService.class);

  private final AllocataireMapper allocataireMapper;
  private final AllocationMapper allocationMapper;

  public AllocationService(
      AllocataireMapper allocataireMapper,
      AllocationMapper allocationMapper) {
    this.allocataireMapper = allocataireMapper;
    this.allocationMapper = allocationMapper;
  }

  public List<Allocataire> findAllAllocataires(String likeNom) {
    //System.out.println("Rechercher tous les allocataires");
    logger.info("Rechercher tous les allocataires");
    return allocataireMapper.findAll(likeNom);
  }

  public List<Allocation> findAllocationsActuelles() {
    return allocationMapper.findAll();
  }

  public String getParentDroitAllocation(FamilyComposition parameters) {
    //System.out.println("Déterminer quel parent a le droit aux allocations");
    logger.info("Déterminer quel parent a le droit aux allocations");

    if (parameters.getParent2Residence()==null) {
      return PARENT_1;
    } else {
      if (parameters.isParent1ActiviteLucrative() && !parameters.isParent2ActiviteLucrative()) {
        return PARENT_1;
      } else if (parameters.isParent2ActiviteLucrative() && !parameters.isParent1ActiviteLucrative()) {
        return PARENT_2;
      } else if (parameters.isParent1ActiviteLucrative() && parameters.isParent2ActiviteLucrative()) {
        if (parameters.isParent1AutoriteParentale() && !parameters.isParent2AutoriteParentale()) {
          return PARENT_1;
        } else if (parameters.isParent2AutoriteParentale() && !parameters.isParent1AutoriteParentale()) {
          return PARENT_2;
        } else if (parameters.isParent1AutoriteParentale() && parameters.isParent2AutoriteParentale()) {
          if (!parameters.getParent1Residence().equals(parameters.getParent2Residence())) {
            if (parameters.getEnfantResidence().equals(parameters.getParent1Residence())) {
              return PARENT_1;
            } else if (parameters.getEnfantResidence().equals(parameters.getParent2Residence())) {
              return PARENT_2;
            }
          } else {
            if (parameters.getParent1Workplace().equals(parameters.getEnfantResidence()) && !parameters.getParent2Workplace().equals(parameters.getEnfantResidence())) {
              return PARENT_1;
            } else if (parameters.getParent2Workplace().equals(parameters.getEnfantResidence()) && !parameters.getParent1Workplace().equals(parameters.getEnfantResidence())) {
              return PARENT_2;
            } else if (parameters.getParent1Workplace().equals(parameters.getEnfantResidence()) && parameters.getParent2Workplace().equals(parameters.getEnfantResidence())) {
              if (parameters.getParent1Salaire() > parameters.getParent2Salaire()) {
                return PARENT_1;
              } else {
                return PARENT_2;
              }
            } else if (parameters.isParent1Independant() && !parameters.isParent2Independant()) {
              return PARENT_2;
            } else if (parameters.isParent2Independant() && !parameters.isParent1Independant()) {
              return PARENT_1;
            } else if (parameters.isParent1Independant() && parameters.isParent2Independant()) {
              if (parameters.getParent1Salaire() > parameters.getParent2Salaire()) {
                return PARENT_1;
              } else {
                return PARENT_2;
              }
            } else if (!parameters.isParent1Independant() && !parameters.isParent2Independant()) {
              if (parameters.getParent1Salaire() > parameters.getParent2Salaire()) {
                return PARENT_1;
              } else {
                return PARENT_2;
              }
            }
          }
        }
      }
    }
    return null;
  }
}
