package br.upf.clinicamedica.controller;

import br.upf.clinicamedica.entity.ConsultaEntity;
import br.upf.clinicamedica.entity.MedicoEntity;
import br.upf.clinicamedica.entity.PacienteEntity;
import br.upf.clinicamedica.facade.ConsultaFacade;
import br.upf.clinicamedica.facade.MedicoFacade;
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
public class ConsultaController implements Serializable {

    @EJB
    private ConsultaFacade facade;

    @EJB
    private MedicoFacade medicoFacade;

    @EJB
    private PacienteFacade pacienteFacade;

    private ConsultaEntity consulta = new ConsultaEntity();
    private List<ConsultaEntity> lista;
    private List<MedicoEntity> listaMedicos;
    private List<PacienteEntity> listaPacientes;

    public enum PersistAction {
        CREATE, UPDATE, DELETE
    }

    public void prepareAdicionar() {
        consulta = new ConsultaEntity();
        consulta.setStatus("Agendada");
    }

    public void salvar(PersistAction action) {
        try {
            if (action == PersistAction.CREATE) {
                consulta.setDatahorareg(new Date());
                facade.create(consulta);
                addMessage("Consulta agendada com sucesso!", FacesMessage.SEVERITY_INFO);
            } else if (action == PersistAction.UPDATE) {
                facade.edit(consulta);
                addMessage("Consulta atualizada com sucesso!", FacesMessage.SEVERITY_INFO);
            } else if (action == PersistAction.DELETE) {
                facade.remove(consulta);
                addMessage("Consulta removida com sucesso!", FacesMessage.SEVERITY_INFO);
            }
            lista = null;
        } catch (Exception e) {
            addMessage("Erro: " + e.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void cancelar() {
        try {
            consulta.setStatus("Cancelada");
            facade.edit(consulta);
            addMessage("Consulta cancelada com sucesso!", FacesMessage.SEVERITY_INFO);
            lista = null;
        } catch (Exception e) {
            addMessage("Erro: " + e.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public List<ConsultaEntity> getLista() {
        if (lista == null) {
            lista = facade.findAll();
        }
        return lista;
    }

    public List<ConsultaEntity> getListaAgendadas() {
        return facade.buscarAgendadas();
    }

    public List<MedicoEntity> getListaMedicos() {
        if (listaMedicos == null) {
            listaMedicos = medicoFacade.findAll();
        }
        return listaMedicos;
    }

    public List<PacienteEntity> getListaPacientes() {
        if (listaPacientes == null) {
            listaPacientes = pacienteFacade.findAll();
        }
        return listaPacientes;
    }

    public ConsultaEntity getConsulta() { return consulta; }
    public void setConsulta(ConsultaEntity consulta) { this.consulta = consulta; }

    private void addMessage(String msg, FacesMessage.Severity severity) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, msg, null));
    }
}