package br.upf.clinicamedica.facade;

import br.upf.clinicamedica.entity.MedicoEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class MedicoFacade extends AbstractFacade<MedicoEntity> {

    @PersistenceContext(unitName = "ClinicaMedicaPU")
    private EntityManager em;

    public MedicoFacade() {
        super(MedicoEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}