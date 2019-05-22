package dk.asbjoern.foto.fotoorganiser;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import dk.asbjoern.foto.fotoorganiser.runner.Runner;

@SpringBootApplication
public class FotoorganiserApplication implements CommandLineRunner {

	Runner runner;

	public FotoorganiserApplication(Runner runner) {
		this.runner = runner;
	}

	public static void main(String[] args) {
		SpringApplication.run(FotoorganiserApplication.class, args);
	}



	@Override
	public void run(String... args) throws Exception {


		this.runner.run();




	}
}
