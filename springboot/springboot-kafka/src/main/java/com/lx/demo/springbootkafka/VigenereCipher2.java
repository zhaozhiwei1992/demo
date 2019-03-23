package com.lx.demo.springbootkafka;

/**
 * 提供一个字母表和密钥，然后在对字符串加密的时候，找到字符串中字母在字母表的序号，在此位置上开始偏移，偏移量为
 * （根据当前为第几个字符在密钥中找到对应的字母，然后在字母表中找到密钥的字母的序号），最终得到加密后的字母。
 * 如果超出了字母表，则从头开始
 */
public class VigenereCipher2 {

    /**大写字母表**/
    static String alpha="ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 处理密钥
     * @param str 字符串
     * @param K 密钥
     * @return 与str长度相等的密钥字符串
     */
    public static String dealK(String str,String K){
        K=K.toUpperCase();// 将密钥转换成大写
        K=K.replaceAll("[^A-Z]", "");//去除所有非字母的字符
        StringBuilder sb=new StringBuilder(K);
        String key="";
        if(sb.length()!=str.length()){
            //如果密钥长度与str不同，则需要生成密钥字符串
            if(sb.length()<str.length()){
                //如果密钥长度比str短，则以不断重复密钥的方式生成密钥字符串
                while(sb.length()<str.length()){
                    sb.append(K);
                }
            }
            //此时，密钥字符串的长度大于或等于str长度
            //将密钥字符串截取为与str等长的字符串
            key=sb.substring(0, str.length());
        }else{
            return K;
        }
        return key;
    }

    /**
     * 根据vigenere密码算法对明文进行加密
     * @param P 明文
     * @param K 密钥
     * @return 密文
     */
    public static String encrypt(String P,String K){
        P=P.toUpperCase();// 将明文转换成大写
        P=P.replaceAll("[^A-Z]", "");//去除所有非字母的字符
        K=dealK(P,K);
        int len=K.length();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<len;i++){
            int row=alpha.indexOf(K.charAt(i));//行号
            int col=alpha.indexOf(P.charAt(i));//列号
            int index=(row+col)%26;
            sb.append(alpha.charAt(index));
        }
        return sb.toString();
    }

    /**
     * 根据vigenere密码算法对密文进行解密
     * @param C 密文
     * @param K 密钥
     * @return 明文
     */
    public static String decrypt(String C,String K){
        C=C.toUpperCase();// 将密文转换成大写
        C=C.replaceAll("[^A-Z]", "");//去除所有非字母的字符
        K=dealK(C,K);
        int len=K.length();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<len;i++){
            int row=alpha.indexOf(K.charAt(i));//行号
            int col=alpha.indexOf(C.charAt(i));//列号
            int index;
            if(row>col){
                index=col+26-row;
            }else{
                index=col-row;
            }
            sb.append(alpha.charAt(index));
        }
        return sb.toString();
    }

    public static void main(String args[]){
        String P="codewars";
        String K="password";
        String C=encrypt(P,K);
        System.out.println("密文："+C);
        System.out.println("明文："+decrypt(C,K));
    }
}
