package br.com.gerencial.configuracoes;

import jakarta.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface VerificarPerfil {
    String[] value() default {}; // Aqui passaremos os perfis aceitos: "ADMIN", "TESOUREIRO", "ASSOCIADO"
}