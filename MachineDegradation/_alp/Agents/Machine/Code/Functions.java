double updateColor()
{/*ALCODESTART::1774539148455*/
boolean isOff = MachineOperation.isStateActive(Off);
boolean isRunning = MachineOperation.isStateActive(Running);
boolean isGood = MachineCondition.isStateActive(Good);
boolean isDegraded = MachineCondition.isStateActive(Degraded);

if (isRunning && isGood) {
    machineIcon.setFillColor(main.colorRunningGood);
}
if (isRunning && isDegraded) {
    machineIcon.setFillColor(main.colorRunningDegraded);
}
if (isOff && isGood) {
    machineIcon.setFillColor(main.colorOffGood);
}
if (isOff && isDegraded) {
    machineIcon.setFillColor(main.colorOffDegraded);
}
/*ALCODEEND*/}

double getStartupRate()
{/*ALCODESTART::1774539312782*/
// Rate (per hour) at which a machine transitions from Off to Running.
// Degraded machines take longer to bring back online.
if (MachineCondition.isStateActive(Good)) {
    return 1.0 / 0.5;   // avg 30 minutes when in good condition
}
if (MachineCondition.isStateActive(Degraded)) {
    return 1.0 / 2.0;   // avg 2 hours when degraded
}
// MachineCondition not yet initialized; initial state is Good.
return 1.0 / 0.5;
/*ALCODEEND*/}

double getBreakdownRate()
{/*ALCODESTART::1774539374353*/
// Rate (per hour) at which a running machine transitions to Off (breakdown).
// Degraded machines break down much more frequently.
if (MachineCondition.isStateActive(Good)) {
    return 1.0 / 8.0;   // avg 8 hours before breakdown when in good condition
}
if (MachineCondition.isStateActive(Degraded)) {
    return 1.0 / 1.0;   // avg 1 hour before breakdown when degraded
}
// MachineCondition not yet initialized; initial state is Good.
return 1.0 / 8.0;
/*ALCODEEND*/}

double getMeanTimeToFailure()
{/*ALCODESTART::1774539397748*/
// Mean time (hours) for a machine to transition from Good to Degraded.
// Running machines degrade faster than idle ones.
if (MachineOperation.isStateActive(Running)) {
    return 48.0;    // avg 2 days when running
}
if (MachineOperation.isStateActive(Off)) {
    return 168.0;   // avg 1 week when idle
}
// MachineOperation not yet initialized; initial state is Off.
return 168.0;
/*ALCODEEND*/}

double getMeanTimeToRepair()
{/*ALCODESTART::1774539413843*/
// Mean time (hours) for a degraded machine to recover to Good condition.
// Off machines can be actively maintained; running ones recover slowly on their own.
if (MachineOperation.isStateActive(Off)) {
    return 1.0;     // avg 1 hour of maintenance when offline
}
if (MachineOperation.isStateActive(Running)) {
    return 72.0;    // avg 3 days of gradual recovery while running
}
// MachineOperation not yet initialized; initial state is Off.
return 1.0;
/*ALCODEEND*/}

boolean isOffGood()
{/*ALCODESTART::1774551137065*/
return MachineOperation.isStateActive(Off) && MachineCondition.isStateActive(Good);
/*ALCODEEND*/}

boolean isOffDegraded()
{/*ALCODESTART::1774551194895*/
return MachineOperation.isStateActive(Off) && MachineCondition.isStateActive(Degraded);
/*ALCODEEND*/}

boolean isRunningGood()
{/*ALCODESTART::1774551208681*/
return MachineOperation.isStateActive(Running) && MachineCondition.isStateActive(Good);
/*ALCODEEND*/}

boolean isRunningDegraded()
{/*ALCODESTART::1774551218702*/
return MachineOperation.isStateActive(Running) && MachineCondition.isStateActive(Degraded);
/*ALCODEEND*/}

