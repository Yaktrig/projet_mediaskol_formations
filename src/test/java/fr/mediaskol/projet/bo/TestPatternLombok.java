package fr.mediaskol.projet.bo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Slf4j
public class TestPatternLombok {

    // Test avec une personne qui a tous les attributs
    @Test
    void test_patternBuilder_TousLesAttributs(){

        // Création d'une nouvelle personne avec le builder Lombok
        Personne krokmou = Personne
                .builder()
                .idPersonne(1L)
                .nom("DRAGON")
                .prenom("Krokmou")
                .email("krokmouDragon@gmail.fr")
                .build();

        // permet d'afficher l'objet avec tous les attributs
        log.info("test_patternBuilder_TousLesAttributs : " + krokmou.toString());

        // Vérification si le nom correspond bien à celui attendu
        assertThat(krokmou.getNom()).isEqualTo("DRAGON");

        // Vérification si le prénom correspond bien à celui attendu
        assertThat(krokmou.getPrenom()).isEqualTo("Krokmou");

        // Vérification si l'email correspond bien à celui attendu
        assertThat(krokmou.getEmail()).isEqualTo("krokmouDragon@gmail.fr");
    }



}
