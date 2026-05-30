package br.upf.clinicamedica.facade;

import br.upf.clinicamedica.entity.PacienteEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class PacienteFacade extends AbstractFacade<PacienteEntity> {

    @PersistenceContext(unitName = "ClinicaMedicaPU")
    private EntityManager em;

    public PacienteFacade() {
        super(PacienteEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}