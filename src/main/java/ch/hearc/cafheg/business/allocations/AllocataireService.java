package ch.hearc.cafheg.business.allocations;

import ch.hearc.cafheg.business.versements.VersementService;
import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import ch.hearc.cafheg.infrastructure.persistance.VersementMapper;

public class AllocataireService {

    private final AllocataireMapper allocataireMapper;

    public AllocataireService() {
        this.allocataireMapper = new AllocataireMapper();
    }

    public AllocataireService(AllocataireMapper allocataireMapper) {
        this.allocataireMapper = allocataireMapper;
    }

    public String updateAllocataire(Allocataire allocataire) {
        return allocataireMapper.updateAllocataire(allocataire);
    }

    public String deleteAllocataire(Allocataire allocataire) {
        VersementService versementService = new VersementService(
                new VersementMapper(),
                this.allocataireMapper,
                null
        );
        if (versementService.existVersementByAllocataire(allocataire)){
            return "Impossible de supprimer l'allocataire car il a des versements";
        } else {
            return allocataireMapper.deleteAllocataire(allocataire);
        }
    }
}
