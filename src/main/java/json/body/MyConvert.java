package json.body;

import json.model.Invoice;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public class MyConvert extends AbstractHttpMessageConverter<Invoice> {

    public MyConvert() {
        super(new MediaType("application","x-tony"));
    }

    //检查是否支持目标转换，这里只支持Invoice类型
    @Override
    protected boolean supports(Class<?> clazz) {
        return Invoice.class.isAssignableFrom(clazz);
    }

    //转换成对象
    @Override
    protected Invoice readInternal(Class<? extends Invoice> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        String inputString = StreamUtils.copyToString(inputMessage.getBody(), Charset.forName("utf-8"));
        String[] targetArray = inputString.split("#");
        Invoice invoice = new Invoice();
        return invoice;
    }

    //对象转成字符串
    @Override
    protected void writeInternal (Invoice invoice, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        /*String outputString = invoice.getXyInvoiceCode()+invoice.getXyInvoiceNum()+invoice.getXyInvoiceCash()+invoice.getXyInvoiceTax()+invoice.getXyInvoiceTotal()+invoice.getXyInvoiceType()+invoice.getXyNote()+invoice.getXyBuyername()+invoice.getXyBuyertaxcode()+invoice.getXyBuyerbankAccount()+invoice.getXyBuyertel()+invoice.getXySalertaxcode()+invoice.getXySalername()+invoice.getXySalertel()+invoice.getXySalerbankaccount()+invoice.getXyOdate()+invoice.getXyV()+invoice.getXyR()+invoice.getXyRdate();
        outputMessage.getBody().write(outputString.getBytes());*/
    }

}
