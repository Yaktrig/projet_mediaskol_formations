# RAPPORT DE TESTS DES ENDPOINTS MEDIASKOL
Date: 2025-11-09
Backend: http://localhost:8080

## Résumé Exécutif

Tests effectués sur tous les endpoints REST de l'application Mediaskol avec authentification JWT (rôle ADMIN).

---

## 1. AUTHENTIFICATION

### POST /mediaskolFormation/auth
- **Status**: ✅ 200 OK
- **Description**: Génération de token JWT
- **Credentials testé**: `admin / password`
- **Résultat**: Token généré avec succès
- **Durée validité token**: 1 heure

---

## 2. FORMATIONS

### GET /mediaskolFormation/formations
- **Status**: ✅ 200 OK
- **Description**: Liste de toutes les formations
- **Résultat**: 4 formations retournées
  - Développement Web Full Stack (Présentiel)
  - DevOps et Cloud Computing (Présentiel)
  - Data Science avec Python (Distanciel)
  - Cybersécurité (Présentiel)

### GET /mediaskolFormation/formations/{id}
- **Status**: ✅ 200 OK
- **Description**: Récupération d'une formation par ID
- **Test effectué**: ID=1
- **Résultat**: Formation retournée avec succès

---

## 3. SESSIONS FORMATION PRÉSENTIEL

### GET /mediaskolFormation/sessionsFormationsPresentiels
- **Status**: ✅ 204 No Content
- **Description**: Liste des sessions de formation en présentiel
- **Résultat**: Aucune session en base de données

### GET /mediaskolFormation/sessionsFormationsPresentiels/moinsSixSessionsApprenants
- **Status**: ✅ 204 No Content
- **Description**: Sessions avec moins de 6 apprenants
- **Résultat**: Aucune session en base

---

## 4. DÉPARTEMENTS

### GET /mediaskolFormation/departements/bretagne
- **Status**: ✅ 200 OK
- **Description**: Départements de Bretagne
- **Résultat**: 1 département retourné
  - 56 - Morbihan (#FF5733)

---

## 5. SALARIÉS (ADMIN only)

### GET /mediaskolFormation/salaries
- **Status**: ✅ 200 OK
- **Description**: Liste de tous les salariés
- **Sécurité**: Endpoint réservé au rôle ADMIN
- **Résultat**: 2 salariés retournés
  - Jean Dupont (ADMIN) - jean.dupont@mediaskol.fr
  - Sophie Martin (SALARIE) - sophie.martin@mediaskol.fr

---

## 6. APPRENANTS ⚠️

### GET /mediaskolFormation/apprenants
- **Status**: ❌ 403 FORBIDDEN
- **Description**: Liste de tous les apprenants
- **Problème identifié**: Endpoint bloqué malgré rôle ADMIN valide
- **Configuration sécurité**: `.requestMatchers("/mediaskolFormation/apprenants/**").hasAnyRole("ADMIN", "SALARIE")`
- **Analyse**:
  - Le token JWT contient bien le rôle "ADMIN"
  - La configuration de sécurité autorise ADMIN et SALARIE
  - Autres endpoints similaires fonctionnent (/salaries, /formations)

### CAUSE PROBABLE
Il peut y avoir un conflit avec un autre matcher ou un problème d'ordre dans la configuration de sécurité.

### SOLUTION RECOMMANDÉE
1. Vérifier l'ordre des requestMatchers dans MediaskolSecurityConfig
2. Vérifier qu'il n'y a pas de matcher plus général qui capture `/apprenants` avant
3. Ajouter des logs dans JwtAuthenticationFilter pour débugger l'extraction des rôles

---

## 7. FORMATEURS

### GET /mediaskolFormation/formateurs
- **Status**: ✅ 204 No Content
- **Description**: Liste des formateurs
- **Résultat**: Aucun formateur en base de données

---

## 8. SALLES

### GET /mediaskolFormation/salles
- **Status**: ✅ 204 No Content
- **Description**: Liste des salles
- **Résultat**: Aucune salle en base de données

---

## SYNTHÈSE

### Endpoints fonctionnels: 7/8 ✅
- Authentification JWT ✅
- Formations (GET all, GET by ID) ✅
- Sessions Formation Présentiel ✅
- Départements ✅
- Salariés ✅
- Formateurs ✅
- Salles ✅

### Endpoints problématiques: 1/8 ⚠️
- Apprenants (403 Forbidden) ❌

### Sécurité JWT
- ✅ Génération de tokens fonctionnelle
- ✅ Validation des tokens fonctionnelle (formations, salariés OK)
- ✅ Extraction des rôles correcte
- ⚠️ Problème spécifique sur endpoint /apprenants

### Données en base
- ✅ 4 Formations
- ✅ 2 Salariés
- ✅ 1 Département (Bretagne)
- ❓ 3 Apprenants (non vérifié - endpoint bloqué)
- ⚠️ 0 Sessions formation
- ⚠️ 0 Formateurs
- ⚠️ 0 Salles

### Recommandations

1. **URGENT**: Corriger le problème 403 sur `/apprenants`
   - Vérifier MediaskolSecurityConfig.java ligne 63
   - Ajouter des logs dans JwtAuthenticationFilter
   - Comparer avec `/salaries` qui fonctionne

2. **Données**: Populer la base avec des données de test
   - Ajouter des sessions de formation
   - Ajouter des formateurs
   - Ajouter des salles
   - Vérifier que data.sql est bien exécuté (spring.sql.init.mode=always)

3. **Tests**: Implémenter des tests automatisés
   - Tests d'intégration pour tous les endpoints
   - Tests de sécurité pour vérifier les rôles
   - Tests de validation des DTOs

---

## DÉTAILS TECHNIQUES

### Configuration JWT
- Algorithme: RS256 (RSA)
- Clés: public.key / private.key
- Durée: 1 heure (3600 secondes)
- Claims: sub (username), roles (array)

### Sécurité Spring
- SessionCreationPolicy: STATELESS
- CSRF: Désactivé
- Filtre: JwtAuthenticationFilter (OncePerRequestFilter)
- Autorisation: hasAnyRole() avec préfixe automatique "ROLE_"

---

**Rapport généré le**: 2025-11-09 16:45
**Par**: Claude Code
**Environnement**: localhost:8080
