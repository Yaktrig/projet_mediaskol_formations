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

        Personne krokmou = Personne
                .builder()
                .idPersonne(1L)
                .nom("DRAGON")
                .prenom("Krokmou")
                .email("krokmouDragon@gmail.fr")
                .build();

        // permet d'afficher l'objet avec tous les attributs
        log.info("test_patternBuilder_TousLesAttributs : " + krokmou.toString());
        assertThat(krokmou.getNom()).isEqualTo("DRAGON");
        assertThat(krokmou.getPrenom()).isEqualTo("Krokmou");
        assertThat(krokmou.getEmail()).isEqualTo("krokmouDragon@gmail.fr");
    }



}
