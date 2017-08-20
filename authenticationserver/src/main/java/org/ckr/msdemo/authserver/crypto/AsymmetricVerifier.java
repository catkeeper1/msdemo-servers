package org.ckr.msdemo.authserver.crypto;

import org.springframework.security.jwt.crypto.sign.InvalidSignatureException;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;

import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;

/**
 * Created by Administrator on 2017/8/20.
 */
public class AsymmetricVerifier implements SignatureVerifier {

    private final PublicKey key;
    private final String algorithm;


    public AsymmetricVerifier(RSAPublicKey key) {
        this(key, AsymmetricSigner.DEFAULT_ALGORITHM);
    }

    public AsymmetricVerifier(PublicKey key, String algorithm) {
        this.key = key;
        this.algorithm = algorithm;
    }



    public void verify(byte[] content, byte[] sig) {
        try {
            Signature signature = Signature.getInstance(algorithm);
            signature.initVerify(key);
            signature.update(content);

            if (!signature.verify(sig)) {
                throw new InvalidSignatureException("Signature did not match content");
            }
        }
        catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public String algorithm() {
        return algorithm;
    }

}
