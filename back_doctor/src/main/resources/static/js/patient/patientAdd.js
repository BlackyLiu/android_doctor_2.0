layui.use(['form','layer','laydate'],function(){
    var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
        		laydate = layui.laydate,
        $ = layui.jquery;

    form.on("submit(addPatient)",function(data){
        //弹出loading
        var index = top.layer.msg('Please wait while the data is submitted.',{icon: 16,time:false,shade:0.8});
        // 实际使用时的提交信息
           let medicalTreatmentNo  =  $("#medicalTreatmentNo").val()
           let email  =  $("#email").val()
           let password  =  $("#password").val()
           let realName  =  $("#realName").val()
           let price  =  $("#price").val()
           let contactInformation  =  $("#contactInformation").val()
               $.post("/patient/add",{
       medicalTreatmentNo:medicalTreatmentNo,
       email:email,
       password:password,
       realName:realName,
       price:price,
       contactInformation:contactInformation,
                       },function(res){
            if(res.code == 200){
            	 top.layer.msg("User added successfully.");
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

    form.on("submit(editPatient)",function(data){
        //弹出loading
        var index = top.layer.msg('Please wait while the data is submitted.',{icon: 16,time:false,shade:0.8});
    let id  =  $("#id").val()
    let medicalTreatmentNo  =  $("#medicalTreatmentNo").val()
    let email  =  $("#email").val()
    let password  =  $("#password").val()
    let realName  =  $("#realName").val()
    let price  =  $("#price").val()
    let contactInformation  =  $("#contactInformation").val()

         $.post("/patient/update",{
                 id:id,
                 medicalTreatmentNo:medicalTreatmentNo,
                 email:email,
                 password:password,
                 realName:realName,
                 price:price,
                 contactInformation:contactInformation,
         },function(res){
             if(res.code == 200){
            	 top.layer.msg("Edit user succeeded.");
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
