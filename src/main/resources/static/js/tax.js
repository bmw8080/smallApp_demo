/*作者：xxq*/
$(document).ready(function () {
    var sum_data = [];
    //获取当前时间，格式YYYY-MM-DD
    function GetDateStr(AddDayCount) {
        var dd = new Date();
        dd.setDate(dd.getDate() + AddDayCount); //获取AddDayCount天后的日期    
        var y = dd.getFullYear();
        //获取当前月份的日期,当月份<10时，在前面补0，确保日期保持如2018-09-11格式   
        var m = (dd.getMonth() + 1) < 10 ? "0" + (dd.getMonth() + 1) : dd.getMonth() + 1;
        //获取当前日期,当日期<10时，在前面补0，确保日期保持如2018-09-01格式
        var d =( dd.getDate())< 10 ? "0" + (dd.getDate()) :(dd.getDate());
        return y + "-" + m + "-" + d;
    }

    $(".xy_odate").val(GetDateStr(0)); //给开票日期赋值今天日期
    console.log(GetDateStr(0));


    /*将手动录入部分信息转为Jason格式 并上传*/
    $(".comment").click(function () {

        //将红色提示文字删除
        $("#mes").remove();
        var jason_data = JSON.stringify(sum_data);
        console.log(jason_data);
        //判断上传的数据是否为空
        if (jason_data != '[]') {
            var r=confirm("确定上传表格中发票信息？");
            if (r==true){
            //连接后端
            xmlHttp = null;
            if (window.XMLHttpRequest) { // code for IE7, Firefox, Opera, etc.
                xmlHttp = new XMLHttpRequest();
            } else if (window.ActiveXObject) { // code for IE6, IE5
                xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            if (xmlHttp != null) {
               // xmlHttp.open("POST", "http://172.25.1.218:8080/xytax/json", false);
                xmlHttp.open("POST", "http://172.16.16.200:6060/xytax/json", false);
                var t = setTimeout("alert('请检查网络连接状态，若网络正常，请联系管理员！')", 5000); //设定时器5s
                xmlHttp.send(jason_data);

                var back_mes = xmlHttp.responseText;
                console.log(back_mes);
            } else {
                alert("Your browser does not support XMLHTTP.");
            }
            var jsonObj = JSON.parse(back_mes); //将后端返回的json字符串转化成json对象
            var json_code = jsonObj.code; //抽取后端返回json的code字段
            var json_data = jsonObj.data; //抽取后端返回json的打他字段


            //判断通信是否成功
            if (json_code == 200) {
                clearTimeout(t); //连接成功解除定时
                //判断返回的是成功的条数还是重复的json
                if (typeof (json_data[0]) == 'number') {
                    //上传成功，清空表格中的数据
                    $("#add_tr").empty(); //清空表格中的所有数据
                    sum_data.splice(0, sum_data.length); //清空数组中的所有数据
                    var arrString = json_data.toString();
                    alert("已成功插入" + arrString + "条发票信息");
                } else if (typeof (json_data[0]) == 'object') {
                    var  flag=false;
                    for (var j = 0; j < json_data.length; j++) {

                        for (var k = 0; k < sum_data.length; k++) {
                            if ((sum_data[k].xyInvoiceFlownum == json_data[j].xyInvoiceFlownum) &&
                                (sum_data[k].xyInvoiceCode == json_data[j].xyInvoiceCode) &&
                                (sum_data[k].xyInvoiceNum == json_data[j].xyInvoiceNum) &&
                                (sum_data[k].xyInvoiceCash == json_data[j].xyInvoiceCash) &&
                                (sum_data[k].xyPeople == json_data[j].xyPeople) &&
                                (sum_data[k].xyInvoiceType == json_data[j].xyInvoiceType)
                            ) {

                                var repeat_index = k + 1;
                                show_tab.rows[repeat_index].style.backgroundColor = "#F78181"; //改变重复发票信息的颜色
                                flag=true;
                                break;
                            }
                        }
                    }
                    if(flag==true){
                        var mess = "<b> <p id='mes'>注意：表格中红色区域的发票已经报销过了，请将上方表格中对应数据删除，再重新上传！</p></b>  ";
                        $(mess).appendTo(".repeat_mes");
                    }

                }

            } else {
                alert("连接数据库失败， 请检查网络是否连接正常！");
            }}
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
        //采集输入框的内容
        var xy_invoice_flownum = $(".xy_invoice_flownum").val();
        var xy_invoice_code = $(".xy_invoice_code").val();
        var xy_invoice_num = $(".xy_invoice_num").val();
        var xy_invoice_cash = $(".xy_invoice_cash").val();
        var xy_invoice_type = $(".xy_invoice_type").find("option:selected").text(); //选中的文本
        var xy_people = $(".xy_people").val();
        var xy_odate = $(".xy_odate").val();

        //验证输入框发票代码和发票号码的内容不能为空
        if ((xy_invoice_code == "") && (xy_invoice_num == "")&&(xy_odate == "")) {
            alert('发票代码、发票号码和开票日期不能为空，请输入！');
        } else if ((xy_invoice_code == "")&&(xy_odate == "")) {
            alert('发票代码和开票日期不能为空，请输入！');
        } else if ((xy_invoice_num == "")&&(xy_odate == "")) {
            alert('发票号码和开票日期不能为空，请输入！');
        } else if ((xy_invoice_num == "")&&(xy_invoice_code == "")) {
            alert('发票号码和发票代码不能为空，请输入！');
        }else if (xy_invoice_num == "") {
            alert('发票号码不能为空，请输入！');
        }else if (xy_invoice_code == "") {
            alert('发票代码不能为空，请输入！');
        }else if (xy_odate == "") {
            alert('开票日期不能为空，请输入！');
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
                if ((data.xyInvoiceCode) == (sum_data[i].xyInvoiceCode) && (data.xyInvoiceNum) == (sum_data[i].xyInvoiceNum) ) {
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
                    //alert("第" + hang + "行");
                    sum_data.splice(hang - 1, 1); //将删除行中对应的数据从数组中删除
                    $(this).parent().parent().remove();
                });

            }

            //重新输入的按钮，清空输入框中的所有数据
            $('#res_btn').click(function () {
                $(".formtips").remove();
            });
        } else {
            alert("为避免数据太多，请先将现有的10条数据上传后再录入，谢谢合作！")
        }
    });





    //清空表格按钮将整张表删除
    $(".empty_btn").click(function () {
        console.log(sum_data.length);
        if(sum_data.length!='0'){
        var r=confirm("确定清空表格中所有的数据？");
        if (r==true)
        { $("#add_tr").empty(); //清空表格中的所有数据
            sum_data.splice(0, sum_data.length); //清空数组中的所有数据
            //将红色提示文字删除
            $("#mes").remove();
        }}
        else {alert("表格中无数据，无需清空！")}
    });

    //清空表格按钮将整张excel表删除
    $("#excel_emp").click(function () {

            var r=confirm("确定清空表格？");
            if (r==true)
            {
                $("#excel_table").empty(); //清空表格中的所有数据
                excel_json=null; //清空数组中的所有数据
                $("#mes1").remove();
            }

    });


//上传税务
    $("#excelbtn2").click(function () {
        $("#mes1").remove();
console.log(excel_json);
        //判断上传的数据是否为空
        if (excel_json!= null) {
            //连接后端
            var r=confirm("确定上传表格？");
            if (r==true){
            xmlHttp = null;
            if (window.XMLHttpRequest) { // code for IE7, Firefox, Opera, etc.
                xmlHttp = new XMLHttpRequest();
            } else if (window.ActiveXObject) { // code for IE6, IE5
                xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            if (xmlHttp != null) {
               // xmlHttp.open("POST", "http://172.25.1.218:8080/xytax/cnjson", false);
                xmlHttp.open("POST", "http://172.16.16.200:6060/xytax/cnjson", false);
                var t = setTimeout("alert('请检查网络连接状态，若网络正常，请联系管理员！')", 5000); //设定时器5s
                xmlHttp.send(excel_json);

                var back_mes = xmlHttp.responseText;
                console.log(back_mes);
            } else {
                alert("Your browser does not support XMLHTTP.");
            }
            var jsonObj = JSON.parse(back_mes); //将后端返回的json字符串转化成json对象
            var json_code = jsonObj.code; //抽取后端返回json的code字段
            var json_data = jsonObj.data; //抽取后端返回json的data字段


            //判断通信是否成功
            if (json_code == 200) {
                clearTimeout(t); //连接成功解除定时
                //判断返回的是成功的条数还是重复的json
                if (typeof (json_data[0]) == 'number') {
                    //上传成功，清空表格中的数据
                   // $("table.table.table-bordered").empty(); //清空表格中的所有数据
                    excel_json=null; //清空数组中的所有数据
                    var arrString = json_data.toString();
                    alert("已成功插入" + arrString + "条发票信息");
                } else if (typeof (json_data[0]) == 'object') {
                    var excel_Obj = JSON.parse(excel_json); //将excel_json字符串转化成json对象
                    console.log(excel_Obj .length);
                    console.log(json_data.length);
                 var  flag=false;
                    for (var j = 0; j < json_data.length; j++) {

                        for (var k = 0; k < excel_Obj .length; k++) {
                            if ((excel_Obj[k].发票代码 == json_data[j].xyInvoiceCode )&&
                                (excel_Obj[k].发票号码 == json_data[j].xyInvoiceNum )
                            ) {

                                var repeat_index = k + 1;
                                excel_table.rows[repeat_index].style.backgroundColor = "#F78181"; //改变重复发票信息的颜色
                                flag=true;

                                break;
                            }
                        }
                    }
            if(flag==true){
                var mes = "<b> <p id='mes1'>注意：表格中红色区域的发票已经报销过了！</p></b>  ";
                $(mes).appendTo(".excel_mes");
            }
                }

            } else {
                alert("连接数据库失败， 请检查网络是否连接正常！");
            }
        } }else {
            alert("请先选择要上传的税务excel表");
        }
    });

});







