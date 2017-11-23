/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.russas.n2s.darwin.controller;

import br.ufc.russas.n2s.darwin.beans.EtapaBeans;
import br.ufc.russas.n2s.darwin.beans.PeriodoBeans;
import br.ufc.russas.n2s.darwin.beans.SelecaoBeans;
import br.ufc.russas.n2s.darwin.beans.UsuarioBeans;
import br.ufc.russas.n2s.darwin.model.EnumCriterioDeAvaliacao;
import br.ufc.russas.n2s.darwin.model.EnumEstadoEtapa;
import br.ufc.russas.n2s.darwin.model.Etapa;
import br.ufc.russas.n2s.darwin.model.UsuarioDarwin;
import br.ufc.russas.n2s.darwin.service.EtapaServiceIfc;
import br.ufc.russas.n2s.darwin.service.SelecaoServiceIfc;
import br.ufc.russas.n2s.darwin.service.UsuarioServiceIfc;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Lavínia Matoso
 */
@Controller("editarEtapaController")
@RequestMapping("/editarEtapa")
public class EditarEtapaController {

    private EtapaServiceIfc etapaServiceIfc;
    private SelecaoServiceIfc selecaoServiceIfc;
    private UsuarioServiceIfc usuarioServiceIfc;
    
    public EtapaServiceIfc getEtapaServiceIfc() {
        return etapaServiceIfc;
    }

    @Autowired(required = true)
    public void setEtapaServiceIfc(@Qualifier("etapaServiceIfc")EtapaServiceIfc etapaServiceIfc) {
        this.etapaServiceIfc = etapaServiceIfc;
    }

    public SelecaoServiceIfc getSelecaoServiceIfc() {
        return selecaoServiceIfc;
    }
    @Autowired(required = true)
    public void setSelecaoServiceIfc(@Qualifier("selecaoServiceIfc") SelecaoServiceIfc selecaoServiceIfc) {
        this.selecaoServiceIfc = selecaoServiceIfc;
    }

    public UsuarioServiceIfc getUsuarioServiceIfc() {
        return usuarioServiceIfc;
    }
    @Autowired(required = true)
    public void setUsuarioServiceIfc(@Qualifier("usuarioServiceIfc")UsuarioServiceIfc usuarioServiceIfc) {
        this.usuarioServiceIfc = usuarioServiceIfc;
    }
           
    
    @RequestMapping(value="/{codSelecao}/{codEtapa}", method = RequestMethod.GET)
    public String getIndex(@PathVariable long codSelecao, @PathVariable long codEtapa, Model model) {
        //EtapaBeans etapaBeans = this.etapaServiceIfc.getEtapa(codEtapa);
        SelecaoBeans selecao = selecaoServiceIfc.getSelecao(codSelecao);
        EtapaBeans e1 = new EtapaBeans();
        e1.setTitulo("Entrevista");
        e1.setDescricao("Poderíamos criar um novo JSP com uma mensagem de confirmação da remoção, mas usualmente isso não costuma ser bom, porque precisaríamos navegar até a lista das tarefas novamente caso tenhamos que remover outra tarefa.");
        e1.setCodEtapa(11);
        e1.setPeriodo(new PeriodoBeans(0, LocalDate.now(), LocalDate.now()));
        e1.setCriterioDeAvaliacao(EnumCriterioDeAvaliacao.DEFERIMENTO);
        ArrayList<String> documentacao = new ArrayList<>();
        documentacao.add("Histórico Acadêmico");
        e1.setDocumentacaoExigida(documentacao);
        model.addAttribute("selecao", selecao);
        model.addAttribute("etapa", e1);
        return "editar-etapa";
    }

    @RequestMapping(value="/{codSelecao}", method = RequestMethod.POST)
    public String atualiza(@PathVariable long codSelecao, EtapaBeans etapa, BindingResult result, Model model, HttpServletRequest request) {
        SelecaoBeans selecao = this.selecaoServiceIfc.getSelecao(codSelecao);
        model.addAttribute("selecao", selecao);
        String[] codAvaliadores = request.getParameterValues("codAvaliadores");
        int criterio = Integer.parseInt(request.getParameter("criterioDeAvaliacao"));
        if (criterio == 1) {
            etapa.setCriterioDeAvaliacao(EnumCriterioDeAvaliacao.NOTA);
        } else if(criterio == 2) {
            etapa.setCriterioDeAvaliacao(EnumCriterioDeAvaliacao.APROVACAO);
        } else if(criterio == 3) {
            etapa.setCriterioDeAvaliacao(EnumCriterioDeAvaliacao.DEFERIMENTO);
        }
        etapa.setEstado(EnumEstadoEtapa.ESPERA);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        etapa.setPeriodo(new PeriodoBeans(0, LocalDate.parse(request.getParameter("dataInicio"), formatter), LocalDate.parse(request.getParameter("dataTermino"), formatter)));
        ArrayList<UsuarioBeans> avaliadores = new ArrayList<>();
        if(codAvaliadores != null){
            for(String cod : codAvaliadores){
                UsuarioBeans u = this.getUsuarioServiceIfc().getUsuario(Long.parseLong(cod),0);
                if(u != null){
                    avaliadores.add(u);
                }
            }
        }
        etapa.setAvaliadores(avaliadores);
        etapa = getEtapaServiceIfc().adicionaEtapa(etapa);
        selecao.getEtapas().add((Etapa)etapa.toBusiness());
        this.selecaoServiceIfc.atualizaSelecao(selecao);
        /*if (!result.hasErrors()) {
        etapas.add(this.getEtapaServiceIfc().adicionaEtapa(etapa));
        model.addAttribute("etapas", etapas);
        }*/
        return "editar-etapa";
    }
    

}