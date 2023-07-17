package com.jx530.excelimporter.scheduler;

import com.jx530.excelimporter.dao.SyncProgressDao;
import com.jx530.excelimporter.model.SyncProgress;
import com.jx530.excelimporter.model.SyncProgress.Progress;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class SyncScheduler {

    SyncProgressDao syncProgressDao;

    @SneakyThrows
    @Scheduled(fixedDelay = 1000)
    public void schedule() {
        List<SyncProgress> syncs = syncProgressDao.findByFinished(false);
        if (CollectionUtils.isEmpty(syncs)) {
            return;
        }
        List<Callable<Object>> tasks = syncs.stream()
                .map((sync) -> Executors.callable(() -> runSyncTask(sync)))
                .collect(Collectors.toList());
        try (ExecutorService es = Executors.newFixedThreadPool(tasks.size())) {
            es.invokeAll(tasks);
        }
    }

    private void runSyncTask(SyncProgress sync) {
        Progress progress = sync.getProgress();

    }

}
