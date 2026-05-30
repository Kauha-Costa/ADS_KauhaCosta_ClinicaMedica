package br.upf.clinicamedica.facade;

import br.upf.clinicamedica.entity.ConsultaEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ConsultaFacade extends AbstractFacade<ConsultaEntity> {

    @PersistenceContext(unitName = "ClinicaMedicaPU")
    private EntityManager em;

    public ConsultaFacade() {
        super(ConsultaEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<ConsultaEntity> buscarAgendadas() {
        return em.createQuery(
            "SELECT c FROM ConsultaEntity c WHERE c.status = 'Agendada' ORDER BY c.datahoraconsulta",
            ConsultaEntity.class
        ).getResultList();
    }
}