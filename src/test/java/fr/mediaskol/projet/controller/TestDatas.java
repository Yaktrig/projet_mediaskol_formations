package fr.mediaskol.projet.controller;

import fr.mediaskol.projet.bll.AdresseService;
import fr.mediaskol.projet.bll.ApprenantService;
import fr.mediaskol.projet.bo.adresse.Adresse;
import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.bo.apprenant.StatutNumPasseport;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import fr.mediaskol.projet.dal.adresse.AdresseRepository;
import fr.mediaskol.projet.dal.apprenant.ApprenantRepository;
import fr.mediaskol.projet.dal.formation.TypeFormationRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class TestDatas {

    @Autowired
    private ApprenantRepository apprenantRepository;

    @Autowired
    private ApprenantService apprenantService;


    @Autowired
    private TypeFormationRepository typeFormationRepository;

    @Test
    void test01_TypeFormation() {

        final TypeFormation presentiel = typeFormationRepository.save(TypeFormation
                .builder()
                .libelleTypeFormation("Présentiel")
                .build());

        final TypeFormation distanciel = typeFormationRepository.save(TypeFormation
                .builder()
                .libelleTypeFormation("Distanciel")
                .build()
        );

        assertThat(presentiel).isNotNull();
        assertThat(distanciel).isNotNull();

    }


    @Test
    void test02_Apprenant() {

        final List<TypeFormation> listeTypeFormation = typeFormationRepository.findAll();
        assertThat(listeTypeFormation).isNotEmpty();
        assertThat(listeTypeFormation).isNotNull();
        assertThat(listeTypeFormation.size()).isEqualTo(2);

        final TypeFormation presentiel = listeTypeFormation.get(0);
        final TypeFormation distanciel = listeTypeFormation.get(1);


        final Apprenant tigrou = Apprenant
                .builder()
                .nom("Le tigre")
                .prenom("Tigrou")
                .email("tigrou.letigre@gmail.fr")
                .numPortable("0600000000")
                .dateNaissance(LocalDate.parse("2000-12-12"))
                .apprenantActif(true)
                .numPasseport("A123456")
                .commentaireApprenant("Fais des bonds partout")
                .statutNumPasseport(StatutNumPasseport.NUM_PASSEPORT_A_CREER)
                .typesFormationSuivies(Set.of(presentiel, distanciel))
                .adresse(Adresse
                        .builder()
                        .rue("Lieu Dit Kerpont, Les Hauts De Kerousse - Lann Sevelin")
                        .codePostal("56600")
                        .ville("Lanester")
                        .build())
                .build();

        apprenantService.ajouterApprenant(tigrou, tigrou.getAdresse(), tigrou.getTypesFormationSuivies());

        final Apprenant simba = Apprenant
                .builder()
                .nom("Le lion")
                .prenom("Simba")
                .email("simba.lelion@gmail.fr")
                .numPortable("0600000000")
                .dateNaissance(LocalDate.parse("1998-06-01"))
                .apprenantActif(true)
                .numPasseport("A123457")
                .commentaireApprenant("")
                .statutNumPasseport(StatutNumPasseport.NUM_PASSEPORT_CREE)
                .typesFormationSuivies(Set.of(distanciel))
                .adresse(Adresse
                        .builder()
                        .rue("4 Avenue Desambrois")
                        .codePostal("06000")
                        .ville("Nice")
                        .build())
                .build();


        apprenantService.ajouterApprenant(simba, simba.getAdresse(), simba.getTypesFormationSuivies());


        final Apprenant ariel = Apprenant
                .builder()
                .nom("La petite sirène")
                .prenom("Ariel")
                .email("ariel.lapetitesirene@gmail.fr")
                .numPortable("0600000000")
                .dateNaissance(LocalDate.parse("1989-11-15"))
                .apprenantActif(true)
                .numPasseport("A123458")
                .commentaireApprenant("")
                .statutNumPasseport(StatutNumPasseport.NUM_PASSEPORT_A_CREER)
                .typesFormationSuivies(Set.of(presentiel))
                .adresse(Adresse
                        .builder()
                        .rue("10 Rue de Gouesnou")
                        .codePostal("29283")
                        .ville("Brest")
                        .build())
                .build();


        apprenantService.ajouterApprenant(ariel, ariel.getAdresse(), ariel.getTypesFormationSuivies());


    }


}
