package org.feasy.cloud.common.util;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Map;

/**
 * <h2>JWT Token签名工具类</h2>
 * @author Yangxiaohui
 */
@Slf4j
public class JwtTokenUtil {

    private static final Long DEFAULT_TTL_MILLIS=30000000L;
    private static final String DEFAULT_JWT_KEY="CHANNEL-Client-UUID-JWT-KEY";
    private static final String DEFAULT_SUBJECT="Login ToKen";




    /**
     * 创建Token
     * @param id        Token的唯一标识
     * @param subject   主题、标题
     * @param ttlMillis 有效时间
     * @return JwtToken字符串
     */
    public static String buildToken(String id, String subject, long ttlMillis, Map<String, Object> claims){
        //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        long expMillis = nowMillis + ttlMillis;
        Date exp = new Date(expMillis);
        //创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
       //生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        SecretKey key = generalKey();
        ///下面就是在为payload添加各种标准声明和私有声明了
        //这里其实就是new一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder()
                //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setClaims(claims)
                //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setId(id)
                //iat: jwt的签发时间
                .setIssuedAt(now)
                //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setSubject(subject)
                // 过期时间
                .setExpiration(exp)
                //设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, key);
        //压缩jwt
        return builder.compact();
    }
    public static String buildToken(String id, long ttlMillis, Map<String, Object> claims){
        return buildToken(id,DEFAULT_SUBJECT,ttlMillis,claims);
    }
    /**
     * 解析JWT 如果jwt已经过期了，这里会抛出jwt过期异常。
     * @param jwt   Jwt字符串
     * @return  Jwt 主体信息 {@link Claims}
     * @throws Exception    jwt过期异常
     */
    public static Claims parseJWT(String jwt) throws Exception {
        //签名秘钥，和生成的签名的秘钥一模一样
        SecretKey key = generalKey();
        //得到DefaultJwtParser
        Claims claims = Jwts.parser()
                //设置签名的秘钥
                .setSigningKey(key)
                //设置需要解析的jwt
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    /**
     * 生成用户Token
     * @param tokenUser 用户信息
     * @return  Token字符
     * @throws Exception    生成异常
     */
//    public static String buildUserLoginToken(@NonNull TokenUser tokenUser)throws Exception{
//        if (tokenUser!=null){
//            Map<String,Object>claims=JSONObject.parseObject(JSONObject.toJSONString(tokenUser),HashMap.class);
//            return JwtTokenUtil.buildToken(tokenUser.getId(),DEFAULT_SUBJECT,DEFAULT_TTL_MILLIS,claims);
//        }
//        throw new RuntimeException("Token生成失败！");
//    }

    /**
     * 解析Token
     *
     * @param jwt   jwt字符串
     * @return  TokenUser
     * @throws Exception    空指针、token过期异常
     */
//    public static TokenUser parseJWTToUser(String jwt)throws Exception{
//        Claims claims=JwtTokenUtil.parseJWT(jwt);
//        if (claims!=null){
//            return JSONObject.parseObject(JSONObject.toJSONString(claims),TokenUser.class);
//        }
//        throw new NullPointerException("Token内容为空...");
//    }

    public static String parseJWTToUserStr(String jwt)throws Exception {
        Claims claims=JwtTokenUtil.parseJWT(jwt);
        if (claims!=null){
            return JSONObject.toJSONString(claims);
        }
        throw new NullPointerException("Token内容为空...");
    }

    private static SecretKey generalKey(){
        //本地的密码解码
        byte[] encodedKey = Base64.decodeBase64(DEFAULT_JWT_KEY);
        System.out.println(encodedKey);
        System.out.println(Base64.encodeBase64URLSafeString(encodedKey));
        // 根据给定的字节数组使用AES加密算法构造一个密钥，使用 encodedKey中的始于且包含 0 到前 leng 个字节这是当然是所有。（后面的文章中马上回推出讲解Java加密和解密的一些算法）
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }


   /* public static void main(String[] args) throws Exception {
        //System.out.println(JwtTokenUtil.buildToken("123","nihao",1000000L,new HashMap<String, Object>(){{put("123","123");}}));
        System.out.println(JSONObject.toJSONString(JwtTokenUtil.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuaWhhbyIsIjEyMyI6IjEyMyIsImV4cCI6MTU3NjQ4NTgzNiwiaWF0IjoxNTc2NDg0ODM2LCJqdGkiOiIxMjMifQ.RbM8NrR1tFu6cpceRmH7m54qQA1Yo9G4iEKIKg5cjJA")));
    }*/
   public static class TokenType{
       public final static String LOGIN_TOKEN="Login Token";
       public final static String SDK_Client_TOKEN="SDK Client Token";
   }
}
