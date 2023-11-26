package com.example.motpapp.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class OTPService {
    private GoogleAuthenticator gAuth;

    @Autowired
    public void QRCodeService(GoogleAuthenticator gAuth) {
        this.gAuth = gAuth;
    }

    public OTPService(GoogleAuthenticator gAuth) {
        this.gAuth = gAuth;
    }

    public byte[] generateQRCode(String username) throws IOException, WriterException {
        final GoogleAuthenticatorKey key = gAuth.createCredentials(username);

        String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("MOTP-Impl", username, key);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthURL, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(0xFF000002, 0xFF04B4AE);
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream, matrixToImageConfig);

        return pngOutputStream.toByteArray();
    }
}
