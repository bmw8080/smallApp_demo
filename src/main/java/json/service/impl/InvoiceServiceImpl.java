package json.service.impl;
import com.github.pagehelper.PageHelper;
import json.mapper.InvoiceMapper;
import json.model.Invoice;
import json.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "InvoiceService")
=======
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Z0522
 */
@Service(value = "InvoiceService")
@Component
>>>>>>> b82eb2d4510b17a80f3512b8cb8f1816841371c0
public class InvoiceServiceImpl implements InvoiceService {
    //这个注解是用来自动帮我省略set/get
    @Autowired
    private InvoiceMapper invoiceMapper;
    /*
     * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
     * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
     * pageNum 开始页数
     * pageSize 每页显示的数据条数
     * */
    //物理翻页
    @Override
    public List<Invoice> findAllInvoice(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        return invoiceMapper.selectAllInvoice();
    }

    //成组检查重复并插入
    @Override//继承父类的方法，对其覆盖
    public List<Invoice> checkRepeat(List<Invoice> receptInvoiceList) {
        //定义一个新的实体对象
        Invoice tempInvoice = new Invoice();
        //定义一个新的表单对象
        List<Invoice> res = new ArrayList();
        //定义一个可变长的字符串，里面带个符号
        StringBuffer code = new StringBuffer("(");
        //定义一个数组存放重复数据表单对象
        ArrayList distinctRecptInvoiceList = new ArrayList();

        //整个循环过程是为了形成（xx,xxx,xxxx）,(xx,xxx,xxxx)...
<<<<<<< HEAD
        if (receptInvoiceList != null) {
            for (int i = 0; i < receptInvoiceList.size(); i++) {
                //根据表单的长度决定循环次数
                tempInvoice = receptInvoiceList.get(i);
                String distinctCode = tempInvoice.getXyInvoiceCode();
                String distinctNum = tempInvoice.getXyInvoiceNum();
                //判断发票代码和发票号是否相同
                if (distinctCode.equals(distinctNum)) {
                    distinctRecptInvoiceList.add(tempInvoice.getXyInvoiceCode());
                    System.out.println("存在为：" + tempInvoice.getXyInvoiceCode() + "=" + tempInvoice.getXyInvoiceNum());
                } else {
                    code.append("'");
                    code.append(tempInvoice.getXyInvoiceCode());
                    code.append("'");
                    code.append(",");
                    code.append("'");
                    code.append(tempInvoice.getXyInvoiceNum());
                    code.append("'");
                    //执行到这里添加需要保证下次循环开头也有符号
                    code.append("),(");
                }
            }
            if (distinctRecptInvoiceList != null) {
                //出来的结果需要减去后面2位，原因在上面
                code.delete(code.length() - 2, code.length());
                //打印看一下，结果格式对不对，数据有没正确传进来
                System.out.print("当前送入查重的数组：" + code.toString());
                if (res != receptInvoiceList) {
                    //执行mapper接口方法将参数送入mapping层，执行SQL查询重复，返回形成一个list对象
                    List<Invoice> result = invoiceMapper.selectForCheckRepeat(code.toString());
                    try {
                        //如果返回的list对象数目大于0
                        if (result.size() > 0) {
                            //返回这个list对象
                            System.out.println("执行结果：有重复退回");
                            res = result;
                        } else {
                            //否则执行mapper接口方法将表单参数传入mappeing层，执行SQL插入list对象，返回插入个数
                            int res1 = invoiceMapper.saveArray(receptInvoiceList);
                            //如果插入个数大于0
                            if (res1 > 0) {
                                System.out.println("执行结果：插入成功");
                                //定义一个动态数组
                                ArrayList al = new ArrayList();
                                //添加一个当前插入返回的行数；
                                al.add(res1);
                                //返回这个数组
                                res = al;
                            } else {
                                System.out.println("插入失败");
                                //定义一个动态数组
                                ArrayList al = new ArrayList();
                                //添加0进入数组来界定空插
                                al.add(0);
                                //返回这个数组
                                res = al;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("错误原因：存在发票号和发票代码相同的情况退回单据");
                res = distinctRecptInvoiceList;
            }
        }
=======
        try {
            if (receptInvoiceList != null) {
                for (int i = 0; i < receptInvoiceList.size(); i++) {
                    //根据表单的长度决定循环次数
                    tempInvoice = receptInvoiceList.get(i);
                    String distinctCode = tempInvoice.getXyInvoiceCode();
                    String distinctNum = tempInvoice.getXyInvoiceNum();
                    //判断发票代码和发票号是否相同
                    if (distinctCode.equals(distinctNum)) {
                        distinctRecptInvoiceList.add(tempInvoice.getXyInvoiceCode());
                        System.out.println("存在为：" + tempInvoice.getXyInvoiceCode() + "=" + tempInvoice.getXyInvoiceNum());
                    } else {
                        code.append("'");
                        code.append(tempInvoice.getXyInvoiceCode());
                        code.append("'");
                        code.append(",");
                        code.append("'");
                        code.append(tempInvoice.getXyInvoiceNum());
                        code.append("'");
                        //执行到这里添加需要保证下次循环开头也有符号
                        code.append("),(");
                    }
                }
                if (distinctRecptInvoiceList != null) {
                    //出来的结果需要减去后面2位，原因在上面
                    code.delete(code.length() - 2, code.length());
                    //打印看一下，结果格式对不对，数据有没正确传进来
                    System.out.print("当前送入查重的数组：" + code.toString());
                    if (res != receptInvoiceList) {
                        //执行mapper接口方法将参数送入mapping层，执行SQL查询重复，返回形成一个list对象
                        List<Invoice> result = invoiceMapper.selectForCheckRepeat(code.toString());
                        try {
                            //如果返回的list对象数目大于0
                            if (result.size() > 0) {
                                //返回这个list对象
                                System.out.println("执行结果：有重复退回");
                                res = result;
                            } else {
                                //否则执行mapper接口方法将表单参数传入mappeing层，执行SQL插入list对象，返回插入个数
                                int res1 = invoiceMapper.saveArray(receptInvoiceList);
                                //如果插入个数大于0
                                if (res1 > 0) {
                                    System.out.println("执行结果：插入成功");
                                    //定义一个动态数组
                                    ArrayList al = new ArrayList();
                                    //添加一个当前插入返回的行数；
                                    al.add(res1);
                                    //返回这个数组
                                    res = al;
                                } else {
                                    System.out.println("插入失败");
                                    //定义一个动态数组
                                    ArrayList al = new ArrayList();
                                    //添加0进入数组来界定空插
                                    al.add(0);
                                    //返回这个数组
                                    res = al;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    System.out.println("错误原因：存在发票号和发票代码相同的情况退回单据");
                    res = distinctRecptInvoiceList;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

>>>>>>> b82eb2d4510b17a80f3512b8cb8f1816841371c0
        System.out.println("------------------------------------------------------");
        System.out.println("------------------------------------------------------");
        System.out.println("执行结束");

        return res;
    }

<<<<<<< HEAD
    @Override
    public List<Invoice> queryDate(String beginDate, String endDate) {
        System.out.println("("+"开始时间："+ beginDate + ")"+";"+"("+"结束时间："+ endDate + ")");
=======
    //查询时间范围
    @Override
    public List<Invoice> queryDate(String beginDate, String endDate,int page,int limit) {
        System.out.println("("+"开始时间："+ beginDate + ")"+";"+"("+"结束时间："+ endDate + ")");
        System.out.println("当前页码："+ page);
        System.out.println("限制页数："+ limit);
        PageHelper.startPage(page,limit);
>>>>>>> b82eb2d4510b17a80f3512b8cb8f1816841371c0
        List<Invoice> result = invoiceMapper.queryInvoiceDate(beginDate,endDate);
        System.out.println("------------------------------------------------------");
        System.out.println("------------------------------------------------------");
        System.out.println("执行结束");
        return result;
    }

    //查找总表
    @Override
    public List<Invoice> Query() {
<<<<<<< HEAD
        List<Invoice> list = invoiceMapper.Query();
        System.out.println("------------------------------------------------------");
        System.out.println("------------------------------------------------------");
        System.out.println("执行完毕");
        return list;
=======
        return invoiceMapper.Query();
>>>>>>> b82eb2d4510b17a80f3512b8cb8f1816841371c0
    }

    //单条检查重复并插入
    @Override
    public Invoice addData(Invoice temp) {
        String code = temp.getXyInvoiceCode();
        String num = temp.getXyInvoiceNum();
        Invoice res = new Invoice();
        try {
<<<<<<< HEAD

=======
>>>>>>> b82eb2d4510b17a80f3512b8cb8f1816841371c0
            Invoice tempInvoice = invoiceMapper.Aldis(code, num);
            if (tempInvoice != null) {
                res = tempInvoice;
            } else {
                invoiceMapper.insert(temp);
<<<<<<< HEAD

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

=======
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
>>>>>>> b82eb2d4510b17a80f3512b8cb8f1816841371c0
        return res;
    }

}

