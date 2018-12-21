package json.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface DemoService {
    @WebMethod
    public String test(@WebParam(name="param") String param);
}