void endOfDay()
{/*ALCODESTART::1774463404204*/
if (hireWorkers) {
    int maxWorkers = workerEfficiencyTable.length;

    double averageDailyWaitTime = 0.0;
    if (dailyCustomersServed > 0) {
        averageDailyWaitTime = dailyTotalWaitTime / dailyCustomersServed;
    }

    double operatingMinutes = dailyCloseTime - dailyOpenTime;
    double availableWorkerMinutes = operatingMinutes * workerCount;
    double dailyUtilization = 0.0;
    if (availableWorkerMinutes > 0) {
        dailyUtilization = Math.min(1.0, dailyWorkerBusyTime / availableWorkerMinutes);
    }

    if (averageDailyWaitTime >= hireWaitTimeThreshold && workerCount < maxWorkers) {
        workerCount++;
        workerPool.set_capacity(workerCount);
    } else if (dailyUtilization < fireUtilizationThreshold && workerCount > 1) {
        workerCount--;
        workerPool.set_capacity(workerCount);
    }
}

resetStartOfDay();
/*ALCODEEND*/}

