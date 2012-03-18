package br.com.appestoque.dominio.faturamento;

import br.com.appestoque.dominio.suprimento.Produto;

public class Item {

	private Long id;	
	private Double quantidade;
	private Double valor;
	private Pedido pedido;
	private Produto produto;
	
	public Item(Long id, Double valor, Double quantidade, Pedido pedido, Produto produto) {
		super();
		this.id = id;
		this.quantidade = quantidade;
		this.valor = valor;
		this.pedido = pedido;
		this.produto = produto;
	}
	
	public Item() {
		super();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public Double getValor() {
		return valor;
	}
	
	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	public Pedido getPedido() {
		return pedido;
	}
	
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
}