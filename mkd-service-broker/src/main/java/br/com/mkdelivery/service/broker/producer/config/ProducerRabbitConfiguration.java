package br.com.mkdelivery.service.broker.producer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerRabbitConfiguration {

	@Value("${spring.rabbitmq.request.routing-key.producer}")
	private String queue;

	@Value("${spring.rabbitmq.request.exchange.producer}")
	private String exchange;

	@Value("${spring.rabbitmq.request.dead-letter.producer}")
	private String deadLetter;

	@Value("${spring.rabbitmq.request.parking-lot.producer}")
	private String parkingLot;

	@Bean
	DirectExchange exchangeProducer() {
		return new DirectExchange(exchange);
	}

	@Bean
	Queue deadLetterProducer() {
		return QueueBuilder.durable(deadLetter).deadLetterExchange(exchange).deadLetterRoutingKey(queue).build();
	}

	@Bean
	Queue queueProducer() {
		return QueueBuilder.durable(queue).deadLetterExchange(exchange).deadLetterRoutingKey(deadLetter).build();
	}

	@Bean
	Queue parkingLotProducer() {
		return new Queue(parkingLot);
	}

	@Bean
	public Binding bindingQueueProducer() {
		return BindingBuilder.bind(queueProducer()).to(exchangeProducer()).with(queue);
	}

	@Bean
	public Binding bindingDeadLetterProducer() {
		return BindingBuilder.bind(deadLetterProducer()).to(exchangeProducer()).with(deadLetter);
	}

	@Bean
	public Binding bindingParkingLotProducer() {
		return BindingBuilder.bind(parkingLotProducer()).to(exchangeProducer()).with(parkingLot);
	}
}
