package json.Controller;

import json.model.Invoice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @author Z0522
 */
@Controller
@RequestMapping("th")
public class ThymeleafController{

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(ModelMap map){
        Invoice testInvoice = new Invoice();
        testInvoice.setXyInvoiceCode("20180928");
        testInvoice.setXyInvoiceNum("20180101");
        map.addAttribute("testInvoice",testInvoice);
        return "thymeleaf/index";
    }
    @RequestMapping(value = "/invoice",method = RequestMethod.GET)
    public String center(){
        return "thymeleaf/invoice";
    }
}