package com.vladyslav.luckypets.controller;

import com.vladyslav.config.EmailTemplate;
import com.vladyslav.luckypets.model.TarjetaBancaria;
import com.vladyslav.luckypets.model.Usuarios;
import com.vladyslav.luckypets.service.EmailService;
import com.vladyslav.luckypets.service.UsuariosService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosService usuariosService;

    @Autowired
    private EmailService emailService;

    @Value("${custom.server.url}")
    private String serverUrl;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");
        Optional<Usuarios> usuario = usuariosService.findByEmail(email);

        if (usuario.isPresent()) {
            String storedPassword = usuario.get().getPassword();
            String hashedPassword = DigestUtils.md5Hex(password);

            if (storedPassword.equals(password) || storedPassword.equals(hashedPassword)) {
                return ResponseEntity.ok(usuario.get());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
        }
    }

    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody Usuarios usuario) {
        if (usuario.getEmail() == null || usuario.getPassword() == null) {
            return ResponseEntity.badRequest().body("El correo electrónico y la contraseña son obligatorios.");
        }

        Optional<Usuarios> existingUser = usuariosService.findByEmail(usuario.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("El correo electrónico ya está registrado.");
        }

        // Encripta la contraseña antes de guardar
        usuario.setPassword(DigestUtils.md5Hex(usuario.getPassword()));

        Usuarios savedUser = usuariosService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping
    public ResponseEntity<List<Usuarios>> getAllUsuarios() {
        List<Usuarios> usuarios = usuariosService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuarios> getUsuarioByEmail(@PathVariable String email) {
        Optional<Usuarios> usuario = usuariosService.findByEmail(email);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @GetMapping("/tarjetas/email/{email}")
    public ResponseEntity<List<TarjetaBancaria>> getTarjetasByEmail(@PathVariable String email) {
        Optional<Usuarios> usuario = usuariosService.findByEmail(email);
        if (usuario.isPresent()) {
            List<TarjetaBancaria> tarjetas = usuario.get().getTarjetasBancarias();
            return ResponseEntity.ok(tarjetas);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/sendResetCode")
    public ResponseEntity<?> sendResetCode(@RequestBody Map<String, String> requestData) {
        String email = requestData.get("email");
        if (email == null || !email.contains("@")) {
            return ResponseEntity.badRequest().body("Proporcione un correo electrónico válido.");
        }

        return usuariosService.findByEmail(email).map(usuario -> {
            String resetCode = generateRandomCode(9);
            usuario.setCodigo_restablecimiento(resetCode);
            usuario.setCodigo_expiry(LocalDateTime.now().plusMinutes(15)); // Asegura que el código expire en 15 minutos
            try {
                usuariosService.save(usuario);

                // Crear el asunto y el contenido del correo electrónico
                String subject = "Código de restablecimiento de contraseña para LuckyPets";
                String content = EmailTemplate.getResetPasswordEmail(serverUrl, resetCode);

                // Enviar el correo electrónico
                emailService.sendEmail(email, subject, content);
                return ResponseEntity.ok("¡Código de restablecimiento enviado!");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error al guardar el usuario con el código de restablecimiento.");
            }
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Correo no registrado en el sistema."));
    }

    @PostMapping("/verifyResetCode")
    public ResponseEntity<?> verifyResetCode(@RequestBody Map<String, String> requestData) {
        String email = requestData.get("email");
        String code = requestData.get("code");
        return usuariosService.findByEmail(email).map(usuario -> {
            if (usuario.getCodigo_restablecimiento() != null && usuario.getCodigo_restablecimiento().equals(code)
                    && usuario.getCodigo_expiry().isAfter(LocalDateTime.now())) {
                return ResponseEntity.ok(true); // Código correcto y no expirado
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("¡Código incorrecto o expirado!");
            }
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("¡Usuario no encontrado!"));
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> requestData) {
        String email = requestData.get("email");
        String newPassword = requestData.get("newPassword");

        if (newPassword == null || newPassword.isEmpty() || newPassword.length() < 9 || newPassword.length() > 25) {
            return ResponseEntity.badRequest().body("La contraseña debe tener entre 9 y 25 caracteres.");
        }

        return usuariosService.findByEmail(email).map(usuario -> {
            // Encripta la nueva contraseña antes de guardar
            usuario.setPassword(DigestUtils.md5Hex(newPassword));
            try {
                usuariosService.save(usuario);
                return ResponseEntity.ok("¡Contraseña actualizada correctamente!");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error al actualizar la contraseña.");
            }
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("¡Usuario no encontrado!"));
    }

    @PutMapping("/email/{email}")
    public ResponseEntity<Usuarios> updateUsuario(@PathVariable String email, @RequestBody Usuarios usuarioDetails) {
        Optional<Usuarios> optionalUsuario = usuariosService.findByEmail(email);
        if (optionalUsuario.isPresent()) {
            Usuarios usuario = optionalUsuario.get();
            usuario.setDni(usuarioDetails.getDni());
            usuario.setNombre(usuarioDetails.getNombre());
            usuario.setApellidos(usuarioDetails.getApellidos());
            usuario.setDireccion(usuarioDetails.getDireccion());
            usuario.setProvincia(usuarioDetails.getProvincia());
            usuario.setCodigoPostal(usuarioDetails.getCodigoPostal());
            usuario.setTelefono(usuarioDetails.getTelefono());
            usuario.setEmail(usuarioDetails.getEmail());
            usuario.setSaldoCR(usuarioDetails.getSaldoCR()); // Asegúrate de actualizar el saldo

            final Usuarios updatedUsuario = usuariosService.save(usuario);
            return ResponseEntity.ok(updatedUsuario);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuariosService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Método para generar un código aleatorio
    private String generateRandomCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            code.append(characters.charAt(index));
        }
        return code.toString();
    }
}
