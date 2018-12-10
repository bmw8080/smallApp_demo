package json.body;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.List;

/**
 * author:lightClouds917
 * date:2018/1/22
 * description:业务处理
 */
public class ConCallable implements Callable {
    private List<String> list;

    @Override
    public Object call() throws Exception {
        List<String> listRe = new ArrayList();
        for(int i = 0;i < list.size();i++){
            //含有‘4599’的字符串都返回
            if(list.get(i).contains("4599")){
                listRe.add(list.get(i));
            }
        }
        return listRe;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
