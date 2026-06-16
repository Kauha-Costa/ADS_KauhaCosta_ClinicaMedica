package br.upf.clinicamedica.controller;

import br.upf.clinicamedica.facade.ConsultaFacade;
import br.upf.clinicamedica.facade.EspecialidadeFacade;
import br.upf.clinicamedica.facade.MedicoFacade;
import br.upf.clinicamedica.facade.PacienteFacade;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class DashboardController {

    @EJB
    private MedicoFacade medicoFacade;

    @EJB
    private PacienteFacade pacienteFacade;

    @EJB
    private ConsultaFacade consultaFacade;

    @EJB
    private EspecialidadeFacade especialidadeFacade;

    public long getTotalMedicos() {
        return medicoFacade.findAll().size();
    }

    public long getTotalPacientes() {
        return pacienteFacade.findAll().size();
    }

    public long getTotalConsultasAgendadas() {
        return consultaFacade.buscarAgendadas().size();
    }

    public long getTotalEspecialidades() {
        return especialidadeFacade.findAll().size();
    }
}