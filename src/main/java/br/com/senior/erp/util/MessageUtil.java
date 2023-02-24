package br.com.senior.erp.util;

import br.com.senior.erp.domain.Pedido;
import br.com.senior.erp.domain.Produto;

public abstract class MessageUtil {

    private MessageUtil() {
    }

    public static final String MAP_PROD_TO_PROD_RESP = "Mapeando Produto para ProdutoResponse";
    public static final String MAP_PROD_REQ_TO_PROD = "Mapeando ProdutoRequest para Produto";
    public static final String MAP_PED_REQ_TO_PED = "Mapeando PedidoRequest para Pedido";
    public static final String MAP_PED_TO_PED_RESP = "Mapeando Pedido para PedidoResponse";

    public static final String RETORNO_HTTP = "Retornando resposta da requisicao";

    public static final String INSERINDO_OBJETO_BD = "Inserindo {} na base de dados";
    public static final String BUSCANDO_OBJETO_BD = "Buscando {} na base de dados";
    public static final String ATUALIZANDO_OBJETO_BD = "Atualizando {} na base de dados";
    public static final String REMOVENDO_OBJETO_BD = "Removendo {} na base de dados";

    public static final String INICIANDO_BUSCA = "Iniciando busca de {} na base de dados";
    public static final String INICIANDO_BUSCA_POR_ID = "Iniciando busca por id de um {} na base de dados";
    public static final String INICIANDO_INSERCAO = "Iniciando insercao de um {} na base de dados";
    public static final String INICIANDO_ATUALIZACAO = "Iniciando atualizacao de um {} na base de dados";
    public static final String INICIANDO_DELECAO = "Iniciando delecao de um {} na base de dados";
    public static final String INICIANDO_APLICACAO_DESCONTO = "Iniciando desconto de um {}";
    public static final String INICIANDO_CANCELAMENTO_PEDIDO = "Iniciando cancelamento de um {}";
    public static final String INICIANDO_FINALIZACAO_PEDIDO = "Iniciando finalizacao de um {}";

    public static final String PRODUTO_ENTIDADE_NOME = Produto.class.getSimpleName();
    public static final String PEDIDO_ENTIDADE_NOME = Pedido.class.getSimpleName();
}
