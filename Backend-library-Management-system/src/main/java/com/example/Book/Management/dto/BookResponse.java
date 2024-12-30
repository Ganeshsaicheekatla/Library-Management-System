package com.example.Book.Management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    private BookAddRequest bookAddRequest;
    private String imageBase64;

    public void setImageBase64(byte[] imageBytes) {
        if (imageBytes != null) {
            this.imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
        } else {
            this.imageBase64 = null; // or set a default value if needed
        }
    }
}
