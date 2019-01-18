package json.Controller;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import json.body.BaseGlobalResponseBodyAdvice;
import json.model.Invoice;
import json.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * @author Z0522
 */
@RestController
@RequestMapping(value = "/xytax")

public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    Semaphore semaphore = new Semaphore(1);  //定义资源的总数量

    //查询总表
    @ResponseBody
    @RequestMapping(value = "/all", produces = {"application/json;charset=UTF-8"})
    public Object Query() {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String TimeString = time.format(new Date());
        System.out.println(TimeString);
        System.out.println("执行开始");
        System.out.println("------------------------------------------------------");
        System.out.println("调用接口：查找数据所有数据");
        System.out.println("------------------------------------------------------");
        int availablePermits = semaphore.availablePermits();//可用资源数
        if (availablePermits > 0) {
            System.out.println("抢到资源");
        } else {
            System.out.println("资源已被占用，稍后再试");
            Map<Object,String> map = new HashMap<Object, String>(16);
            map.put("message","资源已被占用，稍后再试");
            map.put("time",TimeString);
            return map;
        }
        try {
            semaphore.acquire(1);  //请求占用一个资源
            System.out.println("资源正在被使用");
            Thread.sleep(500);//放大资源占用时间，便于观察
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release(1);//释放一个资源
        }
        return invoiceService.query();
    }

    //物理分页
    @ResponseBody
    @RequestMapping(value = "/all/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"})
    public Map findAllInvoice(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        Page page = PageHelper.startPage(pageNum, pageSize);
        Object obj = invoiceService.findAllInvoice(pageNum, pageSize);
        PageInfo info = new PageInfo(page.getResult());
        long total = info.getTotal();
        System.out.println(info);
        Map map = new HashMap();
        map.put("data",obj);
        map.put("total",total);
        return map;
    }

    //查找日期范围
    @ResponseBody
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @RequestMapping(value = "/distinct", produces = {"application/json;charset=UTF-8"})
    public Object distinct(@RequestBody JSONArray params) {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String TimeString = time.format(new Date());
        System.out.println(TimeString);
        System.out.println("执行开始");
        System.out.println("------------------------------------------------------");
        System.out.println("调用接口：查找日期范围");
        System.out.println("------------------------------------------------------");
        String beginDate = new String();
        String endDate = new String();
        int page = 0;
        int limit = 0;
        int availablePermits = semaphore.availablePermits();//可用资源数
        if (availablePermits > 0) {
            System.out.println("抢到资源");
        } else {
            System.out.println("资源已被占用，稍后再试");
            Map<Object,String> map = new HashMap<Object, String>(16);
            map.put("message","资源已被占用，稍后再试");
            map.put("time",TimeString);
            return map;
        }
        try {
            semaphore.acquire(1);  //请求占用一个资源
            System.out.println("资源正在被使用");
            Thread.sleep(500);//放大资源占用时间，便于观察
            try {
                for (int i = 0; i < params.size(); i++) {
                    //用来决定Json对象要循环几次解析
                    JSONObject jsonObj = params.getJSONObject(i);
                    beginDate = jsonObj.getString("xyBeginDate");
                    endDate = jsonObj.getString("xyEndDate");
/*
                    try {
                        page = jsonObj.getInteger("page");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        limit = jsonObj.getInteger("limit");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release(1);//释放一个资源
        }
        return invoiceService.queryDate(beginDate, endDate, page, limit);
    }

    //查找日期范围
    @ResponseBody
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @RequestMapping(value = "/distinct/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"})
    public Map distinctCut(@RequestBody JSONArray params,@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String TimeString = time.format(new Date());
        System.out.println(TimeString);
        System.out.println("执行开始");
        System.out.println("------------------------------------------------------");
        System.out.println("调用接口：查找日期范围带分页");
        System.out.println("------------------------------------------------------");
        String beginDate = new String();
        String endDate = new String();
        int page = pageNum;
        int limit = pageSize;
        int availablePermits = semaphore.availablePermits();//可用资源数
        if (availablePermits > 0) {
            System.out.println("抢到资源");
        } else {
            System.out.println("资源已被占用，稍后再试");
            Map<Object,String> map = new HashMap<Object, String>(16);
            map.put("message","资源已被占用，稍后再试");
            map.put("time",TimeString);
            return map;
        }
        try {
            semaphore.acquire(1);  //请求占用一个资源
            System.out.println("资源正在被使用");
            Thread.sleep(500);//放大资源占用时间，便于观察
            try {
                for (int i = 0; i < params.size(); i++) {
                    //用来决定Json对象要循环几次解析
                    JSONObject jsonObj = params.getJSONObject(i);
                    beginDate = jsonObj.getString("xyBeginDate");
                    endDate = jsonObj.getString("xyEndDate");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release(1);//释放一个资源
        }
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        Page pageList = PageHelper.startPage(page, limit);
        Object obj = invoiceService.queryDate(beginDate, endDate, page, limit);
        PageInfo info = new PageInfo(pageList.getResult());
        long total = info.getTotal();
        System.out.println(info);
        Map map = new HashMap();
        map.put("data",obj);
        map.put("total",total);
        return map;
    }

    //成组解析
    @ResponseBody
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @RequestMapping(value = "/json", produces = "application/json;charset=UTF-8")
    public Object json(@RequestBody JSONArray params) {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String TimeString = time.format(new Date());
        System.out.println(TimeString);
        System.out.println("执行开始");
        System.out.println("------------------------------------------------------");
        System.out.println("调用接口：成组解析");
        System.out.println("------------------------------------------------------");
        //定义一个list对象来存放实体对象
        List<Invoice> receptInvoiceList = new ArrayList();
        int availablePermits = semaphore.availablePermits();//可用资源数
        if (availablePermits > 0) {
            System.out.println("抢到资源");
        } else {
            System.out.println("资源已被占用，稍后再试");
            Map<Object,String> map = new HashMap<Object, String>(16);
            map.put("message","资源已被占用，稍后再试");
            map.put("time",TimeString);
            return map;
        }
        try {
            semaphore.acquire(1);  //请求占用一个资源
            System.out.println("资源正在被使用");
            Thread.sleep(500);//放大资源占用时间，便于观察
            for (int i = 0; i < params.size(); i++) {
                try {
                    //定义一个实体对象来存入JsonArray，放这里主要很吃内存，但是可以实现数组存入
                    Invoice tempInvoice = new Invoice();
                    //用来决定Json对象要循环几次解析
                    JSONObject jsonObj = params.getJSONObject(i);
                    //把json的数据放进实体对象中
                    tempInvoice.setXyId(jsonObj.getInteger("xyId"));
                    tempInvoice.setXyInvoiceCode(jsonObj.getString("xyInvoiceCode"));
                    tempInvoice.setXyInvoiceNum(jsonObj.getString("xyInvoiceNum"));
                    tempInvoice.setXyInvoiceFlownum(jsonObj.getString("xyInvoiceFlownum"));
                    //判断浮点是否为空
                    try {
                        Float cash = jsonObj.getFloat("xyInvoiceCash");
                        if (cash != null) {
                            tempInvoice.setXyInvoiceCash(cash);
                        } else {
                            tempInvoice.setXyInvoiceCash((float) 0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Float tax = jsonObj.getFloat("xyInvoiceTax");
                        if (tax != null) {
                            tempInvoice.setXyInvoiceTax(tax);
                        } else {
                            tempInvoice.setXyInvoiceTax((float) 0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Float total = jsonObj.getFloat("xyInvoiceTotal");
                        if (total != null) {
                            tempInvoice.setXyInvoiceTotal(total);
                        } else {
                            tempInvoice.setXyInvoiceTotal((float) 0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    tempInvoice.setXyInvoiceType(jsonObj.getString("xyInvoiceType"));
                    tempInvoice.setXyNote(jsonObj.getString("xyNote"));
                    tempInvoice.setXyBuyername(jsonObj.getString("xyBuyername"));
                    tempInvoice.setXyBuyertaxcode(jsonObj.getString("xyBuyertaxcode"));
                    tempInvoice.setXyBuyerbankAccount(jsonObj.getString("xyBuyerbankAccount"));
                    tempInvoice.setXyBuyertel(jsonObj.getString("xyBuyertel"));
                    tempInvoice.setXySalertaxcode(jsonObj.getString("xySalertaxcode"));
                    tempInvoice.setXySalername(jsonObj.getString("xySalername"));
                    tempInvoice.setXySalertel(jsonObj.getString("xySalertel"));
                    tempInvoice.setXySalerbankaccount(jsonObj.getString("xySalerbankaccount"));
                    tempInvoice.setXyOdate(jsonObj.getString("xyOdate"));
                    tempInvoice.setXyV(jsonObj.getString("xyV"));
                    tempInvoice.setXyR(jsonObj.getString("xyR"));
                    tempInvoice.setXyPeople(jsonObj.getString("xyPeople"));
                    tempInvoice.setXyRdate(jsonObj.getString("xyRdate"));
                    tempInvoice.setXyInputDate(TimeString);
                    //依次把实体对象的数据，添加进list对象，形成表单
                    receptInvoiceList.add(tempInvoice);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release(1);//释放一个资源
        }
        return invoiceService.checkRepeat(receptInvoiceList);
    }

    //成组中文解析
    @ResponseBody
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @RequestMapping(value = "/cnjson", produces = "application/json;charset=UTF-8")
    public List<Invoice> cnjson(@RequestBody JSONArray params) {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String TimeString = time.format(new Date());
        System.out.println(TimeString);
        System.out.println("执行开始");
        System.out.println("------------------------------------------------------");
        System.out.println("调用接口：成组中文解析");
        System.out.println("------------------------------------------------------");
        //定义一个list对象来存放实体对象
        List<Invoice> receptInvoiceList = new ArrayList();

        for (int i = 0; i < params.size(); i++) {
            try {
                //定义一个实体对象来存入JsonArray，放这里主要很吃内存，但是可以实现数组存入
                Invoice tempInvoice = new Invoice();
                //用来决定Json对象要循环几次解析
                JSONObject jsonObj = params.getJSONObject(i);

                //把json的数据放进实体对象中
                tempInvoice.setXyId(jsonObj.getInteger("xyId"));
                tempInvoice.setXyInvoiceCode(jsonObj.getString("发票代码"));
                tempInvoice.setXyInvoiceNum(jsonObj.getString("发票号码"));
                tempInvoice.setXyInvoiceFlownum("");

                tempInvoice.setXyInvoiceCash(jsonObj.getFloat("金额"));
                tempInvoice.setXyInvoiceTax(jsonObj.getFloat("税额"));
                tempInvoice.setXyInvoiceTotal(jsonObj.getFloat("价税合计"));

                tempInvoice.setXyInvoiceType(jsonObj.getString("发票种类"));
                tempInvoice.setXyNote(jsonObj.getString("备注"));
                tempInvoice.setXyBuyername(jsonObj.getString("购方名称"));
                tempInvoice.setXyBuyertaxcode(jsonObj.getString("购方税号"));
                tempInvoice.setXyBuyerbankAccount(jsonObj.getString("购方银行账号"));
                tempInvoice.setXyBuyertel(jsonObj.getString("购方地址电话"));
                tempInvoice.setXySalertaxcode(jsonObj.getString("销方税号"));
                tempInvoice.setXySalername(jsonObj.getString("销方名称"));
                tempInvoice.setXySalertel(jsonObj.getString("销方地址电话"));
                tempInvoice.setXySalerbankaccount(jsonObj.getString("销方银行账号"));

                tempInvoice.setXyOdate(jsonObj.getString("开票日期"));

                tempInvoice.setXyV(jsonObj.getString("作废标志"));
                tempInvoice.setXyR(jsonObj.getString("是否报销"));
                tempInvoice.setXyPeople(jsonObj.getString("报销人"));

                tempInvoice.setXyRdate(jsonObj.getString("报销日期"));
                tempInvoice.setXyInputDate(TimeString);

                //依次把实体对象的数据，添加进list对象，形成表单
                receptInvoiceList.add(tempInvoice);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        //执行将参数表单通过接口方法传入service层，实现方法后，返回list对象出来
        return invoiceService.checkRepeat(receptInvoiceList);

    }

    //小程序解析
    @ResponseBody
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @RequestMapping(value = "/smalljson", produces = "application/json;charset=UTF-8")
    public List<Invoice> smalljson(@RequestBody JSONArray smallParams) {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String TimeString = time.format(new Date());
        System.out.println(TimeString);
        System.out.println("执行开始");
        System.out.println("------------------------------------------------------");
        System.out.println("调用接口：小程序");
        System.out.println("------------------------------------------------------");
        //定义一个list对象来存放实体对象
        List<Invoice> smallAppFirstResult = new ArrayList();
        ArrayList smallAppFinalResult = new ArrayList();

        for (int i = 0; i < smallParams.size(); i++) {
            try {
                //定义一个实体对象来存入JsonArray，放这里主要很吃内存，但是可以实现数组存入
                Invoice tempInvoice = new Invoice();
                //用来决定Json对象要循环几次解析
                JSONObject jsonObj = smallParams.getJSONObject(i);

                //把json的数据放进实体对象中
                tempInvoice.setXyId(jsonObj.getInteger("xyId"));
                tempInvoice.setXyInvoiceCode(jsonObj.getString("xyInvoiceCode"));
                tempInvoice.setXyInvoiceNum(jsonObj.getString("xyInvoiceNum"));
                tempInvoice.setXyInvoiceFlownum(jsonObj.getString("xyInvoiceFlownum"));

                tempInvoice.setXyInvoiceCash(jsonObj.getFloat("xyInvoiceCash"));
                tempInvoice.setXyInvoiceTax(jsonObj.getFloat("xyInvoiceTax"));
                tempInvoice.setXyInvoiceTotal(jsonObj.getFloat("xyInvoiceTotal"));

                tempInvoice.setXyInvoiceType(jsonObj.getString("xyInvoiceType"));
                tempInvoice.setXyNote(jsonObj.getString("xyNote"));
                tempInvoice.setXyBuyername(jsonObj.getString("xyBuyername"));
                tempInvoice.setXyBuyertaxcode(jsonObj.getString("xyBuyertaxcode"));
                tempInvoice.setXyBuyerbankAccount(jsonObj.getString("xyBuyerbankAccount"));
                tempInvoice.setXyBuyertel(jsonObj.getString("xyBuyertel"));
                tempInvoice.setXySalertaxcode(jsonObj.getString("xySalertaxcode"));
                tempInvoice.setXySalername(jsonObj.getString("xySalername"));
                tempInvoice.setXySalertel(jsonObj.getString("xySalertel"));
                tempInvoice.setXySalerbankaccount(jsonObj.getString("xySalerbankaccount"));

                tempInvoice.setXyOdate(jsonObj.getString("xyOdate"));

                tempInvoice.setXyV(jsonObj.getString("xyV"));
                tempInvoice.setXyR(jsonObj.getString("xyR"));
                tempInvoice.setXyPeople(jsonObj.getString("xyPeople"));

                tempInvoice.setXyRdate(jsonObj.getString("xyRdate"));
                tempInvoice.setXyInputDate(TimeString);


                //依次把实体对象的数据，添加进list对象，形成表单
                smallAppFirstResult.add(tempInvoice);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        //执行将参数表单通过接口方法传入service层，实现方法后，返回list对象出来

        List<Invoice> smallAppSecondResult = invoiceService.checkRepeat(smallAppFirstResult);

        List<Invoice> smallAppThirdResult = new ArrayList<Invoice>();


        smallAppThirdResult.addAll(smallAppSecondResult);

        smallAppThirdResult.remove(smallAppFirstResult);

        try

        {
            Invoice smallAppTempInvoice = new Invoice();
            ArrayList smallAppFourthResult = new ArrayList<Invoice>();
            if (smallAppThirdResult.size() > 0) {
                //定义一个动态数组
                ArrayList al = new ArrayList();
                //添加一个重复id；
                for (int i = 0; i < smallAppThirdResult.size(); i++) {
                    smallAppTempInvoice = smallAppThirdResult.get(i);
                    smallAppFourthResult.add(smallAppTempInvoice.getXyId());
                }
                smallAppFinalResult = smallAppFourthResult;
            } else {
                //定义一个动态数组
                ArrayList smallAppFifthResult = new ArrayList();
                //添加0进入数组来界定空插
                smallAppFifthResult.add(0);
                //返回这个数组
                smallAppFinalResult = smallAppFifthResult;
            }

        } catch (
                Exception e)

        {
            e.printStackTrace();
        }


        return smallAppFinalResult;
    }
}