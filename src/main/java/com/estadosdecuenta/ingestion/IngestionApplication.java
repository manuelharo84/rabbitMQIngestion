package com.estadosdecuenta.ingestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

@SpringBootApplication
public class IngestionApplication { 

	private final static String TASK_QUEUE_NAME = "hello";

	public static void main(String[] args) throws Exception {
		SpringApplication.run(IngestionApplication.class, args);

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.1.60");
		factory.setPort(5672);
		try (Connection connection = factory.newConnection();
				Channel channel = connection.createChannel()) {
			channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);

			for (int i = 1; i < 100; i++) {
				String message = "Hola Mundo" + i;
				channel.basicPublish("", TASK_QUEUE_NAME, null, message.getBytes());
				System.out.println(" [x] Sent '" + message + "'");

			}
		}

	}

}
