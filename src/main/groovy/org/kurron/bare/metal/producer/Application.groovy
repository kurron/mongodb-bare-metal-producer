package org.kurron.bare.metal.producer

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EnableConfigurationProperties( ApplicationProperties )
class Application {

	static void main( String[] args ) {
		SpringApplication.run( Application, args )
	}

	@Bean
	CustomApplicationRunner customApplicationRunner() {
		new CustomApplicationRunner()
	}
}
