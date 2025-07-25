import { StatutSessionFormation } from './statut-session-formation.enum';
import { FormationResponseDTO } from './formation-response-dto.model';
import { DepartementDTO } from './departement.dto';
import { SessionFOADRespDTO } from './session-foad-resp-dto.model';
// import { FinSessionFormation } from './fin-session-formation.model'; // A décommenter/ajouter quand disponible

export interface SessionFormationRespDTO {
  idSessionFormation: number | null;
  noYoda: string | null;
  libelleSessionFormation: string | null;
  statutYoda: string | null;
  dateDebutSession: string | null; // chaîne ISO: conversion à Date possible
  nbHeureSession: number | null;
  statutSessionFormation: StatutSessionFormation;
  formation: FormationResponseDTO | null;
  departement: DepartementDTO | null;
  // finSessionFormation?: FinSessionFormation | null; // à ajouter selon évolution
  sessionFormationDistanciel: SessionFOADRespDTO | null;
}
