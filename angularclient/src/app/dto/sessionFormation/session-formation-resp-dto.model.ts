import {StatutSessionFormation} from './statut-session-formation.enum';
import {FormationResponseDTO} from '../formation/formation-resp-dto.model';
import {DepartementDTO} from '../adresse/departement-resp-dto.model';
import {SessionFoadResponseDTO} from '../sessionFormationDistanciel/session-formation-distanciel-resp-dto.model';
import {FinSessionFormationRespDTO} from './fin-session-formation-resp-dto.model';


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
  finSessionFormation: FinSessionFormationRespDTO | null;
  sessionFormationDistanciel: SessionFoadResponseDTO | null;
}
