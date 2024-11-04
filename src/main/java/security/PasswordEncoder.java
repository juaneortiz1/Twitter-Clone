package security;

import org.mindrot.jbcrypt.BCrypt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PasswordEncoder {

    /**
     * Encripta la contraseña usando BCrypt
     * @param password la contraseña en texto plano
     * @return la contraseña encriptada
     */
    public String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    /**
     * Verifica si la contraseña en texto plano coincide con la contraseña encriptada
     * @param password la contraseña en texto plano
     * @param hashedPassword la contraseña encriptada
     * @return true si coinciden, false si no
     */
    public boolean verify(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}