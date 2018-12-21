package json.service.impl;

import json.service.DemoService;

public class DemoServiceImpl implements DemoService {
    @Override
    public String test(String param) {
        return "webservice demo get param:"+param;
    }
}
