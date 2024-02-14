package com.Otoni.Wallet.main;

import com.Otoni.Wallet.model.Ativo;
import com.Otoni.Wallet.model.Compras;
import com.Otoni.Wallet.model.TipoAtivo;
import com.Otoni.Wallet.repository.AtivoRepository;
import com.Otoni.Wallet.service.Search;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    Locale localBrasil = new Locale("pt", "BR");
    Search search = new Search();
    private final AtivoRepository repository;
    private Scanner scanner = new Scanner(System.in);

    public Main(AtivoRepository repository) {
        this.repository = repository;
    }

    public void displayMenu(){
       var option = -1;

       while (option != 0) {
           var menu = """
                   
                   *** Wallet ***
                   
                   1- Cadastrar ativo
                   2- Cadastrar compra
                   3- Buscar ativo
                   0- Sair
                   """;

           System.out.println(menu);
           option = scanner.nextInt();
           scanner.nextLine();

           switch (option){
               case 1:
                   cadastrarAtivo();
                   break;
               case 2:
                   cadastrarCompra();
                   break;
               case 3:
                   buscarAtivo();
                   break;
               case 0:
                   System.out.println("Encerrando....");
                   break;
               default:
                   System.out.println("Opção inválida!");

           }
       }
    }

    private String formatCurrency(double value){
        return NumberFormat.getCurrencyInstance(localBrasil).format(value);
    }

    private void buscarAtivo() {
        int quantidadeTotal = 0;
        double liquidoTotal = 0;
        double precoMedio = 0;
        double preçoMercado = 0;
        double valorMercado = 0;
        double lucro = 0;

        System.out.println("Digite ticker para busca:");
        var ticker = scanner.nextLine();
        Optional<Ativo> ativoEncontrado = repository.findByTickerContainingIgnoreCase(ticker);

        if (ativoEncontrado.isPresent()){
            var ativoEncontradoCompras = ativoEncontrado.get().getListaCompras();
            System.out.println(ativoEncontradoCompras);

            if (ativoEncontradoCompras != null){
                for (Compras i : ativoEncontradoCompras){
                    quantidadeTotal += i.getQuantidade();
                    liquidoTotal += i.getLiquido();
                }
                precoMedio = liquidoTotal / quantidadeTotal;
                preçoMercado = search.findPrice(ativoEncontrado.get().getTicker());
                valorMercado = quantidadeTotal * preçoMercado;
                lucro = valorMercado - liquidoTotal;

                System.out.println("( " + ativoEncontrado.get().getTicker() + " ) " +
                        " ( Quantidade total: " + quantidadeTotal + " ) " +
                        " ( Liquido Total: " + formatCurrency(liquidoTotal) + " ) " +
                        " ( Preço Médio: " + formatCurrency(precoMedio) + " ) " + " || " +
                        " ( Preço Mercado: " + formatCurrency(preçoMercado) + " ) " +
                        " ( Valor Mercado: " + formatCurrency(valorMercado) + " ) " +
                        " (Lucro ou perda : " + formatCurrency(lucro) + " ) "
                );

                quantidadeTotal = 0;
                liquidoTotal = 0;
                precoMedio = 0;
                preçoMercado = 0;
                valorMercado = 0;
                lucro = 0;
            }

        }else {
            System.out.println(ticker + " Não encontrado");
        }

    }

    private void cadastrarCompra() {
        System.out.println("Digite ticker:");
        var ticker = scanner.nextLine();
        Optional<Ativo> ativoBuscado = repository.findByTickerContainingIgnoreCase(ticker);
        if (ativoBuscado.isPresent()) {
            System.out.println("Digite a data:");
            var data = scanner.nextLine();
            System.out.println("Digite preço");
            var preco = scanner.nextDouble();
            System.out.println("Digite quantidade:");
            var quantidade = scanner.nextInt();
            System.out.println("Digite total emolumentos:");
            var emolumentos = scanner.nextDouble();

            Compras novaCompra = new Compras(data,preco,quantidade,emolumentos);
            novaCompra.setAtivo(ativoBuscado.get());
            ativoBuscado.get().getListaCompras().add(novaCompra);
            repository.save(ativoBuscado.get());
        } else {
            System.out.println(ticker + " Não encontrado");
        }
    }

    private void cadastrarAtivo() {
        var cadastrarNovo = "S";

        while (cadastrarNovo.equalsIgnoreCase("s")) {
            System.out.println("Informe o ticker do ativo:");
            var ticker = scanner.nextLine();
            System.out.println("Informe o tipo: (Acao/Fii) ");
            var tipo = scanner.nextLine();
            TipoAtivo tipoAtivo = TipoAtivo.valueOf(tipo.toUpperCase());
            Ativo ativo = new Ativo(ticker, tipoAtivo);
            repository.save(ativo);
            System.out.println("Cadastrar novo ativo? (S/N)");
            cadastrarNovo = scanner.nextLine();


        }
    }
}
