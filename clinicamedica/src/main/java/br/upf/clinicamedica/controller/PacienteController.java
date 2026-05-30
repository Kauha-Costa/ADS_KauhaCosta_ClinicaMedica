package br.upf.clinicamedica.controller;

import br.upf.clinicamedica.entity.PacienteEntity;
import br.upf.clinicamedica.facade.PacienteFacade;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
public class PacienteController implements Serializable {

    @EJB
    private PacienteFacade facade;

    private PacienteEntity paciente = new PacienteEntity();
    private List<PacienteEntity> lista;

    public enum PersistAction {
        CREATE, UPDATE, DELETE
    }

    public void prepareAdicionar() {
        paciente = new PacienteEntity();
    }

    public void salvar(PersistAction action) {
        try {
            if (action == PersistAction.CREATE) {
                paciente.setDatahorareg(new Date());
                facade.create(paciente);
                addMessage("Paciente cadastrado com sucesso!", FacesMessage.SEVERITY_INFO);
            } else if (action == PersistAction.UPDATE) {
                facade.edit(paciente);
                addMessage("Paciente atualizado com sucesso!", FacesMessage.SEVERITY_INFO);
            } else if (action == PersistAction.DELETE) {
                facade.remove(paciente);
                addMessage("Paciente removido com sucesso!", FacesMessage.SEVERITY_INFO);
            }
            lista = null;
        } catch (Exception e) {
            addMessage("Erro: " + e.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public List<PacienteEntity> getLista() {
        if (lista == null) {
            lista = facade.findAll();
        }
        return lista;
    }

    public PacienteEntity getPaciente() { return paciente; }
    public void setPaciente(PacienteEntity paciente) { this.paciente = paciente; }

    private void addMessage(String msg, FacesMessage.Severity severity) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, msg, null));
    }
}