package br.upf.clinicamedica.controller;

import br.upf.clinicamedica.entity.UsuarioEntity;
import br.upf.clinicamedica.facade.UsuarioFacade;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginController implements Serializable {

    @EJB
    private UsuarioFacade usuarioFacade;

    private String usuario;
    private String senha;

    public String login() {
        UsuarioEntity u = usuarioFacade.buscarPorUsuarioESenha(usuario, senha);
        if (u != null) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(true);
            session.setAttribute("usuarioLogado", u.getUsuario());
            return "/admin/dashboard?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Usuário ou senha inválidos!", null));
            return null;
        }
    }

    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        if (session != null) session.invalidate();
        return "/login?faces-redirect=true";
    }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}