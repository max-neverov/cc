package cc.persistence.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

import java.util.function.Function;

/**
 * @author Maxim Neverov
 */
public class HystrixDbCommand<T> {

    private final HystrixCommand.Setter setter;
    private static final String POOL_KEY = "DbPool";

    public HystrixDbCommand(String commandKey, String groupKey) {
        setter = HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
                .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(POOL_KEY));
    }

    public T execute(Function<HystrixCommand, T> function) {
        return new HystrixCommand<T>(setter) {
            @Override
            protected T run() throws Exception {
                return function.apply(this);
            }
        }.execute();
    }

}
