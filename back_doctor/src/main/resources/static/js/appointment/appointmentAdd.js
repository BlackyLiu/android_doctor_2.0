layui.use(['form','layer','laydate'],function(){
    var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
        		laydate = layui.laydate,		
        $ = layui.jquery;
                                   laydate.render({
          elem: '#appointment_time', //指定元素
          type: 'datetime'
      });
      
    form.on("submit(addAppointment)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        // 实际使用时的提交信息
               let appointmentId  =  $("#appointmentId").val()
               let beAppointmentId  =  $("#beAppointmentId").val()
           let appointmentName  =  $("#appointmentName").val()
           let beAppointmentName  =  $("#beAppointmentName").val()
           let medicalRecord  =  $("#medicalRecord").val()
           let prescription  =  $("#prescription").val()
               $.post("/appointment/add",{
       appointmentId:appointmentId,
       beAppointmentId:beAppointmentId,
       appointmentName:appointmentName,
       beAppointmentName:beAppointmentName,
       medicalRecord:medicalRecord,
       prescription:prescription,
                       },function(res){
            if(res.code == 200){
            	 top.layer.msg("添加预约成功");
             }else{
                   top.layer.msg(res.msg);
                   return;
             }   
              top.layer.close(index);
              layer.closeAll("iframe");
            	 //刷新父页面
              parent.location.reload();
         })
        
        return false;
    })

    form.on("submit(editAppointment)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
    let id  =  $("#id").val()
        let appointmentId  =  $("#appointmentId").val()
        let beAppointmentId  =  $("#beAppointmentId").val()
    let appointmentName  =  $("#appointmentName").val()
    let beAppointmentName  =  $("#beAppointmentName").val()
    let medicalRecord  =  $("#medicalRecord").val()
    let prescription  =  $("#prescription").val()
               
         $.post("/appointment/update",{
                 id:id,
                 appointmentId:appointmentId,
                 beAppointmentId:beAppointmentId,
                 appointmentName:appointmentName,
                 beAppointmentName:beAppointmentName,
                 medicalRecord:medicalRecord,
                 prescription:prescription,
         },function(res){
             if(res.code == 200){
            	 top.layer.msg("编辑预约成功");
             }else{
                   top.layer.msg(res.msg);
                   return;
             }   
              top.layer.close(index);
              layer.closeAll("iframe");
            	 //刷新父页面
              parent.location.reload();
         })
        return false;
    })
    
    //格式化时间
    function filterTime(val){
        if(val < 10){
            return "0" + val;
        }else{
            return val;
        }
    }
    //定时发布
    var time = new Date();
    var submitTime = time.getFullYear()+'-'+filterTime(time.getMonth()+1)+'-'+filterTime(time.getDate())+' '+filterTime(time.getHours())+':'+filterTime(time.getMinutes())+':'+filterTime(time.getSeconds());



})