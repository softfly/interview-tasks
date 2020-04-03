package yapily.marvel.rest.client;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class MarvelAuthClient {

    protected final static String PARAM_TS = "ts";

    protected final static String PARAM_APIKEY = "apikey";

    protected final static String PARAM_HASH = "hash";

    @Value("${marvel.api.publickey}")
    private String publicKey;

    @Value("${marvel.api.privatekey}")
    private String privateKey;

    public String enrichAuthParams(String url) throws NoSuchAlgorithmException {
        return enrichAuthParams(UriComponentsBuilder.fromHttpUrl(url)).toUriString();
    }

    public UriComponentsBuilder enrichAuthParams(UriComponentsBuilder builder) throws NoSuchAlgorithmException {
        String ts = Long.toString(Instant.now().toEpochMilli());

        builder.queryParam(PARAM_TS, ts)//
               .queryParam(PARAM_APIKEY, publicKey)//
               .queryParam(PARAM_HASH, getHash(ts));

        return builder;
    }

    public String getHash(String timestamp) throws NoSuchAlgorithmException {
        return md5(timestamp + privateKey + publicKey);
    }

    protected String md5(String toHash) throws NoSuchAlgorithmException {
        return DigestUtils.md5Hex(toHash);
    }

    protected String getPublicKey() {
		return publicKey;
	}

	protected String getPrivateKey() {
		return privateKey;
	}

	protected void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	protected void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

}
