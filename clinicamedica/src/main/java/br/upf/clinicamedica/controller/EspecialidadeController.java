package br.upf.clinicamedica.controller;
import br.upf.clinicamedica.entity.EspecialidadeEntity;
import br.upf.clinicamedica.facade.EspecialidadeFacade;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class EspecialidadeController implements Serializable {

    @EJB
    private EspecialidadeFacade facade;

    private EspecialidadeEntity especialidade = new EspecialidadeEntity();
    private List<EspecialidadeEntity> lista;

    public enum PersistAction {
        CREATE, UPDATE, DELETE
    }

    public void prepareAdicionar() {
        especialidade = new EspecialidadeEntity();
    }

    public void salvar(PersistAction action) {
        try {
            if (action == PersistAction.CREATE) {
                facade.create(especialidade);
                addMessage("Especialidade cadastrada com sucesso!", FacesMessage.SEVERITY_INFO);
            } else if (action == PersistAction.UPDATE) {
                facade.edit(especialidade);
                addMessage("Especialidade atualizada com sucesso!", FacesMessage.SEVERITY_INFO);
            } else if (action == PersistAction.DELETE) {
                facade.remove(especialidade);
                addMessage("Especialidade removida com sucesso!", FacesMessage.SEVERITY_INFO);
            }
            lista = null;
        } catch (Exception e) {
            addMessage("Erro: " + e.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public List<EspecialidadeEntity> getLista() {
        if (lista == null) {
            lista = facade.findAll();
        }
        return lista;
    }

    public EspecialidadeEntity getEspecialidade() { return especialidade; }
    public void setEspecialidade(EspecialidadeEntity especialidade) { this.especialidade = especialidade; }
    
    public EspecialidadeEntity getEspecialidade(Integer id) {
        return facade.find(id);
    }

    private void addMessage(String msg, FacesMessage.Severity severity) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, msg, null));
    }

    @FacesConverter(forClass = EspecialidadeEntity.class)
    public static class EspecialidadeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EspecialidadeController controller =
                    (EspecialidadeController) facesContext.getApplication().getELResolver()
                            .getValue(facesContext.getELContext(), null, "especialidadeController");
            return controller.getEspecialidade(getKey(value));
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
            if (object instanceof EspecialidadeEntity) {
                EspecialidadeEntity o = (EspecialidadeEntity) object;
                return getStringKey(o.getId());
            }
            return null;
        }
    }
}