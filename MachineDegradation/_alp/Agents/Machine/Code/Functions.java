double updateColor()
{/*ALCODESTART::1774539148455*/
boolean isIdle = machineOperation.isStateActive(idle);
boolean isRunning = machineOperation.isStateActive(running);
boolean isNominal = machineCondition.isStateActive(nominal);
boolean isDegraded = machineCondition.isStateActive(degraded);

if (isRunning && isNominal) {
    machineIcon.setFillColor(main.colorRunningNominal);
}
if (isRunning && isDegraded) {
    machineIcon.setFillColor(main.colorRunningDegraded);
}
if (isIdle && isNominal) {
    machineIcon.setFillColor(main.colorIdleNominal);
}
if (isIdle && isDegraded) {
    machineIcon.setFillColor(main.colorIdleDegraded);
}
/*ALCODEEND*/}

double getStartupTime()
{/*ALCODESTART::1774539312782*/
if (machineCondition.isStateActive(nominal)) {
    return main.meanStartupTimeWhenNominal;
}

if (machineCondition.isStateActive(degraded)) {
    return main.meanStartupTimeWhenDegraded;
}

// Initial state is Nominal
return main.meanStartupTimeWhenNominal;
/*ALCODEEND*/}

double getRunningTime()
{/*ALCODESTART::1774539374353*/
if (machineCondition.isStateActive(nominal)) {
    return main.meanRunningTimeWhenNominal;
}

if (machineCondition.isStateActive(degraded)) {
    return main.meanRunningTimeWhenDegraded;
}

// Initial state is Nominal
return main.meanRunningTimeWhenNominal;
/*ALCODEEND*/}

double getDegradationTime()
{/*ALCODESTART::1774539397748*/
if (machineOperation.isStateActive(idle)) {
    return main.meanDegradationTimeWhenIdle;
}

if (machineOperation.isStateActive(running)) {
    return main.meanDegradationTimeWhenRunning;
}

// Initial state is Idle
return main.meanDegradationTimeWhenIdle;
/*ALCODEEND*/}

double getRestorationTime()
{/*ALCODESTART::1774539413843*/
if (machineOperation.isStateActive(idle)) {
    return main.meanRestorationTimeWhenIdle;
}

if (machineOperation.isStateActive(running)) {
    return main.meanRestorationTimeWhenRunning;
}

// Initial state is Idle.
return main.meanRestorationTimeWhenIdle;
/*ALCODEEND*/}

boolean isIdleNominal()
{/*ALCODESTART::1774551137065*/
return machineOperation.isStateActive(idle) && machineCondition.isStateActive(nominal);
/*ALCODEEND*/}

boolean isIdleDegraded()
{/*ALCODESTART::1774551194895*/
return machineOperation.isStateActive(idle) && machineCondition.isStateActive(degraded);
/*ALCODEEND*/}

boolean isRunningNominal()
{/*ALCODESTART::1774551208681*/
return machineOperation.isStateActive(running) && machineCondition.isStateActive(nominal);
/*ALCODEEND*/}

boolean isRunningDegraded()
{/*ALCODESTART::1774551218702*/
return machineOperation.isStateActive(running) && machineCondition.isStateActive(degraded);
/*ALCODEEND*/}

