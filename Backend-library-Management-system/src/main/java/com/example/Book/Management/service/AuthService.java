package com.example.Book.Management.service;

import com.example.Book.Management.dto.JwtRequest;
import com.example.Book.Management.dto.JwtResponse;
import com.example.Book.Management.jwt.JwtAuthenticationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    AuthenticationManager manager;

    @Autowired
    JwtAuthenticationHelper jwtAuthenticationHelper;

    @Autowired
    UserDetailsService userDetailsService;


    public JwtResponse login(JwtRequest jwtRequest) {

        this.doAuthenticate(jwtRequest.getUsername() , jwtRequest.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtAuthenticationHelper.generateToken(userDetails);
        // Get the user's role (assuming only one role is assigned to the user)
        String role = userDetails.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .findFirst()
                .orElse("ROLE_STUDENT");

        JwtResponse jwtResponse = JwtResponse
                .builder()
                .jwtToken(token)
                .role(role)
                .build();

        return  jwtResponse;
    }

    private void doAuthenticate(String username, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username , password);

        try {
            manager.authenticate(usernamePasswordAuthenticationToken);
        }
        catch (Exception e){
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
