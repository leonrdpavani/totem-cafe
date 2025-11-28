package controller;

import model.Produto;
import model.Pedido;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class TotemController {
    private List<Produto> produtos;
    private Pedido pedidoAtual;
    private Gson gson;

    public TotemController() {
        this.produtos = new ArrayList<>();
        this.pedidoAtual = new Pedido();
        this.gson = new Gson();
        inicializarProdutos();
    }

    private void inicializarProdutos() {
        produtos.add(new Produto(1, "Café Expresso", "Café forte e encorpado", 5.00, "Cafés", "☕"));
        produtos.add(new Produto(2, "Cappuccino", "Café com leite vaporizado", 7.50, "Cafés", "☕"));
        produtos.add(new Produto(3, "Café Latte", "Café com muito leite", 8.00, "Cafés", "☕"));
        produtos.add(new Produto(4, "Pão de Queijo", "Tradicional mineiro", 4.50, "Lanches", "🥖"));
        produtos.add(new Produto(5, "Croissant", "Massa folhada francesa", 6.00, "Lanches", "🥐"));
        produtos.add(new Produto(6, "Sanduíche Natural", "Pão integral com recheio", 12.00, "Lanches", "🥪"));
        produtos.add(new Produto(7, "Bolo de Chocolate", "Fatia generosa", 8.50, "Doces", "🍰"));
        produtos.add(new Produto(8, "Brownie", "Com pedaços de chocolate", 7.00, "Doces", "🍫"));
        produtos.add(new Produto(9, "Cookie", "Chocolate chip", 5.50, "Doces", "🍪"));
    }

    public String getProdutosJson() {
        return gson.toJson(produtos);
    }

    public String getCarrinhoJson() {
        return gson.toJson(pedidoAtual);
    }

    public String adicionarAoCarrinho(int produtoId, int quantidade) {
        Produto produto = produtos.stream()
                                   .filter(p -> p.getId() == produtoId)
                                   .findFirst()
                                   .orElse(null);
        
        if (produto != null) {
            pedidoAtual.adicionarItem(produto, quantidade);
            return gson.toJson(pedidoAtual);
        }
        return "{\"erro\": \"Produto não encontrado\"}";
    }

    public String removerDoCarrinho(int produtoId) {
        pedidoAtual.removerItem(produtoId);
        return gson.toJson(pedidoAtual);
    }

    public String finalizarPedido(String formaPagamento) {
        pedidoAtual.setFormaPagamento(formaPagamento);
        String resultado = gson.toJson(pedidoAtual);
        pedidoAtual = new Pedido();
        return resultado;
    }

    public void resetarPedido() {
        pedidoAtual = new Pedido();
    }
}