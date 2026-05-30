package br.upf.clinicamedica.facade;

import br.upf.clinicamedica.entity.EspecialidadeEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class EspecialidadeFacade extends AbstractFacade<EspecialidadeEntity> {

    @PersistenceContext(unitName = "ClinicaMedicaPU")
    private EntityManager em;

    public EspecialidadeFacade() {
        super(EspecialidadeEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}