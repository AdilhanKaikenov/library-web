package com.epam.adk.web.library.util;

import com.epam.adk.web.library.exception.MessageDigestAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Kaikenov Adilhan on 23.12.2015
 *
 * @author Kaikenov Adilhan on 23.12.2015
 */
public class MD5 {

    private static final Logger log = LoggerFactory.getLogger(MD5.class);

    /**
     * The algorithm for password encryption.
     *
     * @param password user password
     * @return String
     */
    public static String get(String password) throws MessageDigestAlgorithmException {
        MessageDigest md5;
        byte[] digest;
        try{
            md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            md5.update(password.getBytes());
            digest = md5.digest();
        } catch (NoSuchAlgorithmException e) {
            log.error("Error in MD5 in get method: {}",  e);
            throw new MessageDigestAlgorithmException("Error in MD5 in get method:",  e);
        }
        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while (md5Hex.length() < 32){
            md5Hex = "0" + md5Hex;
        }
        return md5Hex;
    }
}
