package ch.hearc.cafheg.business.allocations;

import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;

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
}
