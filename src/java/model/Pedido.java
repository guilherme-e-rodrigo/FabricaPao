/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author gui_v
 */
public class Pedido {
    int id;
    String cliente;
    String dataPedido;
    float valor;
    float qtdPaes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(String dataPedido) {
        this.dataPedido = dataPedido;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public float getQtdPaes() {
        return qtdPaes;
    }

    public void setQtdPaes(float qtdPaes) {
        this.qtdPaes = qtdPaes;
    }
    
    
            
            
}
