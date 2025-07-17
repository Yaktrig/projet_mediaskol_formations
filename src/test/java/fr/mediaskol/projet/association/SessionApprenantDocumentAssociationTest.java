package fr.mediaskol.projet.association;

import fr.mediaskol.projet.bo.sessionFormation.SessionFormation;
import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.bo.apprenant.SessionApprenant;
import fr.mediaskol.projet.bo.apprenant.StatutNumPasseport;
import fr.mediaskol.projet.bo.document.CategorieDocument;
import fr.mediaskol.projet.bo.document.SessionApprenantDocument;
import fr.mediaskol.projet.bo.document.StatutSessionApprenantDocument;
import fr.mediaskol.projet.dal.apprenant.ApprenantRepository;
import fr.mediaskol.projet.dal.apprenant.SessionApprenantRepository;
import fr.mediaskol.projet.dal.document.CategorieDocumentRepository;
import fr.mediaskol.projet.dal.document.SessionApprenantDocumentRepository;
import fr.mediaskol.projet.dal.sessionFormation.SessionFormationRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test unitaire qui permet de valider l'association ManyToOne
 * Entre SessionApprenantDocument et SessionApprenant
 * Entre SessionApprenantDocument et CategorieDocument
 */

// Configure un contexte Spring Boot limité à la couche JPA
@Slf4j
@DataJpaTest
public class SessionApprenantDocumentAssociationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SessionApprenantDocumentRepository sessionApprenantDocumentRepository;

    @Autowired
    private SessionApprenantRepository sessionApprenantRepository;

    @Autowired
    private CategorieDocumentRepository categorieDocumentRepository;

    @Autowired
    private SessionFormationRepository sessionFormationRepository;

    private SessionApprenant sessionTigrou;

    private Apprenant tigrou;

    private CategorieDocument bulletinInscription;

    private CategorieDocument agrement;

    private SessionFormation sessionMICE;

    @Autowired
    private ApprenantRepository apprenantRepository;

    @BeforeEach
    public void init() {

        // Création d'une nouvelle session de formation avec le builder Lombok
        sessionMICE = SessionFormation
                .builder()
                .noYoda("123456")
                .dateDebutSession(LocalDate.parse("2026-01-07"))
                .nbHeureSession(14)
                .libelleSessionFormation("MICE24052025")
                .build();

        // Persitence de la session de formation dans la base
        sessionFormationRepository.save(sessionMICE);

        // Création d'un nouvel apprenant avec le builder Lombok
        tigrou = Apprenant
                .builder()
                .nom("Le tigre")
                .prenom("Tigrou")
                .email("tigrou.letigre@gmail.fr")
                .numPortable("0600000000")
                .dateNaissance(LocalDate.parse("2000-12-12"))
                .apprenantActif(true)
                .numPasseport("A123456")
                .commentaireApprenant("Fait des bonds partout")
                .statutNumPasseport(StatutNumPasseport.NUM_PASSEPORT_A_CREER)
                .build();

        // Sauvegarde dans la base de test l'apprenant Tigrou
        apprenantRepository.save(tigrou);


        // Création d'une 1ère session apprenant de formation avec le builder Lombok
        sessionTigrou = SessionApprenant
                .builder()
                .modeReceptionInscription("mail")
                .apprenant(tigrou)
                .sessionFormation(sessionMICE)
                .build();

        // Sauvegarde dans la base de test la session de l'apprenant tigrou
        sessionApprenantRepository.save(sessionTigrou);

//        // Associaton de l'apprenant à sa session apprenant
//        sessionTigrou.setApprenant(tigrou);
//
//        // Association de la session de formation à la session apprenant
//        sessionTigrou.setSessionFormation(sessionMICE);


        bulletinInscription = CategorieDocument
                .builder()
                .libelleCategorieDocument("Bulletin d'inscription")
                .build();

        agrement = CategorieDocument
                .builder()
                .libelleCategorieDocument("Agrément")
                .build();

        categorieDocumentRepository.save(bulletinInscription);

        categorieDocumentRepository.save(agrement);


    }

    @Test
    public void test_save_session_apprenant_document() {

        // Création de la session apprenant document avec le builder Lombok
        final SessionApprenantDocument sessionTigrouDocBI = SessionApprenantDocument
                .builder()
                .nomSessionApprenantDocument("bulletin inscription tigrou")
                .dateSessionApprenantDocument(LocalDate.parse("2025-07-01"))
                .statutSessionApprenantDocument(StatutSessionApprenantDocument.DOCUMENT_CONFORME)
                .build();

        // Association ManyToOne
        sessionTigrouDocBI.setSessionApprenant(sessionTigrou);
        sessionTigrouDocBI.setCategorieDocument(bulletinInscription);


        // Sauvegarde de la session apprenant document dans la base de test
        final SessionApprenantDocument sessionTigrouDocBIDB = sessionApprenantDocumentRepository.save(sessionTigrouDocBI);

        // Vérifie que l'objet retourné n'est pas nul
        assertThat(sessionTigrouDocBIDB.getIdSessionApprenantDocument()).isGreaterThan(0);

        // Vérifie que la session de l'apprenant existe
        assertThat(sessionTigrouDocBIDB.getSessionApprenant()).isNotNull();
        assertThat(sessionTigrouDocBIDB.getSessionApprenant()).isEqualTo(sessionTigrou);

        // Vérifie que la catégorie du document "bulletin inscription tigrou" existe
        assertThat(sessionTigrouDocBIDB.getCategorieDocument()).isNotNull();
        assertThat(sessionTigrouDocBIDB.getCategorieDocument()).isEqualTo(bulletinInscription);

        // Log pour visualiser l'objet persisté
        log.info(sessionTigrouDocBIDB.toString());


        // Création de la session apprenant document avec le builder Lombok
        final SessionApprenantDocument sessionTigrouDocAgrement = SessionApprenantDocument
                .builder()
                .nomSessionApprenantDocument("agrément tigrou")
                .dateSessionApprenantDocument(LocalDate.parse("2025-06-01"))
                .statutSessionApprenantDocument(StatutSessionApprenantDocument.DOCUMENT_NON_RECU)
                .build();

        // Association ManyToOne
        sessionTigrouDocAgrement.setSessionApprenant(sessionTigrou);
        sessionTigrouDocAgrement.setCategorieDocument(agrement);


        // Sauvegarde de la session apprenant document dans la base de test
        final SessionApprenantDocument sessionTigrouDocAgrementDB = sessionApprenantDocumentRepository.save(sessionTigrouDocAgrement);

        // Vérifie que l'objet retourné n'est pas nul
        assertThat(sessionTigrouDocAgrementDB.getIdSessionApprenantDocument()).isGreaterThan(0);

        // Vérifie que la session de l'apprenant existe
        assertThat(sessionTigrouDocAgrementDB.getSessionApprenant()).isNotNull();
        assertThat(sessionTigrouDocAgrementDB.getSessionApprenant()).isEqualTo(sessionTigrou);

        // Vérifie que la catégorie du document "agrément tigrou" existe
        assertThat(sessionTigrouDocAgrementDB.getCategorieDocument()).isNotNull();
        assertThat(sessionTigrouDocAgrementDB.getCategorieDocument()).isEqualTo(agrement);

        // Log pour visualiser l'objet persisté
        log.info(sessionTigrouDocAgrementDB.toString());


    }


    @Test
    public void test_delete_session_apprenant_document() {

        // Création de la session apprenant document avec le builder Lombok
        final SessionApprenantDocument sessionTigrouDocBI = SessionApprenantDocument
                .builder()
                .nomSessionApprenantDocument("bulletin inscription tigrou")
                .dateSessionApprenantDocument(LocalDate.parse("2025-07-01"))
                .statutSessionApprenantDocument(StatutSessionApprenantDocument.DOCUMENT_CONFORME)
                .build();

        // Association ManyToOne
        sessionTigrouDocBI.setSessionApprenant(sessionTigrou);
        sessionTigrouDocBI.setCategorieDocument(bulletinInscription);

        // Sauvegarde de la session apprenant document dans la base de test
        final SessionApprenantDocument sessionTigrouDocBIDB = sessionApprenantDocumentRepository.save(sessionTigrouDocBI);

        // Vérifie que l'objet retourné n'est pas nul
        assertThat(sessionTigrouDocBIDB.getIdSessionApprenantDocument()).isGreaterThan(0);

        // Vérifie que la session de l'apprenant existe
        assertThat(sessionTigrouDocBIDB.getSessionApprenant()).isNotNull();
        assertThat(sessionTigrouDocBIDB.getSessionApprenant()).isEqualTo(sessionTigrou);

        // Vérifie que la catégorie du document "bulletin inscription tigrou" existe
        assertThat(sessionTigrouDocBIDB.getCategorieDocument()).isNotNull();
        assertThat(sessionTigrouDocBIDB.getCategorieDocument()).isEqualTo(bulletinInscription);

        // Suppression du document de la sessionApprenantDocument
        sessionApprenantDocumentRepository.delete(sessionTigrouDocBIDB);

        // Vérifie que l'entité SessionApprenantDocument n'est plus présente en base (doit être null)
        SessionApprenantDocument sessionTigrouDocBIDB2 = entityManager.find(SessionApprenantDocument.class, sessionTigrouDocBIDB.getIdSessionApprenantDocument());
        assertNull(sessionTigrouDocBIDB2);

        // Vérifie que la sessionApprenant existe toujours en base (pas de suppression en cascade)
        List<SessionApprenant> sessionApprenant = sessionApprenantRepository.findAll();
        assertThat(sessionApprenant).isNotNull();
        assertThat(sessionApprenant).isNotEmpty();
        assertThat(sessionApprenant.size()).isEqualTo(1);

        // Vérifie que les catégories de documents associées existent toujours en base (pas de suppression en cascade)
        List<CategorieDocument> categorieDocument = categorieDocumentRepository.findAll();
        assertThat(categorieDocument).isNotNull();
        assertThat(categorieDocument).isNotEmpty();
        assertThat(categorieDocument.size()).isEqualTo(2);

    }


    }
