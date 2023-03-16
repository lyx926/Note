import JSEncrypt from '@/utils/encryption/jsencrypt/jsencrypt';
import CryptoJS from '@/utils/encryption/crypto-js/crypto-js';
//前端私钥
const PRIVATE_KEY="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIjNDEpHm3MVZMbOF1HUOi7mjms/W8o9fhTrCXYoqfaC+lzFvVm1AiEk0RDPR0d/1+YFEXbK3lj4Dk39xyjLWQ0ZsPvjwt7TcOnUafsxHpSaydYIfFPbQcIIYIvvjho8i2RPiTBqqyAu5qpU0Ts6all48nC6gkJwWnz4VqQHpjw7AgMBAAECgYA0A3KG048hRKMai8WGEWFF79d6o45s6NCFd5ATYwasecRjfbOu5gm3PF5ZO9ZoMw2NoaOwPRYpUbduo+0Tb62doyDG0O/NFOVcIJIg00GRnMmh8LiC9GVAUF+9ebC1v61Dtsb7RUrcbqWGXKIT94Ly580S7v6YSJkRmrlaz9zz4QJBAN4GfhtUJxCnK38C9tQmQSk/Q1jQlCAwa+cqASu8m2DbyXqGSGbH/NXC1A+v9kYD98/Wr5LCetFWI+PeH8yMqOkCQQCdvATSyDOXxx+bDjJlSaTENhMakwik5Rx1ATkdjAKQH24UVM7HuR8ZlluZuBTBNPs0HJwmEbBpMICS/eT53UWDAkBasqRJXbMnpmKqW/eg6o+r3eARJAOesqkhoZMg3sdKZf6EQt7IveGXISOn1OXx7CULjlPiy/eIFHxM1ax/zuYhAkAqPeMQy9q9R+9X6AppA7rjbRPapwvGlTD0yLoSBp/dFv205i1mYVZJ1PF9DkZIo7aucwcCTvRuhzW3p9T9cVufAkBq0xH79K5vajTlVqOlZCtSr4JElwNV9ypi4yBnT3jwLi8uDsmws/1N4LotTnrA9PPjeZVixCa/ohMWxXga1Gny";

// 后台公钥
const PUBLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCIzQxKR5tzFWTGzhdR1Dou5o5rP1vKPX4U6wl2KKn2gvpcxb1ZtQIhJNEQz0dHf9fmBRF2yt5Y+A5N/ccoy1kNGbD748Le03Dp1Gn7MR6UmsnWCHxT20HCCGCL744aPItkT4kwaqsgLuaqVNE7OmpZePJwuoJCcFp8+FakB6Y8OwIDAQAB";
 
 
 //解密 数据
export const decryptData = (data) => {
	let aseKey= rsaDecrypt(PRIVATE_KEY,data.encryptedKey)
	return aesDecrypt(data.responseData,aseKey)
}
 
 //加密 数据
export const encryptData = (params) => {
	const key= initAesKey(16);
	const encrypted= rsaEncrypt(key,PUBLIC_KEY)
	const requestData= aesEncrypt(JSON.stringify(params),key)
	return {"requestData":requestData,"encryptedKey":encrypted}
} 
 
//解密  
export const aesDecrypt=(data,publicKey)=>{  
	var key  = CryptoJS.enc.Utf8.parse(publicKey);
	var decrypt = CryptoJS.AES.decrypt(data, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
	return CryptoJS.enc.Utf8.stringify(decrypt).toString();
}

//加密
export const aesEncrypt=(word,publicKey)=>{ 
	var key  = CryptoJS.enc.Utf8.parse(publicKey);
	var srcs = CryptoJS.enc.Utf8.parse(word);
	var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
	return encrypted.toString();
}

/**
 * rsa加密（需要先加载jsencrypt.js文件）, 加密最大长度116位，足够应付这里的aes密匙加密传输
 */
export const rsaEncrypt = (word, publicKey) => {
	const encrypt = new JSEncrypt();
	encrypt.setPublicKey(publicKey);
	return encrypt.encrypt(word);
}

/**
 * rsa解密（需要先加载jsencrypt.js文件）, 加密最大长度116位，足够应付这里的aes密匙加密传输
 */
export const rsaDecrypt = (privateKey, data) => {
	const encrypt =new JSEncrypt();
	encrypt.setPrivateKey(privateKey);
	return encrypt.decrypt(data);
}

/**
 * 生成aes密匙
 * 生成规则：md5(当前时间戳 + 随机字符串)
 */
export const initAesKey = (len) => {
	len = len || 32;
	var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678'; /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
	var maxPos = $chars.length;
	var pwd = '';
	for (let i = 0; i < len; i++) {
		pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
	}
	return pwd;
}
