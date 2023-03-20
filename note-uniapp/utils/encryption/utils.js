import JSEncrypt from '@/utils/encryption/jsencrypt/jsencrypt';
import CryptoJS from '@/utils/encryption/crypto-js/crypto-js';
//前端私钥
const PRIVATE_KEY="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAI6T+J2g6gKmkRmlUccqJQiEo98xHdOeDjEbMY8DaZhZh7mekrd2S7N2EjTJaaBRRiqxxxW9umWgC51GlM9bSdzGBf8oG0xPswjHhKkt0d5jpj562sAfcGdXuzWOOjqAUtKLMZPwcIuylF/9eFPXhaKQx5aQm4F4yCtkjaeKrgYVAgMBAAECgYBOifpIsmxVm3WQf/3FTVTrFU3D06WPfaBpsoYhNosDbkYSnORLBXH74I104II72qlhOV7mfgXgl0HumAO/GZVcm427Z7tf0D8E8XKKuGFBrnjGWbix9gSSQpXSaDeaDw3w+0EnxqwBUbRjK+KO5iwEX4+6KKzXlzUxKDLvTgxsXQJBAPcAeOLYorVI+GyIJglJ9TSPisOG0HUCVyGc06PhF4fabugag2LGy/jKggd7XhBoMlqNZhIAnZmeyeLiCsBxLYsCQQCTxaWo+oisnI8g/bAvyOpmAcUfHVYJXQVq3qHro4Owq/TOm6CtLZFPBngtwV/FqXGFBGBM+T50XpVs9p6phU7fAkAKLgXNa5pBGHHMn0vaBCdL9EKGzm6W8/K9WuZKEcPTgIgjnrwdfQ35Afeb8VSuHm082CkFwuq74xR0+lXxGnkDAkBz5o96K5ZLHDg+fftUX1dJTpYXQ2EZ0vXOznNdaFB/nz9RVUr+yUCxJLfZ21OMqsu4sqf9d3f+ubilUAE9oKstAkAJchJmMdD8Mbcp872nv22iIaCEp65j9sGG6TlqvdPUHdMG6YxzHgBKADwVdhp3J/S3soTOqYB7GqNRZFaFXwFw";

// 后台公钥
const PUBLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCOk/idoOoCppEZpVHHKiUIhKPfMR3Tng4xGzGPA2mYWYe5npK3dkuzdhI0yWmgUUYqsccVvbploAudRpTPW0ncxgX/KBtMT7MIx4SpLdHeY6Y+etrAH3BnV7s1jjo6gFLSizGT8HCLspRf/XhT14WikMeWkJuBeMgrZI2niq4GFQIDAQAB";
 
 
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
