package z.note.lite.advice.cipher;

public interface CipherService {

    byte[] encode(byte[] src);

    byte[] decode(byte[] src);

}
