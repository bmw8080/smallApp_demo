package json.mapper;


import json.model.Invoice;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface InvoiceMapper {

    List<Invoice> Query();

    List<Invoice> selectAllInvoice();

    Invoice  Aldis(String code, String num);

    void insert(Invoice temp);

    List<Invoice> selectForCheckRepeat(@Param("inAreaOfCodeAndNum") String code);

    int saveArray(@Param("saveInvoiceList") List<Invoice> receptInvoiceList);

    List<Invoice> queryInvoiceDate(String beginDate, String endDate);
}