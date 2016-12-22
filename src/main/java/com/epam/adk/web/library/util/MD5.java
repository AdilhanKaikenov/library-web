package com.epam.adk.web.library.util;

import com.epam.adk.web.library.exception.MessageDigestAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 class created on 04.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public final class MD5 {

    private static final Logger log = LoggerFactory.getLogger(MD5.class);

    private static final int SIGNUM = 1;
    private static final int HEX_RADIX = 16;
    private static final int HASH_LENGTH = 32;
    private static final String ZERO_PREFIX = "0";
    private static final String MD_5_ALGORITHM = "MD5";

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
            md5 = MessageDigest.getInstance(MD_5_ALGORITHM);
            md5.reset();
            md5.update(password.getBytes());
            digest = md5.digest();
        } catch (NoSuchAlgorithmException e) {
            log.error("Error in MD5 in get method: {}",  e);
            throw new MessageDigestAlgorithmException("Error in MD5 in get method:",  e);
        }
        BigInteger bigInt = new BigInteger(SIGNUM, digest);
        String md5Hex = bigInt.toString(HEX_RADIX);
        while (md5Hex.length() < HASH_LENGTH){
            md5Hex = ZERO_PREFIX + md5Hex;
        }
        return md5Hex;
    }
}
