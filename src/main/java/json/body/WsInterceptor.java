package json.body;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.AbstractLoggingInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.io.DelegatingInputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.logging.Logger;

public class WsInterceptor extends AbstractLoggingInterceptor {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(WsInterceptor.class);
    public WsInterceptor() {
        super(Phase.RECEIVE);
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        InputStream is = message.getContent(InputStream.class);
        if(is!=null){
            CachedOutputStream bos = new CachedOutputStream();
            if (threshold > 0) {
                bos.setThreshold(threshold);
            }
            try {
                // use the appropriate input stream and restore it later
                InputStream bis = is instanceof DelegatingInputStream
                        ? ((DelegatingInputStream)is).getInputStream() : is;


                //only copy up to the limit since that's all we need to log
                //we can stream the rest
                IOUtils.copyAtLeast(bis, bos, limit == -1 ? Integer.MAX_VALUE : limit);
                bos.flush();
                bis = new SequenceInputStream(bos.getInputStream(), bis);

                // restore the delegating input stream or the input stream
                if (is instanceof DelegatingInputStream) {
                    ((DelegatingInputStream)is).setInputStream(bis);
                } else {
                    message.setContent(InputStream.class, bis);
                }

                bos.close();
            } catch (Exception e) {
                throw new Fault(e);
            }finally{
                LOGGER.info(bos.toString());
            }
        }
    }

    @Override
    protected Logger getLogger() {
        // TODO Auto-generated method stub
        return null;
    }
}
