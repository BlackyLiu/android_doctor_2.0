var SERVER_URL = "http://localhost:8080/";
// var SERVER_URL = "http://118.178.120.133/";


function post(url,callback){
	post(url,{},callback,"");
}

/**
 * url:发送请求地址。
   param:待发送 Key/value 参数。
   callback:发送成功时回调函数。
   type:返回内容格式，xml, html, script, json, text, _default。
 * @param url
 * @param param
 * @param callback
 * @param type
 * @returns
 */
function post(url,param,callback,type){
	 if(!param){
		 param = {}
	 }
	 if(!type){
		 type = "json"
	 }
	 $.post(SERVER_URL+url,param,callback,type);
}

function get(url,param,callback,type){
	 if(!param){
		 param = {}
	 }
	 if(!type){
		 type = "json"
	 }
	 $.get(SERVER_URL+url,param,callback,type);
}