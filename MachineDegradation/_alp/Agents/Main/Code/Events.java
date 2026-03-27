void updateCounts()
{/*ALCODESTART::1774551621598*/
countIdleNominal = 0;
countIdleDegraded = 0;
countRunningNominal = 0;
countRunningDegraded = 0;

for (Machine m : machines) {
    if (m.isIdleNominal())         countIdleNominal++;
    if (m.isIdleDegraded())     countIdleDegraded++;
    if (m.isRunningNominal())     countRunningNominal++;
    if (m.isRunningDegraded()) countRunningDegraded++;
}
/*ALCODEEND*/}

