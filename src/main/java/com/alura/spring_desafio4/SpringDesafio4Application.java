package com.alura.spring_desafio4;

import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDesafio4Application implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SpringDesafio4Application.class, args);
	}

	public static void escreve(String texto){
		System.out.println(texto);
	}

	public static Boolean testaArgumento(int argumento){
		return argumento * 5 > 30;
	}

	public static Integer soma(int a, int b){
		return a+b;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Testes com Funcionais ====");
		OperaSoma<Integer,Integer> operasSoma =(a, b) -> a+b;
		System.out.println(operasSoma.operacao(3,3));

		OperaSoma<String,Boolean> concatena = (a,b) -> (a+" "+b).length()>5;
		System.out.println(concatena.operacao("am", "sex"));

		Consumer<String> print = (message) -> System.out.println(message);
		print.accept("Hello, functional!");

		Predicate<Integer> testaSituacao = a -> a>5;
		System.out.println(testaSituacao.test(6));
		
		Predicate<String> testaPalavra = palavra -> palavra.contains("amor");
		System.out.println(testaPalavra.test("ramon"));

		//Function<Integer, Boolean> funcao = argumento -> argumento * 5 > 30;
		Function<Integer, Boolean> funcao = SpringDesafio4Application::testaArgumento;
		System.out.println("Calcula maiores que 30: "+funcao.apply(7)); 

		Supplier<String> supplier = () -> "meu supplier";
		System.out.println(supplier.get());

		Supplier<Integer> supplier2 = () -> 5*4;
		System.out.println(supplier2.get());

		Comparator<String> comparador = (s1, s2) -> s1.compareTo(s2);
		System.out.println(comparador.compare("aaaaaaa","xupeta"));

		Comparator<Integer> comparadorB = (i1, i2) -> i1.compareTo(i2);
		System.out.println(comparadorB.compare(2,1));

		Consumer<String> imprime = SpringDesafio4Application::escreve;
		imprime.accept("escreveu!");

		//OperaSoma<Integer,Integer> soma = (a,b) -> a*b;
		OperaSoma<Integer,Integer> soma = SpringDesafio4Application::soma;
		System.out.println(soma.operacao(5, 4));

		System.out.println("========== DESAFIOS ==============");

		//1 - Dada a lista de números inteiros a seguir, encontre o maior número dela.
		List<Integer> numeros = Arrays.asList(10, 20, 30, 40, 50);
		IntSummaryStatistics est = numeros.stream()
										.collect(Collectors.summarizingInt(Integer::valueOf));
		System.out.println(est.getMax());

		//2 -Dada a lista de palavras (strings) abaixo, agrupe-as pelo seu tamanho. No código a seguir, há um exemplo prático do resultado esperado.
		List<String> palavras = Arrays.asList("java", "stream", "lambda", "code");
		Map<Integer, List<String>> collect = palavras.stream()
				.collect(Collectors.groupingBy(String::length));
		collect.forEach((t, u) -> System.out.print(t+"="+u+", "));

		System.out.println("");

		//3 - Dada a lista de nomes abaixo, concatene-os separados por vírgula. No código a seguir, há um exemplo prático do resultado esperado.
		List<String> nomes = Arrays.asList("Alice", "Bob", "Charlie");
		nomes.stream()
			.map(s -> s.concat(","))
			.forEach(System.out::print);

		//4 - Dada a lista de números inteiros abaixo, calcule a soma dos quadrados dos números pares.
		List<Integer> numerosDois = Arrays.asList(1, 2, 3, 4, 5, 6);
		Optional<Integer> retorno =numerosDois.stream()
					.peek(System.out::println)
					.filter(n -> n % 2 == 0)
					.peek(n -> System.out.println("depois do filtro "+n))
					.map(n -> n * n)
					.peek(n -> System.out.println("depois do map "+n))
					.reduce(Integer::sum);
		retorno.ifPresent(System.out::println);
		retorno.orElseThrow();

		//5 - Filtre todos os produtos da categoria "Eletrônicos" com preço menor que R$ 1000, 
		//ordene-os pelo preço em ordem crescente e colete o resultado em uma nova lista.
		List<Produto> produtos = Arrays.asList(
			new Produto("Smartphone", 800.0, "Eletrônicos"),
			new Produto("Notebook", 1500.0, "Eletrônicos"),
			new Produto("Teclado", 200.0, "Eletrônicos"),
			new Produto("Cadeira", 300.0, "Móveis"),
			new Produto("Monitor", 900.0, "Eletrônicos"),
			new Produto("Mesa", 700.0, "Móveis")
		);

		List<Produto> produtosFiltrados = produtos.stream()
				.filter(p -> p.getCategoria().equalsIgnoreCase("eletrônicos"))
				.filter(p -> p.getPreco()<1000.0)
				.sorted((p1,p2) -> Double.valueOf(p1.getPreco()).compareTo(p2.getPreco()))
				.collect(Collectors.toList());
		produtosFiltrados.forEach(System.out::println);

		System.out.println("");
		//6 - Dada a lista de produtos acima, agrupe-os por categoria em um Map<String, List<Produto>>.
		Map<String,List<Produto>> mapaProdutos = produtos.stream()
				.collect(Collectors.groupingBy(Produto::getCategoria));
		mapaProdutos.forEach((k,v) -> System.out.println(k+" "+v));

		//7 - Dada a lista de produtos acima, conte quantos produtos há em cada categoria e armazene em um Map<String, Long>
		Map<String, Long> collect2 = produtos.stream()
				.collect(Collectors.groupingBy(Produto::getCategoria,Collectors.counting()));
		collect2.forEach((k,v) -> System.out.println(k+" "+v));

		//8 - Dada a lista de produtos acima, encontre o produto mais caro de cada categoria e armazene o resultado em um Map<String, Optional<Produto>>.
		//produtos.stream()

		//9 - Dada a lista de produtos acima, calcule o total dos preços dos produtos em cada categoria e armazene o resultado em um Map<String, Double>.
	}
}
