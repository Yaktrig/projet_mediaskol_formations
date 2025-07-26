import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AjouterSessionFormationPresentiel } from './ajouter-session-formation-presentiel';

describe('AjouterSessionFormationPresentiel', () => {
  let component: AjouterSessionFormationPresentiel;
  let fixture: ComponentFixture<AjouterSessionFormationPresentiel>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AjouterSessionFormationPresentiel]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AjouterSessionFormationPresentiel);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
