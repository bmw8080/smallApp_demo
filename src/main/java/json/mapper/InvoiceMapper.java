package json.mapper;


import json.model.Invoice;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface InvoiceMapper {

    List<Invoice> queryAllDate();

    List<Invoice> selectAllInvoice();

    Invoice  Aldis(String code, String num);

    void insert(Invoice temp);

    List<Invoice> selectForCheckRepeat(@Param("inAreaOfCodeAndNum") String code);

    int saveArray(@Param("saveInvoiceList") List<Invoice> receptInvoiceList);

    List<Invoice> queryInvoiceDate(String beginDate, String endDate);
}