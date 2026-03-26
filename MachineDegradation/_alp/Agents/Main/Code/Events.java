void updateCounts()
{/*ALCODESTART::1774551621598*/
countOffGood = 0;
countOffDegraded = 0;
countRunningGood = 0;
countRunningDegraded = 0;

for (Machine m : machines) {
    if (m.isOffGood())         countOffGood++;
    if (m.isOffDegraded())     countOffDegraded++;
    if (m.isRunningGood())     countRunningGood++;
    if (m.isRunningDegraded()) countRunningDegraded++;
}
/*ALCODEEND*/}

