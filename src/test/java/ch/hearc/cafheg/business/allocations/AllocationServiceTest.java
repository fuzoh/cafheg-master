package ch.hearc.cafheg.business.allocations;

import static ch.hearc.cafheg.business.allocations.Canton.BE;
import static ch.hearc.cafheg.business.allocations.Canton.NE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import ch.hearc.cafheg.business.common.Montant;
import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import ch.hearc.cafheg.infrastructure.persistance.AllocationMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AllocationServiceTest {

  private AllocationService allocationService;

  private AllocataireMapper allocataireMapper;
  private AllocationMapper allocationMapper;

  @BeforeEach
  void setUp() {
    allocataireMapper = Mockito.mock(AllocataireMapper.class);
    allocationMapper = Mockito.mock(AllocationMapper.class);

    allocationService = new AllocationService(allocataireMapper, allocationMapper);
  }

  @Test
  void findAllAllocataires_GivenEmptyAllocataires_ShouldBeEmpty() {
    Mockito.when(allocataireMapper.findAll("Geiser")).thenReturn(Collections.emptyList());
    List<Allocataire> all = allocationService.findAllAllocataires("Geiser");
    assertThat(all).isEmpty();
  }

  @Test
  void findAllAllocataires_Given2Geiser_ShouldBe2() {
    Mockito.when(allocataireMapper.findAll("Geiser"))
        .thenReturn(Arrays.asList(new Allocataire(new NoAVS("1000-2000"), "Geiser", "Arnaud"),
            new Allocataire(new NoAVS("1000-2001"), "Geiser", "Aurélie")));
    List<Allocataire> all = allocationService.findAllAllocataires("Geiser");
    assertAll(() -> assertThat(all.size()).isEqualTo(2),
        () -> assertThat(all.get(0).getNoAVS()).isEqualTo(new NoAVS("1000-2000")),
        () -> assertThat(all.get(0).getNom()).isEqualTo("Geiser"),
        () -> assertThat(all.get(0).getPrenom()).isEqualTo("Arnaud"),
        () -> assertThat(all.get(1).getNoAVS()).isEqualTo(new NoAVS("1000-2001")),
        () -> assertThat(all.get(1).getNom()).isEqualTo("Geiser"),
        () -> assertThat(all.get(1).getPrenom()).isEqualTo("Aurélie"));
  }

  @Test
  void findAllocationsActuelles() {
    Mockito.when(allocationMapper.findAll())
        .thenReturn(Arrays.asList(new Allocation(new Montant(new BigDecimal(1000)), NE,
            LocalDate.now(), null), new Allocation(new Montant(new BigDecimal(2000)), Canton.FR,
            LocalDate.now(), null)));
    List<Allocation> all = allocationService.findAllocationsActuelles();
    assertAll(() -> assertThat(all.size()).isEqualTo(2),
        () -> assertThat(all.get(0).getMontant()).isEqualTo(new Montant(new BigDecimal(1000))),
        () -> assertThat(all.get(0).getCanton()).isEqualTo(NE),
        () -> assertThat(all.get(0).getDebut()).isEqualTo(LocalDate.now()),
        () -> assertThat(all.get(0).getFin()).isNull(),
        () -> assertThat(all.get(1).getMontant()).isEqualTo(new Montant(new BigDecimal(2000))),
        () -> assertThat(all.get(1).getCanton()).isEqualTo(Canton.FR),
        () -> assertThat(all.get(1).getDebut()).isEqualTo(LocalDate.now()),
        () -> assertThat(all.get(1).getFin()).isNull());
  }

  @Test
  void getParentDroitAllocation() {
    FamilyComposition parameters = new FamilyComposition(NE, NE, BE, true, true, false, 2500, 3000, true, true, NE, BE, false, false);
    assertThat(allocationService.getParentDroitAllocation(parameters)).isEqualTo("Parent1");
  }

  @Test
  void getParentDroitAllocation_GivenParent1AL_ShouldBeParent1() {
    FamilyComposition parameters = new FamilyComposition(NE, NE, NE, true, false, false, 2500, 3000, true, true, NE, NE, false, false);
        assertThat(allocationService.getParentDroitAllocation(parameters)).isEqualTo("Parent1");
  }

  @Test
  void getParentDroitAllocation_Given1ParentAL_ShouldBeParent1() {
    FamilyComposition parameters = new FamilyComposition(NE, NE, true, false, 2500,true, NE, false);
    assertThat(allocationService.getParentDroitAllocation(parameters)).isEqualTo("Parent1");
  }

  @Test
  void getParentDroitAllocation_Given2ParentAL1ParentAP_ShouldBeParent1() {
    FamilyComposition parameters = new FamilyComposition(NE, NE, NE, true, true, false, 2500, 3000,true,false, NE, NE, false, false);
    assertThat(allocationService.getParentDroitAllocation(parameters)).isEqualTo("Parent1");
  }

  @Test
  void getParentDroitAllocation_Given2ParentAL2ParentAPParent1RDifferent_ShouldBeParent2() {
    FamilyComposition parameters = new FamilyComposition(NE, BE, NE, true, true, false, 2500, 3000, true, true, NE, NE, false, false);
    assertThat(allocationService.getParentDroitAllocation(parameters)).isEqualTo("Parent2");
  }

  @Test
  void getParentDroitAllocation_Given2ParentAL2ParentAP2ParentREParent1WorkSameCantonEnfant_ShouldBeParent1() {
    FamilyComposition parameters = new FamilyComposition(NE, NE, NE, true, true, false, 2500, 3000, true, true, NE, BE, false, false);
    assertThat(allocationService.getParentDroitAllocation(parameters)).isEqualTo("Parent1");
  }

  @Test
  void getParentDroitAllocation_Given2ParentAL2ParentAP2ParentREParent1Independant_ShouldBeParent2() {
    FamilyComposition parameters = new FamilyComposition(NE, NE, NE, true, true, false, 2500, 3000, true, true, NE, NE, true, false);
    assertThat(allocationService.getParentDroitAllocation(parameters)).isEqualTo("Parent2");
  }

  @Test
  void getParentDroitAllocation_Given2ParentAL2ParentAP2ParentREParent2RevenueHigher_ShouldBeParent2() {
    FamilyComposition parameters = new FamilyComposition(NE, NE, NE, true, true, false, 2500, 3000, true, true, BE, BE, false, false);
    assertThat(allocationService.getParentDroitAllocation(parameters)).isEqualTo("Parent2");
  }

  @Test
  void getParentDroitAllocation_Given2ParentAL2ParentAP2ParentRE2ParentIndependantParent1RevenueHigher_ShouldBeParent1() {
    FamilyComposition parameters = new FamilyComposition(NE, NE, NE, true, true, false, 3500, 3000, true, true, NE, BE, true, true);
    assertThat(allocationService.getParentDroitAllocation(parameters)).isEqualTo("Parent1");
  }

}