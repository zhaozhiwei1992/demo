package com.lx.demo.springbootkafka;

/**
 * 提供一个字母表和密钥，然后在对字符串加密的时候，找到字符串中字母在字母表的序号，在此位置上开始偏移，偏移量为
 * （根据当前为第几个字符在密钥中找到对应的字母，然后在字母表中找到密钥的字母的序号），最终得到加密后的字母。
 * 如果超出了字母表，则从头开始
 */
public class VigenereCipher {

    /**
     * 字母表
     */
    private  String alpha = "abcdefghijklmnopqrstuvwxyz";

    /**
     * 密钥
     */
    private String  key = "password";

    public VigenereCipher(String key, String alphabet){
        this.key = key;
        this.alpha = alphabet;
    }

    public static void main(String[] args) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String key1 = "password";

        // 创建一个密码工具，通过字母表中的每一个键位进行移动得到替换后的字母
        VigenereCipher c = new VigenereCipher(key1, alphabet);
        // returns 'rovwsoiv'
        System.out.println(c.encode("codewars"));

        // returns 'codewars '
        System.out.println(c.decode("rovwsoiv"));

        char msg[] = alphabet.toCharArray();
        char key[] = key1.toCharArray();
        int msgLen = msg.length, i, j;

        char newKey[] = new char[msgLen];
        char encryptedMsg[] = new char[msgLen];
        char decryptedMsg[] = new char[msgLen];

        //generate new key in cyclic manner equal to the length of original message
        for(i = 0, j = 0; i < msgLen; ++i, ++j){
            if(j == key.length) {
                j = 0;
            }

            newKey[i] = key[j];
        }

        //encryption
        for(i = 0; i < msgLen; ++i) {
            encryptedMsg[i] = (char)(((msg[i] + newKey[i]) % 26) + 'A');
        }

        //decryption
        for(i = 0; i < msgLen; ++i) {
            decryptedMsg[i] = (char)((((encryptedMsg[i] - newKey[i]) + 26) % 26) + 'A');
        }

        System.out.println("Original Message: " + String.valueOf(msg));		//String.valueOf() converts character array to String
        System.out.println("Key: " + String.valueOf(key));
        System.out.println("Generated Key: " + String.valueOf(newKey));
        System.out.println("\nEncrypted Message: " + String.valueOf(encryptedMsg));
        System.out.println("\nDecrypted Message: " + String.valueOf(decryptedMsg));
    }

    /**
     * 解密 17-->2 14-->14 21->3
     * @param rovwsoiv
     * @return codewars
     */
    private String decode(String rovwsoiv) {
        StringBuffer buffer = new StringBuffer();

        //拆分单个字符, 定位索引
        char[] chars = rovwsoiv.toCharArray();

        char[] keyArray = key.toCharArray();

        char[] alphabetArr = alpha.toCharArray();
        //
        for (int i = 0; i < chars.length; i++) {
            //1. 找到加密后字符串在字母表中位置
            //
//            buffer.append(c);
        }
        return buffer.toString();
    }

    /**
     * 加密
     * @param codewars
     * @return rovwsoiv
     */
    private String encode(String codewars) {
        StringBuffer buffer = new StringBuffer();

        //拆分单个字符, 定位索引
        char[] chars = codewars.toCharArray();

        char[] keyArray = key.toCharArray();

        char[] alphabetArr = alpha.toCharArray();
        //
        for (int i = 0; i < chars.length; i++) {
            //找数据的索引应该取余
            int keyIndex = i%key.length();

            //获取密钥字符在字母表中位置
            int indexOfAlphabet = alpha.indexOf(keyArray[keyIndex]);

            //当前待加密字符在字母表中偏移indexOfAlphabet位
            int indexOf = alpha.indexOf(chars[i]);

            //取余数，字母表长度
            char c = alphabetArr[(indexOf + indexOfAlphabet)% alpha.length()];
            buffer.append(c);
        }
        return buffer.toString();
    }

}
