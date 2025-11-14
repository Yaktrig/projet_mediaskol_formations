package fr.mediaskol.projet.service;

import fr.mediaskol.projet.entities.SessionFormation;
import fr.mediaskol.projet.repository.SessionFormationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionFormationService {

    private final SessionFormationRepository repository;

    public SessionFormationService(SessionFormationRepository repository) {
        this.repository = repository;
    }

    public List<SessionFormation> chargerToutesSessionsFormations() {
        return repository.findAll();
    }

    public SessionFormation chargerSessionFormationParId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("SessionFormation introuvable"));
    }

    public List<SessionFormation> rechercheSessionFormations(String termeRecherche) {
        return repository.findSessionFormationsBySearchText(termeRecherche);
    }

    public void ajouterSessionFormation(SessionFormation sessionFormation) {
        repository.save(sessionFormation);
    }

    public void supprimerSessionFormation(Long id) {
        repository.deleteById(id);
    }
}