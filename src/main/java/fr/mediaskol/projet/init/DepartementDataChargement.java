package fr.mediaskol.projet.init;

import fr.mediaskol.projet.bo.departement.Departement;
import fr.mediaskol.projet.dal.departement.DepartementRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DepartementDataChargement implements CommandLineRunner {


    private final DepartementRepository departementRepository;


    public DepartementDataChargement(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        List<DepartementData> departements = chargerDepuisCSV();

        Map<String, String> couleursBretagne = Map.of(
                "22", "#A9438F",
                "29", "#6FBBD5",
                "35", "#6FBBD5",
                "56", "#F2DA42");

        for (DepartementData d : departements) {
            if (departementRepository.findByNumDepartement(d.numDep()).isEmpty()) {
                String couleur = d.region().equalsIgnoreCase("Bretagne")
                        ? couleursBretagne.getOrDefault(d.numDep(), "#FFFFFF")
                        : "#FFFFFF";
                Departement dep = Departement.builder()
                        .numDepartement(d.numDep())
                        .nomDepartement(d.nom())
                        .region(d.region())
                        .couleurDepartement(couleur)
                        .build();
                departementRepository.save(dep);
            }
        }
    }

    private List<DepartementData> chargerDepuisCSV() {

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new ClassPathResource("departements/liste_departements.csv").getInputStream(), StandardCharsets.UTF_8))) {

            return reader.lines()
                    .skip(1)
                    .map(line -> line.split(",", -1))
                    .filter(columns -> columns.length >= 3)
                    .map(columns -> new DepartementData(columns[0].trim(), columns[1].trim(), columns[2].trim()))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new UncheckedIOException("Erreur lors du chargement du fichier des départements", e);
        }

    }
}
