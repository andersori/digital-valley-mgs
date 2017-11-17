/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.russas.n2s.darwin.model;

/**
 *
 * @author Wallison Carlos
 */
public enum EnumPermissoes {
    PARTICIPANTE(1),
    AVALIADOR(2),
    RESPONSAVEL(3),
    ADMINISTRADOR(4);

    private int nivel;
    
    EnumPermissoes() {
        
    }

    EnumPermissoes(int nivel) {
        setNivel(nivel);
    }

    public int getNivel() {
        return nivel;
    }
    
    public void setNivel(int nivel){
        if (nivel>=1 && nivel<=4) { 
            this.nivel = nivel;
        } else {
            throw new IllegalArgumentException("Critério de avaliação deve ser maior igual a um e menor igual a quatro!");
        }            
    }
}
