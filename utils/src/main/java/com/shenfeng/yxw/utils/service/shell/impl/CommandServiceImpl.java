package com.shenfeng.yxw.utils.service.shell.impl;

import com.shenfeng.yxw.utils.service.shell.CommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: yangxiaowei37
 * @Date: 5/11/2021 下午3:57
 * @Version: 1.0
 * @Description:
 */
@Slf4j
@Service
public class CommandServiceImpl implements CommandService, InitializingBean {
    @Value("${cmd.threadname:cmd-executor}")
    private String threadName;

    @Value("${cmd.taskQueueMaxStorage:20}")
    private Integer taskQueueMaxStorage;

    @Value("${cmd.corePoolSize:4}")
    private Integer corePoolSize;

    @Value("${cmd.maximumPoolSize:8}")
    private Integer maximumPoolSize;

    @Value("${cmd.keepAliveSeconds:15}")
    private Integer keepAliveSeconds;
    private ThreadPoolExecutor executor;
    private static final String BASH = "sh";
    private static final String BASH_PARAM = "-c";

    // use thread pool to read streams
    @Override
    public void afterPropertiesSet() {
        executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveSeconds, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(taskQueueMaxStorage),
                new ThreadFactory() {
                    public Thread newThread(Runnable r) {
                        return new Thread(r, threadName + r.hashCode());
                    }
                },
                new ThreadPoolExecutor.AbortPolicy());
    }

    @Override
    public String executeCmd(String cmd) {
        Process p = null;
        String res;
        log.info("CommandService cmd info : {}", cmd);
        try {
            // need to pass command as bash's param,
            // so that we can compatible with commands: "echo a >> b.txt" or "bash a && bash b"
            List<String> cmds = new ArrayList<>();
            cmds.add(BASH);
            cmds.add(BASH_PARAM);
            cmds.add(cmd);
            ProcessBuilder pb = new ProcessBuilder(cmds);
            p = pb.start();

            Future<String> errorFuture = executor.submit(new ReadTask(p.getErrorStream()));
            Future<String> resFuture = executor.submit(new ReadTask(p.getInputStream()));
            int exitValue = p.waitFor();
            if (exitValue > 0) {
                log.info("exec cmd error: {} ", errorFuture.get());
                res = errorFuture.get();
                //throw new RuntimeException(errorFuture.get());
            } else {
                res = resFuture.get();
            }
        } catch (Exception e) {
            log.info("exec cmd error: {} ", e.getMessage());
            res = e.getMessage();
            //throw new RuntimeException(e);
        } finally {
            if (p != null) {
                p.destroy();
            }
        }
        // remove System.lineSeparator() (actually it's '\n') in the end of res if exists
        if (!StringUtils.isEmpty(res) && res.endsWith(System.lineSeparator())) {
            res = res.substring(0, res.lastIndexOf(System.lineSeparator()));
        }
        return res;
    }

    class ReadTask implements Callable<String> {
        InputStream is;

        ReadTask(InputStream is) {
            this.is = is;
        }

        @Override
        public String call() throws Exception {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }
    }
}

