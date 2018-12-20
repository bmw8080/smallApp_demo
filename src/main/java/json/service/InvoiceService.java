package json.service;


import json.model.Invoice;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface InvoiceService {
    List<Invoice> query();

    Invoice addData(Invoice temp);
    

    Object findAllInvoice(int pageNum, int pageSize);


    List<Invoice> checkRepeat(List<Invoice> receptInvoiceList);


    List<Invoice> queryDate(String beginDate, String endDate,int page,int limit);
}


