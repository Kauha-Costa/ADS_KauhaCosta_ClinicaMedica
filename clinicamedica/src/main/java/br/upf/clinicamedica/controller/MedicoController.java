package br.upf.clinicamedica.controller;
import br.upf.clinicamedica.entity.EspecialidadeEntity;
import br.upf.clinicamedica.entity.MedicoEntity;
import br.upf.clinicamedica.facade.EspecialidadeFacade;
import br.upf.clinicamedica.facade.MedicoFacade;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
public class MedicoController implements Serializable {

    @EJB
    private MedicoFacade facade;

    @EJB
    private EspecialidadeFacade especialidadeFacade;

    private MedicoEntity medico = new MedicoEntity();
    private List<MedicoEntity> lista;
    private List<EspecialidadeEntity> listaEspecialidades;

    public enum PersistAction {
        CREATE, UPDATE, DELETE
    }

    public void prepareAdicionar() {
        medico = new MedicoEntity();
        listaEspecialidades = null; // força recarregar
    }
    
    public void setMedico(MedicoEntity medico) { 
        this.medico = medico; 
        listaEspecialidades = null; // força recarregar
    }
    
    public void salvar(PersistAction action) {
        try {
            if (action == PersistAction.CREATE) {
                medico.setDatahorareg(new Date());
                facade.create(medico);
                addMessage("Médico cadastrado com sucesso!", FacesMessage.SEVERITY_INFO);
            } else if (action == PersistAction.UPDATE) {
                facade.edit(medico);
                addMessage("Médico atualizado com sucesso!", FacesMessage.SEVERITY_INFO);
            } else if (action == PersistAction.DELETE) {
                facade.remove(medico);
                addMessage("Médico removido com sucesso!", FacesMessage.SEVERITY_INFO);
            }
            lista = null;
        } catch (Exception e) {
            addMessage("Erro: " + e.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public List<MedicoEntity> getLista() {
        if (lista == null) {
            lista = facade.findAll();
        }
        return lista;
    }

    public List<EspecialidadeEntity> getListaEspecialidades() {
        if (listaEspecialidades == null) {
            listaEspecialidades = especialidadeFacade.findAll();
        }
        return listaEspecialidades;
    }

    public MedicoEntity getMedico() { return medico; }

    public MedicoEntity getMedico(Integer id) {
        return facade.find(id);
    }

    private void addMessage(String msg, FacesMessage.Severity severity) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, msg, null));
    }

    @FacesConverter(forClass = MedicoEntity.class)
    public static class MedicoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MedicoController controller =
                    (MedicoController) facesContext.getApplication().getELResolver()
                            .getValue(facesContext.getELContext(), null, "medicoController");
            return controller.getMedico(getKey(value));
        }

        Integer getKey(String value) {
            return Integer.valueOf(value);
        }

        String getStringKey(Integer value) {
            return String.valueOf(value);
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof MedicoEntity) {
                MedicoEntity o = (MedicoEntity) object;
                return getStringKey(o.getId());
            }
            return null;
        }
    }
}