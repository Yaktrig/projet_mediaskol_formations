package fr.mediaskol.projet.bll;

import fr.mediaskol.projet.bo.sessionFormation.SessionFormation;
import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.bo.apprenant.SessionApprenant;
import fr.mediaskol.projet.dal.apprenant.SessionApprenantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class InscriptionServiceImpl implements InscriptionService{

    /**
     * Permet d'inscrire un apprenant à une session de formation
     */

    private SessionApprenantRepository sessionApprenantRepository;

    @Override
    public SessionApprenant inscrireApprenant(Apprenant apprenant, SessionFormation sessionFormation){

       SessionApprenant sa = new SessionApprenant();
       sa.setApprenant(apprenant);
       sa.setSessionFormation(sessionFormation);

       return sessionApprenantRepository.save(sa);
    }

}
