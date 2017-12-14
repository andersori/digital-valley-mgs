/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.russas.n2s.darwin.beans;

import br.ufc.russas.n2s.darwin.model.Documentacao;
import br.ufc.russas.n2s.darwin.model.Participante;
import br.ufc.russas.n2s.darwin.model.UsuarioDarwin;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 *
 * @author N2S-PC03
 */
public class ParticipanteBeans implements Beans {

    private long codParticipante;
    private UsuarioDarwin candidato;
    private boolean deferido;
    private LocalDateTime data;
    private boolean notificado;

    public ParticipanteBeans(){
    }

    public ParticipanteBeans(long codParticipante, UsuarioDarwin candidato, boolean deferido, LocalDateTime data, boolean notificado) {
        this.codParticipante = codParticipante;
        this.candidato = candidato;
        this.deferido = deferido;
        this.data = data;
        this.notificado = notificado;
    }

    public long getCodParticipante() {
        return codParticipante;
    }

    public void setCodParticipante(long codParticipante) {
        this.codParticipante = codParticipante;
    }

    public UsuarioDarwin getCandidato() {
        return candidato;
    }

    public void setCandidato(UsuarioDarwin candidato) {
        this.candidato = candidato;
    }

    public boolean isDeferido() {
        return deferido;
    }

    public void setDeferido(boolean deferido) {
        this.deferido = deferido;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public boolean isNotificado() {
        return notificado;
    }

    public void setNotificado(boolean notificado) {
        this.notificado = notificado;
    }

    @Override
    public Object toBusiness() {
        Participante participante = new Participante();

        if (this.getCodParticipante() > 0) {
            participante.setCodParticipante(this.getCodParticipante());
        }
        participante.setCandidato(this.getCandidato());
        participante.setData(this.getData());
        participante.setDeferido(this.isDeferido());
        participante.setNotificado(this.isNotificado());
        return participante;
    }

    @Override
    public Beans toBeans(final Object object) {
        if (object != null) {
            if (object instanceof Participante) {
                Participante participante = (Participante) object;
                this.setCodParticipante(participante.getCodParticipante());
                this.setCandidato(participante.getCandidato());
                this.setData(participante.getData());
                this.setDeferido(participante.isDeferido());
                this.setNotificado(isNotificado());
                return this;
            } else {
                throw new IllegalArgumentException("O objeto a ser adicionado não é um participante");
            }
        } else {
            throw new NullPointerException("Participante não pode ser nulo!");
        }
    }
}
