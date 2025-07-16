package fr.mediaskol.projet.dal;

import fr.mediaskol.projet.bo.document.SessionApprenantDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionApprenantDocumentRepository extends JpaRepository<SessionApprenantDocument, Long> {
}
