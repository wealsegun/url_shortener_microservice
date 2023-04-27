package com.example.url_shortener.auth;

import com.example.url_shortener.config.JwtService;
import com.example.url_shortener.user.Role;
import com.example.url_shortener.user.User;
import com.example.url_shortener.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private  final PasswordEncoder passwordEncoder;
    private  final JwtService jwtService;
    private  final AuthenticationManager authenticationManager;
//    public AuthenticationResponse authenticate(AuthenticateRequest request) {
//       try {
//           var response = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
//
//           if (response.isAuthenticated()) {
//               var user = repository.findByEmail(request.getEmail());
//               if(user.isPresent()) {
//                   var jwtToken = jwtService.generateToken(user.orElseThrow());
//                   return AuthenticationResponse.builder().token(jwtToken).build();
//               } else {
//                   throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
//               }
//           } else  {
//               throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
//           }
//       }  catch (NoSuchElementException ex) {
//           throw new ResponseStatusException(
//                   HttpStatus.NOT_FOUND, "User not found", ex);
//       }
//    }

    public AuthenticationResponse authenticate(AuthenticateRequest request) {
        try {
            var response = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            if (response.isAuthenticated()) {
                var user = repository.findByEmail(request.getEmail());
                if (user == null) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
                }
                var jwtToken = jwtService.generateToken(user.orElseThrow());
                return AuthenticationResponse.builder().token(jwtToken).user(user.get()).build();
            } else  {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
        }  catch (NoSuchElementException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found", ex);
        }
    }

    public AuthenticationResponse register( RegisterRequest request) {
        var checkExist = repository.findByEmail(request.getEmail());
        if(checkExist.isEmpty()) {
            var user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();
            repository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder().token(jwtToken).user(user).build();
        } else  {
            return new AuthenticationResponse();
        }


    }
}
