package json.service;


import json.model.Invoice;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface InvoiceService {
    List<Invoice> Query();

    Invoice addData(Invoice temp);
    

    Object findAllInvoice(int pageNum, int pageSize);


    List<Invoice> checkRepeat(List<Invoice> receptInvoiceList);


<<<<<<< HEAD
    List<Invoice> queryDate(String beginDate, String endDate);
=======
    List<Invoice> queryDate(String beginDate, String endDate,int page,int limit);
>>>>>>> b82eb2d4510b17a80f3512b8cb8f1816841371c0
}


