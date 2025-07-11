package fr.mediaskol.projet.bll;

import fr.mediaskol.projet.bo.SessionFormation.SessionFormation;
import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.bo.apprenant.SessionApprenant;
import fr.mediaskol.projet.dal.SessionApprenantRepository;

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
