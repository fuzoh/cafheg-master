package ch.hearc.cafheg.infrastructure.persistance;

import ch.hearc.cafheg.business.allocations.Allocataire;
import ch.hearc.cafheg.business.allocations.NoAVS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AllocataireMapper extends Mapper {

  private static final String QUERY_FIND_ALL = "SELECT NOM,PRENOM,NO_AVS FROM ALLOCATAIRES";
  private static final String QUERY_FIND_WHERE_NOM_LIKE = "SELECT NOM,PRENOM,NO_AVS FROM ALLOCATAIRES WHERE NOM LIKE ?";
  private static final String QUERY_FIND_WHERE_NUMERO = "SELECT NO_AVS, NOM, PRENOM FROM ALLOCATAIRES WHERE NUMERO=?";
  private static final String QUERY_FIND_WHERE_NO_AVS = "SELECT NO_AVS, NOM, PRENOM FROM ALLOCATAIRES WHERE NO_AVS=?";
  private static final String QUERY_UPDATE = "UPDATE ALLOCATAIRES SET PRENOM=?, NOM=? WHERE NO_AVS=?";
  private static final String QUERY_DELETE = "DELETE FROM ALLOCATAIRES WHERE NO_AVS=?";
  private static final Logger logger = LoggerFactory.getLogger(AllocataireMapper.class);

  public List<Allocataire> findAll(String likeNom) {
      logger.info("findAll() {}", likeNom);
    Connection connection = activeJDBCConnection();
    try {
      PreparedStatement preparedStatement;
      if (likeNom == null) {
        logger.info("SQL: " + QUERY_FIND_ALL);
        preparedStatement = connection
            .prepareStatement(QUERY_FIND_ALL);
      } else {

        logger.info("SQL: " + QUERY_FIND_WHERE_NOM_LIKE);
        preparedStatement = connection
            .prepareStatement(QUERY_FIND_WHERE_NOM_LIKE);
        preparedStatement.setString(1, likeNom + "%");
      }
      logger.info("Allocation d'un nouveau tableau");
      List<Allocataire> allocataires = new ArrayList<>();

      logger.info("Exécution de la requête");
      try (ResultSet resultSet = preparedStatement.executeQuery()) {

        logger.info("Allocataire mapping");
        while (resultSet.next()) {
          logger.info("ResultSet#next");
          allocataires
              .add(new Allocataire(new NoAVS(resultSet.getString(3)), resultSet.getString(1),
                  resultSet.getString(2)));
        }
      }
      logger.info("Allocataires trouvés " + allocataires.size());
      return allocataires;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Allocataire findById(long id) {
    logger.info("findById() " + id);
    Connection connection = activeJDBCConnection();
    try {
      logger.info("SQL:" + QUERY_FIND_WHERE_NUMERO);
      PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND_WHERE_NUMERO);
      preparedStatement.setLong(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      logger.info("ResultSet#next");
      resultSet.next();
      logger.info("Allocataire mapping");
      return new Allocataire(new NoAVS(resultSet.getString(1)),
          resultSet.getString(2), resultSet.getString(3));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Allocataire findByNoAVS(NoAVS noAVS) {
    logger.info("findByNoAVS() " + noAVS.getValue());
    Connection connection = activeJDBCConnection();
    try {
      logger.info("SQL:" + QUERY_FIND_WHERE_NO_AVS);
      PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND_WHERE_NO_AVS);
      preparedStatement.setString(1, noAVS.getValue());
      ResultSet resultSet = preparedStatement.executeQuery();
      logger.info("ResultSet#next");
      resultSet.next();
      logger.info("Allocataire mapping");
      return new Allocataire(new NoAVS(resultSet.getString(1)),
          resultSet.getString(2), resultSet.getString(3));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public String updateAllocataire(Allocataire allocataire) {
    logger.info("updateAllocataire() " + allocataire);
    Connection connection = activeJDBCConnection();
    Allocataire baseAllocataire = findByNoAVS(allocataire.getNoAVS());
    if  (baseAllocataire == null) {
      throw new RuntimeException("Allocataire non trouvé");
    } else {
      if (!baseAllocataire.getNom().equals(allocataire.getNom()) || !baseAllocataire.getPrenom().equals(allocataire.getPrenom())) {
        try {
          PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
          preparedStatement.setString(1, allocataire.getPrenom());
          preparedStatement.setString(2, allocataire.getNom());
          preparedStatement.setString(3, allocataire.getNoAVS().getValue());
          preparedStatement.executeUpdate();
          return "Allocataire mis à jour";
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      } else {
        return "Aucune modification à apporter";
      }
    }
  }

  public String deleteAllocataire(Allocataire allocataire) {
    logger.info("deleteAllocataire() " + allocataire);
    Connection connection = activeJDBCConnection();
    Allocataire baseAllocataire = findByNoAVS(allocataire.getNoAVS());
    if  (baseAllocataire == null) {
      throw new RuntimeException("Allocataire non trouvé");
    } else {
      try {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
        preparedStatement.setString(1, allocataire.getNoAVS().getValue());
        preparedStatement.executeUpdate();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
    return "Allocataire supprimé";
  }
}
