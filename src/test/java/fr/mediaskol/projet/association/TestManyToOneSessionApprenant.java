package fr.mediaskol.projet.association;

import fr.mediaskol.projet.bo.SessionFormation.SessionFormation;
import fr.mediaskol.projet.dal.ApprenantRepository;
import fr.mediaskol.projet.dal.SessionApprenantRepository;
import fr.mediaskol.projet.dal.SessionFormationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * Test unitaire qui permet de valider l'association ManyToOne
 * Entre SessionApprenant et Apprenant
 * Entre SessionApprenant et SessionFormation
 */

// Configure un contexte Spring Boot limité à la couche JPA
@Slf4j
@DataJpaTest
public class TestManyToOneSessionApprenant {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SessionApprenantRepository sessionApprenantRepository;

    @Autowired
    private SessionFormationRepository sessionFormationRepository;

    @Autowired
    private ApprenantRepository apprenantRepository;


}
