package z.note.lite.web.advice.cipher;

public interface CipherService {

    byte[] encode(byte[] src);

    byte[] decode(byte[] src);

}
