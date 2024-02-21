package z.note.lite.config.advice.cipher;

public interface CipherService {

    byte[] encode(byte[] src);

    byte[] decode(byte[] src);

}
