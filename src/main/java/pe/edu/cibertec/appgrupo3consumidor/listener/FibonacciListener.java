package pe.edu.cibertec.appgrupo3consumidor.listener;

import pe.edu.cibertec.appgrupo3consumidor.dto.FibonacciSolicitadoEvent;
import pe.edu.cibertec.appgrupo3consumidor.service.FibonacciService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Component
public class FibonacciListener {

    private final FibonacciService fibonacciService;

    public FibonacciListener(FibonacciService fibonacciService) {
        this.fibonacciService = fibonacciService;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void receiveMessage(FibonacciSolicitadoEvent event) throws InterruptedException {
        String cadenaNumeros = event.numbers();
        System.out.println("Mensaje recibido de RabbitMQ: " + cadenaNumeros);

        Integer[] integerArray = Stream.of(cadenaNumeros.split(";"))
                .map(String::trim)
                .map(Integer::parseInt)
                .toArray(Integer[]::new);

        List<Integer> positions = Arrays.asList(integerArray);


        Thread.sleep(20000);

        List<Long> resultado = fibonacciService.calculateSequence(positions);

        System.out.println("Resultado Fibonacci: " + resultado);
    }
}

