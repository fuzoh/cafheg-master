package ch.hearc.cafheg.business.allocations;

import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AllocataireServiceTest {
    private AllocataireService allocataireService;
    private AllocataireMapper allocataireMapper;

    @BeforeEach
    void setUp() {
        allocataireMapper = Mockito.mock(AllocataireMapper.class);
        allocataireService = new AllocataireService(allocataireMapper);
    }

    @Test
    void updateAllocataire() {
        Allocataire allocataire = new Allocataire(new NoAVS("1000-2000"), "Geiser", "Arnaud");
        Mockito.when(allocataireMapper.updateAllocataire(allocataire)).thenReturn("success");
        String result = allocataireService.updateAllocataire(allocataire);
        assert(result.equals("success"));
    }
}
