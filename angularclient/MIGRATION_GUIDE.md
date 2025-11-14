# API URL Migration Guide

## Summary
Environment configuration has been set up to externalize API URLs from the service files.

## What's Been Done
- Created `src/environments/environment.ts` (development)
- Created `src/environments/environment.prod.ts` (production)
- Updated `angular.json` to use environment file replacement for production builds
- Updated these services as examples:
  - `formation/services/formation.service.ts`
  - `sessionFormation/services/ajouter-session-formation-presentiel.service.ts`

## How to Update Remaining Services

### Current Pattern (WRONG):
```typescript
private apiUrl = 'http://localhost:8080/mediaskolFormation';
```

### New Pattern (CORRECT):
```typescript
import { environment } from '../../environments/environment';  // Adjust path based on location

private apiUrl = `${environment.apiUrl}/mediaskolFormation`;
```

## Services That Need Updating (19 files)
Run this command to find all files that still need updating:
```bash
grep -r "http://localhost:8080" angularclient/src/app --exclude-dir=node_modules
```

## Production Deployment
When deploying to production:
1. Update `src/environments/environment.prod.ts` with your production API URL
2. Build with: `ng build --configuration production`
3. The correct environment file will be used automatically

## Testing
- Development: `ng serve` (uses environment.ts)
- Production build: `ng build --configuration production` (uses environment.prod.ts)
