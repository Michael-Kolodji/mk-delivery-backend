package br.com.mkdelivery.service.broker.consumer.amqp;

public interface AmqpConsumer<T> {
	
	void consumer(T t);
	
	void consumesProcessedPaymentQueue(T t);
	
}
