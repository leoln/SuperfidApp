package br.com.bhl.superfid.model;

import java.io.Serializable;

public class Pagamento  implements Serializable {

    private CartaoCredito cartao;
    private Compra compra;

    public CartaoCredito getCartao() {
        return cartao;
    }

    public void setCartao(CartaoCredito cartao) {
        this.cartao = cartao;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }
}
