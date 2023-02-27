package com.wtrue.rical.common.eve.utils;

import com.wtrue.rical.common.adam.domain.BaseException;
import com.wtrue.rical.common.adam.enums.ErrorEnum;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA加密
 * 非对称加密，有公钥和私钥之分，公钥用于数据加密，私钥用于数据解密。加密结果可逆
 * 公钥一般提供给外部进行使用，私钥需要放置在服务器端保证安全性。
 * 特点：加密安全性很高，但是加密速度较慢
 *
 * @author meidanlong
 */
public class RSAUtil {

	public static RSAPublicKey getPublicKey(String publicKey) {
		try{
			byte[] decoded = Base64.decodeBase64(publicKey);
			return (RSAPublicKey) KeyFactory.getInstance("RSA")
					.generatePublic(new X509EncodedKeySpec(decoded));
		}catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new BaseException(ErrorEnum.UTILS_ERROR, e);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new BaseException(ErrorEnum.UTILS_ERROR, e);
		}
	}

	public static RSAPrivateKey getPrivateKey(String privateKey) {
		try {
			byte[] decoded = Base64.decodeBase64(privateKey);
			return (RSAPrivateKey) KeyFactory.getInstance("RSA")
					.generatePrivate(new PKCS8EncodedKeySpec(decoded));
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new BaseException(ErrorEnum.UTILS_ERROR, e);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new BaseException(ErrorEnum.UTILS_ERROR, e);
		}
	}
	
	public static RSAKey generateKeyPair() {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(1024, new SecureRandom());
			KeyPair keyPair = keyPairGen.generateKeyPair();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
			String privateKeyString = new String(Base64.encodeBase64(privateKey.getEncoded()));
			return new RSAKey(privateKey, privateKeyString, publicKey, publicKeyString);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new BaseException(ErrorEnum.UTILS_ERROR, e);
		}
	}

	public static String encrypt(String publicKey, String source) {
		try{
			byte[] decoded = Base64.decodeBase64(publicKey);
			RSAPublicKey rsaPublicKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
					.generatePublic(new X509EncodedKeySpec(decoded));
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(1, rsaPublicKey);
			return Base64.encodeBase64String(cipher.doFinal(source.getBytes(StandardCharsets.UTF_8)));
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			throw new BaseException(ErrorEnum.UTILS_ERROR, e);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new BaseException(ErrorEnum.UTILS_ERROR, e);
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new BaseException(ErrorEnum.UTILS_ERROR, e);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new BaseException(ErrorEnum.UTILS_ERROR, e);
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new BaseException(ErrorEnum.UTILS_ERROR, e);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new BaseException(ErrorEnum.UTILS_ERROR, e);
		}
	}

	public static Cipher getCipher(String privateKey) {
		try{
			byte[] decoded = Base64.decodeBase64(privateKey);
			RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA")
					.generatePrivate(new PKCS8EncodedKeySpec(decoded));
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(2, rsaPrivateKey);
			return cipher;
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			throw new BaseException(ErrorEnum.UTILS_ERROR, e);
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new BaseException(ErrorEnum.UTILS_ERROR, e);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new BaseException(ErrorEnum.UTILS_ERROR, e);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new BaseException(ErrorEnum.UTILS_ERROR, e);
		}
	}

	public static String decrypt(String priavteKey, String text) {
		try{
			Cipher cipher = getCipher(priavteKey);
			byte[] inputByte = Base64.decodeBase64(text.getBytes(StandardCharsets.UTF_8));
			return new String(cipher.doFinal(inputByte));
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new BaseException(ErrorEnum.UTILS_ERROR, e);
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new BaseException(ErrorEnum.UTILS_ERROR, e);
		}
	}
	
	public static class RSAKey {
		  private RSAPrivateKey privateKey;
		  private String privateKeyString;
		  private RSAPublicKey publicKey;
		  public String publicKeyString;

		  public RSAKey(RSAPrivateKey privateKey, String privateKeyString, RSAPublicKey publicKey, String publicKeyString) {
		    this.privateKey = privateKey;
		    this.privateKeyString = privateKeyString;
		    this.publicKey = publicKey;
		    this.publicKeyString = publicKeyString;
		  }

		  public RSAPrivateKey getPrivateKey() {
		    return this.privateKey;
		  }

		  public void setPrivateKey(RSAPrivateKey privateKey) {
		    this.privateKey = privateKey;
		  }

		  public String getPrivateKeyString() {
		    return this.privateKeyString;
		  }

		  public void setPrivateKeyString(String privateKeyString) {
		    this.privateKeyString = privateKeyString;
		  }

		  public RSAPublicKey getPublicKey() {
		    return this.publicKey;
		  }

		  public void setPublicKey(RSAPublicKey publicKey) {
		    this.publicKey = publicKey;
		  }

		  public String getPublicKeyString() {
		    return this.publicKeyString;
		  }

		  public void setPublicKeyString(String publicKeyString) {
		    this.publicKeyString = publicKeyString;
		  }
		}
}