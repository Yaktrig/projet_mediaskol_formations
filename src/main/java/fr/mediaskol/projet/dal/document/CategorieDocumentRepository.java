package fr.mediaskol.projet.dal.document;

import fr.mediaskol.projet.bo.document.CategorieDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieDocumentRepository extends JpaRepository<CategorieDocument, Long> {
}
