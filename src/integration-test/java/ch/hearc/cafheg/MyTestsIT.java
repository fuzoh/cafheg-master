package ch.hearc.cafheg;

import ch.hearc.cafheg.business.allocations.Allocataire;
import ch.hearc.cafheg.business.allocations.AllocataireService;
import ch.hearc.cafheg.business.allocations.AllocationService;
import ch.hearc.cafheg.business.versements.VersementService;
import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import ch.hearc.cafheg.infrastructure.persistance.Database;
import ch.hearc.cafheg.infrastructure.persistance.Migrations;
import ch.hearc.cafheg.infrastructure.persistance.VersementMapper;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class MyTestsIT {

    @BeforeEach
    void setUp() throws Exception {
        // Set up the db with the provided database classes
        var database = new Database();
        var migrations = new Migrations(database);
        database.start();
        migrations.start();

        var dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream(
                Thread.currentThread().getContextClassLoader().getResource("dataset.xml").getFile()));
        // Connects to the same db as our database services
        var databaseTester = new JdbcDatabaseTester(
                "org.h2.Driver",
                "jdbc:h2:mem:sample",
                "",
                ""
        );
        // Load the dataset into the db
        DatabaseOperation.CLEAN_INSERT.execute(databaseTester.getConnection(), dataSet);
    }

    @ParameterizedTest
    @ValueSource(ints = {2})
    void deleteAllocataire_ShouldRemoveItFromDb(int allocataireId) {
        var allocataireMapper = new AllocataireMapper();
        var allocataireService = new AllocataireService();
        var versmentMapper = new VersementMapper();
        var versmentService = new VersementService(
                versmentMapper,
                new AllocataireMapper(),
                null
        );
        Database.inTransaction(() -> {
            var allocataire = allocataireMapper.findById(allocataireId);
            assertNotNull(allocataire);
            assertFalse(versmentService.existVersementByAllocataire(allocataire));
            assertEquals("Allocataire supprimé", allocataireService.deleteAllocataire(allocataire));
            return null;
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    void deleteAllocataire_ShouldNotRemoveItFromDb(int allocataireId) {
        var allocataireMapper = new AllocataireMapper();
        var allocataireService = new AllocataireService();
        var versmentMapper = new VersementMapper();
        var versmentService = new VersementService(
                versmentMapper,
                new AllocataireMapper(),
                null
        );
        Database.inTransaction(() -> {
            var allocataire = allocataireMapper.findById(allocataireId);
            assertNotNull(allocataire);
            assertTrue(versmentService.existVersementByAllocataire(allocataire));
            assertEquals("Impossible de supprimer l'allocataire car il a des versements", allocataireService.deleteAllocataire(allocataire));
            return null;
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"Super new name"})
    void updateAllocataire_GivenNewName_ShouldUpdateInDatabase(String newName) {
        var allocataireMapper = new AllocataireMapper();
        var allocataireService = new AllocataireService();
        Database.inTransaction(() -> {
            var allocataire = allocataireMapper.findById(1);
            assert (allocataire.getNom().equals("Deguzman"));
            var newAllocataire = new Allocataire(
                    allocataire.getNoAVS(),
                    newName,
                    allocataire.getPrenom()
            );
            allocataireService.updateAllocataire(newAllocataire);
            assertNotEquals(allocataire.getNom(), newAllocataire.getNom());
            assertEquals(newName, allocataireMapper.findById(1).getNom());
            return null;
        });
    }

}
