const timestamp = Date.now().toString();
const nonce = Math.random().toString();
const plaintext = timestamp + nonce;
const signature = crypto.md5().updateWithText(plaintext).digest().toHex();
client.log(`plaintext: ${plaintext}, signature: ${signature}`)
client.log(request.body.tryGetSubstituted());
request.variables.set("timestamp", timestamp);
request.variables.set("nonce", nonce);
request.variables.set("signature", signature);