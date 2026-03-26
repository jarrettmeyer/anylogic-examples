double updateColor()
{/*ALCODESTART::1774539148455*/
boolean isRunning = MachineOperation.isStateActive(Running);
boolean isDegraded = MachineCondition.isStateActive(Degraded);

if (isRunning && !isDegraded) {
    machineIcon.setFillColor(new java.awt.Color(0xADFF2F));     // greenYellow: Running + Good
} else if (isRunning && isDegraded) {
    machineIcon.setFillColor(new java.awt.Color(0xFF4500));     // orangeRed: Running + Degraded
} else if (!isRunning && isDegraded) {
    machineIcon.setFillColor(new java.awt.Color(0xFF4500));     // orangeRed: Off + Degraded
} else {
    machineIcon.setFillColor(java.awt.Color.YELLOW);            // yellow: Off + Good
}
/*ALCODEEND*/}

double getStartupRate()
{/*ALCODESTART::1774539312782*/
// Rate (per hour) at which a machine transitions from Off to Running.
// Degraded machines take longer to bring back online.
if (MachineCondition.isStateActive(Good)) {
    return 1.0 / 0.5;   // avg 30 minutes when in good condition
} else {
    return 1.0 / 2.0;   // avg 2 hours when degraded
}
/*ALCODEEND*/}

double getBreakdownRate()
{/*ALCODESTART::1774539374353*/
// Rate (per hour) at which a running machine transitions to Off (breakdown).
// Degraded machines break down much more frequently.
if (MachineCondition.isStateActive(Good)) {
    return 1.0 / 8.0;   // avg 8 hours before breakdown when in good condition
} else {
    return 1.0 / 1.0;   // avg 1 hour before breakdown when degraded
}
/*ALCODEEND*/}

double getMeanTimeToFailure()
{/*ALCODESTART::1774539397748*/
// Mean time (hours) for a machine to transition from Good to Degraded.
// Running machines degrade faster than idle ones.
if (MachineOperation.isStateActive(Running)) {
    return 48.0;    // avg 2 days when running
} else {
    return 168.0;   // avg 1 week when idle
}
/*ALCODEEND*/}

double getMeanTimeToRepair()
{/*ALCODESTART::1774539413843*/
// Mean time (hours) for a degraded machine to recover to Good condition.
// Off machines can be actively maintained; running ones recover slowly on their own.
if (MachineOperation.isStateActive(Off)) {
    return 1.0;     // avg 1 hour of maintenance when offline
} else {
    return 72.0;    // avg 3 days of gradual recovery while running
}
/*ALCODEEND*/}

