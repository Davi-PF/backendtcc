package zlo.projeto.backendtcc;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import java.io.IOException;

@SpringBootApplication
@EnableCaching
public class ZlospringserviceApplication {

	public static void main(String[] args) {
		try {
			initializeFirebase();
		} catch (IOException e) {
			System.err.println("Erro ao inicializar o Firebase: " + e.getMessage());
		}
		SpringApplication.run(ZlospringserviceApplication.class, args);
	}

	private static void initializeFirebase() throws IOException {
		FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.getApplicationDefault()) // Usa a variável de ambiente
				.build();

		if (FirebaseApp.getApps().isEmpty()) {
			FirebaseApp.initializeApp(options);
		}
	}
		SpringApplication.run(ZlospringserviceApplication.class, args);
	}
}
