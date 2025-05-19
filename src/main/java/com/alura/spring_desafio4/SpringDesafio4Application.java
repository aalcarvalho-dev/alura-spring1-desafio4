package com.alura.spring_desafio4;

import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
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

	public static String nada(){
		return "nadinha";
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

		Function<Integer, Boolean> funcao = argumento -> argumento * 5 > 30;
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

		Supplier<String> teste = SpringDesafio4Application::nada;
		System.out.println(teste.get());

		System.out.println("========== DESAFIOS ==============");

		//1 - Dada a lista de números inteiros a seguir, encontre o maior número dela.
		List<Integer> numeros = Arrays.asList(10, 20, 30, 40, 50);
		IntSummaryStatistics est = numeros.stream()
										.collect(Collectors.summarizingInt(n -> n));
		System.out.println(est.getMax());

		//2 -Dada a lista de palavras (strings) abaixo, agrupe-as pelo seu tamanho. No código a seguir, há um exemplo prático do resultado esperado.
		List<String> palavras = Arrays.asList("java", "stream", "lambda", "code");
		Map<Integer, List<String>> collect = palavras.stream()
				.collect(Collectors.groupingBy(p -> p.length()));
		collect.forEach((t, u) -> System.out.print(t+"="+u+", "));
		
	}

}
