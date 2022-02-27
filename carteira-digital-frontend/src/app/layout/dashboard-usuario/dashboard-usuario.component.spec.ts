import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardUsuarioComponent } from './dashboard-usuario.component';

describe('DashboardUsuarioComponent', () => {
  let component: DashboardUsuarioComponent;
  let fixture: ComponentFixture<DashboardUsuarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardUsuarioComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardUsuarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
