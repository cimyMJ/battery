package com.bupt.battery.task;

import com.bupt.battery.entity.ModelMonitorDO;
import com.bupt.battery.service.IModelMonitorDOService;
import com.bupt.battery.util.SpringUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 启动时调用，优先级为2，低于清理数据的优先级
 */
@Component
@Order(value = 2)
public class MonitorScaner implements ApplicationRunner  {
    private DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.print("run monitor_scaner\n");
        while (true) {
            List<ModelMonitorDO> list = SpringUtil.getBean(IModelMonitorDOService.class).findAll();
            if (list.size() > 0) {
                for(ModelMonitorDO monitorDO : list) {
                    if (fmt.format(monitorDO.getStartTime()).equals(fmt.format(new Date()))) {
                        monitorDO.setStatus("进行中");
                        SpringUtil.getBean(IModelMonitorDOService.class).update(monitorDO);
                    } else if (fmt.format(monitorDO.getEndTime()).equals(fmt.format(new Date()))) {
                        monitorDO.setStatus("已完成");
                        SpringUtil.getBean(IModelMonitorDOService.class).update(monitorDO);
                    } else if (fmt.format(monitorDO.getEndTime()).compareTo(fmt.format(new Date())) < 0) {
                        if (monitorDO.getStatus().equals("已完成") || monitorDO.getStatus().equals("进行中")) {}
                        else {
                            monitorDO.setStatus("已失败");
                            SpringUtil.getBean(IModelMonitorDOService.class).update(monitorDO);
                        }
                    }
                }
            }
            //5s执行一次
            TimeUnit.MILLISECONDS.sleep(5000);
        }
    }
}
