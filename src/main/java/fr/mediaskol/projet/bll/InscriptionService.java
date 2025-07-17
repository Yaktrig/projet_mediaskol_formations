package fr.mediaskol.projet.bll;

import fr.mediaskol.projet.bo.sessionFormation.SessionFormation;
import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.bo.apprenant.SessionApprenant;

public interface InscriptionService {


    SessionApprenant inscrireApprenant(Apprenant apprenant, SessionFormation sessionFormation);
}
