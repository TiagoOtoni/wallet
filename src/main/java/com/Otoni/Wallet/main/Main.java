package com.Otoni.Wallet.main;

import com.Otoni.Wallet.calc.Calc;
import com.Otoni.Wallet.model.Ativo;
import com.Otoni.Wallet.model.Compras;
import com.Otoni.Wallet.model.TipoAtivo;
import com.Otoni.Wallet.repository.AtivoRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    Calc calc = new Calc();
    private final AtivoRepository repository;
    Scanner scanner = new Scanner(System.in);

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
                   4- Excluir ativo
                   5- Listar ativos
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
               case 4:
                   deletarAtivo();
                   break;
               case 5:
                   listarAtivos();
                   break;
               case 0:
                   System.out.println("Encerrando....");
                   break;
               default:
                   System.out.println("Opção inválida!");

           }
       }
    }

    private void listarAtivos() {
        List<Ativo> todosAtivos = repository.findAll();
        System.out.println(todosAtivos);
    }


    private void buscarAtivo() {

        System.out.println("Digite ticker para busca:");
        var ticker = scanner.nextLine();
        Optional<Ativo> ativoEncontrado = repository.findByTickerContainingIgnoreCase(ticker);

        if (ativoEncontrado.isPresent()){
            var ativoEncontradoCompras = ativoEncontrado.get().getListaCompras();
            System.out.println(ativoEncontradoCompras);

            System.out.println(calc.calculaTotal(ativoEncontrado));
            calc.clear();

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
    private void deletarAtivo () {
        System.out.println("Digite ticker para excluir:");
        var ticker = scanner.nextLine();
        Optional<Ativo> ativoEncontrado = repository.findByTickerContainingIgnoreCase(ticker);
        if (ativoEncontrado.isPresent()){
            var ativoEncontradoCompras = ativoEncontrado.get();

            repository.delete(ativoEncontradoCompras);
            System.out.println(ativoEncontradoCompras.getTicker().toUpperCase() + " Deletado");
        }else {
            System.out.println(ticker + " Não encontrado");
        }
    }
}
