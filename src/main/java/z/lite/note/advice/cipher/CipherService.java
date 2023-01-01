package z.lite.note.advice.cipher;

public interface CipherService {

    byte[] encode(byte[] src);

    byte[] decode(byte[] src);

}
