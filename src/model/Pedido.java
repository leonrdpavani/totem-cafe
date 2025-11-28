package model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private List<ItemCarrinho> itens;
    private String formaPagamento;
    private double total;

    public Pedido() {
        this.itens = new ArrayList<>();
        this.total = 0.0;
    }

    public void adicionarItem(Produto produto, int quantidade) {
        for (ItemCarrinho item : itens) {
            if (item.getProduto().getId() == produto.getId()) {
                item.setQuantidade(item.getQuantidade() + quantidade);
                calcularTotal();
                return;
            }
        }
        itens.add(new ItemCarrinho(produto, quantidade));
        calcularTotal();
    }

    public void removerItem(int produtoId) {
        itens.removeIf(item -> item.getProduto().getId() == produtoId);
        calcularTotal();
    }

    public void calcularTotal() {
        total = itens.stream()\n                     .mapToDouble(ItemCarrinho::getSubtotal)
                     .sum();
    }

    public List<ItemCarrinho> getItens() { return itens; }
    public double getTotal() { return total; }
    public String getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(String formaPagamento) { 
        this.formaPagamento = formaPagamento; 
    }
    
    public int getTotalItens() {
        return itens.stream()
                    .mapToInt(ItemCarrinho::getQuantidade)
                    .sum();
    }
}