void endOfDay()
{/*ALCODESTART::1774463404204*/
if (hireWorkers) {
    int maxWorkers = workerEfficiencyTable.length;

    double averageDailyWaitTime = (dailyCustomersServed > 0)
        ? dailyTotalWaitTime / dailyCustomersServed
        : 0.0;

    double operatingMinutes = dailyCloseTime - dailyOpenTime;
    double dailyUtilization = (operatingMinutes * workerCount > 0)
        ? Math.min(1.0, dailyWorkerBusyTime / (operatingMinutes * workerCount))
        : 0.0;

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

