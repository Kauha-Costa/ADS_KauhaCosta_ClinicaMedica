package br.upf.clinicamedica.facade;

import br.upf.clinicamedica.entity.UsuarioEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Stateless
public class UsuarioFacade extends AbstractFacade<UsuarioEntity> {

    @PersistenceContext(unitName = "ClinicaMedicaPU")
    private EntityManager em;

    public UsuarioFacade() {
        super(UsuarioEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioEntity buscarPorUsuarioESenha(String usuario, String senha) {
        TypedQuery<UsuarioEntity> query = em.createQuery(
            "SELECT u FROM UsuarioEntity u WHERE u.usuario = :usuario AND u.senha = :senha",
            UsuarioEntity.class);
        query.setParameter("usuario", usuario);
        query.setParameter("senha", senha);
        List<UsuarioEntity> resultado = query.getResultList();
        return resultado.isEmpty() ? null : resultado.get(0);
    }
}