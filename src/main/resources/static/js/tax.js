 /*--------------------------------------------------------------------------



                                                                                                                                 * Author: xxq
                                                                                                                               --------------------------------------------------------------------------*/
 $(document).ready(function () {
     var sum_data = [];
     //获取当前时间，格式YYYY-MM-DD
     function GetDateStr(AddDayCount) {
         var dd = new Date();
         dd.setDate(dd.getDate() + AddDayCount); //获取AddDayCount天后的日期    
         var y = dd.getFullYear();
         //获取当前月份的日期,当月份<10时，在前面补0，确保日期保持如2018-09-11格式   
         var m = (dd.getMonth() + 1) < 10 ? "0" + (dd.getMonth() + 1) : dd.getMonth() + 1;
         var d = dd.getDate();
         return y + "-" + m + "-" + d;
     }

     $(".xy_odate").val(GetDateStr(0)); //给开票日期赋值今天日期


     /*将手动录入部分信息转为Jason格式 */
     $(".comment").click(function () {

         //将后端返回重复的数据显示在前端红色区域的表格删除
         $(".repeat").remove();
         var jason_data = JSON.stringify(sum_data);
         alert(jason_data);
         //判断上传的数据是否为空
         if (jason_data != '[]') {
             //连接后端
             xmlHttp = null;
             if (window.XMLHttpRequest) { // code for IE7, Firefox, Opera, etc.
                 xmlHttp = new XMLHttpRequest();
             } else if (window.ActiveXObject) { // code for IE6, IE5
                 xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
             }
             if (xmlHttp != null) {
                 //xmlHttp.open("POST", "http://172.25.1.218:8080/xytax/json", false);
                 xmlHttp.open("POST", "http://172.16.16.200:6060/xytax/json", false);
                 xmlHttp.send(jason_data);
                 var back_mes = xmlHttp.responseText;
                 alert(back_mes);
             } else {
                 alert("Your browser does not support XMLHTTP.");
             }
             var jsonObj = JSON.parse(back_mes); //将后端返回的json字符串转化成json对象
             var json_code = jsonObj.code; //抽取后端返回json的code字段
             var json_data = jsonObj.data; //抽取后端返回json的打他字段

             //判断通信是否成功
             if (json_code == 200) {
                 //判断返回的是成功的条数还是重复的json
                 if (typeof (json_data[0]) == 'number') {
                     //上传成功，清空表格中的数据
                     $("#add_tr").empty(); //清空表格中的所有数据
                     sum_data.splice(0, sum_data.length); //清空数组中的所有数据
                     var arrString = json_data.toString();
                     alert("已成功插入" + arrString + "条发票信息");
                 } else if (typeof (json_data[0]) == 'object') {
                     //将后端接收到的重复的发票信息显示在表格下方红色区域
                     var repeat_data = " <tr bgcolor='LightCoral' class='repeat'> <td colspan='8'>以下发票已经报销过了，请将上方表格中对应数据删除，再重新上传！</td> </tr>";
                     for (var j = 0; j < json_data.length; j++) {
                         repeat_data += "<tr bgcolor='LightCoral' class='repeat'>";
                         repeat_data += "<td>" + (json_data[j].xyInvoiceFlownum) + "</td>";
                         repeat_data += "<td>" + (json_data[j].xyInvoiceCode) + "</td>";
                         repeat_data += "<td>" + (json_data[j].xyInvoiceNum) + "</td>";
                         repeat_data += "<td>" + (json_data[j].xyInvoiceCash) + "</td>";
                         repeat_data += "<td>" + (json_data[j].xyPeople) + "</td>";
                         repeat_data += "<td>" + (json_data[j].xyOdate) + "</td>";
                         repeat_data += "<td>" + (json_data[j].xyInvoiceType) + "</td>";
                         repeat_data += "<td><a href='#' class='del' >删除 </a></td > "; //加入删除列
                         repeat_data += "</tr>";
                     }
                     $(repeat_data).appendTo("#add_tr");
                     //执行删除操作
                     $(".del").click(function () {
                         $(this).parent().parent().remove();
                     });
                 }

             } else {
                 alert("连接数据库失败， 请检查网络是否连接正常！");
             }
         } else {
             alert("上传的发票信息为空，请输入发票信息");
         }
     });

     /*将文本框输入的数据读取到表格中显示，并进行数据的增删*/
     var clickCount = 0;
     $("#add_btn").click(function () {
         // 获得总表格的行数
         var table = document.getElementById("show_tab");
         var rows = table.rows.length;
         //将后端返回重复的数据删除
         $(".repeat").remove();
         //采集输入框的内容
         var xy_invoice_flownum = $(".xy_invoice_flownum").val();
         var xy_invoice_code = $(".xy_invoice_code").val();
         var xy_invoice_num = $(".xy_invoice_num").val();
         var xy_invoice_cash = $(".xy_invoice_cash").val();
         var xy_invoice_type = $(".xy_invoice_type").find("option:selected").text(); //选中的文本
         var xy_people = $(".xy_people").val();
         var xy_odate = $(".xy_odate").val();

         //验证输入框发票代码和发票号码的内容不能为空
         if ((xy_invoice_code == "") && (xy_invoice_num == "")) {
             alert('发票代码和发票号码不能为空，请输入！');
         } else if (xy_invoice_code == "") {
             alert('发票代码不能为空，请输入！');
         } else if (xy_invoice_num == "") {
             alert('发票号码不能为空，请输入！');
         }


         //当发票代码和发票号码不为空且插入数据总数<=10时，才允许插入
         else if (rows <= 10) {


             //将采集到的数值填入到Jason的数值
             var data = {
                 //                 xy_id: '0',
                 xyInvoiceFlownum: xy_invoice_flownum,
                 xyInvoiceCode: xy_invoice_code,
                 xyInvoiceNum: xy_invoice_num,
                 xyInvoiceCash: xy_invoice_cash,
                 xyInvoiceTax: '0.0',
                 xyInvoiceTotal: '0.00',
                 xyInvoiceType: xy_invoice_type,
                 xyNote: '0',
                 xyBuyername: '0',
                 xyBuyertaxcode: '0',
                 xyBuyerbankAccount: '0',
                 xyBuyertel: '0',
                 xySalertaxcode: '0',
                 xySalername: '0',
                 xySalertel: '0',
                 xySalerbankaccount: '0',
                 xyOdate: xy_odate,
                 xyV: '0',
                 xyR: '0',
                 xyPeople: xy_people,
                 xyRdate: GetDateStr(0)
             }
             var flag = false;
             for (var i = 0; i < sum_data.length; i++) {
                 if ((data.xyInvoiceFlownum) == (sum_data[i].xyInvoiceFlownum) && (data.xyInvoiceCode) == (sum_data[i].xyInvoiceCode) && (data.xyInvoiceNum) == (sum_data[i].xyInvoiceNum) && (data.xyInvoiceCash) == (sum_data[i].xyInvoiceCash) &&
                     (data.xyInvoiceType) == (sum_data[i].xyInvoiceType) && (data.xyOdate) == (sum_data[i].xyOdate) && (data.xyPeople) == (sum_data[i].xyPeople)) {
                     alert("输入的发票信息与右边表格中发票重复，请重新输入！");
                     flag = true;
                     break;
                 }
             }
             if (flag == false) {
                 sum_data.push(data);
                 var a = new Array();
                 a = [xy_invoice_flownum, xy_invoice_code, xy_invoice_num, xy_invoice_cash, xy_people, xy_odate, xy_invoice_type];
                 //将文本款采集的数据和列标签td组合在一起，变成一行
                 var trString = "<tr class='show_tab_tr'>";
                 for (var j = 0; j < a.length; j++) {
                     trString += "<td>" + (a[j]) + "</td>";
                 }
                 //加入删除列
                 trString += "<td><a href='#' class='del' >删除 </a></td > ";
                 trString += "</tr>";
                 //以下两种方法都可以
                 //$("#show_tab").append(trString);
                 $(trString).appendTo("#add_tr");
                 //执行删除操作
                 $(".del").click(function () {
                     var hang = $(this.parentNode).parent().prevAll().length + 1; //获得即将要删除的行号
                     alert("第" + hang + "行");
                     sum_data.splice(hang - 1, 1); //将删除行中对应的数据从数组中删除
                     $(this).parent().parent().remove();
                 });

             }



             //清空表格按钮将整张表删除
             $(".empty_btn").click(function () {
                 $("#add_tr").empty(); //清空表格中的所有数据
                 sum_data.splice(0, sum_data.length); //清空数组中的所有数据
             });


             //重新输入的按钮，清空输入框中的所有数据
             $('#res_btn').click(function () {
                 $(".formtips").remove();
             });
         } else {
             alert("为避免数据太多，请先将现有的10条数据上传后再录入，谢谢合作！")
         }

     });

 });





 /*将批量导入的excel表转为Jason格式 */
 /*
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             FileReader共有4种读取方法：
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             1.readAsArrayBuffer(file)：将文件读取为ArrayBuffer。
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             2.readAsBinaryString(file)：将文件读取为二进制字符串
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             3.readAsDataURL(file)：将文件读取为Data URL
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             4.readAsText(file, [encoding])：将文件读取为文本，encoding缺省值为'UTF-8'
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          */
 var wb; //读取完成的数据
 var rABS = false; //是否将文件读取为二进制字符串

 function importf(obj) { //导入
     if (!obj.files) {
         return;
     }
     const IMPORTFILE_MAXSIZE = 1 * 2048; //这里可以自定义控制导入文件大小
     var suffix = obj.files[0].name.split(".")[1]
     if (suffix != 'xls' && suffix != 'xlsx') {
         alert('导入的文件格式不正确，请导入excel文件！');
         return;
     }
     if (obj.files[0].size / 1024 > IMPORTFILE_MAXSIZE) {
         alert('导入的表格文件不能大于2M');
         return;
     }
     var f = obj.files[0];
     var reader = new FileReader();
     reader.onload = function (e) {
         var data = e.target.result;
         if (rABS) {
             wb = XLSX.read(btoa(fixdata(data)), { //手动转化
                 type: 'base64'
             });
         } else {
             wb = XLSX.read(data, {
                 type: 'binary'
             });
         }
         //wb.SheetNames[0]是获取Sheets中第一个Sheet的名字
         //wb.Sheets[Sheet名]获取第一个Sheet的数据
         document.getElementById("demo").innerHTML = JSON.stringify(XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]]));
     };
     if (rABS) {
         reader.readAsArrayBuffer(f);
     } else {
         reader.readAsBinaryString(f);
     }
 }

 function fixdata(data) { //文件流转BinaryString
     var o = "",
         l = 0,
         w = 10240;
     for (; l < data.byteLength / w; ++l) o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)));
     o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)));
     return o;
 }
