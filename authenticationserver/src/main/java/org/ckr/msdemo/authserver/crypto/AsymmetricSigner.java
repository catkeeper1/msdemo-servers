package org.ckr.msdemo.authserver.crypto;

import org.springframework.security.jwt.crypto.sign.Signer;

import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;

/**
 * Created by Administrator on 2017/8/20.
 */
public class AsymmetricSigner implements Signer {

    static final String DEFAULT_ALGORITHM = "SHA256withRSA";

    private final PrivateKey key;
    private final String algorithm;



    public AsymmetricSigner(RSAPrivateKey key) {
        this(key, DEFAULT_ALGORITHM);
    }

    public AsymmetricSigner(PrivateKey key, String algorithm) {
        this.key = key;
        this.algorithm = algorithm;
    }


    public byte[] sign(byte[] bytes) {
        try {
            Signature signature = Signature.getInstance(algorithm);
            signature.initSign(key);
            signature.update(bytes);
            return signature.sign();
        }
        catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public String algorithm() {
        return algorithm;
    }



}
