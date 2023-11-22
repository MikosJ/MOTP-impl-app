package com.example.motpapp.Controller;

import com.example.motpapp.DTO.ValidateCodeDto;
import com.example.motpapp.model.Validation;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/code")
public class OTPController {

    private final GoogleAuthenticator gAuth;

    @SneakyThrows
    @GetMapping("/generate/{username}")
    public void generate(@PathVariable String username, HttpServletResponse response) {
        final GoogleAuthenticatorKey key = gAuth.createCredentials(username);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("demo", username, key);

        BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthURL, BarcodeFormat.QR_CODE, 200, 200);

        ServletOutputStream outputStream = response.getOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        outputStream.close();
    }

    @PostMapping("/validate/key")
    public Validation validateKey(@RequestBody ValidateCodeDto body) {
        return new Validation(gAuth.authorizeUser(body.getUsername(), body.getCode()));
    }
}