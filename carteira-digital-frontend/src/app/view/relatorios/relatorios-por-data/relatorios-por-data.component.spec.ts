import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RelatoriosPorDataComponent } from './relatorios-por-data.component';

describe('RelatoriosPorDataComponent', () => {
  let component: RelatoriosPorDataComponent;
  let fixture: ComponentFixture<RelatoriosPorDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RelatoriosPorDataComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RelatoriosPorDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
