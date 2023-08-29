     function getImgArr(str){
    	    imgRep = /.*?<img src=\"(.*?)\".*?>/ig;
    	    result = imgRep.exec(str);
    	    imgArr = [];
    	    while(result!=null){
    	        imgArr.push(result[1])
    	        result = imgRep.exec(str);
    	    }
    	    return imgArr;
    	}

    	function getVideoArr(str) {
    	    videoRep = /.*?<video src=\"(.*?)\".*?>/ig;
    	    result = videoRep.exec(str);
    	    videoArr = [];
    	    while(result!=null){
    	        videoArr.push(result[1])
    	        result = videoRep.exec(str);
    	    }
    	    return videoArr;
    	}
     
    	
    	function dateFormat(fmt, date) {
    	    let ret;
    	    const opt = {
    	        "y+": date.getFullYear().toString(),        // 年
    	        "M+": (date.getMonth() + 1).toString(),     // 月
    	        "d+": date.getDate().toString(),            // 日
    	        "H+": date.getHours().toString(),           // 时
    	        "m+": date.getMinutes().toString(),         // 分
    	        "s+": date.getSeconds().toString()          // 秒
    	        // 有其他格式化字符需求可以继续添加，必须转化成字符串
    	    };
    	    for (let k in opt) {
    	        ret = new RegExp("(" + k + ")").exec(fmt);
    	        if (ret) {
    	            fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
    	        };
    	    };
    	    return fmt;
    	}
    	
    	
    	var reg = /^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\d{8}$/;
        function verifyTel (tel){
              if(reg.test(tel)){
                return true;
              }
              return false;
        }
        
        
        //这个传的默认是秒
        function getDateBiff(dateTimeStamp){
        	  var minute = 1000 * 60;
        	  var hour = minute * 60;
        	  var day = hour * 24;
        	  var halfamonth = day * 15;
        	  var month = day * 30;
        	  var now = new Date().getTime();
        	  var diffValue = now - dateTimeStamp;
        	  if(diffValue < 0){return;}
        	  var monthC =diffValue/month;
        	  var weekC =diffValue/(7*day);
        	  var dayC =diffValue/day;
        	  var hourC =diffValue/hour;
        	  var minC = diffValue / minute;
        	  var result = '';
        	  if(monthC>=1){
        	    result="" + parseInt(monthC) + "月前";
        	  }
        	  else if(weekC>=1){
        	    result="" + parseInt(weekC) + "周前";
        	  }
        	  else if(dayC>=1){
        	    result=""+ parseInt(dayC) +"天前";
        	  }
        	  else if(hourC>=1){
        	    result=""+ parseInt(hourC) +"小时前";
        	  }
        	  else if(minC>=1){
        	    result=""+ parseInt(minC) +"分钟前";
        	  }else
        	    result="刚刚";
        	  return result;
        	} 