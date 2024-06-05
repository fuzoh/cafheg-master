package ch.hearc.cafheg;

import ch.hearc.cafheg.business.allocations.Allocataire;
import ch.hearc.cafheg.business.allocations.AllocataireService;
import ch.hearc.cafheg.business.allocations.AllocationService;
import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import ch.hearc.cafheg.infrastructure.persistance.Database;
import ch.hearc.cafheg.infrastructure.persistance.Migrations;
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

        var dataset = new FlatXmlDataSetBuilder().build(new FileInputStream("dataset.xml"));
        // Connects to the same db as our database services
        var databaseTester = new JdbcDatabaseTester(
                "org.h2.Driver",
                "jdbc:h2:mem:sample",
                "",
                ""
        );
        // Load the dataset into the db
        DatabaseOperation.CLEAN_INSERT.execute(databaseTester.getConnection(), dataset);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void deleteAllocataire_ShouldRemoveItFromDb(int allocataireId) {
        var allocataireMapper = new AllocataireMapper();
        var allocataireService = new AllocataireService();
        Database.inTransaction(() -> {
            var allocataire = allocataireMapper.findById(allocataireId);
            assertNotNull(allocataire);
            allocataireService.deleteAllocataire(allocataire);
            assertNull(allocataireMapper.findById(allocataireId));
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
            assert(allocataire.getNom().equals("Deguzman"));
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
