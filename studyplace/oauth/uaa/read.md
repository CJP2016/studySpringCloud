#授权码模式
#1.获取授权码
http://localhost:8888/uaa/oauth/authorize?client_id=c1&response_type=code&scope=all&redirect_url=http://www.baidu.com
获取code

#2.使用授权码获取token
//获取token 、refresh_token
http://localhost:8888/uaa/oauth/token
client_id c1
client_secret secret
grant_type authorization_code
code   xxxx
redirect_uri  xxx

#简化模式  针对没有服务器端的第三方页面应用，因为没有服务器端就无法接收授权码。 因此直接拼接在url后面
http://localhost:8888/uaa/oauth/authorize?client_id=c1&response_type=token&scope=all&redirect_url=http://www.baidu.com

#密码模式   会泄露用户敏感信息给client，只能用于client是自己开发的情况下，第一方原生App或第一方单页面应用
http://localhost:8888/uaa/oauth/token
client_id c1
client_secret secret
grant_type password
username  xxxx
password  xxxx

#客户端模式  要求对client完全信任，因此不需要账号，密码。一般用于合作方系统对接，拉取一组用户信息
http://localhost:8888/uaa/oauth/token
client_id c1
client_secret secret
grant_type client_credentials


#jwt令牌
三部分组成
#1.HEADER(令牌类型、使用的哈希算法【HMAC SHA256 RSA】)
{
    "alg":"HS256",
    "typ":"JWT"
}

#2.Payload 内容部分 
#可以存放jwt提供的现成字段，如：iss(签发者)、exp（过期时间戳）、sub(面向的用户)等，也可自定义字段，不建议存放敏感信息
{
    "sub": "1234567890",
    "name": "456",
    "admin": true
}

#3.Signature 签名，用于防止jwt内容被篡改
HMACSHA256(
    base64UrlEncode(header) + "." +
    base64UrlEncode(payload),
    secret)

#JWT令牌优点
1.基于json，方便解析
2.可自定义内容，易扩展
3.通过非对称加密算法及数字签名技术，安全性高
4.资源服务使用jwt可不依赖认证服务即可完成授权

#缺点
1.jwt令牌较长，占存储空间较大
